/*
 * Class: CMSC203
 * Instructor:
 * Description: ManagementCompany holds a fixed-size array of Property
 *              objects (aggregation) and supports adding properties,
 *              totaling rent and deep copying.
 * Due: 11/18/2026
 * Platform/compiler: Java 21 (OpenJDK) / javac
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *    Print your Name here: Daniel Miranda
 */

/**
 * A company that manages up to MAX_PROPERTIES rental properties.
 *
 * @author Daniel Miranda
 */
public class ManagementCompany {

    /** Maximum number of properties the company can hold. */
    public static final int MAX_PROPERTIES = 5;

    private String name;
    private String taxId;
    private Property[] properties;
    private int propertyCount;

    /**
     * Parameterized constructor. Sets the name and tax ID and creates an
     * empty array with room for MAX_PROPERTIES properties.
     *
     * @param name  company name
     * @param taxId company tax ID
     */
    public ManagementCompany(String name, String taxId) {
        this.name = name;
        this.taxId = taxId;
        this.properties = new Property[MAX_PROPERTIES];
        this.propertyCount = 0;
    }

    /**
     * Copy constructor (deep copy). Every Property in the new company is a
     * brand-new object made with the Property copy constructor, so the two
     * companies never share Property objects.
     *
     * @param other the company to copy
     */
    public ManagementCompany(ManagementCompany other) {
        this.name = other.name;
        this.taxId = other.taxId;
        this.properties = new Property[MAX_PROPERTIES];
        this.propertyCount = other.propertyCount;
        for (int i = 0; i < propertyCount; i++) {
            this.properties[i] = new Property(other.properties[i]);
        }
    }

    /**
     * Adds a copy of the property in the next free slot. Properties are
     * added in order with no gaps, so the next free slot is always at index
     * propertyCount. If that index is past the end of the array, there is
     * no room and -1 is returned.
     *
     * @param p the property to add
     * @return the index used, or -1 if the array is full
     */
    public int addProperty(Property p) {
        if (propertyCount >= MAX_PROPERTIES) {
            return -1;
        }
        int index = propertyCount;
        properties[index] = new Property(p);
        propertyCount++;
        return index;
    }

    /**
     * Adds up the rent of only the filled slots (0 to propertyCount - 1).
     *
     * @return the sum of all property rents
     */
    public double totalRent() {
        double total = 0.0;
        for (int i = 0; i < propertyCount; i++) {
            total += properties[i].getRentAmount();
        }
        return total;
    }

    /** @return the number of properties currently stored */
    public int getPropertyCount() {
        return propertyCount;
    }

    /** @return the company name */
    public String getName() {
        return name;
    }

    /** @return the company tax ID */
    public String getTaxId() {
        return taxId;
    }

    /**
     * Returns the property stored at an index.
     *
     * @param index position in the array
     * @return the Property at that index, or null if the index is not filled
     */
    public Property getProperty(int index) {
        if (index < 0 || index >= propertyCount) {
            return null;
        }
        return properties[index];
    }

    /**
     * Builds a multi-line report: a header with the company name and tax
     * ID, one numbered line per property, and a final line with total rent.
     *
     * @return the formatted company report
     */
    @Override
    public String toString() {
        String result = "Management Company: " + name + " (Tax ID: " + taxId + ")";
        result += "\nProperties:";
        for (int i = 0; i < propertyCount; i++) {
            result += "\n  " + i + ": " + properties[i].toString();
        }
        result += "\nTotal Rent: $" + String.format("%.2f", totalRent());
        return result;
    }
}
