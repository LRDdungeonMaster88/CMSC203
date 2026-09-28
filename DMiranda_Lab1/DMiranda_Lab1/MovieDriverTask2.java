/**
 * Assignment Name: Lab1 Movie Driver Task 2
 * File Name: MovieDriverTask2.java
 * Author: Daniel Miranda
 * Class: CMSC 203
 * Professor: Professor Grigoriy Grinberg
 * Description: This program creates Movie objects by allowing the user to enter the movie’s 
 * title, rating, and number of tickets sold. After collecting the information, the program
 * displays the details of the movie. It continues creating additional Movie objects 
 * until the user chooses not to create another one.
 */


import java.util.Scanner;

public class MovieDriverTask2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String again;

        do {
            // Get the movie details from the user
            System.out.print("Movie title: ");
            String title = input.nextLine();

            System.out.print("Movie rating: ");
            String rating = input.nextLine();

            System.out.print("Tickets sold: ");
            int tickets = Integer.parseInt(input.nextLine().trim());

            // Build the movie using the setters
            Movie movie = new Movie();
            movie.setTitle(title);
            movie.setRating(rating);
            movie.setSoldTickets(tickets);

            // Show the result
            System.out.println();
            System.out.println(movie);

            // Ask whether to keep going
            System.out.print("\nAdd another movie? (y/n): ");
            again = input.nextLine().trim();
            System.out.println();

        } while (!again.equalsIgnoreCase("n"));

        input.close();
    }
}
