package com.example.spark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.time.Month;

import java.util.Random;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CalendarView calendar = findViewById(R.id.myCalendar);
        final TextView todayDate = findViewById(R.id.todayDate);
        final TextView todayEvents = findViewById(R.id.eventNames);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //Date d = new GregorianCalendar(year, month, dayOfMonth);
                todayDate.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    todayDate.setText(Month.of(month + 1).name().substring(0,3) + " " + dayOfMonth + ", " + year);
                }
                String[] events = new String[]{"Infosession  6:00PM   Soda Hall\n",
                                                "Food Event  6:00PM   MLK\n",
                                                "Festivals   9:00PM   Greek Theater\n",
                                                "Hackathon   9:00AM   Cory Hall\n",
                                                "Networking  8:00PM   Barrows Hall\n"};

                String e = getRandomElement(events);
                e += getRandomElement(events);
                todayEvents.setVisibility(View.VISIBLE);
                todayEvents.setText(e);

            }
        });

    }

    public String getRandomElement(String[] list)
    {
        Random rand = new Random();
        int len = list.length;
        return list[rand.nextInt(len)];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        menu.findItem(R.id.calendar_view).setVisible(false);
        menu.findItem(R.id.backButton).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.list_view:
                Intent list = new Intent(CalendarActivity.this, ScrollingActivity.class);
                startActivity(list);
                return (true);
            case R.id.logout:
                Intent login = new Intent(CalendarActivity.this, LoginActivity.class);
                startActivity(login);
        }
        return super.onOptionsItemSelected(item);
    }
}
