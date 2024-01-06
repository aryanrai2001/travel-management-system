package me.aryanrai.tms;

import me.aryanrai.tms.controller.TravelManagementSystem;
import me.aryanrai.tms.model.Passenger;

import java.io.*;
import java.util.Scanner;

public class Driver {

    private static final String path = System.getenv("APPDATA") + "\\aryanrai\\TravelManagementSystem\\";
    private static String profileName = null;

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

    private static TravelManagementSystem loadFromFile(Scanner scan) {
        TravelManagementSystem tms;
        System.out.println("Do you want to load a profile? (y/n)");
        char confirm = scan.nextLine().charAt(0);
        if (confirm != 'y' && confirm != 'Y') {
            System.out.println("New profile created!");
            tms = new TravelManagementSystem();
        }
        else {
            while (true) {
                File file = new File(path);
                boolean directoryExists = file.exists();
                if (!directoryExists)
                    directoryExists = file.mkdirs();

                if (!directoryExists) {
                    System.out.println("Error in creating the directory!");
                    System.out.println("New profile created!");
                    tms = new TravelManagementSystem();
                    break;
                }
                else {
                    File[] profiles = file.listFiles();
                    assert profiles != null;
                    if (profiles.length == 0) {
                        System.out.println("No profiles found!");
                        System.out.println("New profile created!");
                        tms = new TravelManagementSystem();
                        break;
                    }
                    System.out.println("Available profiles:-");
                    int index = 1;
                    for (File profile : profiles) {
                        if (profile.getName().endsWith(".ser"))
                            System.out.println(index++ + ". " + profile.getName().substring(0, profile.getName().length() - 4));
                    }
                    System.out.print("\nEnter the name of the profile to load: ");
                    String profile = scan.nextLine();
                    if (profile.isEmpty()) {
                        System.out.println("New profile created!");
                        tms = new TravelManagementSystem();
                        break;
                    }
                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path + profile + ".ser"));
                        tms = (TravelManagementSystem) objectInputStream.readObject();
                        profileName = profile;
                        break;
                    } catch (Exception e) {
                        System.out.println("Profile not found! Please try again [Enter to cancel].");
                    }
                }
            }
        }
        return tms;
    }

    private static void saveToFile(TravelManagementSystem tms, Scanner scan) {
        while (profileName == null) {
            System.out.print("Enter the name of the profile to save: ");
            String profile = scan.nextLine();
            if (!profile.isEmpty())
                profileName = profile;
            else
                System.out.println("Profile name cannot be empty!");
        }
        try {
            File file = new File(path);
            if (!file.exists())
                if (!file.mkdirs())
                    throw new IOException();
            FileOutputStream fileOutputStream = new FileOutputStream(path + profileName + ".ser");
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

    private static int searchForPassenger(Scanner scan, TravelManagementSystem tms) {
        System.out.print("Enter the name of the Passenger to search for: ");
        String name = scan.nextLine();
        if (!tms.listPassengersWithName(name)) {
            return -1;
        }
        System.out.print("Enter the Passenger number: ");
        int passengerNumber = scan.nextInt();
        scan.nextLine();
        return passengerNumber;
    }

    private static void printPassengerDetails(Scanner scan, TravelManagementSystem tms) {
        int passengerNumber = searchForPassenger(scan, tms);
        if (passengerNumber == -1)
            return;
        tms.printPassengerDetails(passengerNumber);
    }

    private static void signUpForActivity(Scanner scan, TravelManagementSystem tms) {
        int passengerNumber = searchForPassenger(scan, tms);
        if (passengerNumber == -1)
            return;
        if (!tms.listAvailableActivities(passengerNumber))
            return;
        System.out.print("Enter the name of the Activity: ");
        String activityName = scan.nextLine();
        System.out.println("\nPassenger Details:-");
        System.out.println("Name: " + tms.getPassenger(passengerNumber).getName());
        System.out.println("Activity Name: " + activityName);
        System.out.println("Are you sure you want to sign up for this Activity? (y/n)");
        char confirm = scan.nextLine().charAt(0);
        if (confirm != 'y' && confirm != 'Y')
            return;
        tms.signUpForActivity(passengerNumber, activityName);
    }

    private static void signUpForPackage(Scanner scan, TravelManagementSystem tms) {
        int passengerNumber = searchForPassenger(scan, tms);
        if (passengerNumber == -1)
            return;
        System.out.println("Choose the Travel Package:-");
        if (tms.listTravelPackages()) {
            System.out.print("Enter your choice: ");
            int travelPackageChoice = scan.nextInt();
            scan.nextLine();
            System.out.println("\nPassenger Details:-");
            System.out.println("Name: " + tms.getPassenger(passengerNumber).getName());
            System.out.println("Travel Package Name: " + tms.getTravelPackage(travelPackageChoice).getName());
            System.out.println("Are you sure you want to sign up for this Travel Package? (y/n)");
            char confirm = scan.nextLine().charAt(0);
            if (confirm != 'y' && confirm != 'Y')
                return;
            tms.signUpForPackage(passengerNumber, travelPackageChoice);
        }
    }

    private static void addPassenger(Scanner scan, TravelManagementSystem tms) {
        System.out.print("Enter the name of the Passenger: ");
        String name = scan.nextLine().trim();
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
        System.out.println("Balance: " + balance);
        System.out.println("Type: " + type);
        System.out.println("Are you sure you want to add this Passenger? (y/n)");
        char confirm = scan.nextLine().charAt(0);
        if (confirm != 'y' && confirm != 'Y')
            return;
        tms.addPassenger(name, balance, type);
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
        TravelManagementSystem tms = loadFromFile(scan);
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
                    saveToFile(tms, scan);
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
        scan.close();
    }
}
