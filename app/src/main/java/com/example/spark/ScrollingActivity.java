package com.example.spark;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class ScrollingActivity extends AppCompatActivity implements RecyclerAdapter.OnEventItemListener {
    ViewPager viewPager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Event> mEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // code Included
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        mEventList = new ArrayList<>();
        //just example image array lists
        ArrayList<Integer> concertImages = new ArrayList<>();
        concertImages.add(R.drawable.concert1);
        concertImages.add(R.drawable.concert2);
        concertImages.add(R.drawable.concert3);
        ArrayList<Integer> festivalImages = new ArrayList<>();
        festivalImages.add(R.drawable.festival1);
        festivalImages.add(R.drawable.festival2);
        ArrayList<Integer> foodImages = new ArrayList<>();
        foodImages.add(R.drawable.food1);
        foodImages.add(R.drawable.food2);
        foodImages.add(R.drawable.food3);
        ArrayList<Integer> hackathonImages = new ArrayList<>();
        hackathonImages.add(R.drawable.hackathon1);
        hackathonImages.add(R.drawable.hackathon2);
        ArrayList<Integer> networkingImages = new ArrayList<>();
        networkingImages.add(R.drawable.networking1);
        networkingImages.add(R.drawable.networking2);
        ArrayList<Integer> speakerImages = new ArrayList<>();
        speakerImages.add(R.drawable.speaker1);
        speakerImages.add(R.drawable.speaker2);
        mEventList.add(new Event("Concert Event", "This is an example concert event",
                new GregorianCalendar(), "Location 1", "Host 1", 1, concertImages));
        mEventList.add(new Event("Festival Event", "This is an example festival event",
                new GregorianCalendar(), "Location 2", "Host 2", 2, festivalImages));
        mEventList.add(new Event("Food Event", "This is an example food event",
                new GregorianCalendar(), "Location 3", "Host 3", 3, foodImages));
        mEventList.add(new Event("Hackathon Event", "This is an example hackathon event",
                new GregorianCalendar(), "Location 4", "Host 4", 4, hackathonImages));
        mEventList.add(new Event("Networking Event", "This is an example networking event",
                new GregorianCalendar(), "Location 5", "Host 5", 5, networkingImages));
        mEventList.add(new Event("Speaker Event", "This is an example speaker event",
                new GregorianCalendar(), "Location 6", "Host 6", 6, speakerImages));
        Event.sortByRecommendation(mEventList);

        mRecyclerView = findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerAdapter(mEventList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // code for the sort by dropdown
        Spinner sortby = findViewById(R.id.sortby);
        String[] sortItems = new String[]{"Recommended", "Date Ascending", "Date Descending"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sortItems);
        sortby.setAdapter(adapter);
        sortby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0) {
                    Event.sortByRecommendation(mEventList);
                } else if (position == 1) {
                    Event.sortByDateAscending(mEventList);
                } else {
                    Event.sortByDateDescending(mEventList);
                }
                mAdapter = new RecyclerAdapter(mEventList, ScrollingActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onEventItemClick(int position) {
        Event event = mEventList.get(position);
        Intent i = new Intent(ScrollingActivity.this, EventActivity.class);
        i.putExtra("event_object", new Gson().toJson(event));
        Object[] images = event.getImages().toArray();
        i.putExtra("event_images", new Gson().toJson(images));
        startActivity(i);
    }
}