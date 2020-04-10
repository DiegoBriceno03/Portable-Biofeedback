package com.example.biofeedbackstress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import timber.log.Timber;

public class NotificationsActivity extends AppCompatActivity
{
    private static ArrayList<String> listNotify = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        TextView date = findViewById(R.id.date_today);
        ListView notify = findViewById(R.id.notifyList);

        BottomNavigationView bottomNavBar = findViewById(R.id.bottom_navigation);
        bottomNavBar.getMenu().findItem(R.id.nav_notifications).setChecked(true);
        bottomNavBar.setOnNavigationItemSelectedListener(navListener);

        Intent mainIntent = getIntent();

        String obtainMainIntent = (String) mainIntent.getSerializableExtra("WARN");

        if (obtainMainIntent != null)
            listNotify.add(obtainMainIntent);

        if (!listNotify.isEmpty())
        {
            ArrayAdapter<String> notifyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listNotify);
            notify.setAdapter(notifyAdapter);
        }

        Utils.setDate(date);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener()
            {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item)
                {
                    Intent intent;

                    switch (item.getItemId())
                    {
                        case R.id.nav_dashboard:
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            return true;
                        case R.id.nav_graph:
                            intent = new Intent(getApplicationContext(), GraphActivity.class);
                            startActivity(intent);
                            return true;
                        default:
                            break;
                    }

                    return false;
                }
            };
}
