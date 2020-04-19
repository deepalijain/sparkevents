package com.example.spark;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

public class ScrollingActivity extends AppCompatActivity {
    ViewPager viewPager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Event> eventList;

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

        eventList = new ArrayList<>();
        //just example image array lists
        ArrayList<ArrayList<Integer>> imageExamples = new ArrayList<>();
        ArrayList<Integer> example1 = new ArrayList<>();
        ArrayList<Integer> example2 = new ArrayList<>();
        ArrayList<Integer> example3 = new ArrayList<>();
        example1.add(R.drawable.img1);
        example2.add(R.drawable.img2);
        example3.add(R.drawable.img3);
        imageExamples.add(example1);
        imageExamples.add(example2);
        imageExamples.add(example3);
        for (int i = 1; i < 21; i += 1) {
            eventList.add(new Event("Event " + i, "Example event " + i,
                    new GregorianCalendar(), "Place " + i, "John Doe", i,
                    imageExamples.get(i % 3)));
        }
        Event.sortByRecommendation(eventList);

        mRecyclerView = findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerAdapter(eventList);

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
                    Event.sortByRecommendation(eventList);
                } else if (position == 1) {
                    Event.sortByDateAscending(eventList);
                } else {
                    Event.sortByDateDescending(eventList);
                }
                mAdapter = new RecyclerAdapter(eventList);
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


}
