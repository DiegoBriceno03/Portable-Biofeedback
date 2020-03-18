package com.example.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

// Contains utilities useful for all other aspects of this app. Most useful
// is the toast() function it comes with
public class Utils
{
    public final static int REQUEST_ENABLE_BT = 1;
    public final static int ACCESS_LOCATION_REQUEST = 2;
    public final static int BLE_SERVICES = 3;

//    public final static String ACTION_GATT_CONNECTED = "";
//    public final static String ACTION_GATT_DISCONNECTED = "";
//    public final static String ACTION_GATT_SERVICES_DISCONNECTED = "";
//    public final static String ACTION_DATA_AVAILABLE = "";

    // Ensures that Bluetooth is available on device and is enabled. If not,
    // displays a dialog requesting user permission to enable Bluetooth.
    public static boolean checkBluetooth(BluetoothAdapter bluetoothAdapter)
    {
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled())
            return false;
        else
            return true;
    }

    // Launches an activity towards enabling a Bluetooth activity
    public static void requestUserBluetooth(Activity activity)
    {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    // Matches against actions, categories, and data in an Intent.
    // This specific function
//    public static IntentFilter makeGattUpdateIntentFilter()
//    {
//        final IntentFilter intentFilter = new IntentFilter();
//
//        // Giving errors cause, in the original proj, Service_BTLE_GATT
//        // is a Java class found in the 'ble' folder
//        intentFilter.addAction(ACTION_GATT_CONNECTED);
//        intentFilter.addAction(ACTION_GATT_DISCONNECTED);
//        intentFilter.addAction(ACTION_GATT_SERVICES_DISCOVERED);
//        intentFilter.addAction(ACTION_DATA_AVAILABLE);
//
//        return intentFilter;
//    }

    public static String hexToString(byte[] data)
    {
        final StringBuilder sb = new StringBuilder(data.length);

        for (byte byteChar : data)
            sb.append(String.format("%02X ", byteChar));

        return sb.toString();
    }

    public static int hasWriteProperty(int property)
    {
        return property & BluetoothGattCharacteristic.PROPERTY_WRITE;
    }

    public static int hasReadProperty(int property)
    {
        return property & BluetoothGattCharacteristic.PROPERTY_READ;
    }

    public static int hasNotifyProperty(int property)
    {
        return property & BluetoothGattCharacteristic.PROPERTY_NOTIFY;
    }

    // A 'toast' is a view containing quick little messages for the user. This
    // class helps create and show these
    // URL: https://developer.android.com/reference/android/widget/Toast
    //      Appears as a floating view over the app; will never receive focus.
    //      Idea is to be as unobtrusive as possible, while still showing user
    //      the info you want them to see
    public static void toast(Context context, String string)
    {
        Toast toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        toast.show();
    }
}
