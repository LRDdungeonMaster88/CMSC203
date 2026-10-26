/*
 * Class: CMSC203
 * Instructor:
 * Description: Console driver for the Property Management application.
 *              Creates a ManagementCompany and runs a menu loop for
 *              adding properties, displaying them and showing total rent.
 * Due: 11/18/2026
 * Platform/compiler: Java 21 (OpenJDK) / javac
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *    Print your Name here: Daniel Miranda
 */

import java.util.Scanner;

/**
 * Driver class with main for the Property Management application.
 *
 * @author Daniel Miranda
 */
public class PropertyDriver {

    /**
     * Prints the menu.
     */
    public static void printMenu() {
        System.out.println("====================================");
        System.out.println("   Assignment 4: Property Manager   ");
        System.out.println("====================================");
        System.out.println("1 - Add Property");
        System.out.println("2 - Display All Properties");
        System.out.println("3 - Show Total Rent");
        System.out.println("4 - Exit");
        System.out.print("Enter choice (1-4): ");
    }

    /**
     * Reads a non-negative rent amount, asking again on bad data.
     *
     * @param input the Scanner to read from
     * @return the rent entered
     */
    public static double readRent(Scanner input) {
        while (true) {
            System.out.print("Enter monthly rent: ");
            String line = input.nextLine().trim();
            try {
                double rent = Double.parseDouble(line);
                if (rent < 0) {
                    System.out.println("Rent cannot be negative.");
                } else {
                    return rent;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid rent, enter a number.");
            }
        }
    }

    /**
     * Creates the company, then repeats a menu loop until the user chooses
     * Exit. Each choice calls the matching ManagementCompany method and
     * prints the result. The programmer's name is printed at the end.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        ManagementCompany company = new ManagementCompany("Campus Realty", "123-45-6789");
        Scanner input = new Scanner(System.in);
        int choice = 0;

        while (choice != 4) {
            printMenu();
            String line = input.hasNextLine() ? input.nextLine().trim() : "4";
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                choice = -1;
            }

            if (choice == 1) {
                System.out.print("Enter property name: ");
                String name = input.nextLine();
                System.out.print("Enter city: ");
                String city = input.nextLine();
                double rent = readRent(input);
                System.out.print("Enter owner name: ");
                String owner = input.nextLine();

                Property property = new Property(name, city, rent, owner);
                int result = company.addProperty(property);
                if (result == -1) {
                    System.out.println("Cannot add property: the company is full.");
                } else {
                    System.out.println("Property added at index " + result + ".");
                }
            } else if (choice == 2) {
                System.out.println(company.toString());
            } else if (choice == 3) {
                System.out.println("Total rent: $" + String.format("%.2f", company.totalRent()));
            } else if (choice == 4) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice, enter 1-4.");
            }
            System.out.println();
        }

        // Demonstrate the deep copy constructor before finishing.
        ManagementCompany backup = new ManagementCompany(company);
        System.out.println("Backup copy made with copy constructor: "
                + backup.getPropertyCount() + " properties, total rent $"
                + String.format("%.2f", backup.totalRent()));

        System.out.println("Programmer: Daniel Miranda");
        input.close();
    }
}
