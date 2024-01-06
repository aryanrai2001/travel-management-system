package me.aryanrai.tms.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PassengerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private static Passenger getSamplePassenger(Passenger.Type type) {
        TravelPackage travelPackage = new TravelPackage("Package1", 10);
        Destination destination = new Destination("Destination1");
        destination.addActivity("Activity1", "Desc1", 30.0, 10);
        destination.addActivity("Activity2", "Desc2", 20.0, 10);
        travelPackage.addDestination(destination);
        Passenger passenger = new Passenger("Passenger1", 1, 100.0, type);
        passenger.assignPackage(travelPackage);

        Activity activity1 = destination.getActivities().get(0);
        Activity activity2 = destination.getActivities().get(1);
        passenger.purchaseActivity(activity1);
        passenger.purchaseActivity(activity2);

        return passenger;
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void purchaseActivity() {
        TravelPackage travelPackage = new TravelPackage("Package1", 10);
        Destination destination = new Destination("Destination1");
        destination.addActivity("Activity1", "Desc1", 50.0, 10);
        travelPackage.addDestination(destination);
        Passenger passenger = new Passenger("Passenger1", 1, 100.0, Passenger.Type.STANDARD);
        passenger.assignPackage(travelPackage);

        Activity activity = destination.getActivities().getFirst();
        passenger.purchaseActivity(activity);

        assertTrue(passenger.getActivities().contains(activity));
    }

    @Test
    void purchaseActivityStandard() {
        TravelPackage travelPackage = new TravelPackage("Package1", 10);
        Destination destination = new Destination("Destination1");
        destination.addActivity("Activity1", "Desc1", 50.0, 10);
        travelPackage.addDestination(destination);
        Passenger passenger = new Passenger("Passenger1", 1, 100.0, Passenger.Type.STANDARD);
        passenger.assignPackage(travelPackage);

        Activity activity = destination.getActivities().getFirst();
        passenger.purchaseActivity(activity);

        assertEquals(passenger.getActivityPrices().getFirst(), 50.0);
        assertEquals(passenger.getBalance(), 50.0);
    }

    @Test
    void purchaseActivityGold() {
        TravelPackage travelPackage = new TravelPackage("Package1", 10);
        Destination destination = new Destination("Destination1");
        destination.addActivity("Activity1", "Desc1", 50.0, 10);
        travelPackage.addDestination(destination);
        Passenger passenger = new Passenger("Passenger1", 1, 100.0, Passenger.Type.GOLD);
        passenger.assignPackage(travelPackage);

        Activity activity = destination.getActivities().getFirst();
        passenger.purchaseActivity(activity);

        assertEquals(passenger.getActivityPrices().getFirst(), 45.0);
        assertEquals(passenger.getBalance(), 55.0);
    }

    @Test
    void purchaseActivityPremium() {
        TravelPackage travelPackage = new TravelPackage("Package1", 10);
        Destination destination = new Destination("Destination1");
        destination.addActivity("Activity1", "Desc1", 50.0, 10);
        travelPackage.addDestination(destination);
        Passenger passenger = new Passenger("Passenger1", 1, 100.0, Passenger.Type.PREMIUM);
        passenger.assignPackage(travelPackage);

        Activity activity = destination.getActivities().getFirst();
        passenger.purchaseActivity(activity);

        assertEquals(passenger.getActivityPrices().getFirst(), 0.0);
        assertEquals(passenger.getBalance(), 100.0);
    }

    @Test
    void printDetailsStandard() {
        Passenger passenger = getSamplePassenger(Passenger.Type.STANDARD);
        outContent.reset();
        passenger.printDetails();

        String expectedOutput = """
                Passenger1
                Number: 1
                Type: Standard
                Balance: 50.0
                Purchased Activities:
                	1. Activity1, Destination1
                	Price Paid: 30.0
                	2. Activity2, Destination1
                	Price Paid: 20.0
                """;
        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    void printDetailsGold() {
        Passenger passenger = getSamplePassenger(Passenger.Type.GOLD);
        outContent.reset();
        passenger.printDetails();

        String expectedOutput = """
                Passenger1
                Number: 1
                Type: Gold
                Balance: 55.0
                Purchased Activities:
                	1. Activity1, Destination1
                	Price Paid: 27.0
                	2. Activity2, Destination1
                	Price Paid: 18.0
                """;
        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    void printDetailsPremium() {
        Passenger passenger = getSamplePassenger(Passenger.Type.PREMIUM);
        outContent.reset();
        passenger.printDetails();

        String expectedOutput = """
                Passenger1
                Number: 1
                Type: Premium
                Purchased Activities:
                	1. Activity1, Destination1
                	2. Activity2, Destination1
                """;
        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    void printDetailsNoActivity() {
        Passenger passenger = new Passenger("Passenger1", 1, 100.0, Passenger.Type.STANDARD);
        outContent.reset();
        passenger.printDetails();

        String expectedOutput = """
                Passenger1
                Number: 1
                Type: Standard
                Balance: 100.0
                No Activities Purchased!
                """;
        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }
}