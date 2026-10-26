/*
 * Class: CMSC203
 * Instructor:
 * Description: JUnit tests for the Property class (constructors, copy
 *              constructor, getters/setters and toString).
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
 * Tests the Property class.
 *
 * @author Daniel Miranda
 */
public class PropertyJUnitTestStudent {

    private Property property;

    /** Creates a fresh Property before every test so tests are independent. */
    @Before
    public void setUp() {
        property = new Property("Sunset Villa", "Rockville", 1500.00, "Alice Smith");
    }

    /** The default constructor sets empty text and zero rent. */
    @Test
    public void testDefaultConstructor() {
        Property p = new Property();
        assertEquals("", p.getPropertyName());
        assertEquals("", p.getCity());
        assertEquals(0.0, p.getRentAmount(), 0.001);
        assertEquals("", p.getOwner());
    }

    /** The parameterized constructor stores every value passed in. */
    @Test
    public void testParameterizedConstructor() {
        assertEquals("Sunset Villa", property.getPropertyName());
        assertEquals("Rockville", property.getCity());
        assertEquals(1500.00, property.getRentAmount(), 0.001);
        assertEquals("Alice Smith", property.getOwner());
    }

    /** The copy constructor copies all fields into a different object. */
    @Test
    public void testCopyConstructorCopiesFields() {
        Property copy = new Property(property);
        assertNotSame(property, copy);
        assertEquals(property.getPropertyName(), copy.getPropertyName());
        assertEquals(property.getCity(), copy.getCity());
        assertEquals(property.getRentAmount(), copy.getRentAmount(), 0.001);
        assertEquals(property.getOwner(), copy.getOwner());
    }

    /** Changing the original after copying must not change the copy. */
    @Test
    public void testCopyConstructorIsIndependent() {
        Property copy = new Property(property);
        property.setRentAmount(9999.0);
        property.setOwner("Someone Else");
        assertEquals(1500.00, copy.getRentAmount(), 0.001);
        assertEquals("Alice Smith", copy.getOwner());
    }

    /** Every setter stores its value and the matching getter returns it. */
    @Test
    public void testSetters() {
        property.setPropertyName("Lakeview");
        property.setCity("Gaithersburg");
        property.setRentAmount(2100.50);
        property.setOwner("Bob");
        assertEquals("Lakeview", property.getPropertyName());
        assertEquals("Gaithersburg", property.getCity());
        assertEquals(2100.50, property.getRentAmount(), 0.001);
        assertEquals("Bob", property.getOwner());
    }

    /** toString shows the rent with a dollar sign and two decimals. */
    @Test
    public void testToString() {
        assertEquals("Sunset Villa, Rockville, Rent: $1500.00, Owner: Alice Smith",
                property.toString());
    }
}
