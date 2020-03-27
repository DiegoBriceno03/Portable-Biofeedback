package com.example.biofeedbackstress;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ble.*;
import com.example.utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import timber.log.Timber;

import static com.example.utils.Utils.ACCESS_LOCATION_REQUEST;

public class MainActivity extends AppCompatActivity
{
    private final String TAG = MainActivity.class.getSimpleName();

    // Sensor values
    private TextView heartRateValue;
    private TextView temperatureValue;
    private TextView gsrValue;

    // Files
    private File rootFolder;
    private File jsonHRFile;
    private File jsonTempFile;
    private File jsonGSRFile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate() called");
        Timber.plant(new Timber.DebugTree());

        setContentView(R.layout.activity_main);
        heartRateValue = (TextView) findViewById(R.id.heartRateValue);
        temperatureValue = (TextView) findViewById(R.id.temperatureValue);
        gsrValue = (TextView) findViewById(R.id.gsrValue);

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

        rootFolder = this.getExternalFilesDir(null);

        jsonHRFile = new File(rootFolder, HeartRate_Measurement.fileHR);
        jsonTempFile = new File(rootFolder, Temperature_Measurement.tempFile);
        jsonGSRFile = new File(rootFolder, GSR_Measurement.gsrFile);
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

            try
            {
                saveData(String.format("{\"time\" : \"%s\", \"beat\" : %d}\n", ts.toString(), measurement.pulse), jsonHRFile);
            } catch (IOException e)
            {
                Timber.e(e);
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

            try
            {
                saveData(String.format("{\"time\" : \"%s\", \"temperature\" : %s}\n", ts.toString(), measurement.temperature), jsonTempFile);
            } catch (IOException e)
            {
                Timber.e(e);
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

            try
            {
                saveData(String.format("{\"time\" : \"%s\", \"conductance\" : %d}\n", ts.toString(), measurement.conduct), jsonGSRFile);
            } catch (IOException e)
            {
                Timber.e(e);
            }

            gsrValue.setText(String.format(Locale.ENGLISH, "%d", measurement.conduct));
        }
    };

    // Activity will cause app to ask User for permission to allow Bluetooth access
    private boolean hasPermissions()
    {
        int targetSdkVersion = getApplicationInfo().targetSdkVersion;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && targetSdkVersion >= Build.VERSION_CODES.Q)
        {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    // Will this work?
    public void saveData(String data, File jsonFileName) throws IOException
    {
        FileWriter writer = new FileWriter(jsonFileName, true);
        writer.write(data);
        writer.close();
    }
}
