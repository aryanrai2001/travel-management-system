package me.aryanrai.tms.controller;

import me.aryanrai.tms.model.Passenger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TravelManagementSystemTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void listTravelPackagesEmpty() {
        TravelManagementSystem tms = new TravelManagementSystem();
        outContent.reset();
        assertFalse(tms.listTravelPackages());

        String expectedOutput = "No Travel Packages found!\n";

        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    void listTravelPackages() {
        TravelManagementSystem tms = new TravelManagementSystem();
        tms.createTravelPackage("Package1", 100);
        tms.createTravelPackage("Package2", 200);
        
        outContent.reset();
        assertTrue(tms.listTravelPackages());

        String expectedOutput = """
                1. Package1
                2. Package2
                """;
        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    void listAvailableActivities() {
        TravelManagementSystem tms = new TravelManagementSystem();
        tms.createTravelPackage("Package1", 100);
        tms.addDestination(1, "Destination1");
        tms.addActivity("Destination1", "Activity1", "Desc1", 100, 100);
        tms.addActivity("Destination1", "Activity2", "Desc2", 100, 100);
        tms.addPassenger("Passenger1", 100, Passenger.Type.GOLD);
        tms.signUpForPackage(1, 1);
        tms.signUpForActivity(1, "Activity1");
        tms.signUpForActivity(1, "Activity2");
        outContent.reset();
        assertTrue(tms.listAvailableActivities(1));

        String expectedOutput = """
                List of Activities available on travel package Package1:-
                - Activity1 - Desc1
                		Cost: 100.0
                		Capacity: 100
                		Vacancy: 99
                - Activity2 - Desc2
                		Cost: 100.0
                		Capacity: 100
                		Vacancy: 100
                """;
        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    void listPassengersWithName() {
        TravelManagementSystem tms = new TravelManagementSystem();
        tms.createTravelPackage("Package1", 100);
        tms.addDestination(1, "Destination1");
        tms.addPassenger("PassengerRed1", 100, Passenger.Type.GOLD);
        tms.addPassenger("PassengerBlue1", 100, Passenger.Type.GOLD);
        tms.addPassenger("PassengerRed2", 100, Passenger.Type.PREMIUM);
        tms.addPassenger("PassengerBlue2", 100, Passenger.Type.PREMIUM);
        tms.signUpForPackage(1, 1);
        tms.signUpForPackage(2, 1);
        tms.signUpForPackage(3, 1);
        tms.signUpForPackage(4, 1);
        outContent.reset();
        assertTrue(tms.listPassengersWithName("PassengerRed"));

        String expectedOutput = """
                1 - PassengerRed1
                3 - PassengerRed2
                """;
        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    void getTravelPackage() {
        String secondPackageName = "Package2";
        TravelManagementSystem tms = new TravelManagementSystem();
        tms.createTravelPackage("Package1", 100);
        tms.createTravelPackage(secondPackageName, 100);
        tms.createTravelPackage("Package3", 100);
        assertEquals(secondPackageName, tms.getTravelPackage(2).getName());
    }

    @Test
    void getPassenger() {
        String secondPassengerName = "Passenger2";
        TravelManagementSystem tms = new TravelManagementSystem();
        tms.addPassenger("Passenger1", 100, Passenger.Type.STANDARD);
        tms.addPassenger(secondPassengerName, 100, Passenger.Type.STANDARD);
        tms.addPassenger("Passenger3", 100, Passenger.Type.STANDARD);
        assertEquals(secondPassengerName, tms.getPassenger(2).getName());
    }

    @Test
    void createTravelPackage() {
        String packageName = "Package1";
        TravelManagementSystem tms = new TravelManagementSystem();
        tms.createTravelPackage(packageName, 100);
        assertEquals(packageName, tms.getTravelPackages().getFirst().getName());
    }
    @Test
    void addDestination() {
        String destinationName = "Destination1";
        TravelManagementSystem tms = new TravelManagementSystem();
        tms.createTravelPackage("Package1", 100);
        tms.addDestination(1, destinationName);
        assertEquals(destinationName, tms.getTravelPackages().getFirst().getDestinations().getFirst().getName());
    }

    @Test
    void addActivity() {
        String activityName = "Activity1";
        TravelManagementSystem tms = new TravelManagementSystem();
        tms.createTravelPackage("Package1", 100);
        tms.addDestination(1, "Destination1");
        tms.addActivity("Destination1", activityName, "Desc1", 100, 100);
        assertEquals(activityName, tms.getTravelPackages().getFirst().getDestinations().getFirst().getActivities().getFirst().getName());
    }

    @Test
    void addPassenger() {
        String passengerName = "Passenger1";
        TravelManagementSystem tms = new TravelManagementSystem();
        tms.addPassenger(passengerName, 100, Passenger.Type.STANDARD);
        assertEquals(passengerName, tms.getPassengers().getFirst().getName());
    }

    @Test
    void signUpForPackage() {
        String passengerName = "Passenger1";
        TravelManagementSystem tms = new TravelManagementSystem();
        tms.createTravelPackage("Package1", 100);
        tms.addDestination(1, "Destination1");
        tms.addActivity("Destination1", "Activity1", "Desc1", 100, 100);
        tms.addPassenger(passengerName, 100, Passenger.Type.STANDARD);
        tms.signUpForPackage(1, 1);
        assertEquals(passengerName, tms.getTravelPackages().getFirst().getPassengers().getFirst().getName());
    }

    @Test
    void signUpForActivity() {
        String passengerName = "Passenger1";
        String activityName = "Activity1";
        TravelManagementSystem tms = new TravelManagementSystem();
        tms.createTravelPackage("Package1", 100);
        tms.addDestination(1, "Destination1");
        tms.addActivity("Destination1", activityName, "Desc1", 100, 100);
        tms.addPassenger(passengerName, 100, Passenger.Type.STANDARD);
        tms.signUpForPackage(1, 1);
        tms.signUpForActivity(1, activityName);
        assertEquals(passengerName, tms.getTravelPackages().getFirst().getDestinations().getFirst().getActivities().getFirst().getPassengers().getFirst().getName());
    }

    @Test
    void printPassengerDetails() {
        TravelManagementSystem tms = new TravelManagementSystem();
        tms.createTravelPackage("Package1", 100);
        tms.addDestination(1, "Destination1");
        tms.addActivity("Destination1", "Activity1", "Desc1", 50, 100);
        tms.addActivity("Destination1", "Activity2", "Desc2", 50, 100);
        tms.addPassenger("Passenger1", 100, Passenger.Type.GOLD);
        tms.signUpForPackage(1, 1);
        tms.signUpForActivity(1, "Activity1");
        tms.signUpForActivity(1, "Activity2");
        outContent.reset();
        tms.printPassengerDetails(1);

        String expectedOutput = """
                Passenger1
                Number: 1
                Type: Gold
                Balance: 10.0
                Purchased Activities:
                	1. Activity1, Destination1
                	Price Paid: 45.0
                	2. Activity2, Destination1
                	Price Paid: 45.0
                """;
        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    void printItinerary() {
        TravelManagementSystem tms = new TravelManagementSystem();
        tms.createTravelPackage("Package1", 100);
        tms.addDestination(1, "Destination1");
        tms.addActivity("Destination1", "Activity1", "Desc1", 50, 100);
        tms.addActivity("Destination1", "Activity2", "Desc2", 50, 100);
        tms.addPassenger("Passenger1", 100, Passenger.Type.GOLD);
        tms.signUpForPackage(1, 1);
        tms.signUpForActivity(1, "Activity1");
        tms.signUpForActivity(1, "Activity2");
        outContent.reset();
        tms.printItinerary(1);

        String expectedOutput = """
                Itinerary for Package1
                Destinations:-
                1. Destination1
                	Activities:-
                	a.	Activity1 - Desc1
                		Cost: 50.0
                		Capacity: 100
                		Vacancy: 99
                	b.	Activity2 - Desc2
                		Cost: 50.0
                		Capacity: 100
                		Vacancy: 99
                """;

        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    void printPassengers() {
        TravelManagementSystem tms = new TravelManagementSystem();
        tms.createTravelPackage("Package1", 100);
        tms.addDestination(1, "Destination1");
        tms.addPassenger("Passenger1", 100, Passenger.Type.GOLD);
        tms.addPassenger("Passenger2", 100, Passenger.Type.GOLD);
        tms.signUpForPackage(1, 1);
        tms.signUpForPackage(2, 1);
        outContent.reset();
        tms.printPassengers(1);

        String expectedOutput = """
                Passengers on Package1
                Capacity: 100
                Enrolled: 2
                Passenger Details:-
                1. Passenger1, 1
                2. Passenger2, 2
                """;

        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }
}