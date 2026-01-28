package sappy.parser;

import sappy.SappyException;

/**
 * Handles the parsing of user input strings into data.
 * Provides utility methods to extract task IDs and descriptions from commands.
 */
public class Parser {

    /**
     * Extracts a numeric task ID from a command string starting from a given offset.
     *
     * @param input The raw user input string.
     * @param offset The starting index where the ID is expected to begin.
     * @return The parsed integer task ID.
     * @throws SappyException If the input is malformed or the ID is not a valid integer.
     */
    public static int parseId(String input, int offset) throws SappyException {
        try {
            return Integer.parseInt(input.substring(offset).trim());
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new SappyException("Please provide a valid task number.");
        }
    }

    /**
     * Extracts a non-empty description from a command string starting from a given offset.
     *
     * @param input The raw user input string.
     * @param offset The starting index where the description is expected to begin.
     * @return The trimmed description string.
     * @throws SappyException If the resulting description is empty.
     */
    public static String parseDescription(String input, int offset) throws SappyException {
        String description = input.substring(offset).trim();
        if (description.isEmpty()) {
            throw new SappyException("The description cannot be empty.");
        }
        return description;
    }

    /**
     * Extracts a search keyword from a command string starting from a given offset.
     *
     * @param input The raw user input string.
     * @param offset The starting index where the keyword is expected to begin.
     * @return The trimmed keyword string.
     * @throws SappyException If the keyword is empty.
     */
    public static String parseKeyword(String input, int offset) throws SappyException {
        String keyword = input.substring(offset).trim();
        if (keyword.isEmpty()) {
            throw new SappyException("The search keyword cannot be empty.");
        }
        return keyword;
    }
}
