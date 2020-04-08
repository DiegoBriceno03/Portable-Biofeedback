package com.example.biofeedbackstress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GraphActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        TextView date = findViewById(R.id.date_today);

        BottomNavigationView bottomNavBar = findViewById(R.id.bottom_navigation);
        bottomNavBar.getMenu().findItem(R.id.nav_graph).setChecked(true);
        bottomNavBar.setOnNavigationItemSelectedListener(navListener);

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
                        case R.id.nav_notifications:
                            intent = new Intent(getApplicationContext(), NotificationsActivity.class);
                            startActivity(intent);
                            return true;
                        default:
                            break;
                    }

                    return false;
                }
            };
}
