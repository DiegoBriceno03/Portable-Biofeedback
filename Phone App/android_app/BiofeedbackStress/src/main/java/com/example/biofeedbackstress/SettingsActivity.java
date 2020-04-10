package com.example.biofeedbackstress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// IDEA
//      Have this activity start new Fragments on button press to view the Calibration values, for
//      example. Perhaps even view the data collected from the other stuff as well
public class SettingsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
