package com.example.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;

import com.example.biofeedbackstress.MainActivity;
import com.example.utils.Utils;

public class Scanner_BTLE
{
    private MainActivity ma;

    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;

    private long scanPeriod;
    private int signalStrength;

    public Scanner_BTLE(MainActivity mainActivity, long scanPeriod, int signalStrength)
    {
        ma = mainActivity;

        mHandler = new Handler();

        this.scanPeriod = scanPeriod;
        this.signalStrength = signalStrength;

        final BluetoothManager bluetoothManager =
                (BluetoothManager) ma.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    public boolean ismScanning()
    {
        return mScanning;
    }

    public void start()
    {
        if (!Utils.checkBluetooth(mBluetoothAdapter))
        {
            Utils.requestUserBluetooth(ma);
            ma.stopScan();
        }
        else
        {
            scanLeDevice(true);
        }
    }

    public void stop()
    {
        scanLeDevice(false);
    }

    // Scans for BLE devices
    private void scanLeDevice(final boolean enable)
    {
        if (enable && !mScanning)
        {
            Utils.toast(ma.getApplicationContext(), "Starting BLE scan...");

            // Stops the scan after pre-defined period elapses
            mHandler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    Utils.toast(ma.getApplicationContext(), "Stopping BLE scan...");

                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);

                    ma.stopScan();
                }
            }, scanPeriod);
        }
        else
        {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    // Device scan callback
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback()
            {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord)
                {
                    final int new_rssi = rssi;

                    if (rssi > signalStrength)
                    {
                        mHandler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                ma.addDevice(device, new_rssi);
                            }
                        });
                    }
                }
            };
}
