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

    public boolean listAvailableActivities(int passengerNumber) {
        Passenger passenger = getPassenger(passengerNumber);
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

    public boolean listPassengersWithName(String passengerName) {
        passengerName = passengerName.trim().toLowerCase();
        List<Passenger> passengerList = new ArrayList<>();
        for (Passenger passenger : passengers) {
            if (passenger.getName().toLowerCase().contains(passengerName))
                passengerList.add(passenger);
        }
        if (passengerList.isEmpty()) {
            System.out.printf("No match found for '%s'!\n", passengerName);
            return false;
        }
        for (Passenger passenger : passengerList) {
            System.out.printf("%d - %s\n", passenger.getNumber(), passenger.getName());
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

    public Passenger getPassenger(int passengerNumber) {
        for (Passenger passenger : passengers) {
            if (passenger.getNumber() == passengerNumber)
                return passenger;
        }
        System.out.printf("Passenger with number %d not found!\n", passengerNumber);
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
        destination.addActivity(activityName, activityDescription, activityCost, activityCapacity);
    }

    public void addPassenger(String name, double balance, Passenger.Type type) {
        Passenger passenger = new Passenger(name, passengers.size() + 1, balance, type);
        passengers.add(passenger);
        System.out.println("Passenger added successfully!");
    }

    public void signUpForPackage(int passengerNumber, int travelPackageChoice) {
        TravelPackage travelPackage = getTravelPackage(travelPackageChoice);
        Passenger passenger = getPassenger(passengerNumber);
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

    public void signUpForActivity(int passengerNumber, String activityName) {
        Passenger passenger = getPassenger(passengerNumber);
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

    public void printPassengerDetails(int passengerNumber) {
        Passenger passenger = getPassenger(passengerNumber);
        if (passenger == null)
            return;
        passenger.printDetails();
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

    public List<TravelPackage> getTravelPackages() {
        return travelPackages;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }
}
