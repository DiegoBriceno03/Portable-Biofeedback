package com.example.ble;

import com.welie.blessed.BluetoothBytesParser;

import java.io.Serializable;
import java.util.Locale;

import static com.welie.blessed.BluetoothBytesParser.FORMAT_SINT32;
import static com.welie.blessed.BluetoothBytesParser.FORMAT_FLOAT;

public class Temperature_Measurement implements Serializable
{
    // This is for testing purposes
//    public int temperature;

    // This is the actual one to be used
    public double temperature;

    public Temperature_Measurement(byte[] value)
    {
        BluetoothBytesParser parser = new BluetoothBytesParser(value);

        // This is for testing purposes
//        temperature = parser.getIntValue(FORMAT_SINT32);

        // This is the actual one to be used
        temperature = parser.getFloatValue(FORMAT_FLOAT);
    }

    @Override
    public String toString()
    {
        // This is for testing purposes
//        return String.format(Locale.ENGLISH, "Temperature is %d", temperature);

        // This is the actual one to be used
        return String.format(Locale.ENGLISH, "Temperature is %.2f", temperature);
    }
}
