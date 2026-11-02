/*
 * Class: CMSC203
 * Instructor:
 * Description: JUnit 5 tests for every public method of CryptoManager
 *              (isStringInBounds, and the encryption/decryption methods of
 *              the Vigenere, Playfair and Caesar ciphers).
 * Due: MM/DD/YYYY
 * Platform/compiler: IntelliJ IDEA / Java 21 (JDK)
 * I pledge that I have completed the programming assignment independently.
 * I have not copied the code from a student or any source.
 * I have not given my code to any student.
 * Print your Name here: __________
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

/**
 * Student JUnit tests for the CryptoManager class.
 */
public class CryptoManagerTestStudent {

    private static final String NOT_IN_BOUNDS = "The selected string is not in bounds, Try again.";

    /** Prints the programmer's name at the end of the test output. */
    @AfterAll
    public static void printProgrammerName() {
        System.out.println("Programmer: __________"); // replace with your name
    }

    /* ---------- isStringInBounds ---------- */

    @Test
    public void testIsStringInBoundsValidSentence() {
        assertTrue(CryptoManager.isStringInBounds("MEET ME AT 5 PM!"));
    }

    @Test
    public void testIsStringInBoundsLowerCaseRejected() {
        assertFalse(CryptoManager.isStringInBounds("Meet me"));
    }

    @Test
    public void testIsStringInBoundsTildeRejected() {
        assertFalse(CryptoManager.isStringInBounds("OK~"));
    }

    @Test
    public void testIsStringInBoundsEmptyString() {
        assertTrue(CryptoManager.isStringInBounds(""));
    }

    @Test
    public void testIsStringInBoundsBadCharAtEnd() {
        assertFalse(CryptoManager.isStringInBounds("GOOD TEXT{"));
    }

    /* ---------- Caesar ---------- */

    @Test
    public void testCaesarEncryptionKnownAnswer() {
        assertEquals("KHOOR", CryptoManager.caesarEncryption("HELLO", 3));
    }

    @Test
    public void testCaesarEncryptionWrapsAroundRange() {
        // X(88)+10 = 98 -> wraps to 34 ('"'), Y -> '#', Z -> '$'
        assertEquals("\"#$", CryptoManager.caesarEncryption("XYZ", 10));
    }

    @Test
    public void testCaesarEncryptionNotInBounds() {
        assertEquals(NOT_IN_BOUNDS, CryptoManager.caesarEncryption("hello", 3));
    }

    @Test
    public void testCaesarEncryptionNegativeAndLargeKey() {
        assertEquals(CryptoManager.caesarEncryption("PIZZA", 5),
                CryptoManager.caesarEncryption("PIZZA", 5 + 64 * 3));
        assertEquals(CryptoManager.caesarEncryption("PIZZA", 59),
                CryptoManager.caesarEncryption("PIZZA", -5));
    }

    @Test
    public void testCaesarDecryptionKnownAnswer() {
        assertEquals("HELLO", CryptoManager.caesarDecryption("KHOOR", 3));
    }

    @Test
    public void testCaesarDecryptionRoundTrip() {
        String text = "THE QUICK BROWN FOX, 123!";
        assertEquals(text, CryptoManager.caesarDecryption(CryptoManager.caesarEncryption(text, 17), 17));
    }

    @Test
    public void testCaesarDecryptionNotInBounds() {
        assertEquals(NOT_IN_BOUNDS, CryptoManager.caesarDecryption("abc", 2));
    }

    /* ---------- Vigenere ---------- */

    @Test
    public void testVigenereEncryptionKnownAnswer() {
        // key "BCD" shifts by 1, 2, 3 then repeats
        assertEquals("BCDBCD", CryptoManager.vigenereEncryption("AAAAAA", "BCD"));
    }

    @Test
    public void testVigenereEncryptionKeyShorterThanText() {
        // key "AF" shifts by 0, 5, 0, 5, 0 (repeats over the text)
        assertEquals("CFMJL", CryptoManager.vigenereEncryption("CAMEL", "AF"));
    }

    @Test
    public void testVigenereEncryptionNotInBounds() {
        assertEquals(NOT_IN_BOUNDS, CryptoManager.vigenereEncryption("lower", "KEY"));
    }

    @Test
    public void testVigenereDecryptionKnownAnswer() {
        assertEquals("AAAAAA", CryptoManager.vigenereDecryption("BCDBCD", "BCD"));
    }

    @Test
    public void testVigenereDecryptionRoundTrip() {
        String text = "ATTACK AT DAWN_ (NOW)";
        assertEquals(text, CryptoManager.vigenereDecryption(CryptoManager.vigenereEncryption(text, "LEMON"), "LEMON"));
    }

    @Test
    public void testVigenereDecryptionNotInBounds() {
        assertEquals(NOT_IN_BOUNDS, CryptoManager.vigenereDecryption("a~b", "KEY"));
    }

    /* ---------- Playfair ---------- */

    @Test
    public void testPlayfairEncryptionSameRow() {
        // With key "KEY" row 0 is K E Y A B C D F, so KE -> EY
        assertEquals("EY", CryptoManager.playfairEncryption("KE", "KEY"));
    }

    @Test
    public void testPlayfairEncryptionSameColumn() {
        // K is above G, and G is above P: KG -> GP
        assertEquals("GP", CryptoManager.playfairEncryption("KG", "KEY"));
    }

    @Test
    public void testPlayfairEncryptionRectangle() {
        // K(0,0) and H(1,1) swap columns: KH -> EG
        assertEquals("EG", CryptoManager.playfairEncryption("KH", "KEY"));
    }

    @Test
    public void testPlayfairEncryptionOddLengthIsPadded() {
        assertEquals(4, CryptoManager.playfairEncryption("DOG", "KEY").length());
    }

    @Test
    public void testPlayfairEncryptionChangesText() {
        assertNotEquals("HELLO WORLD", CryptoManager.playfairEncryption("HELLO WORLD", "MONARCHY"));
    }

    @Test
    public void testPlayfairEncryptionNotInBounds() {
        assertEquals(NOT_IN_BOUNDS, CryptoManager.playfairEncryption("hello", "KEY"));
    }

    @Test
    public void testPlayfairDecryptionSameRow() {
        assertEquals("KE", CryptoManager.playfairDecryption("EY", "KEY"));
    }

    @Test
    public void testPlayfairDecryptionRoundTripDoubleLetters() {
        String text = "BOOKKEEPER";
        assertEquals(text, CryptoManager.playfairDecryption(CryptoManager.playfairEncryption(text, "SECRET"), "SECRET"));
    }

    @Test
    public void testPlayfairDecryptionRoundTripOddLengthWithSymbols() {
        String text = "GO TEAM #1!";
        assertEquals(text, CryptoManager.playfairDecryption(CryptoManager.playfairEncryption(text, "JAVA 203"), "JAVA 203"));
    }

    @Test
    public void testPlayfairDecryptionNotInBounds() {
        assertEquals(NOT_IN_BOUNDS, CryptoManager.playfairDecryption("xy", "KEY"));
    }
}
