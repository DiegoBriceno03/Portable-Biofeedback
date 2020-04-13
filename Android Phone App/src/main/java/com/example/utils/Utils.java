package com.example.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

// Contains utilities useful for all other aspects of this app. Most useful
// is the toast() function it comes with
public class Utils
{
    public final static int REQUEST_ENABLE_BT = 1;
    public final static int ACCESS_LOCATION_REQUEST = 2;
    public final static int BLE_SERVICES = 3;

    public static void setDate(TextView text)
    {
        String date =  new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH).format(new Date());

        text.setText(date);
    }

    // A 'toast' is a view containing quick little messages for the user. This
    // class helps create and show these
    // URL: https://developer.android.com/reference/android/widget/Toast
    //      Appears as a floating view over the app; will never receive focus.
    //      Idea is to be as unobtrusive as possible, while still showing user
    //      the info you want them to see
    public static void toast(Context context, String string)
    {
        Toast toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    public static void saveData(String data, File jsonFileName) throws IOException
    {
        FileWriter writer = new FileWriter(jsonFileName, true);

        writer.write(data);
        writer.close();
    }
}
