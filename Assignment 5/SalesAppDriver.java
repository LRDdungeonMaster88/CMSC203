/*
 * Class: CMSC203
 * Instructor: Grigoriy Grinberg
 * Description: Console driver: prompts for file names, processes data, writes summary.
 * Due: 11/16/2026
 * Platform/compiler: <e.g., Eclipse 2024 / JDK 21>
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *    Print your Name here: Daniel Miranda
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Console driver for the Store Sales Data Processor.
 */
public class SalesAppDriver {

    /**
     * Asks for input/output file names, prints results and saves a summary.
     * @param args not used
     */
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter input file name: ");
        String inFile = kb.nextLine().trim();
        System.out.print("Enter output file name: ");
        String outFile = kb.nextLine().trim();

        try {
            double[][] data = SalesFileIO.readSalesData(inFile);

            System.out.printf("Total sales: %.2f%n", SalesDataUtility.getTotal(data));
            System.out.printf("Average sale: %.2f%n", SalesDataUtility.getAverage(data));
            System.out.printf("Highest sale: %.2f%n", SalesDataUtility.getHighestInArray(data));
            System.out.printf("Lowest sale: %.2f%n", SalesDataUtility.getLowestInArray(data));
            for (int r = 0; r < data.length; r++) {
                System.out.printf("Row %d total: %.2f%n", r,
                        SalesDataUtility.getRowTotal(data, r));
            }
            for (int c = 0; c < SalesDataUtility.getMaxColumns(data); c++) {
                System.out.printf("Column %d total: %.2f%n", c,
                        SalesDataUtility.getColumnTotal(data, c));
            }

            SalesFileIO.writeSummary(outFile, data);
            System.out.println("Summary written to " + outFile);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Input file not found: " + inFile);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid number in data file.");
        } catch (IOException e) {
            System.out.println("ERROR writing summary: " + e.getMessage());
        }

        System.out.println("Programmer: Daniel Miranda");
        kb.close();
    }
}
