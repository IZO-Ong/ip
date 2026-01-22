package sappy;

public class SappyException extends Exception {
    public SappyException(String message) {
        super("Sappy got an error! " + message);
    }
}