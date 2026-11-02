/*
 * Class: CMSC203
 * Instructor:
 * Description: Utility class that encrypts and decrypts a phrase using three
 *              classic (and insecure by modern standards) ciphers: Vigenere,
 *              Playfair and Caesar. All methods are static and work on the
 *              ASCII range ' ' (32) through '_' (95).
 * Due: MM/DD/YYYY
 * Platform/compiler: IntelliJ IDEA / Java 21 (JDK)
 * I pledge that I have completed the programming assignment independently.
 * I have not copied the code from a student or any source.
 * I have not given my code to any student.
 * Print your Name here: Daniel Miranda
 */

import java.util.ArrayList;

/**
 * This is a utility class that encrypts and decrypts a phrase using three
 * different approaches.
 *
 * The first approach is called the Vigenere Cipher. Vigenere encryption
 * is a method of encrypting alphabetic text based on the letters of a keyword.
 *
 * The second approach is Playfair Cipher. It encrypts two letters (a digraph)
 * at a time instead of just one.
 *
 * The third approach is Caesar Cipher. It is a simple replacement cypher.
 *
 * @author Huseyin Aygun
 * @version 8/3/2025
 */
public class CryptoManager {

    private static final char LOWER_RANGE = ' ';
    private static final char UPPER_RANGE = '_';
    private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
    // Use 64-character matrix (8X8) for Playfair cipher
    private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 !\"#$%&'()*+,-./:;<=>?@[\\]^_";
    private static final int GRID_SIZE = 8;
    private static final String OUT_OF_BOUNDS_MESSAGE = "The selected string is not in bounds, Try again.";
    private static final char PAD_LETTER = 'X';       // Playfair filler for doubles / odd length
    private static final char ALT_PAD_LETTER = 'Q'; // used when the letter to be separated is already PAD_LETTER

