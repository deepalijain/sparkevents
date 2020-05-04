package com.example.spark;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.SearchEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.ArrayList;
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
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(2020, 4, 20, 20, 00);
        mEventList.add(new Event("Concert", "Come enjoy some music, open to all UC Berkeley students.",
                cal.getTime(), "Greek Theatre", "Band", 1, concertImages));
        cal.set(2020, 5, 1, 13, 00);
        mEventList.add(new Event("Music Festival", "Come celebrate diverse music at the annual music festival.",
                cal.getTime(), "Telegraph Ave.", "Berkeley", 2, festivalImages));
        cal.set(2020, 4, 13, 20, 00);
        mEventList.add(new Event("Night Market", "Enjoy some food at the Night Market.",
                cal.getTime(), "Lower Sproul", "Clubs", 3, foodImages));
        cal.set(2020, 4, 6, 9, 00);
        mEventList.add(new Event("Hackathon", "Registration is open to all students! Join for a weekend of workshops, collaboration, and hacking!",
                cal.getTime(), "Cory Hall", "Hack Club", 4, hackathonImages));
        cal.set(2020, 4, 8, 20, 00);
        mEventList.add(new Event("Networking", "Come to this networking event for a chance to talk to recruiters of numerous companies.",
                cal.getTime(), "Barrows Hall", "Berkeley", 5, networkingImages));
        cal.set(2020, 4, 15, 18, 00);
        mEventList.add(new Event("Guest Speaker", "This is an example speaker event",
                cal.getTime(), "Zellerbach", "Speaker", 6, speakerImages));
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
        menu.findItem(R.id.list_view).setVisible(false);
        menu.findItem(R.id.backButton).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.calendar_view:
                Intent list = new Intent(ScrollingActivity.this, CalendarActivity.class);
                startActivity(list);
                return (true);
            case R.id.logout:
                Intent login = new Intent(ScrollingActivity.this, LoginActivity.class);
                startActivity(login);
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