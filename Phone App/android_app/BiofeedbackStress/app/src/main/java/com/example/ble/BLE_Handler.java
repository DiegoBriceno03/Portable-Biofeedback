package com.example.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.example.utils.Utils;
import com.welie.blessed.BluetoothBytesParser;
import com.welie.blessed.BluetoothCentral;
import com.welie.blessed.BluetoothCentralCallback;
import com.welie.blessed.BluetoothPeripheral;
import com.welie.blessed.BluetoothPeripheralCallback;

import java.util.UUID;

import timber.log.Timber;

import static android.bluetooth.BluetoothGatt.CONNECTION_PRIORITY_HIGH;
import static android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE;
import static android.bluetooth.BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT;
import static com.welie.blessed.BluetoothBytesParser.FORMAT_UINT16;
import static com.welie.blessed.BluetoothBytesParser.FORMAT_UINT8;
import static com.welie.blessed.BluetoothBytesParser.bytes2String;
import static com.welie.blessed.BluetoothPeripheral.GATT_SUCCESS;
import static com.welie.blessed.BluetoothBytesParser.bytes2String;

// Handles connection and communication with Bluetooth peripherals
public class BLE_Handler
{
    // List of UUIDs for both services and characteristics are listed below:

    // UUID for unique Senior Design Service (SDS)
     private static final UUID SDS_SERVICE_UUID = UUID.fromString("63228c99-ee20-4dcf-a90a-711948af59e0");

    // Characteristic UUIDs found within the SDS
//     private static final UUID BATTERY_LEVEL_CHARACTERISTIC_UUID = UUID.fromString("00002A19-0000-1000-8000-00805f9b34fb");
     private static final UUID HEARTRATE_MEASUREMENT_CHARACTERISTIC_UUID = UUID.fromString("00002A37-0000-1000-8000-00805f9b34fb");
     private static final UUID TEMPERATURE_MEASUREMENT_CHARACTERISTIC_UUID = UUID.fromString("00002A1C-0000-1000-8000-00805f9b34fb");
     private static final UUID GSR_MEASUREMENT_CHARACTERISTIC_UUID = UUID.fromString("0d823996-e5a7-4ce3-9ae3-6649d46d8f85");

    private static final String MY_ESP32 = "Group 11 BLE";

    private BluetoothCentral bleCentral;
    private static BLE_Handler instance = null;
    private Context context;
    private Handler handler = new Handler();
    private int currentTimeCounter = 0;

    // Callback for peripherals. Provides set of functions that execute depending on what occurs
    // with BluetoothPeripherals
    private final BluetoothPeripheralCallback peripheralCallback = new BluetoothPeripheralCallback()
    {
        // Callback invoked when new services have been discovered
        @Override
        public void onServicesDiscovered(BluetoothPeripheral peripheral)
        {
            Utils.toast(context, "Discovered services");

            // Request new connection priority
            peripheral.requestConnectionPriority(CONNECTION_PRIORITY_HIGH);

            // Hopefully this fucking work
            if (peripheral.getService(SDS_SERVICE_UUID) != null)
            {
                if (peripheral.getCharacteristic(SDS_SERVICE_UUID, HEARTRATE_MEASUREMENT_CHARACTERISTIC_UUID) != null)
                {
                    peripheral.setNotify(peripheral.getCharacteristic(SDS_SERVICE_UUID, HEARTRATE_MEASUREMENT_CHARACTERISTIC_UUID), true);
                    peripheral.setNotify(peripheral.getCharacteristic(SDS_SERVICE_UUID, TEMPERATURE_MEASUREMENT_CHARACTERISTIC_UUID), true);
                    peripheral.setNotify(peripheral.getCharacteristic(SDS_SERVICE_UUID, GSR_MEASUREMENT_CHARACTERISTIC_UUID), true);
                }
            }
        }

        // Callback invoked when the notification state of a characteristic has changed
        @Override
        public void onNotificationStateUpdate(BluetoothPeripheral peripheral, BluetoothGattCharacteristic characteristic, int status)
        {
            if (status == GATT_SUCCESS)
            {
                if (peripheral.isNotifying(characteristic))
                    Timber.i("SUCCESS: Notify set to 'on' for %s", characteristic.getUuid());
                else
                    Timber.i("SUCCESS: Notify set to 'off' for %s", characteristic.getUuid());
            }
            else
            {
                Timber.e("ERROR: Changing notification state failed for %s", characteristic.getUuid());
            }
        }

        // Callback invoked that indicates the result of a characteristic write operation
        @Override
        public void onCharacteristicWrite(BluetoothPeripheral peripheral, byte[] value, BluetoothGattCharacteristic characteristic, int status)
        {
            if (status == GATT_SUCCESS)
                Timber.i("SUCCESS: Writing <%s> to <%s>", bytes2String(value), characteristic.getUuid().toString());
            else
                Timber.i("ERROR: Failed writing <%s> to <%s>", bytes2String(value), characteristic.getUuid().toString());
        }

        // Callback invoked as the result of a characteristic read operation or notification
        @Override
        public void onCharacteristicUpdate(BluetoothPeripheral peripheral, byte[] value, BluetoothGattCharacteristic characteristic, int status)
        {
            if (status != GATT_SUCCESS)
                return;

            UUID characteristicUUID = characteristic.getUuid();
            BluetoothBytesParser parser = new BluetoothBytesParser(value);

            if (characteristicUUID.equals(HEARTRATE_MEASUREMENT_CHARACTERISTIC_UUID))
            {
                HeartRate_Measurement measurement = new HeartRate_Measurement(value);

                Intent intent = new Intent("HeartRateMeasurement");
                intent.putExtra("HeartRateVal", measurement);

                context.sendBroadcast(intent);
                Timber.d("%s", measurement);
            }

            if (characteristicUUID.equals(TEMPERATURE_MEASUREMENT_CHARACTERISTIC_UUID))
            {
                Temperature_Measurement measurement = new Temperature_Measurement(value);

                Intent intent = new Intent("TemperatureMeasurement");
                intent.putExtra("TempVal", measurement);

                context.sendBroadcast(intent);
                Timber.d("%s", measurement);
            }

            if (characteristicUUID.equals(GSR_MEASUREMENT_CHARACTERISTIC_UUID))
            {
                GSR_Measurement measurement = new GSR_Measurement(value);

                Intent intent = new Intent("GSRMeasurement");
                intent.putExtra("GSRVal", measurement);

                context.sendBroadcast(intent);
                Timber.d("%s", measurement);
            }
        }
    };

