import android.bluetooth.BluetoothDevice;

public class BT_Device {
    private BluetoothDevice bluetoothDevice;
    private int rssi;

    public BT_Device(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public String getAddress() {
        return bluetoothDevice.getAddress();
    }

    public String getName() {
        return bluetoothDevice.getAddress();
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getRssi() {
        return rssi;
    }
}