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
}