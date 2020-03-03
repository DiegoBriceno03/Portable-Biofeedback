package com.example.myapplication

import android.content.Intent
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //create activity for the reset button
        reset_button.setOnClickListener {
            d( "grace",  "reset button was pressed")
        }

        //create activity for the reset button
        pair_button.setOnClickListener {
            d( "grace",  "pair button was pressed")
            startActivity(Intent(this, Pair::class.java))
        }
    }
}
