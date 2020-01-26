package com.example.ble;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.biofeedbackstress.R;
import com.example.utils.Utils;

import java.util.List;
import java.util.UUID;

// Service for managing connection and data communication with a GATT server
// hosted on a given Bluetooth LE device
public class Service_BTLE_GATT extends Service
{
    private final static String TAG = Service_BTLE_GATT.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    private int mConnectionState = STATE_DISCONNECTED;

    // Fix all of these
    public final static String ACTION_GATT_CONNECTED = "";
    public final static String ACTION_GATT_DISCONNECTED = "";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "";
    public final static String ACTION_DATA_AVAILABLE = "";
    public final static String EXTRA_UUID = "";
    public final static String EXTRA_DATA = "";

    // Callback methods for GATT events, such as connection change and
    // service discovery

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback()
    {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState)
        {
            String intentAction;

            if (newState == BluetoothProfile.STATE_CONNECTED)
            {
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;

                broadcastUpdate(intentAction);

                Log.i(TAG, "Connected to GATT server.");
                Log.i(TAG, "Attempting to start service discovery:" + mBluetoothGatt.discoverServices());
            }
            else if (newState == BluetoothProfile.STATE_DISCONNECTED)
            {
                intentAction = ACTION_GATT_DISCONNECTED;mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server");
                broadcastUpdate(intentAction);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status)
        {
            if (status == BluetoothGatt.GATT_SUCCESS)
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            else
                Log.w(TAG, "onServicesDiscovered received: " + status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status)
        {
            if (status == BluetoothGatt.GATT_SUCCESS)
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
        {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    };

    private void broadcastUpdate(final String action)
    {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic)
    {
        final Intent intent = new Intent(action);

        intent.putExtra(EXTRA_UUID, characteristic.getUuid().toString());

        // For all other profiles, writes data formatted in HEX
        final byte[] data = characteristic.getValue();

        if (data != null && data.length > 0)
            intent.putExtra(EXTRA_DATA, new String(data) + "\n" + Utils.hexToString(data));
        else
            intent.putExtra(EXTRA_DATA, "0");

        sendBroadcast(intent);
    }

    public class BTLeServiceBinder extends Binder
    {
        Service_BTLE_GATT getService()
        {
            return Service_BTLE_GATT.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    @Override
    public void onCreate()
    {}

    @Override
    public boolean onUnbind(Intent intent)
    {
        // After using a given device, ensures that BluetoothGatt.close()
        // is called such that resources are cleared up properly
        close();

        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new BTLeServiceBinder();

    // Initializes a reference to local Bluetooth adapter
    // @return Return true if initialization is successful
    public boolean initialize()
    {
        if (mBluetoothManager == null)
        {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null)
            {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if (mBluetoothAdapter == null)
        {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    // Connects to GATT server hosted on Bluetooth LE device
    // @param address The device address of the destination device.
    // @return Return true if connection is initiated successfully. Connection result
    //          is reported asynchronously through the
    //          {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
    //          callback
    public boolean connect(final String address)
    {
        if (mBluetoothAdapter == null || address == null)
        {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device. Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress) && mBluetoothGatt != null)
        {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");

            if (mBluetoothGatt.connect())
            {
                mConnectionState = STATE_CONNECTING;
                return true;
            }
            else
            {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

        if (device == null)
        {
            Log.w(TAG, "Device not found. Unable to connect.");
            return false;
        }

        // Attempt to directly connect to device, so setting autoConnect
        // param to false
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;

        return true;
    }

    // DC's an existing connection or cancel a pending connection. The DC
    // is reported asynchronously through the
    // {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
    // callback
    public void disconnect()
    {
        if (mBluetoothAdapter == null || mBluetoothGatt == null)
        {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }

        mBluetoothGatt.disconnect();
    }

    // After using a given BLE device, app must call method to ensure
    // resources are properly released.
    public void close()
    {
        if (mBluetoothGatt == null)
            return;

        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    // Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
    // asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead()}
    // callback
    //
    // @param characteristic The characteristic to read from
    //
    public void readCharacteristic(BluetoothGattCharacteristic characteristic)
    {
        if (mBluetoothAdapter == null || mBluetoothGatt == null)
        {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }

        mBluetoothGatt.readCharacteristic(characteristic);
    }

    // Request a write on a given {@code BluetoothGattCharacteristic}. Write result is
    // reported asynchronously through the {@code BluetoothGattCallback#onCharacteristicWrite()}
    // callback
    //
    // @param characteristic The characteristic to read from.
    //
    public void writeCharacteristic(BluetoothGattCharacteristic characteristic)
    {
        if (mBluetoothAdapter == null || mBluetoothGatt == null)
        {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }

        mBluetoothGatt.writeCharacteristic(characteristic);
    }

    // Enables or disables notifications on a given characteristic
    //
    // @param characteristic Characteristic to act on
    // @param enabled If true, enable notification. False otherwise
    //
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled)
    {
        if (mBluetoothAdapter == null || mBluetoothGatt == null)
        {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }

        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                UUID.fromString(getString(R.string.CLIENT_CHARACTERISTIC_CONFIG)));

        if (enabled)
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        else
            descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);

        mBluetoothGatt.writeDescriptor(descriptor);
    }

    // Retrieves a list of supported GATT services on connected device. This should be
    // invoked only after {@code BluetoothGatt#discoverServices()} completes successfully
    //
    // @return A {@code List} of supported services
    //
    public List<BluetoothGattService> getSupportedGattServices()
    {
        if (mBluetoothGatt == null)
            return null;

        return mBluetoothGatt.getServices();
    }
}
