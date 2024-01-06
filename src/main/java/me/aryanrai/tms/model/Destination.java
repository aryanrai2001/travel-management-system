package me.aryanrai.tms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Destination implements Serializable {
    private final String name;
    private final List<Activity> activities;

    public Destination(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
    }

    public void addActivity(String name, String description, double cost, int capacity) {
        Activity activity = new Activity(this, name, description, cost, capacity);
        this.activities.add(activity);
    }

    public void printDetails() {
        System.out.println(name);
        if (this.activities.isEmpty()) {
            System.out.println("No Activities found!");
            return;
        }
        System.out.println("\tActivities:-");
        char index = 'a';
        for (Activity activity : this.activities) {
            System.out.print("\t" + index + ".\t");
            activity.printDetails();
            index++;
        }
    }

    public String getName() {
        return this.name;
    }

    public List<Activity> getActivities() {
        return this.activities;
    }
}
