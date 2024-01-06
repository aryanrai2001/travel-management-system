package me.aryanrai.tms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Activity implements Serializable {
    private final String name;
    private final String description;
    private final double cost;
    private final int capacity;
    private final Destination destination;
    private final List<Passenger> passengers;

    public Activity(Destination destination, String name, String description, double cost, int capacity) {
        this.name = name;
        this.description = description;
        this.destination = destination;
        this.cost = cost;
        this.capacity = capacity;
        this.passengers = new ArrayList<>();
    }

    public boolean hasVacancy() {
        return this.passengers.size() < this.capacity;
    }

    public void addPassenger(Passenger passenger) {
        if (this.passengers.contains(passenger)) {
            System.out.println("Passenger already added!");
            return;
        }
        if (!passenger.getAssignedPackage().getDestinations().contains(this.destination)) {
            System.out.println("Passenger not in assigned package!");
            return;
        }
        if (this.passengers.size() < this.capacity) {
            this.passengers.add(passenger);
            System.out.println("Added " + passenger.getName() + " to " + this.name);
        } else
            System.out.println("Capacity Full!");
    }

    public void printDetails() {
        System.out.println(name + " - " + description);
        System.out.println("\t\tCost: " + cost);
        System.out.println("\t\tCapacity: " + capacity);
        System.out.println("\t\tVacancy: " + (capacity - passengers.size()));
    }

    public String getName() {
        return this.name;
    }

    public double getCost() {
        return this.cost;
    }

    public Destination getDestination() {
        return this.destination;
    }

    public List<Passenger> getPassengers() {
        return this.passengers;
    }

}
