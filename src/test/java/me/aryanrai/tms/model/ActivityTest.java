package me.aryanrai.tms.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void addPassenger() {
        TravelPackage travelPackage = new TravelPackage("Package1", 10);
        Destination destination = new Destination("Destination1");
        Activity activity = new Activity(destination, "Activity1", "Desc1", 100.0, 1);
        travelPackage.addDestination(destination);
        Passenger passenger = new Passenger("Passenger1", 1, 100, Passenger.Type.STANDARD);
        passenger.assignPackage(travelPackage);
        activity.addPassenger(passenger);
        assertTrue(activity.getPassengers().contains(passenger));
    }

    @Test
    void hasVacancyTrue() {
        Destination destination = new Destination("Destination1");
        Activity activity = new Activity(destination, "Activity1", "Desc1", 100.0, 1);
        assertTrue(activity.hasVacancy());
    }

    @Test
    void hasVacancyFalse() {
        TravelPackage travelPackage = new TravelPackage("Package1", 10);
        Destination destination = new Destination("Destination1");
        Activity activity = new Activity(destination, "Activity1", "Desc1", 100.0, 1);
        travelPackage.addDestination(destination);
        Passenger passenger = new Passenger("Passenger1", 1, 100, Passenger.Type.STANDARD);
        passenger.assignPackage(travelPackage);
        activity.addPassenger(passenger);
        assertFalse(activity.hasVacancy());
    }

    @Test
    void printDetails() {
        Destination destination = new Destination("Destination1");
        Activity activity = new Activity(destination, "Activity1", "Desc1", 100.0, 1);
        outContent.reset();
        activity.printDetails();
        String expectedOutput = """
                Activity1 - Desc1
                		Cost: 100.0
                		Capacity: 1
                		Vacancy: 1
                """;
        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }
}