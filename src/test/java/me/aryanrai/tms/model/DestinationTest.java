package me.aryanrai.tms.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DestinationTest {

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
    void addActivity() {
        String activityName = "Activity1";
        Destination destination = new Destination("Destination1");
        destination.addActivity(activityName, "Desc1", 50.0, 10);
        assertEquals(destination.getActivities().getFirst().getName(), activityName);
    }

    @Test
    void printDetails() {
        Destination destination = new Destination("Destination1");
        destination.addActivity("Activity1", "Desc1", 20.0, 75);
        destination.addActivity("Activity2", "Desc2", 30.0, 25);
        destination.printDetails();

        String expectedOutput = """
                Destination1
                	Activities:-
                	a.	Activity1 - Desc1
                		Cost: 20.0
                		Capacity: 75
                		Vacancy: 75
                	b.	Activity2 - Desc2
                		Cost: 30.0
                		Capacity: 25
                		Vacancy: 25
                """;

        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }
}