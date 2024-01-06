package me.aryanrai.tms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Passenger implements Serializable {
    private final List<Activity> activities;
    private final List<Double> activityPrices;
    private final Type type;
    private final String name;
    private final int number;
    private double balance;
    private TravelPackage assignedPackage;

    public Passenger(String name, int number, double balance, Type type) {
        this.name = name;
        this.number = number;
        this.activities = new ArrayList<>();
        this.activityPrices = new ArrayList<>();
        this.balance = balance;
        this.type = type;
    }

    public void assignPackage(TravelPackage travelPackage) {
        this.assignedPackage = travelPackage;
    }

    public boolean purchaseActivity(Activity activity) {
        if (!assignedPackage.getDestinations().contains(activity.getDestination())) {
            System.out.println("Activity not available in the assigned package!");
            return false;
        }
        if (!activity.hasVacancy()) {
            System.out.println("Activity is full!");
            return false;
        }
        if (activities.contains(activity)) {
            System.out.println("Activity already purchased!");
            return false;
        }
        double price = activity.getCost();
        switch (this.type) {
            case GOLD:
                price *= 0.9;
                break;
            case PREMIUM:
                price = 0;
                break;
        }
        if (this.balance >= price) {
            this.balance -= price;
            this.activities.add(activity);
            this.activityPrices.add(price);
            activity.addPassenger(this);
            System.out.println("Purchased Activity: " + activity.getName() + " for " + price);
            return true;
        }
        System.out.println("Insufficient Balance!");
        return false;
    }

    public void printDetails() {
        System.out.println(name);
        System.out.println("Number: " + number);
        if (type == Type.STANDARD) {
            System.out.println("Type: Standard");
            System.out.println("Balance: " + balance);
        } else if (type == Type.GOLD) {
            System.out.println("Type: Gold");
            System.out.println("Balance: " + balance);
        } else {
            System.out.println("Type: Premium");
        }
        int index = 1;
        if (activities.isEmpty()) {
            System.out.println("No Activities Purchased!");
            return;
        }
        System.out.println("Purchased Activities:");
        for (Activity activity : activities) {
            System.out.print("\t" + index + ". ");
            System.out.println(activity.getName() + ", " + activity.getDestination().getName());
            if (type != Type.PREMIUM)
                System.out.println("\tPrice Paid: " + activityPrices.get(index - 1));
            index++;
        }
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public List<Double> getActivityPrices() {
        return activityPrices;
    }

    public TravelPackage getAssignedPackage() {
        return assignedPackage;
    }

    public enum Type {
        STANDARD, GOLD, PREMIUM
    }

}
