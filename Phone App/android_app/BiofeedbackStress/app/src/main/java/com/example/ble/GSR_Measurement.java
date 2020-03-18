package com.example.ble;

import com.welie.blessed.BluetoothBytesParser;

import java.io.Serializable;
import java.util.Locale;

import static com.welie.blessed.BluetoothBytesParser.FORMAT_SINT32;
import static com.welie.blessed.BluetoothBytesParser.FORMAT_FLOAT;

public class GSR_Measurement implements Serializable
{
    // Based on the ESP32 firmware that was developed for this project,
    // no testing variables will be necessary for this

    public int conduct;

    public GSR_Measurement(byte[] value)
    {
        BluetoothBytesParser parser = new BluetoothBytesParser(value);

        conduct = parser.getIntValue(FORMAT_SINT32);
    }

    @Override
    public String toString()
    {
        return String.format(Locale.ENGLISH, "GSR is %d", conduct);
    }
}
