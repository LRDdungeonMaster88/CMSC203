/*
 * Class: CMSC203
 * Instructor:
 * Description: JUnit tests for PropertyDriver. Each test feeds menu input
 *              to main through System.in and checks the printed output.
 * Due: 11/18/2026
 * Platform/compiler: Java 21 (OpenJDK) / javac, JUnit 4.13.2
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *    Print your Name here: Daniel Miranda
 */

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the console driver.
 *
 * @author Daniel Miranda
 */
public class PropertyDriverJUnitTestStudent {

    private InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayOutputStream captured;

    /** Saves the real System.in/out and starts capturing output. */
    @Before
    public void setUp() {
        originalIn = System.in;
        originalOut = System.out;
        captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));
    }

    /** Restores the real System.in/out after every test. */
    @After
    public void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    // Runs main with the given lines as keyboard input and returns the output.
    private String run(String... lines) {
        String input = String.join("\n", lines) + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        PropertyDriver.main(new String[0]);
        return captured.toString();
    }

    /** Choosing Exit right away prints goodbye and the programmer name. */
    @Test
    public void testExitImmediately() {
        String out = run("4");
        assertTrue(out.contains("Goodbye!"));
        assertTrue(out.contains("Programmer: Daniel Miranda"));
    }

    /** Adding a property reports index 0. */
    @Test
    public void testAddOneProperty() {
        String out = run("1", "Property-1", "Rockville", "3500", "Joe", "4");
        assertTrue(out.contains("Property added at index 0."));
    }

    /** Display shows the numbered properties and the total rent line. */
    @Test
    public void testDisplayAllProperties() {
        String out = run("1", "Property-1", "Rockville", "3500", "Joe",
                "1", "Property-2", "Gaithersburg", "2500", "Mary", "2", "4");
        assertTrue(out.contains("Management Company: Campus Realty (Tax ID: 123-45-6789)"));
        assertTrue(out.contains("  0: Property-1, Rockville, Rent: $3500.00, Owner: Joe"));
        assertTrue(out.contains("  1: Property-2, Gaithersburg, Rent: $2500.00, Owner: Mary"));
        assertTrue(out.contains("Total Rent: $6000.00"));
    }

    /** Menu option 3 prints the total rent. */
    @Test
    public void testShowTotalRent() {
        String out = run("1", "A", "Rockville", "3500", "Joe",
                "1", "B", "Gaithersburg", "2500", "Mary", "3", "4");
        assertTrue(out.contains("Total rent: $6000.00"));
    }

    /** The 6th property is rejected with the full message. */
    @Test
    public void testCompanyFull() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append("1\nP").append(i).append("\nCity\n1000\nOwner\n");
        }
        sb.append("4\n");
        System.setIn(new ByteArrayInputStream(sb.toString().getBytes()));
        PropertyDriver.main(new String[0]);
        String out = captured.toString();
        assertTrue(out.contains("Property added at index 4."));
        assertTrue(out.contains("Cannot add property: the company is full."));
    }

    /** A number outside 1-4 and non-numeric text both give the invalid message. */
    @Test
    public void testInvalidChoice() {
        String out = run("9", "abc", "4");
        int first = out.indexOf("Invalid choice, enter 1-4.");
        int last = out.lastIndexOf("Invalid choice, enter 1-4.");
        assertTrue(first >= 0);
        assertTrue(last > first);
    }

    /** A bad rent value is rejected and asked again until a valid number is typed. */
    @Test
    public void testInvalidRentIsReprompted() {
        String out = run("1", "A", "Rockville", "abc", "-5", "1200", "Joe", "4");
        assertTrue(out.contains("Invalid rent, enter a number."));
        assertTrue(out.contains("Rent cannot be negative."));
        assertTrue(out.contains("Property added at index 0."));
    }
}
