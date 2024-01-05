package me.aryanrai.tms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TravelPackage implements Serializable {
    private final String name;
    private final int passengerCapacity;
    private final List<Destination> destinations;
    private final List<Passenger> passengers;

    public TravelPackage(String name, int passengerCapacity) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.destinations = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    public void addDestination(Destination destination) {
        this.destinations.add(destination);
        System.out.printf("Added Destination %s to Travel Package %s\n", destination.getName(), this.name);
    }

    public void addPassenger(Passenger passenger) {
        if (this.passengers.size() < this.passengerCapacity) {
            this.passengers.add(passenger);
            passenger.assignPackage(this);
            System.out.printf("Signed Up %s for Travel Package %s\n", passenger.getName(), this.name);
        } else
            System.out.println("Capacity Full!");
    }

    public void printItinerary() {
        System.out.println("Itinerary for " + this.name);
        if (this.destinations.isEmpty()) {
            System.out.println("No Destinations found!");
            return;
        }
        System.out.println("Destinations:-");
        int index = 1;
        for (Destination destination : this.destinations) {
            System.out.print(index + ". ");
            destination.printDetails();
            index++;
        }
    }

    public void printPassengers() {
        System.out.println("Passengers on " + this.name);
        System.out.println("Capacity: " + this.passengerCapacity);
        System.out.println("Enrolled: " + this.passengers.size());
        if (this.passengers.isEmpty()) {
            System.out.println("No Passengers found!");
            return;
        }
        System.out.println("Passenger Details:-");
        int index = 1;
        for (Passenger passenger : this.passengers) {
            System.out.print(index + ". " + passenger.getName());
            System.out.println(", " + passenger.getNumber());
            index++;
        }
    }

    public String getName() {
        return this.name;
    }

    public List<Destination> getDestinations() {
        return this.destinations;
    }

    public List<Passenger> getPassengers() {
        return this.passengers;
    }
}
