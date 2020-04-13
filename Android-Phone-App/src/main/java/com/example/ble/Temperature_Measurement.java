package com.example.ble;

import com.welie.blessed.BluetoothBytesParser;

import java.io.Serializable;
import java.util.Locale;

public class Temperature_Measurement implements Serializable
{
    // Name of files to save received temperature data
    public static final String tempFile = "temperature.json";
    public static final String calibrateTempFile = "temperature_calibrate.json";

    public String temperature;

    public Temperature_Measurement(byte[] value)
    {
        BluetoothBytesParser parser = new BluetoothBytesParser(value);

        temperature = parser.getStringValue();
    }

    @Override
    public String toString()
    {
        return String.format(Locale.ENGLISH, "Temperature is %s", temperature);
    }
}
