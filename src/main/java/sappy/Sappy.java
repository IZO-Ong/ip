package sappy;

import sappy.command.Command;
import sappy.logic.ChatBot;

/**
 * The logic engine of the Sappy chatbot.
 */
public class Sappy {
    private final String botName = "Sappy";
    private final String filePath = "./data/sappy.txt";
    private final ChatBot chatbot;

    /**
     * Initializes a new instance of the Sappy logic engine.
     */
    public Sappy() {
        this.chatbot = new ChatBot(botName, filePath);
    }

    /**
     * Generates a response for the user's chat message.
     * This is the primary entry point for the GUI to interact with the chatbot logic.
     *
     * @param input The raw user input from the GUI text field.
     * @return Sappy's processed response as a String.
     */
    public String getResponse(String input) {
        return chatbot.getResponse(input);
    }

    /**
     * Returns the last command typed by the user.
     */
    public Command getLatestCommand() {
        return chatbot.getLastCommand();
    }
}
