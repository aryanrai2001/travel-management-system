package me.aryanrai.tms.controller;

import me.aryanrai.tms.model.Activity;
import me.aryanrai.tms.model.Destination;
import me.aryanrai.tms.model.Passenger;
import me.aryanrai.tms.model.TravelPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TravelManagementSystem implements Serializable {

    private final List<TravelPackage> travelPackages;
    private final List<Passenger> passengers;

    public TravelManagementSystem() {
        this.travelPackages = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    public boolean listTravelPackages() {
        int index = 1;
        for (TravelPackage travelPackage : travelPackages) {
            System.out.print(index + ". ");
            System.out.println(travelPackage.getName());
            index++;
        }
        if (index == 1) {
            System.out.println("No Travel Packages found!");
            return false;
        }
        return true;
    }

    public boolean listAvailableActivities(String passengerName) {
        Passenger passenger = getPassenger(passengerName);
        if (passenger == null) {
            System.out.println("Could not list available Activities!");
            return false;
        }
        TravelPackage travelPackage = passenger.getAssignedPackage();
        if (travelPackage == null) {
            System.out.println("No Packages Assigned!");
            System.out.println("Could not list available Activities!");
            return false;
        }
        System.out.printf("List of Activities available on travel package %s:-\n", travelPackage.getName());
        List<Destination> destinations = travelPackage.getDestinations();
        for (Destination destination : destinations) {
            List<Activity> activities = destination.getActivities();
            for (Activity activity : activities) {
                if (activity.hasVacancy()) {
                    System.out.print("- ");
                    activity.printDetails();
                }
            }
        }
        return true;
    }

    public TravelPackage getTravelPackage(int index) {
        if (index < 1 || index > travelPackages.size()) {
            System.out.println("Invalid Index!");
            return null;
        }
        return travelPackages.get(index - 1);
    }

    public Passenger getPassenger(String name) {
        for (Passenger passenger : passengers) {
            if (passenger.getName().equals(name))
                return passenger;
        }
        System.out.println("Passenger not found!");
        return null;
    }

    public void createTravelPackage(String packageName, int passengerCapacity) {
        for (TravelPackage travelPackage : travelPackages) {
            if (travelPackage.getName().equals(packageName)) {
                System.out.println("Travel Package already exists!");
                return;
            }
        }
        TravelPackage travelPackage = new TravelPackage(packageName, passengerCapacity);
        travelPackages.add(travelPackage);
        System.out.println("Travel Package created successfully!");
    }

    public void addDestination(int travelPackageChoice, String destinationName) {
        TravelPackage travelPackage = getTravelPackage(travelPackageChoice);
        if (travelPackage == null) {
            System.out.println("Could not add Destination to Travel Package!");
            return;
        }
        for (Destination destination : travelPackage.getDestinations()) {
            if (destination.getName().equals(destinationName)) {
                System.out.println("Destination already exists!");
                return;
            }
        }
        Destination destination = new Destination(destinationName);
        travelPackage.addDestination(destination);
    }

    public void addActivity(String destinationName, String activityName, String activityDescription, double activityCost, int activityCapacity) {
        Destination destination = null;
        for (TravelPackage travelPackage : travelPackages) {
            List<Destination> destinations = travelPackage.getDestinations();
            for (Destination dest : destinations) {
                if (dest.getName().equals(destinationName)) {
                    destination = dest;
                    break;
                }
            }
        }
        if (destination == null) {
            System.out.println("Could not add Activity to Destination!");
            return;
        }
        for (Activity activity : destination.getActivities()) {
            if (activity.getName().equals(activityName)) {
                System.out.println("Activity already exists!");
                return;
            }
        }
        Activity activity = new Activity(activityName, activityDescription, activityCost, activityCapacity);
        destination.addActivity(activity);
    }

    public void addPassenger(String name, int number, double balance, Passenger.Type type) {
        for (Passenger passenger : passengers) {
            if (passenger.getName().equals(name)) {
                System.out.println("Passenger already exists!");
                return;
            }
        }
        Passenger passenger = new Passenger(name, number, balance, type);
        passengers.add(passenger);
        System.out.println("Passenger added successfully!");
    }

    public void signUpForPackage(String name, int travelPackageChoice) {
        TravelPackage travelPackage = getTravelPackage(travelPackageChoice);
        Passenger passenger = getPassenger(name);
        if (travelPackage == null || passenger == null) {
            System.out.println("Could not sign up Passenger!");
            return;
        }
        if (passenger.getAssignedPackage() != null) {
            System.out.println("Passenger already signed up for a Package!");
            return;
        }
        travelPackage.addPassenger(passenger);
        System.out.println("Passenger signed up successfully!");

    }

    public void signUpForActivity(String name, String activityName) {
        Passenger passenger = getPassenger(name);
        if (passenger == null) {
            System.out.println("Could not sign up Passenger!");
            return;
        }
        TravelPackage travelPackage = passenger.getAssignedPackage();
        if (travelPackage == null) {
            System.out.println("Could not sign up Passenger!");
            return;
        }

        for (Destination destination : travelPackage.getDestinations()) {
            for (Activity activity : destination.getActivities()) {
                if (activity.getName().equals(activityName)) {
                    if (passenger.purchaseActivity(activity)) {
                        System.out.println("Passenger signed up successfully!");
                    } else {
                        System.out.println("Could not sign up Passenger!");
                    }
                    return;
                }
            }
        }
    }

    public void printItinerary(int travelPackageChoice) {
        TravelPackage travelPackage = getTravelPackage(travelPackageChoice);
        if (travelPackage == null) {
            System.out.println("Could not print Itinerary!");
            return;
        }
        travelPackage.printItinerary();
    }

    public void printPassengers(int travelPackageChoice) {
        TravelPackage travelPackage = getTravelPackage(travelPackageChoice);
        if (travelPackage == null) {
            System.out.println("Could not print Passengers!");
            return;
        }
        travelPackage.printPassengers();
    }

    public void printPassengerDetails(String name) {
        for (Passenger passenger : passengers) {
            if (passenger.getName().equals(name)) {
                passenger.printDetails();
                return;
            }
        }
        System.out.println("Passenger not found!");
    }
}
