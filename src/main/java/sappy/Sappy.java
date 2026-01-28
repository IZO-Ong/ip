package sappy;

import sappy.command.Command;
import sappy.ui.Ui;

/**
 * Acts as the entry point for the Sappy chatbot application.
 * Initializes the required components and manages the main execution loop.
 */
public class Sappy {
    private final String botName = "Sappy";
    private final String filePath = "./data/sappy.txt";

    private final Ui ui;
    private final ChatBot chatbot;

    /**
     * Initializes a new instance of the Sappy application.
     * Sets up the user interface and the core chatbot logic.
     */
    public Sappy() {
        this.ui = new Ui();
        this.chatbot = new ChatBot(botName, filePath, ui);
    }

    /**
     * Starts the main execution loop of the chatbot.
     * Continuously reads user commands and provides responses until an exit command is received.
     */
    public void run() {
        ui.startUp(botName);

        boolean running = true;

        while (running) {
            String userInput = ui.readCommand();
            Command cmd = Command.fromString(userInput);

            if (cmd.isExit()) {
                running = false;
            }

            String response = chatbot.getResponse(userInput);
            ui.printResponse(response);
        }
    }

    /**
     * Main entry point of the program.
     * Creates an instance of Sappy and begins its execution.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Sappy().run();
    }
}
