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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import timber.log.Timber;

public class NotificationsActivity extends AppCompatActivity
{
    private ArrayList<String> listNotify = new ArrayList<>();

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

        readAlerts();

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
                            intent = new Intent(getApplicationContext(), DashboardActivity.class);
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

    private void readAlerts()
    {
        BufferedReader buffRead;
        FileReader fileRead;
        ArrayList<String> tmp = new ArrayList<>();

        try
        {
            fileRead = new FileReader(DashboardActivity.alerts);
            buffRead = new BufferedReader(fileRead);
            String line = buffRead.readLine();

            while (line != null)
            {
                tmp.add(line);
                line = buffRead.readLine();
            }
        } catch (IOException e)
        {
            Timber.e(e);
        }

        for (int i = tmp.size() - 1; i >= 0; i--)
            listNotify.add(tmp.get(i));
    }
}
