package com.example.spark;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;

public class EventActivity extends Activity {
    private ViewPager imageViewPager;
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
        Event eventItem = new Gson().fromJson(jsonEventItem, Event.class);
        Integer[] eventImages = new Gson().fromJson(jsonEventImages, Integer[].class);
        setContentView(R.layout.event_page);
        TextView testEvent = findViewById(R.id.testEvent);
        testEvent.setText(eventItem.getName());

        imageViewPager = findViewById(R.id.eventImagePager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, eventImages);

        imageViewPager.setAdapter(viewPagerAdapter);

    }
}
