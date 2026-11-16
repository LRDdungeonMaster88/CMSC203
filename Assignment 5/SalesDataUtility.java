/*
 * Class: CMSC203
 * Instructor: Grigoriy Grinberg
 * Description: Static utility methods for processing a ragged 2D array of sales data.
 * Due: 11/16/2026
 * Platform/compiler: <e.g., Eclipse 2024 / JDK 21>
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *    Print your Name here: Daniel Miranda
 */

/**
 * Static utility methods for ragged double[][] sales data.
 * Each row is a department; each column is a day. Rows may differ in length.
 */
public class SalesDataUtility {

    /** Utility class: no instances. */
    private SalesDataUtility() {
    }

    /**
     * Adds up every value in the array using nested loops.
     * @param data ragged sales array
     * @return sum of all values
     */
    public static double getTotal(double[][] data) {
        double total = 0;
        for (int r = 0; r < data.length; r++) {
            for (int c = 0; c < data[r].length; c++) {
                total += data[r][c];
            }
        }
        return total;
    }

    /**
     * Computes the average over all values (not per row).
     * @param data ragged sales array
     * @return average value, or 0 if there are no values
     */
    public static double getAverage(double[][] data) {
        int count = 0;
        for (int r = 0; r < data.length; r++) {
            count += data[r].length;
        }
        return count == 0 ? 0 : getTotal(data) / count;
    }

    /**
     * Adds up the values in one row.
     * @param data ragged sales array
     * @param row row index
     * @return row total, or 0 if the row index is invalid
     */
    public static double getRowTotal(double[][] data, int row) {
        if (row < 0 || row >= data.length) {
            return 0;
        }
        double total = 0;
        for (int c = 0; c < data[row].length; c++) {
            total += data[row][c];
        }
        return total;
    }

    /**
     * Adds up one column, skipping rows too short to have that column.
     * @param data ragged sales array
     * @param col column index
     * @return column total
     */
    public static double getColumnTotal(double[][] data, int col) {
        double total = 0;
        for (int r = 0; r < data.length; r++) {
            if (col >= 0 && col < data[r].length) {
                total += data[r][col];
            }
        }
        return total;
    }

    /**
     * Finds the largest value in one row.
     * @param data ragged sales array
     * @param row row index
     * @return highest value in the row, or 0 if the row is invalid or empty
     */
    public static double getHighestInRow(double[][] data, int row) {
        if (row < 0 || row >= data.length || data[row].length == 0) {
            return 0;
        }
        double highest = data[row][0];
        for (int c = 1; c < data[row].length; c++) {
            if (data[row][c] > highest) {
                highest = data[row][c];
            }
        }
        return highest;
    }

    /**
     * Finds the smallest value in one row.
     * @param data ragged sales array
     * @param row row index
     * @return lowest value in the row, or 0 if the row is invalid or empty
     */
    public static double getLowestInRow(double[][] data, int row) {
        if (row < 0 || row >= data.length || data[row].length == 0) {
            return 0;
        }
        double lowest = data[row][0];
        for (int c = 1; c < data[row].length; c++) {
            if (data[row][c] < lowest) {
                lowest = data[row][c];
            }
        }
        return lowest;
    }

    /**
     * Finds the largest value anywhere in the array.
     * @param data ragged sales array
     * @return highest value, or 0 if there are no values
     */
    public static double getHighestInArray(double[][] data) {
        boolean found = false;
        double highest = 0;
        for (int r = 0; r < data.length; r++) {
            for (int c = 0; c < data[r].length; c++) {
                if (!found || data[r][c] > highest) {
                    highest = data[r][c];
                    found = true;
                }
            }
        }
        return highest;
    }

    /**
     * Finds the smallest value anywhere in the array.
     * @param data ragged sales array
     * @return lowest value, or 0 if there are no values
     */
    public static double getLowestInArray(double[][] data) {
        boolean found = false;
        double lowest = 0;
        for (int r = 0; r < data.length; r++) {
            for (int c = 0; c < data[r].length; c++) {
                if (!found || data[r][c] < lowest) {
                    lowest = data[r][c];
                    found = true;
                }
            }
        }
        return lowest;
    }

    /**
     * Returns the length of the longest row (number of columns to report).
     * @param data ragged sales array
     * @return maximum row length
     */
    public static int getMaxColumns(double[][] data) {
        int max = 0;
        for (int r = 0; r < data.length; r++) {
            if (data[r].length > max) {
                max = data[r].length;
            }
        }
        return max;
    }
}
