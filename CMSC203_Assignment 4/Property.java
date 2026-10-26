/*
 * Class: CMSC203
 * Instructor:
 * Description: Property represents a single rental property with a name,
 *              city, monthly rent amount and owner. Includes default,
 *              parameterized and copy constructors.
 * Due: 11/18/2026
 * Platform/compiler: Java 21 (OpenJDK) / javac
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *    Print your Name here: Daniel Miranda
 */

/**
 * Represents a single rental property.
 *
 * @author Daniel Miranda
 */
public class Property {

    private String propertyName;
    private String city;
    private double rentAmount;
    private String owner;

    /**
     * Default constructor. Sets text fields to empty strings and the
     * rent amount to 0.0.
     */
    public Property() {
        propertyName = "";
        city = "";
        rentAmount = 0.0;
        owner = "";
    }

    /**
     * Parameterized constructor. Stores the values passed in for each
     * field of the new property.
     *
     * @param propertyName name of the property
     * @param city         city where the property is located
     * @param rentAmount   monthly rent in dollars
     * @param owner        name of the owner
     */
    public Property(String propertyName, String city, double rentAmount, String owner) {
        this.propertyName = propertyName;
        this.city = city;
        this.rentAmount = rentAmount;
        this.owner = owner;
    }

    /**
     * Copy constructor. Builds a new, independent Property that has the
     * same field values as an existing one.
     *
     * @param other the property to copy
     */
    public Property(Property other) {
        this.propertyName = other.propertyName;
        this.city = other.city;
        this.rentAmount = other.rentAmount;
        this.owner = other.owner;
    }

    /** @return the property name */
    public String getPropertyName() {
        return propertyName;
    }

    /** @return the city */
    public String getCity() {
        return city;
    }

    /** @return the monthly rent amount */
    public double getRentAmount() {
        return rentAmount;
    }

    /** @return the owner's name */
    public String getOwner() {
        return owner;
    }

    /** @param propertyName the new property name */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /** @param city the new city */
    public void setCity(String city) {
        this.city = city;
    }

    /** @param rentAmount the new monthly rent */
    public void setRentAmount(double rentAmount) {
        this.rentAmount = rentAmount;
    }

    /** @param owner the new owner name */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Joins the fields into one line, with the rent shown with two
     * decimal places and a dollar sign, separated by commas.
     *
     * @return formatted description of the property
     */
    @Override
    public String toString() {
        return propertyName + ", " + city + ", Rent: $"
                + String.format("%.2f", rentAmount) + ", Owner: " + owner;
    }
}
