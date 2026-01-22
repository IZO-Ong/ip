package sappy;

/**
 * Represents a custom exception specific to the Sappy chatbot application.
 * This exception is used to handle domain-specific errors such as invalid user input
 * or task management failures.
 */
public class SappyException extends Exception {
    /**
     * Constructs a new SappyException with a standardized error prefix.
     * @param message The specific error message detail.
     */
    public SappyException(String message) {
        super("Sappy got an error! " + message);
    }
}