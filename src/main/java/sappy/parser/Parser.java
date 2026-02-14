package sappy.parser;

import sappy.command.Command;
import sappy.logic.SappyException;

/**
 * Handles the parsing of user input strings into data.
 * Provides utility methods to extract task IDs and descriptions from commands.
 */
public class Parser {

    /**
     * Extracts a numeric task ID from a command string.
     *
     * @param input The raw user input string.
     * @return The parsed integer task ID.
     * @throws SappyException If the ID is missing or not a valid integer.
     */
    public static int parseId(String input) throws SappyException {
        String[] parts = input.trim().split("\\s+", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new SappyException("Sappy needs a task number to peck at!");
        }
        try {
            return Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new SappyException("That doesn't look like a number!");
        }
    }

    /**
     * Parses todo input to extract description.
     *
     * @param input The raw user input string.
     * @return The trimmed description string.
     * @throws SappyException If the resulting description is empty.
     */
    public static String parseToDoDetails(String input) throws SappyException {
        String[] parts = input.trim().split("\\s+", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new SappyException("The description cannot be empty.");
        }
        return parts[1].trim();
    }

    /**
     * Parses deadline input to extract description and date.
     * @param input Raw user input.
     * @return String array where [0] is description and [1] is date.
     * @throws SappyException If /by is missing or parts are empty.
     */
    public static String[] parseDeadlineDetails(String input) throws SappyException {
        String[] mainParts = input.trim().split("\\s+", 2);
        if (mainParts.length < 2 || mainParts[1].trim().isEmpty()) {
            throw new SappyException("A deadline needs a description and a /by date!");
        }

        String content = mainParts[1];
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
     * @return String array where [0] is description, [1] is from, and [2] is to.
     * @throws SappyException If /from or /to is missing or parts are empty.
     */
    public static String[] parseEventDetails(String input) throws SappyException {
        String[] mainParts = input.trim().split("\\s+", 2);
        if (mainParts.length < 2 || mainParts[1].trim().isEmpty()) {
            throw new SappyException("An event needs a description, /from, and /to dates!");
        }

        String content = mainParts[1];
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
     * Extracts a search keyword from a command string.
     *
     * @param input The raw user input string.
     * @return The trimmed keyword string.
     * @throws SappyException If the keyword is empty.
     */
    public static String parseKeyword(String input) throws SappyException {
        String[] parts = input.trim().split("\\s+", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new SappyException("Sappy needs a word to scout for!");
        }
        return parts[1].trim();
    }

    /**
     * Parses a raw string input to determine the corresponding Command.
     * If the input is null, empty or unrecognized, returns UNKNOWN.
     *
     * @param input The raw string input from the user.
     * @return The matching Command enum constant.
     */
    public static Command parseCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Command.UNKNOWN;
        }
        String action = input.trim().split("\\s+")[0].toUpperCase();
        try {
            return Command.valueOf(action);
        } catch (IllegalArgumentException e) {
            return Command.UNKNOWN;
        }
    }
}
