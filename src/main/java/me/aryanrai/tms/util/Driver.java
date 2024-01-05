package me.aryanrai.tms.util;

import me.aryanrai.tms.controller.TravelManagementSystem;
import me.aryanrai.tms.model.Passenger;

import java.io.*;
import java.util.Scanner;

public class Driver {

    private static void printMenu() {
        System.out.println("Menu:-");
        System.out.println("1. Create a Travel Package");
        System.out.println("2. Add a Destination to a Travel Package");
        System.out.println("3. Add an Activity to a Destination");
        System.out.println("4. Add a Passenger");
        System.out.println("5. Sign Up a Passenger for a Travel Package");
        System.out.println("6. Sign Up a Passenger for Activities");
        System.out.println("7. Print Details of a Passenger");
        System.out.println("8. Print Itinerary of a Travel Package");
        System.out.println("9. Print Passengers of a Travel Package");
        System.out.println("10. Save and Exit");
        System.out.print("Enter your choice: ");
    }

    private static TravelManagementSystem loadFromFile() {
        TravelManagementSystem tms;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("TMS.bin"));
            tms = (TravelManagementSystem) objectInputStream.readObject();
        } catch (Exception e) {
            tms = new TravelManagementSystem();
        }
        return tms;
    }

    private static void saveToFile(TravelManagementSystem tms) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("TMS.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tms);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error in saving the file!");
        }
    }

    private static void printPassengers(TravelManagementSystem tms, Scanner scan) {
        System.out.println("Choose the Travel Package:-");
        if (tms.listTravelPackages()) {
            System.out.print("Enter your choice: ");
            int travelPackageChoice = scan.nextInt();
            scan.nextLine();
            tms.printPassengers(travelPackageChoice);
        }
    }

    private static void printItinerary(TravelManagementSystem tms, Scanner scan) {
        System.out.println("Choose the Travel Package:-");
        if (tms.listTravelPackages()) {
            System.out.print("Enter your choice: ");
            int travelPackageChoice = scan.nextInt();
            scan.nextLine();
            tms.printItinerary(travelPackageChoice);
        }
    }

    private static void printPassengerDetails(Scanner scan, TravelManagementSystem tms) {
        System.out.print("Enter the name of the Passenger: ");
        String name = scan.nextLine();
        tms.printPassengerDetails(name);
    }

    private static void signUpForActivity(Scanner scan, TravelManagementSystem tms) {
        System.out.print("Enter the name of the Passenger: ");
        String name = scan.nextLine();
        if (!tms.listAvailableActivities(name))
            return;
        System.out.print("Enter the name of the Activity: ");
        String activityName = scan.nextLine();
        System.out.println("\nPassenger Details:-");
        System.out.println("Name: " + name);
        System.out.println("Activity Name: " + activityName);
        System.out.println("Are you sure you want to sign up this Passenger for the Activity? (y/n)");
        char confirm = scan.nextLine().charAt(0);
        if (confirm != 'y' && confirm != 'Y')
            return;
        tms.signUpForActivity(name, activityName);
    }

    private static void signUpForPackage(Scanner scan, TravelManagementSystem tms) {
        System.out.print("Enter the name of the Passenger: ");
        String name = scan.nextLine();
        System.out.println("Choose the Travel Package:-");
        if (tms.listTravelPackages()) {
            System.out.print("Enter your choice: ");
            int travelPackageChoice = scan.nextInt();
            scan.nextLine();
            System.out.println("\nPassenger Details:-");
            System.out.println("Name: " + name);
            System.out.println("Are you sure you want to sign up this Passenger for the Travel Package? (y/n)");
            char confirm = scan.nextLine().charAt(0);
            if (confirm != 'y' && confirm != 'Y')
                return;
            tms.signUpForPackage(name, travelPackageChoice);
        }
    }

    private static void addPassenger(Scanner scan, TravelManagementSystem tms) {
        System.out.print("Enter the name of the Passenger: ");
        String name = scan.nextLine();
        System.out.print("Enter the number of the Passenger: ");
        int number = scan.nextInt();
        scan.nextLine();
        System.out.print("Enter the balance of the Passenger: ");
        double balance = scan.nextDouble();
        scan.nextLine();
        System.out.println("Choose the type of the Passenger:-");
        System.out.println("1. Standard");
        System.out.println("2. Gold");
        System.out.println("3. Premium");
        System.out.print("Enter your choice: ");
        int typeChoice = scan.nextInt();
        scan.nextLine();
        if (typeChoice < 1 || typeChoice > 3) {
            System.out.println("Invalid Choice!");
            return;
        }
        Passenger.Type type = Passenger.Type.values()[typeChoice - 1];
        System.out.println("\nPassenger Details:-");
        System.out.println("Name: " + name);
        System.out.println("Number: " + number);
        System.out.println("Balance: " + balance);
        System.out.println("Type: " + type);
        System.out.println("Are you sure you want to add this Passenger? (y/n)");
        char confirm = scan.nextLine().charAt(0);
        if (confirm != 'y' && confirm != 'Y')
            return;
        tms.addPassenger(name, number, balance, type);
    }

    private static void addActivity(Scanner scan, TravelManagementSystem tms) {
        System.out.print("Enter the destination name: ");
        String destinationName = scan.nextLine();
        System.out.print("Enter the name of the Activity: ");
        String activityName = scan.nextLine();
        System.out.print("Enter the description of the Activity: ");
        String activityDescription = scan.nextLine();
        System.out.print("Enter the cost of the Activity: ");
        double activityCost = scan.nextDouble();
        scan.nextLine();
        System.out.print("Enter the capacity of the Activity: ");
        int activityCapacity = scan.nextInt();
        scan.nextLine();
        System.out.println("\nActivity Details:-");
        System.out.println("Name: " + activityName);
        System.out.println("Description: " + activityDescription);
        System.out.println("Cost: " + activityCost);
        System.out.println("Capacity: " + activityCapacity);
        System.out.println("Destination Name: " + destinationName);
        System.out.println("Are you sure you want to add this Activity to the Destination? (y/n)");
        char confirm = scan.nextLine().charAt(0);
        if (confirm != 'y' && confirm != 'Y')
            return;
        tms.addActivity(destinationName, activityName, activityDescription, activityCost, activityCapacity);
    }

    private static void addDestination(Scanner scan, TravelManagementSystem tms) {
        System.out.print("Enter the name of the Destination: ");
        String destinationName = scan.nextLine();
        System.out.println("Choose the Travel Package:-");
        if (tms.listTravelPackages()) {
            System.out.print("Enter your choice: ");
            int travelPackageChoice = scan.nextInt();
            scan.nextLine();
            System.out.println("\nDestination Details:-");
            System.out.println("Name: " + destinationName);
            System.out.println("Are you sure you want to add this Destination to the Travel Package? (y/n)");
            char confirm = scan.nextLine().charAt(0);
            if (confirm != 'y' && confirm != 'Y')
                return;
            tms.addDestination(travelPackageChoice, destinationName);
        }
    }

    private static void createTravelPackage(Scanner scan, TravelManagementSystem tms) {
        System.out.print("Enter the name of the Travel Package: ");
        String packageName = scan.nextLine();
        System.out.print("Enter the passenger capacity of the Travel Package: ");
        int passengerCapacity = scan.nextInt();
        scan.nextLine();
        System.out.println("\nTravel Package Details:-");
        System.out.println("Name: " + packageName);
        System.out.println("Passenger Capacity: " + passengerCapacity);
        System.out.println("Are you sure you want to create this Travel Package? (y/n)");
        char confirm = scan.nextLine().charAt(0);
        if (confirm != 'y' && confirm != 'Y')
            return;
        tms.createTravelPackage(packageName, passengerCapacity);
    }


    public static void main(String[] args) {
        System.out.println("Travel Management System!");
        Scanner scan = new Scanner(System.in);
        TravelManagementSystem tms = loadFromFile();
        boolean running = true;
        while (running) {
            System.out.println("<Press Enter to continue...>");
            scan.nextLine();
            printMenu();
            int choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1:
                    createTravelPackage(scan, tms);
                    break;
                case 2:
                    addDestination(scan, tms);
                    break;
                case 3:
                    addActivity(scan, tms);
                    break;
                case 4:
                    addPassenger(scan, tms);
                    break;
                case 5:
                    signUpForPackage(scan, tms);
                    break;
                case 6:
                    signUpForActivity(scan, tms);
                    break;
                case 7:
                    printPassengerDetails(scan, tms);
                    break;
                case 8:
                    printItinerary(tms, scan);
                    break;
                case 9:
                    printPassengers(tms, scan);
                    break;
                case 10:
                    running = false;
                    saveToFile(tms);
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
        scan.close();
    }
}
