package com.example.spark;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

public class Event {
    private String name;
    private String description;
    private GregorianCalendar date;
    private String location;
    private String host;
    private ArrayList<String> tags;
    private int eventID;
    private ArrayList<Integer> images;
    private int recommendation;
    private String interest;

    public Event(String name, String description, GregorianCalendar date, String location,
                 String host, int eventID, ArrayList<Integer> images) {
        this.name = name;
        this.description = description;

        this.location = location;
        this.host = host;
        this.eventID = eventID;
        this.images = images;
        // for now, recommendation is randomized (will later use a formula to compute recommendation value
        // and this value will be uniquely calculated for the user
        Random r = new Random();
        recommendation = r.nextInt(10);
        // for now, date is randomized (should be provided by the host when event is created)
        this.date = new GregorianCalendar();
        this.date.roll(GregorianCalendar.DAY_OF_MONTH, r.nextInt(14));
        interest = "";
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getHost() {
        return host;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public ArrayList<Integer> getImages() {
        return images;
    }

    public static void sortByRecommendation(ArrayList<Event> events) {
            int min;
            Event temp;
            for (int i = 0; i < events.size(); i += 1) {
                min = i;
                for (int j = i + 1; j < events.size(); j += 1) {
                    if (events.get(j).recommendation < events.get(min).recommendation) {
                        min = j;
                    }
                }
                temp = events.get(i);
                events.set(i, events.get(min));
                events.set(min, temp);
            }
    }

    public static void sortByDateAscending(ArrayList<Event> events) {
        int min;
        Event temp;
        for (int i = 0; i < events.size(); i += 1) {
            min = i;
            for (int j = i + 1; j < events.size(); j += 1) {
                if(events.get(j).date.before(events.get(min).date)) {
                    min = j;
                }
            }
            temp = events.get(i);
            events.set(i, events.get(min));
            events.set(min, temp);
        }
    }

    public static void sortByDateDescending(ArrayList<Event> events) {
        int max;
        Event temp;
        for (int i = 0; i < events.size(); i += 1) {
            max = i;
            for (int j = i + 1; j < events.size(); j += 1) {
                if(events.get(j).date.after(events.get(max).date)) {
                    max = j;
                }
            }
            temp = events.get(i);
            events.set(i, events.get(max));
            events.set(max, temp);
        }
    }
}