    // Central callback. Provides set of functions that execute depending on what occurs with
    // the Bluetooth Central device
    private final BluetoothCentralCallback bleCentralCallback = new BluetoothCentralCallback()
    {
        // Callback invoked upon successful connection with a Bluetooth Peripheral
        // device
        @Override
        public void onConnectedPeripheral(BluetoothPeripheral peripheral)
        {
            String msg = "Connected to " + peripheral.getName();
            Utils.toast(context, msg);
        }

        // Callback invoked upon the failed connection with a Bluetooth Peripheral
        // device
        @Override
        public void onConnectionFailed(BluetoothPeripheral peripheral, final int status)
        {
            Timber.e("Connection '%s' failed with status %d", peripheral.getName(), status);
//            String msg = "Connection " + peripheral.getName() + " failed with status " + status;
//            Utils.toast(context, msg);
        }

        // Callback invoked upon the disconnection with a Bluetooth Peripheral device
        @Override
        public void onDisconnectedPeripheral(final BluetoothPeripheral peripheral, int status)
        {
            Timber.i("Disconnected '%s' with status %d", peripheral.getName(), status);

            // Reconnect to disconnected device when it is available once again
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    bleCentral.autoConnectPeripheral(peripheral, peripheralCallback);
                }
            }, 5000);
        }

        // Callback invoked upon the discovery of a Bluetooth Peripheral device
        @Override
        public void onDiscoveredPeripheral(BluetoothPeripheral peripheral, ScanResult scanResult)
        {
            Timber.i("Found peripheral '%s'", peripheral.getName());
//            String msg = "Found peripheral " + peripheral.getName();
//            Utils.toast(context, msg);

            bleCentral.stopScan();
            bleCentral.connectPeripheral(peripheral, peripheralCallback);
        }

        // Callback invoked upon the status change of the Bluetooth adapter
        @Override
        public void onBluetoothAdapterStateChanged(int state)
        {
            if (state == BluetoothAdapter.STATE_ON)
            {
                // Scan for peripherals with a certain service UUIDs
                bleCentral.startPairingPopupHack();
                bleCentral.scanForPeripheralsWithNames(new String[]{MY_ESP32});
            }
        }
    };

    public static synchronized BLE_Handler getInstance(Context context)
    {
        if (instance == null)
            instance = new BLE_Handler(context.getApplicationContext());

        return instance;
    }

    private BLE_Handler(Context context)
    {
        this.context = context;

        // Create the BluetoothCentral object
        bleCentral = new BluetoothCentral(context, bleCentralCallback, new Handler());

        // Scan for peripherals w/ a specific name
        bleCentral.startPairingPopupHack();
        bleCentral.scanForPeripheralsWithNames(new String[]{MY_ESP32});
    }
}