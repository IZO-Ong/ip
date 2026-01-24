package sappy.ui;

import java.util.Scanner;

/**
 * Represents the user interface of the chatbot.
 * Responsible for handling all interactions with the user, including
 * displaying messages and reading user input.
 */
public class Ui {
    private final String indent = "    ";
    private final Scanner scanner;

    /**
     * Constructs a new Ui instance and initialises the Scanner for user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }
    
    private void showLine() {
        String line = "____________________________________________________________";
        System.out.println(indent + line);
    }

    /**
     * Displays the initial greeting message when the chatbot starts.
     * @param botName The name of the chatbot to be displayed in the greeting.
     */
    public void startUp(String botName) {
        printResponse("Hello! I'm " + botName + "\nWhat can I do for you?");
    }

    /**
     * Reads the next line of input from the user via the console.
     * @return The raw string input entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints a formatted response to the console.
     * The message is wrapped in separator lines and every newline is indented.
     * @param text The message string to be displayed to the user.
     */
    public void printResponse(String text) {
        showLine();
        String indentedText = indent + text.replace("\n", "\n" + indent);
        System.out.println(indentedText);
        showLine();
    }
}
