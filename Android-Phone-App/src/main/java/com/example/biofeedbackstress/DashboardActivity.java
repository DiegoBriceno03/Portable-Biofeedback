package com.example.biofeedbackstress;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.ble.*;
import com.example.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

import static com.example.utils.Utils.ACCESS_LOCATION_REQUEST;
import static com.example.utils.Utils.saveData;

public class DashboardActivity extends AppCompatActivity
{
    private static final int RESET_SENSITIVITY = 10;

    private final String TAG = DashboardActivity.class.getSimpleName();
    private final String pathCalibrationData = "/calibrationData";

    private Button calibrateBtn;
    private boolean calibrate = false;
    private boolean compare = false;

    // Sensor values
    private TextView heartRateValue;
    private TextView temperatureValue;
    private TextView gsrValue;

    // Files
    private File jsonDataHRFile;
    private File jsonDataTempFile;
    private File jsonDataGSRFile;
    private File jsonCalibrateHRFile;
    private File jsonCalibrateTempFile;
    private File jsonCalibrateGSRFile;

    private File jsonCalibration;

    public static File alerts;

    // Form part of the "strike" system
    private static int heartStrikes = 0;
    private static int heartStrikeCounter = 0;
    private static int temperatureStrikes = 0;
    private static int temperatureStrikeCounter = 0;
    private static int gsrStrikes = 0;
    private static int gsrStrikeCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate() called");
        Timber.plant(new Timber.DebugTree());

        // Associated with some of the minor things being displayed on the app
        TextView date = findViewById(R.id.date_today);

        calibrateBtn = findViewById(R.id.calibration_btn);

        BottomNavigationView bottomNavBar = findViewById(R.id.bottom_navigation);

        // Associated with the data being received from the BLE device
        heartRateValue = findViewById(R.id.heartRateValue);
        temperatureValue = findViewById(R.id.temperatureValue);
        gsrValue = findViewById(R.id.gsrValue);

        Utils.setDate(date);

        // Completely flesh out this
        calibrateBtn.setOnClickListener(calibrateListener);

        bottomNavBar.setOnNavigationItemSelectedListener(navListener);

