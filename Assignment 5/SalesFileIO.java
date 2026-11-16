/*
 * Class: CMSC203
 * Instructor: Grigoriy Grinberg
 * Description: File I/O helper: reads ragged sales data and writes a summary file.
 * Due: 11/16/2026
 * Platform/compiler: <e.g., Eclipse 2024 / JDK 21>
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *    Print your Name here: Daniel Miranda
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads sales data from a text file into a ragged array and writes summaries.
 */
public class SalesFileIO {

    /**
     * Reads each line, splits on whitespace, converts tokens to double and
     * builds a ragged array. Blank lines are skipped.
     * @param filename input file name
     * @return ragged double[][] of the sales data
     * @throws FileNotFoundException if the file cannot be opened
     * @throws NumberFormatException if a token is not a valid number
     */
    public static double[][] readSalesData(String filename)
            throws FileNotFoundException {
        ArrayList<double[]> rows = new ArrayList<>();
        Scanner in = new Scanner(new File(filename));
        try {
            while (in.hasNextLine()) {
                String line = in.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] tokens = line.split("\\s+");
                double[] row = new double[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    row[i] = Double.parseDouble(tokens[i]);
                }
                rows.add(row);
            }
        } finally {
            in.close();
        }
        double[][] data = new double[rows.size()][];
        for (int r = 0; r < data.length; r++) {
            data[r] = rows.get(r);
        }
        return data;
    }

    /**
     * Writes totals, averages, row/column totals and highest/lowest values.
     * @param filename output file name
     * @param data ragged sales array
     * @throws IOException if the file cannot be written
     */
    public static void writeSummary(String filename, double[][] data)
            throws IOException {
        PrintWriter out = new PrintWriter(new File(filename));
        try {
            out.printf("Total sales: %.2f%n", SalesDataUtility.getTotal(data));
            out.printf("Average sale: %.2f%n", SalesDataUtility.getAverage(data));
            out.printf("Highest sale: %.2f%n", SalesDataUtility.getHighestInArray(data));
            out.printf("Lowest sale: %.2f%n", SalesDataUtility.getLowestInArray(data));
            out.println();
            for (int r = 0; r < data.length; r++) {
                out.printf("Row %d total: %.2f%n", r, SalesDataUtility.getRowTotal(data, r));
            }
            out.println();
            for (int r = 0; r < data.length; r++) {
                out.printf("Row %d highest: %.2f, lowest: %.2f%n", r,
                        SalesDataUtility.getHighestInRow(data, r),
                        SalesDataUtility.getLowestInRow(data, r));
            }
            out.println();
            int cols = SalesDataUtility.getMaxColumns(data);
            for (int c = 0; c < cols; c++) {
                out.printf("Column %d total: %.2f%n", c, SalesDataUtility.getColumnTotal(data, c));
            }
            out.println();
            out.println("Programmer: Daniel Miranda");
        } finally {
            out.close();
        }
        if (out.checkError()) {
            throw new IOException("Error writing to " + filename);
        }
    }
}
