package com.example.ble;

import com.welie.blessed.BluetoothBytesParser;

import java.io.Serializable;
import java.util.Locale;

import static com.welie.blessed.BluetoothBytesParser.FORMAT_SINT32;

public class GSR_Measurement implements Serializable
{
    // Name of files to save received GSR data
    public static final String gsrFile = "gsr.json";
    public static final String calibrateGSRFile = "gsr_calibrate.json";

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