        createNotificationChannel();

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null)
            return;

        if (!bluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, Utils.REQUEST_ENABLE_BT);
        }

        if (hasPermissions())
            initBluetoothHandler();

        initFiles();
    }

    private void initBluetoothHandler()
    {
        BLE_Handler.getInstance(getApplicationContext());

        // The action that's being filtered should be matching with what is stated within the
        // BLE_Handler class
        registerReceiver(heartRateDataReceiver, new IntentFilter("HeartRateMeasurement"));
        registerReceiver(temperatureDataReceiver, new IntentFilter("TemperatureMeasurement"));
        registerReceiver(gsrDataReceiver, new IntentFilter("GSRMeasurement"));
    }

    // Initializes the file hierarchy to store the incoming data being received from the BLE device
    private void initFiles()
    {
        File rootFolder = this.getExternalFilesDir(null);

        File dataFolder = new File(rootFolder + "/data");
        File calibrateFolder = new File(rootFolder + pathCalibrationData);
        File alertFolder = new File(rootFolder + "/alerts");

        if (!dataFolder.exists() && !dataFolder.isDirectory())
            dataFolder.mkdir();

        if (!calibrateFolder.exists() && !calibrateFolder.isDirectory())
            calibrateFolder.mkdir();

        if (!alertFolder.exists() && !alertFolder.isDirectory())
            alertFolder.mkdir();

        jsonDataHRFile = new File(dataFolder, HeartRate_Measurement.fileHR);
        jsonDataTempFile = new File(dataFolder, Temperature_Measurement.tempFile);
        jsonDataGSRFile = new File(dataFolder, GSR_Measurement.gsrFile);

        jsonCalibrateHRFile = new File(calibrateFolder, HeartRate_Measurement.calibrateFileHR);
        jsonCalibrateTempFile = new File(calibrateFolder, Temperature_Measurement.calibrateTempFile);
        jsonCalibrateGSRFile = new File(calibrateFolder, GSR_Measurement.calibrateGSRFile);

        jsonCalibration = new File(calibrateFolder, "calibration-data.json");

        alerts = new File(alertFolder, "alerts.log");

        if (jsonCalibration.exists())
            compare = true;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        unregisterReceiver(heartRateDataReceiver);
        unregisterReceiver(temperatureDataReceiver);
        unregisterReceiver(gsrDataReceiver);
    }

    private final BroadcastReceiver heartRateDataReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            HeartRate_Measurement measurement = (HeartRate_Measurement) intent.getSerializableExtra("HeartRateVal");

            Timestamp ts = new Timestamp(System.currentTimeMillis());
            String data = String.format(Locale.ENGLISH, "{\"time\" : \"%s\", \"beat\" : %d}\n", ts.toString(), measurement.pulse);

            try
            {
                if (calibrate)
                    Utils.saveData(data, jsonCalibrateHRFile);
                else
                    Utils.saveData(data, jsonDataHRFile);
            } catch (IOException e)
            {
                Timber.e(e);
            }

            if (compare)
            {
                try
                {
                    JSONObject obj = new JSONObject(obtainJSONObject());
                    JSONArray arr = obj.getJSONArray("heart");

                    int low = arr.getInt(0);
                    int high = arr.getInt(1);

                    if ((measurement.pulse < low) || (measurement.pulse > high))
                    {
                        heartStrikes++;

                        if (heartStrikes > 3)
                        {
                            heartStrikes = 0;
                            warnUser("Heart Rate");
                        }

                        if (heartStrikeCounter >= RESET_SENSITIVITY)
                        {
                            heartStrikeCounter = 0;
                            heartStrikes = 0;
                        }

                        if (heartStrikes < 3)
                            heartStrikeCounter++;
                    }
                } catch (JSONException e)
                {
                    Timber.e(e);
                }
            }

            heartRateValue.setText(String.format(Locale.ENGLISH, "%d bpm", measurement.pulse));
        }
    };

    private final BroadcastReceiver temperatureDataReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Temperature_Measurement measurement = (Temperature_Measurement) intent.getSerializableExtra("TempVal");

            Timestamp ts = new Timestamp(System.currentTimeMillis());
            String data = String.format(Locale.ENGLISH, "{\"time\" : \"%s\", \"temperature\" : %s}\n", ts.toString(), measurement.temperature);

            try
            {
                if (calibrate)
                    Utils.saveData(data, jsonCalibrateTempFile);
                else
                    Utils.saveData(data, jsonDataTempFile);
            } catch (IOException e)
            {
                Timber.e(e);
            }

            if (compare)
            {
                try
                {
                    JSONObject obj = new JSONObject(obtainJSONObject());
                    JSONArray arr = obj.getJSONArray("temperature");

                    double low = arr.getDouble(0);
                    double high = arr.getDouble(1);
                    double temp = Double.parseDouble(measurement.temperature);

                    if ((temp < low) || (temp > high))
                    {
                        temperatureStrikes++;

                        if (temperatureStrikes > 3)
                        {
                            temperatureStrikes = 0;
                            warnUser("Temperature");
                        }

                        if (temperatureStrikeCounter >= RESET_SENSITIVITY)
                        {
                            temperatureStrikeCounter = 0;
                            temperatureStrikes = 0;
                        }

                        if (temperatureStrikes < 3)
                            temperatureStrikeCounter++;
                    }
                } catch (JSONException e)
                {
                    Timber.e(e);
                }
            }

            temperatureValue.setText(String.format(Locale.ENGLISH, "%s F", measurement.temperature));
        }
    };

    private final BroadcastReceiver gsrDataReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            GSR_Measurement measurement = (GSR_Measurement) intent.getSerializableExtra("GSRVal");

            Timestamp ts = new Timestamp(System.currentTimeMillis());
            String data = String.format(Locale.ENGLISH, "{\"time\" : \"%s\", \"conductance\" : %d}\n", ts.toString(), measurement.conduct);

            try
            {
                if (calibrate)
                    Utils.saveData(data, jsonCalibrateGSRFile);
                else
                    Utils.saveData(data, jsonDataGSRFile);
            } catch (IOException e)
            {
                Timber.e(e);
            }

            if (compare)
            {
                try
                {
                    JSONObject obj = new JSONObject(obtainJSONObject());
                    JSONArray arr = obj.getJSONArray("conductance");

                    int low = arr.getInt(0);
                    int high = arr.getInt(1);

                    if ((measurement.conduct < low) || (measurement.conduct > high))
                    {
                        gsrStrikes++;

                        if (gsrStrikes > 3)
                        {
                            gsrStrikes = 0;
                            warnUser("Conductance");
                        }

                        if (gsrStrikeCounter >= RESET_SENSITIVITY)
                        {
                            gsrStrikeCounter = 0;
                            gsrStrikes = 0;
                        }

                        if (gsrStrikes < 3)
                            gsrStrikeCounter++;
                    }
                } catch (JSONException e)
                {
                    Timber.e(e);
                }
            }

            gsrValue.setText(String.format(Locale.ENGLISH, "%d ohms", measurement.conduct));
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener()
            {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item)
                {
                    Intent intent;

                    if (item.getItemId() == R.id.nav_notifications)
                    {
                        intent = new Intent(getApplicationContext(), NotificationsActivity.class);
                        startActivity(intent);
                        return true;
                    }

                    return false;
                }
            };

    private View.OnClickListener calibrateListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            calibrate = !calibrate;

            heartStrikes = 0;
            heartStrikeCounter = 0;
            temperatureStrikes = 0;
            temperatureStrikeCounter = 0;
            gsrStrikes = 0;
            gsrStrikeCounter = 0;

            // Begins calibration
            if (calibrate)
            {
                compare = false;
                calibrateBtn.setText(R.string.endCalibrateBtn);
                Utils.toast(getApplicationContext(), "Calibration started");

                // Clear all previous calibration results for a clean one
                if (jsonCalibrateHRFile.exists())
                    jsonCalibrateHRFile.delete();
                if (jsonCalibrateTempFile.exists())
                    jsonCalibrateTempFile.delete();
                if (jsonCalibrateGSRFile.exists())
                    jsonCalibrateGSRFile.delete();
            }
            // Ends the calibration
            else
            {
                calibrateBtn.setText(R.string.startCalibrateBtn);
                Utils.toast(getApplicationContext(), "Calibration ended");
                calibrateData();
                compare = true;
            }
        }
    };

    // Data calibration occurs by obtaining the average and standard deviation of each
    // data set that has been collected during the calibration period.
    // The average and average +/- std dev will act as the bounds that will be compared
    // against before a notification is pushed alerting the user of increased stress
    private void calibrateData()
    {
        ArrayList<Integer> heartRate = new ArrayList<>();
        ArrayList<Double> temperature = new ArrayList<>();
        ArrayList<Integer> gsr = new ArrayList<>();
        ArrayList<File> files = new ArrayList<>();

        int heartAvg = 0; int stdDevHeart; double varHeart = 0;
        double tempAvg = 0; double stdDevTemp; double varTemp = 0;
        int gsrAvg = 0; int stdDevGSR; double varGSR = 0;

        BufferedReader buffRead;
        FileReader fileRead;

        String data;

        files.add(jsonCalibrateHRFile);
        files.add(jsonCalibrateTempFile);
        files.add(jsonCalibrateGSRFile);

        for (int i = 0; i < 3; i++)
        {
            try
            {
                fileRead = new FileReader(files.get(i));
                buffRead = new BufferedReader(fileRead);
                String line = buffRead.readLine();

                while (line != null)
                {
                    try
                    {
                        JSONObject obj = new JSONObject(line);

                        switch (i)
                        {
                            // Handles Heart Rate data
                            case 0:
                                heartRate.add(obj.getInt("beat"));
                                break;
                            // Handles Temperature data
                            case 1:
                                temperature.add(obj.getDouble("temperature"));
                                break;
                            // Handles GSR data
                            case 2:
                                gsr.add(obj.getInt("conductance"));
                                break;
                            default:
                                break;
                        }
                    } catch (JSONException e)
                    {
                        Timber.e(e);
                    }

                    line = buffRead.readLine();
                }
            } catch (IOException e)
            {
                Timber.e(e);
            }
        }

        // Obtaining averages
        for (int val : heartRate)
            heartAvg += val;

        for (double val : temperature)
            tempAvg += val;

        for (int val : gsr)
            gsrAvg += val;

        heartAvg /= heartRate.size();
        tempAvg /= temperature.size();
        gsrAvg /= gsr.size();

        // Calculating the variance of the data
        for (int val : heartRate)
            varHeart += Math.pow((heartAvg - val), 2);

        for (double val : temperature)
            varTemp += Math.pow((tempAvg - val), 2);

        for (int val : gsr)
            varGSR += Math.pow((gsrAvg - val), 2);

        varHeart /= heartRate.size();
        varTemp /= temperature.size();
        varGSR /= gsr.size();

        // Obtaining standard deviation
        stdDevHeart = (int) Math.sqrt(varHeart);
        stdDevTemp = Math.sqrt(varTemp);
        stdDevGSR = (int) Math.sqrt(varGSR);

        data = String.format(Locale.ENGLISH, "{\n\t\"heart\" : [%d, %d],\n", heartAvg - stdDevHeart, heartAvg + stdDevHeart);
        data += String.format(Locale.ENGLISH, "\t\"temperature\" : [%.2f, %.2f],\n", tempAvg - stdDevTemp, tempAvg + stdDevTemp);
        data += String.format(Locale.ENGLISH, "\t\"conductance\" : [%d, %d]\n}", gsrAvg - stdDevGSR, gsrAvg + stdDevGSR);

        try
        {
            if (jsonCalibration.exists())
                jsonCalibration.delete();

            Utils.saveData(data, jsonCalibration);
        } catch (IOException e)
        {
            Timber.e(e);
        }
    }

    // Activity will cause app to ask User for permission to allow Bluetooth access
    private boolean hasPermissions()
    {
        int targetSdkVersion = getApplicationInfo().targetSdkVersion;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && targetSdkVersion >= Build.VERSION_CODES.Q)
        {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST);
                return false;
            }
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_LOCATION_REQUEST);
                return false;
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case ACCESS_LOCATION_REQUEST:
                if (grantResults.length > 0)
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        initBluetoothHandler();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    // Notifies the user of what triggered an alert by pushing out a notification
    private void warnUser(String src)
    {
        // Creating the timestamp
        SimpleDateFormat dateForm = new SimpleDateFormat("h:mm:ss a", Locale.ENGLISH);
        String formattedDate = dateForm.format(new Date());

        // Creating timestamped message to pass along in the intent
        String warning = String.format(Locale.ENGLISH, "%s\t Be mindful of your %s!\n", formattedDate, src);

        // Saves the timestamped warning to alerts.log
        try
        {
            Utils.saveData(warning, alerts);
        } catch (IOException e)
        {
            Timber.e(e);
        }

        // Creating the intent that will alert the user
        Intent warnIntent = new Intent(getApplicationContext(), NotificationsActivity.class);
        warnIntent.putExtra("WARN", warning);
        //warnIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentWarnIntent = PendingIntent.getActivity(getApplicationContext(), 0, warnIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification
        NotificationCompat.Builder warn = new NotificationCompat.Builder(this, "StressAlert")
                .setSmallIcon(R.drawable.icon_warning)
                .setContentTitle("Stress Detected")
                .setContentText(src + " seems to be off")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentWarnIntent)
                .setAutoCancel(true);

        // Adding the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(0, warn.build());
    }

    // This is necessary for Android 8 and above
    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("StressAlert", name, importance);
            channel.setDescription(description);

            // Registering the channel
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private String obtainJSONObject()
    {
        BufferedReader buffRead;
        FileReader fileRead;

        String currentLine;
        StringBuilder content = new StringBuilder();

        try
        {
            fileRead = new FileReader(jsonCalibration);
            buffRead = new BufferedReader(fileRead);
            currentLine = buffRead.readLine();

            while (currentLine != null)
            {
                content.append(currentLine).append("\n");
                currentLine = buffRead.readLine();
            }
        } catch (IOException e)
        {
            Timber.e(e);
        }

        return content.toString();
    }
}
