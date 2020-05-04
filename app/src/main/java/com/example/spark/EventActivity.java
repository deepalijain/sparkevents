package com.example.spark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventActivity extends Activity {
    private ViewPager imageViewPager;
    private String INFO_PATTERN = "Date: %s\nTime: %s\nLocation: %s\nHost: %s";
    static final String DAY_PATTERN = "LL/dd/yy";
    static final String TIME_PATTERN = "hh:mm aa";
    private Event mEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String jsonEventItem = "";
        String jsonEventImages = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonEventItem = extras.getString("event_object");
            jsonEventImages = extras.getString("event_images");
        }
        mEvent = new Gson().fromJson(jsonEventItem, Event.class);
        Integer[] eventImages = new Gson().fromJson(jsonEventImages, Integer[].class);
        setContentView(R.layout.event_page);
        TextView testEvent = findViewById(R.id.testEvent);
        testEvent.setText(mEvent.getName());
        TextView descriptionEvent = findViewById(R.id.eventDescription);
        descriptionEvent.setText(mEvent.getDescription());
        DateFormat df = new SimpleDateFormat(DAY_PATTERN);
        DateFormat tf = new SimpleDateFormat(TIME_PATTERN);
        Date date = mEvent.getDate().getTime();
        TextView infoEvent = findViewById(R.id.eventInfo);
        infoEvent.setText(String.format(INFO_PATTERN, df.format(date), tf.format(date),
                mEvent.getLocation(), mEvent.getHost()));
        imageViewPager = findViewById(R.id.eventImagePager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, eventImages);
        imageViewPager.setAdapter(viewPagerAdapter);
        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(EventActivity.this, ScrollingActivity.class);
                startActivity(i);
            }
        });

        // Dropdown for interest
        Spinner interestSpinner = findViewById(R.id.interest);
        String[] sortItems = new String[]{"Select", "Interested", "Uninterested", "Going"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sortItems);
        interestSpinner.setAdapter(adapter);
        interestSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0) {
                    mEvent.setInterest("");
                } else if (position == 1) {
                    mEvent.setInterest("interested");
                } else if (position == 2) {
                    mEvent.setInterest("not interested");
                } else if (position == 3) {
                    mEvent.setInterest("going");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }
}
