package com.example.ble;

import com.welie.blessed.BluetoothBytesParser;

import java.io.Serializable;
import java.util.Locale;

import static com.welie.blessed.BluetoothBytesParser.FORMAT_SINT32;
import static com.welie.blessed.BluetoothBytesParser.FORMAT_FLOAT;

public class HeartRate_Measurement implements Serializable
{
    // This is for testing purposes
//    public int pulse;

    // This is the actual one to be used
    public float pulse;

    public HeartRate_Measurement(byte[] value)
    {
        BluetoothBytesParser parser = new BluetoothBytesParser(value);

        // This is for testing purposes
//        pulse = parser.getIntValue(FORMAT_SINT32);

        // This is the actual one to be used
        pulse = parser.getFloatValue(FORMAT_FLOAT);
    }

    @Override
    public String toString()
    {
        // This is for testing purposes
//        return String.format(Locale.ENGLISH, "Heart Beat is %d", pulse);

        // This is the actual one to be used
        return String.format(Locale.ENGLISH, "Heart beat is %.2f", pulse);
    }
}
