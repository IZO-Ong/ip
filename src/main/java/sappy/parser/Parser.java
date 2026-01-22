package sappy.parser;

import sappy.SappyException;

public class Parser {
    
    public static int parseId(String input, int offset) throws SappyException {
        try {
            return Integer.parseInt(input.substring(offset).trim());
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new SappyException("Please provide a valid task number.");
        }
    }
    
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