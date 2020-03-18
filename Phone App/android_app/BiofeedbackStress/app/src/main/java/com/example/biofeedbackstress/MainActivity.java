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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import timber.log.Timber;

import static com.example.utils.Utils.ACCESS_LOCATION_REQUEST;

// ===================
//      TEST UUID
// ===================
// Test Value Service: 96ef0ec6-b0a7-4ffa-9e49-74c2d24d5371
// TEST_CHARACTERISTIC_UUID: dda9a9d0-2de0-4da8-84eb-2c9d15d6407c

public class MainActivity extends AppCompatActivity
{
    private final String TAG = MainActivity.class.getSimpleName();
    private TextView measurementValue;

    // These are for the actual sensor stuff
    private TextView heartRateValue;
    private TextView temperatureValue;
    private TextView gsrValue;

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
    }

    // Isn't this where the BLE_Handler is initialized?
    private void initBluetoothHandler()
    {
        BLE_Handler.getInstance(getApplicationContext());

        // The action that's being filtered should be matching with what is stated within the
        // BLE_Handler class
        //registerReceiver(esp32DataReceiver, new IntentFilter("TestMeasurement"));
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
        //unregisterReceiver(esp32DataReceiver);
    }

    private final BroadcastReceiver heartRateDataReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            HeartRate_Measurement measurement = (HeartRate_Measurement) intent.getSerializableExtra("HeartRateVal");

            // This is for testing purposes
//            heartRateValue.setText(String.format(Locale.ENGLISH, "%d bpm", measurement.pulse));

            // This is the actual one to be used
            heartRateValue.setText(String.format(Locale.ENGLISH, "%.2f bpm", measurement.pulse));
        }
    };

    private final BroadcastReceiver temperatureDataReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Temperature_Measurement measurement = (Temperature_Measurement) intent.getSerializableExtra("TempVal");
            temperatureValue.setText(String.format(Locale.ENGLISH, "%.2f F", measurement.temperature));
        }
    };

    private final BroadcastReceiver gsrDataReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            GSR_Measurement measurement = (GSR_Measurement) intent.getSerializableExtra("GSRVal");
            gsrValue.setText(String.format(Locale.ENGLISH, "%d", measurement.conduct));
        }
    };

//    private final BroadcastReceiver esp32DataReceiver = new BroadcastReceiver()
//    {
//        // What to do with received data
//        @Override
//        public void onReceive(Context context, Intent intent)
//        {
//            TestMeasurement measurement = (TestMeasurement) intent.getSerializableExtra("TestVal");
////            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
////            String formattedTimestamp = df.format(measurement.timestamp);
//            measurementValue.setText(String.format(Locale.ENGLISH, "%d", measurement.count));
//        }
//    };

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
}
