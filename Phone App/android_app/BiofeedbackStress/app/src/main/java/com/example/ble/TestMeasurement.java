package com.example.ble;

import com.welie.blessed.BluetoothBytesParser;

import java.io.Serializable;
import java.util.Locale;

import static com.welie.blessed.BluetoothBytesParser.FORMAT_UINT8;
import static com.welie.blessed.BluetoothBytesParser.FORMAT_FLOAT;

public class TestMeasurement implements Serializable
{
    public int count;

    public TestMeasurement(byte[] value)
    {
        BluetoothBytesParser parser = new BluetoothBytesParser(value);

        // Obtain test value
        count = parser.getIntValue(FORMAT_UINT8);
    }

    @Override
    public String toString()
    {
        // Value seems to increase by 1 every 255 vals (i.e. 8-bits)
        return String.format(Locale.ENGLISH, "Value is %d", count);
    }
}
