package com.example.ble;

import com.welie.blessed.BluetoothBytesParser;

import java.io.Serializable;
import java.util.Locale;

import static com.welie.blessed.BluetoothBytesParser.FORMAT_UINT8;
import static com.welie.blessed.BluetoothBytesParser.FORMAT_SINT32;
import static com.welie.blessed.BluetoothBytesParser.FORMAT_FLOAT;

public class HeartRate_Measurement implements Serializable
{
    public int pulse;

    public HeartRate_Measurement(byte[] value)
    {
        BluetoothBytesParser parser = new BluetoothBytesParser(value);

        pulse = parser.getIntValue(FORMAT_SINT32);
//        pulse = parser.getFloatValue(FORMAT_FLOAT);
    }

    @Override
    public String toString()
    {
        return String.format(Locale.ENGLISH, "Heart Beat is %d", pulse);
    }
}
