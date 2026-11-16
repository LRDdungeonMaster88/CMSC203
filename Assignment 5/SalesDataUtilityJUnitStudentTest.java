/*
 * Class: CMSC203
 * Instructor: Grigoriy Grinberg
 * Description: JUnit tests for SalesDataUtility using ragged arrays.
 * Due: 11/16/2026
 * Platform/compiler: <e.g., Eclipse 2024 / JDK 21>
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *    Print your Name here: Daniel Miranda
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Student JUnit tests for SalesDataUtility.
 */
public class SalesDataUtilityJUnitStudentTest {

    private double[][] sample = {
        {100.50, 200.75, 150.00},
        {80.00, 90.00},
        {220.10, 130.50, 300.00, 50.25}
    };

    @Test
    public void testGetTotal() {
        assertEquals(1322.10, SalesDataUtility.getTotal(sample), 0.001);
    }

    @Test
    public void testGetAverage() {
        assertEquals(146.90, SalesDataUtility.getAverage(sample), 0.001);
    }

    @Test
    public void testGetRowTotal() {
        assertEquals(451.25, SalesDataUtility.getRowTotal(sample, 0), 0.001);
        assertEquals(170.00, SalesDataUtility.getRowTotal(sample, 1), 0.001);
        assertEquals(700.85, SalesDataUtility.getRowTotal(sample, 2), 0.001);
    }

    @Test
    public void testGetColumnTotalRagged() {
        assertEquals(400.60, SalesDataUtility.getColumnTotal(sample, 0), 0.001);
        assertEquals(421.25, SalesDataUtility.getColumnTotal(sample, 1), 0.001);
        assertEquals(450.00, SalesDataUtility.getColumnTotal(sample, 2), 0.001);
        assertEquals(50.25, SalesDataUtility.getColumnTotal(sample, 3), 0.001);
        assertEquals(0.0, SalesDataUtility.getColumnTotal(sample, 9), 0.001);
    }

    @Test
    public void testHighestLowestInRow() {
        assertEquals(200.75, SalesDataUtility.getHighestInRow(sample, 0), 0.001);
        assertEquals(100.50, SalesDataUtility.getLowestInRow(sample, 0), 0.001);
        assertEquals(300.00, SalesDataUtility.getHighestInRow(sample, 2), 0.001);
        assertEquals(50.25, SalesDataUtility.getLowestInRow(sample, 2), 0.001);
    }

    @Test
    public void testHighestLowestInArray() {
        assertEquals(300.00, SalesDataUtility.getHighestInArray(sample), 0.001);
        assertEquals(50.25, SalesDataUtility.getLowestInArray(sample), 0.001);
    }
}