    /**
     * Determines if every character of a string is inside the allowable
     * ASCII bounds (LOWER_RANGE through UPPER_RANGE, inclusive).
     *
     * @param plainText the string to be checked
     * @return true if all characters are in bounds, false if any is outside
     */
    public static boolean isStringInBounds(String plainText) {
        for (int i = 0; i < plainText.length(); i++) {
            if (!(plainText.charAt(i) >= LOWER_RANGE && plainText.charAt(i) <= UPPER_RANGE)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vigenere Cipher is a method of encrypting alphabetic text
     * based on the letters of a keyword. It works as below:
     *      Choose a keyword (e.g., KEY).
     *      Repeat the keyword to match the length of the plaintext.
     *      Each letter in the plaintext is shifted by the position of the
     *      corresponding letter in the keyword (A = 0, B = 1, ..., Z = 25).
     *
     * @param plainText the uppercase string to encrypt
     * @param key the keyword
     * @return the encrypted string, or an error message if plainText is out of bounds
     */
    public static String vigenereEncryption(String plainText, String key) {
        if (!isStringInBounds(plainText)) {
            return OUT_OF_BOUNDS_MESSAGE;
        }
        return vigenereShift(plainText, key, 1);
    }

    /**
     * Decrypts a string that was encrypted with vigenereEncryption
     * (the inverse operation).
     *
     * @param encryptedText the encrypted string
     * @param key the keyword used to encrypt
     * @return the original plain text, or an error message if the text is out of bounds
     */
    public static String vigenereDecryption(String encryptedText, String key) {
        if (!isStringInBounds(encryptedText)) {
            return OUT_OF_BOUNDS_MESSAGE;
        }
        return vigenereShift(encryptedText, key, -1);
    }

    /**
     * Shifts every character by the value of the matching (repeating) key
     * character, wrapping around inside the allowed range.
     *
     * @param text the text to shift
     * @param key the keyword (repeated to match the text length)
     * @param direction 1 to encrypt, -1 to decrypt
     * @return the shifted text
     */
    private static String vigenereShift(String text, String key, int direction) {
        if (key.isEmpty()) {
            return text; // nothing to shift by
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            int shift = key.charAt(i % key.length()) - 'A'; // A = 0, B = 1, ...
            result.append(shiftCharacter(text.charAt(i), direction * shift));
        }
        return result.toString();
    }

    /**
     * Playfair Cipher encrypts two letters at a time instead of just one.
     * It works as follows:
     * A matrix (8X8 in our case) is built using a keyword
     * Plaintext is split into letter pairs (e.g., ME ET YO UR).
     * Encryption rules depend on the positions of the letters in the matrix:
     *     Same row: replace each letter with the one to its right.
     *     Same column: replace each with the one below.
     *     Rectangle: replace each letter with the one in its own row but in the column of the other letter in the pair.
     *
     * Pairs made of two identical characters get a filler ('X', or 'Q' when
     * the character is 'X') inserted between them, and an odd-length text is
     * padded at the end with the same filler. Decryption removes these fillers.
     *
     * @param plainText the uppercase string to encrypt
     * @param key the keyword used to build the matrix
     * @return the encrypted string, or an error message if plainText is out of bounds
     */
    public static String playfairEncryption(String plainText, String key) {
        if (!isStringInBounds(plainText)) {
            return OUT_OF_BOUNDS_MESSAGE;
        }
        char[][] grid = buildPlayfairGrid(key);
        String prepared = preparePlayfairPlaintext(plainText);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < prepared.length(); i += 2) {
            result.append(transformPair(grid, prepared.substring(i, i + 2), 1));
        }
        return result.toString();
    }

    /**
     * Decrypts a string that was encrypted with playfairEncryption
     * (the inverse operation). Fillers added during encryption are removed.
     *
     * @param encryptedText the encrypted string
     * @param key the keyword used to encrypt
     * @return the original plain text, or an error message if the text is out of bounds
     */
    public static String playfairDecryption(String encryptedText, String key) {
        if (!isStringInBounds(encryptedText)) {
            return OUT_OF_BOUNDS_MESSAGE;
        }
        char[][] grid = buildPlayfairGrid(key);

        // Decrypt every digraph first
        ArrayList<String> pairs = new ArrayList<String>();
        for (int i = 0; i + 1 < encryptedText.length(); i += 2) {
            pairs.add(transformPair(grid, encryptedText.substring(i, i + 2), -1));
        }

        // Then drop the fillers that were inserted during encryption
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < pairs.size(); i++) {
            char first = pairs.get(i).charAt(0);
            char second = pairs.get(i).charAt(1);
            result.append(first);

            boolean isLastPair = (i == pairs.size() - 1);
            boolean looksLikeFiller = (second == fillerFor(first));
            // Padding at the end, or a filler separating two identical letters
            boolean isFiller = looksLikeFiller
                    && (isLastPair || pairs.get(i + 1).charAt(0) == first);
            if (!isFiller) {
                result.append(second);
            }
        }
        // A dangling character (invalid odd-length ciphertext) is kept as is
        if (encryptedText.length() % 2 == 1) {
            result.append(encryptedText.charAt(encryptedText.length() - 1));
        }
        return result.toString();
    }

    /**
     * Builds the 8x8 Playfair grid: the unique key characters first, then
     * the remaining characters of ALPHABET64 in order.
     *
     * @param key the keyword
     * @return the filled 8x8 grid
     */
    private static char[][] buildPlayfairGrid(String key) {
        StringBuilder order = new StringBuilder();
        String source = key + ALPHABET64;
        for (int i = 0; i < source.length(); i++) {
            char current = source.charAt(i);
            // Ignore characters that are not in the alphabet and duplicates
            if (ALPHABET64.indexOf(current) >= 0 && order.indexOf(String.valueOf(current)) < 0) {
                order.append(current);
            }
        }

        char[][] grid = new char[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < order.length(); i++) {
            grid[i / GRID_SIZE][i % GRID_SIZE] = order.charAt(i);
        }
        return grid;
    }

    /**
     * Prepares plain text for Playfair: separates identical neighbours with a
     * filler and pads an odd ending, so the result is made of whole digraphs.
     *
     * @param plainText the plain text
     * @return the prepared text (always an even number of characters)
     */
    private static String preparePlayfairPlaintext(String plainText) {
        StringBuilder prepared = new StringBuilder();
        int i = 0;
        while (i < plainText.length()) {
            char first = plainText.charAt(i);
            prepared.append(first);
            if (i + 1 == plainText.length() || plainText.charAt(i + 1) == first) {
                prepared.append(fillerFor(first)); // odd length or double letter
                i++;
            } else {
                prepared.append(plainText.charAt(i + 1));
                i += 2;
            }
        }
        return prepared.toString();
    }

    /**
     * Applies the Playfair rules to one digraph.
     *
     * @param grid the Playfair grid
     * @param pair the two characters
     * @param direction 1 to encrypt (right/down), -1 to decrypt (left/up)
     * @return the transformed two-character string
     */
    private static String transformPair(char[][] grid, String pair, int direction) {
        int row1 = findRow(grid, pair.charAt(0));
        int col1 = findCol(grid, pair.charAt(0));
        int row2 = findRow(grid, pair.charAt(1));
        int col2 = findCol(grid, pair.charAt(1));

        if (row1 == row2) {
            // Same row: move right (or left)
            col1 = Math.floorMod(col1 + direction, GRID_SIZE);
            col2 = Math.floorMod(col2 + direction, GRID_SIZE);
        } else if (col1 == col2) {
            // Same column: move down (or up)
            row1 = Math.floorMod(row1 + direction, GRID_SIZE);
            row2 = Math.floorMod(row2 + direction, GRID_SIZE);
        } else {
            // Rectangle: swap the columns
            int temp = col1;
            col1 = col2;
            col2 = temp;
        }
        return "" + grid[row1][col1] + grid[row2][col2];
    }

    /**
     * Finds the row of a letter in the grid.
     *
     * @param grid the Playfair grid
     * @param letter the character to look for
     * @return the row index of the letter
     */
    private static int findRow(char[][] grid, char letter) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col] == letter) {
                    return row;
                }
            }
        }
        return 0; // not reachable for in-bounds text
    }

    /**
     * Finds the column of a letter in the grid.
     *
     * @param grid the Playfair grid
     * @param letter the character to look for
     * @return the column index of the letter
     */
    private static int findCol(char[][] grid, char letter) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col] == letter) {
                    return col;
                }
            }
        }
        return 0; // not reachable for in-bounds text
    }

    /**
     * Chooses the Playfair filler for a letter ('X', or 'Q' if the letter is 'X').
     *
     * @param letter the letter that needs a filler next to it
     * @return the filler character
     */
    private static char fillerFor(char letter) {
        return (letter == PAD_LETTER) ? ALT_PAD_LETTER : PAD_LETTER;
    }

    /**
     * Caesar Cipher is a simple substitution cipher that replaces each letter in a message
     * with a letter some fixed number of positions down the alphabet.
     * For example, with a shift of 3, 'A' would become 'D', 'B' would become 'E', and so on.
     *
     * @param plainText the uppercase string to encrypt
     * @param key the number of positions to shift
     * @return the encrypted string, or an error message if plainText is out of bounds
     */
    public static String caesarEncryption(String plainText, int key) {
        if (!isStringInBounds(plainText)) {
            return OUT_OF_BOUNDS_MESSAGE;
        }
        return caesarShift(plainText, key);
    }

    /**
     * Decrypts a string that was encrypted with caesarEncryption
     * (the inverse operation).
     *
     * @param encryptedText the encrypted string
     * @param key the shift used to encrypt
     * @return the original plain text, or an error message if the text is out of bounds
     */
    public static String caesarDecryption(String encryptedText, int key) {
        if (!isStringInBounds(encryptedText)) {
            return OUT_OF_BOUNDS_MESSAGE;
        }
        return caesarShift(encryptedText, -key);
    }

    /**
     * Shifts every character of a string by the same amount.
     *
     * @param text the text to shift
     * @param shift positive or negative number of positions
     * @return the shifted text
     */
    private static String caesarShift(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(shiftCharacter(text.charAt(i), shift));
        }
        return result.toString();
    }

    /**
     * Shifts one in-bounds character, wrapping around inside the allowed range.
     *
     * @param character the character to shift
     * @param shift positive or negative number of positions
     * @return the shifted character
     */
    private static char shiftCharacter(char character, int shift) {
        int offset = character - LOWER_RANGE;
        int shifted = (offset + Math.floorMod(shift, RANGE)) % RANGE; // floorMod avoids overflow/negatives
        return (char) (LOWER_RANGE + shifted);
    }
}