package me.aryanrai.tms.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TravelPackageTest {

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
    void addDestination() {
        TravelPackage travelPackage = new TravelPackage("Package1", 50);
        Destination destination = new Destination("Destination1");
        travelPackage.addDestination(destination);

        assertTrue(travelPackage.getDestinations().contains(destination));
    }

    @Test
    void addPassenger() {
        TravelPackage travelPackage = new TravelPackage("Package1", 50);
        Passenger passenger = new Passenger("Passenger1", 1, 100.0, Passenger.Type.STANDARD);
        travelPackage.addPassenger(passenger);

        assertTrue(travelPackage.getPassengers().contains(passenger));
    }

    @Test
    void printItinerary() {
        TravelPackage travelPackage = getSamplePackage();
        outContent.reset();
        travelPackage.printItinerary();

        String expectedOutput = """
                Itinerary for MainPackage
                Destinations:-
                1. Dest1
                	Activities:-
                	a.	A1D1 - Desc1D1
                		Cost: 100.0
                		Capacity: 10
                		Vacancy: 10
                2. Dest2
                	Activities:-
                	a.	A1D2 - Desc1D2
                		Cost: 50.0
                		Capacity: 25
                		Vacancy: 25
                	b.	A2D2 - Desc2D2
                		Cost: 50.0
                		Capacity: 75
                		Vacancy: 75
                """;
        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }

    private static TravelPackage getSamplePackage() {
        TravelPackage travelPackage = new TravelPackage("MainPackage", 100);
        Destination destination1 = new Destination("Dest1");
        destination1.addActivity("A1D1", "Desc1D1", 100.0, 10);
        Destination destination2 = new Destination("Dest2");
        destination2.addActivity("A1D2", "Desc1D2", 50.0, 25);
        destination2.addActivity("A2D2", "Desc2D2", 50.0, 75);
        travelPackage.addDestination(destination1);
        travelPackage.addDestination(destination2);
        return travelPackage;
    }

    @Test
    void printPassengers() {
    }
}