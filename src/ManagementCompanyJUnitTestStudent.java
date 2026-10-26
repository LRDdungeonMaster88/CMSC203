/*
 * Class: CMSC203
 * Instructor:
 * Description: JUnit tests for the ManagementCompany class (adding
 *              properties, total rent, full array, deep copy, toString).
 * Due: 11/18/2026
 * Platform/compiler: Java 21 (OpenJDK) / javac, JUnit 4.13.2
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *    Print your Name here: Daniel Miranda
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the ManagementCompany class.
 *
 * @author Daniel Miranda
 */
public class ManagementCompanyJUnitTestStudent {

    private ManagementCompany mc;

    /** Creates a fresh, empty company before every test. */
    @Before
    public void setUp() {
        mc = new ManagementCompany("Campus Realty", "123-45-6789");
    }

    /** A new company has no properties and zero total rent. */
    @Test
    public void testNewCompanyIsEmpty() {
        assertEquals(0, mc.getPropertyCount());
        assertEquals(0.0, mc.totalRent(), 0.001);
    }

    /** addProperty returns consecutive indexes and totalRent sums the rents. */
    @Test
    public void testAddPropertyAndTotalRent() {
        int index1 = mc.addProperty(new Property("Apt 1", "Rockville", 1200.0, "Bob"));
        int index2 = mc.addProperty(new Property("Apt 2", "Rockville", 1500.0, "Carol"));
        assertEquals(0, index1);
        assertEquals(1, index2);
        assertEquals(2, mc.getPropertyCount());
        assertEquals(2700.0, mc.totalRent(), 0.001);
    }

    /** The company stores a copy, so changing the caller's object has no effect. */
    @Test
    public void testAddPropertyStoresCopy() {
        Property p = new Property("Apt 1", "Rockville", 1200.0, "Bob");
        mc.addProperty(p);
        p.setRentAmount(5000.0);
        assertNotSame(p, mc.getProperty(0));
        assertEquals(1200.0, mc.totalRent(), 0.001);
    }

    /** Adding to a full company returns -1 and leaves the count unchanged. */
    @Test
    public void testAddPropertyWhenFull() {
        for (int i = 0; i < ManagementCompany.MAX_PROPERTIES; i++) {
            assertEquals(i, mc.addProperty(new Property("P" + i, "City", 1000.0 + i, "Owner")));
        }
        int result = mc.addProperty(new Property("Extra", "City", 2000.0, "OwnerX"));
        assertEquals(-1, result);
        assertEquals(ManagementCompany.MAX_PROPERTIES, mc.getPropertyCount());
    }

    /** The copy constructor makes a deep copy with equal values and new objects. */
    @Test
    public void testManagementCompanyCopyConstructor() {
        mc.addProperty(new Property("Apt 1", "Rockville", 1200.0, "Bob"));
        mc.addProperty(new Property("Apt 2", "Rockville", 1500.0, "Carol"));

        ManagementCompany copy = new ManagementCompany(mc);

        assertEquals(mc.getName(), copy.getName());
        assertEquals(mc.getTaxId(), copy.getTaxId());
        assertEquals(mc.getPropertyCount(), copy.getPropertyCount());
        assertEquals(mc.totalRent(), copy.totalRent(), 0.001);
        assertNotSame(mc.getProperty(0), copy.getProperty(0));
        assertNotSame(mc.getProperty(1), copy.getProperty(1));
    }

    /** Changing a property in the original must not change the deep copy. */
    @Test
    public void testDeepCopyIsIndependent() {
        mc.addProperty(new Property("Apt 1", "Rockville", 1200.0, "Bob"));
        ManagementCompany copy = new ManagementCompany(mc);
        mc.getProperty(0).setRentAmount(9999.0);
        assertEquals(1200.0, copy.totalRent(), 0.001);
    }

    /** toString lists the header, numbered properties and the total rent. */
    @Test
    public void testToString() {
        mc.addProperty(new Property("Property-1", "Rockville", 3500, "Joe"));
        mc.addProperty(new Property("Property-2", "Gaithersburg", 2500, "Mary"));
        String expected = "Management Company: Campus Realty (Tax ID: 123-45-6789)\n"
                + "Properties:\n"
                + "  0: Property-1, Rockville, Rent: $3500.00, Owner: Joe\n"
                + "  1: Property-2, Gaithersburg, Rent: $2500.00, Owner: Mary\n"
                + "Total Rent: $6000.00";
        assertEquals(expected, mc.toString());
    }
}
