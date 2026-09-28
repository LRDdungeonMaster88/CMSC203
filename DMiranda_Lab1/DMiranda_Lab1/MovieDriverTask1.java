/**
 * Assignment Name: MovieDriver Task 1 Lab 1
 * File Name: MovieDriverTask1.java
 * Author: Daniel Miranda
 * Class: CMSC 203
 * Professor: Professor Grigoriy Grinberg
 * Due Date: 09/28/2026
 * Description: This program creates a Movie object that allows the user to enter the movie’s
 * title, rating, and number of tickets sold. It then displays the movie’s information.
 */

import java.util.Scanner;

public class MovieDriverTask1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String title = readRequiredLine(input, "Movie title: ");
        String rating = readRequiredLine(input, "Movie rating: ");
        int tickets = readTicketsSold(input, "Tickets sold: ");

        Movie movie = new Movie(title, rating, tickets);

        System.out.println();
        System.out.println(movie);

        input.close();
    }

    private static String readRequiredLine(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!input.hasNextLine()) {
                return "";
            }

            String value = input.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Please enter a value.");
        }
    }

    private static int readTicketsSold(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!input.hasNextLine()) {
                return 0;
            }

            String value = input.nextLine().trim();
            try {
                int tickets = Integer.parseInt(value);
                if (tickets >= 0) {
                    return tickets;
                }
            } catch (NumberFormatException ignored) {
            }

            System.out.println("Please enter a non-negative whole number.");
        }
    }
}
