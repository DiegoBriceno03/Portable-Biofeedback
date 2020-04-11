package com.example.ble;

import com.welie.blessed.BluetoothBytesParser;

import java.io.Serializable;
import java.util.Locale;

import static com.welie.blessed.BluetoothBytesParser.FORMAT_UINT8;

public class HeartRate_Measurement implements Serializable
{
    // Name of files to save received heart rate data
    public static final String fileHR = "heart-rate.json";
    public static final String calibrateFileHR = "heart-rate_calibrate.json";

    public int pulse;

    public HeartRate_Measurement(byte[] value)
    {
        BluetoothBytesParser parser = new BluetoothBytesParser(value);

        pulse = parser.getIntValue(FORMAT_UINT8);
    }

    @Override
    public String toString()
    {
        return String.format(Locale.ENGLISH, "Heart Beat is %d", pulse);
    }
}
