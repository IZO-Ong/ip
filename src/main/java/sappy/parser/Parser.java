package sappy.parser;

import sappy.logic.SappyException;

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
        assert offset >= 0 : "Offset cannot be negative";
        assert offset <= input.length() : "Offset " + offset + " is out of bounds for input: " + input;

        try {
            return Integer.parseInt(input.substring(offset).trim());
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new SappyException("Please provide a valid task number.");
        }
    }

    /**
     * Parses todo input to extract description.
     *
     * @param input The raw user input string.
     * @param offset The starting index where the description is expected to begin.
     * @return The trimmed description string.
     * @throws SappyException If the resulting description is empty.
     */
    public static String parseToDoDetails(String input, int offset) throws SappyException {
        assert offset >= 0 : "Offset cannot be negative";
        assert offset <= input.length() : "Offset " + offset + " is out of bounds for input: " + input;

        String description = input.substring(offset).trim();
        if (description.isEmpty()) {
            throw new SappyException("The description cannot be empty.");
        }
        return description;
    }

    /**
     * Parses deadline input to extract description and date.
     * @param input Raw user input.
     * @param offset Starting index after the command word.
     * @return String array where [0] is description and [1] is date.
     * @throws SappyException If /by is missing or parts are empty.
     */
    public static String[] parseDeadlineDetails(String input, int offset) throws SappyException {
        assert input != null : "Input string cannot be null";
        assert offset >= 0 : "Offset cannot be negative";
        assert offset <= input.length() : "Offset " + offset + " is out of bounds for input: " + input;
        String content = input.substring(offset).trim();

        if (!content.contains("/by")) {
            throw new SappyException("A deadline must have a /by date.");
        }

        String[] parts = content.split(" /by ");
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new SappyException("A description and date are required for a deadline.");
        }
        return parts;
    }

    /**
     * Parses event input to extract description, start date, and end date.
     * @param input Raw user input.
     * @param offset Starting index after the command word.
     * @return String array where [0] is description, [1] is from, and [2] is to.
     * @throws SappyException If /from or /to is missing or parts are empty.
     */
    public static String[] parseEventDetails(String input, int offset) throws SappyException {
        assert input != null : "Input string cannot be null";
        assert offset >= 0 : "Offset cannot be negative";
        assert offset <= input.length() : "Offset " + offset + " is out of bounds for input: " + input;
        String content = input.substring(offset).trim();

        if (!content.contains("/from") || !content.contains("/to")) {
            throw new SappyException("An event must have a /from and /to date.");
        }

        String[] parts = content.split(" /from | /to ");
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new SappyException("A description, from date, and to date are required for an event.");
        }
        return parts;
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
        assert offset >= 0 : "Offset cannot be negative";
        assert offset <= input.length() : "Offset " + offset + " is out of bounds for input: " + input;

        String keyword = input.substring(offset).trim();
        if (keyword.isEmpty()) {
            throw new SappyException("The search keyword cannot be empty.");
        }
        return keyword;
    }
}
