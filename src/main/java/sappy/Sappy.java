package sappy;

import sappy.ui.Ui;
import sappy.command.Command;

public class Sappy {
    private final String botName = "Sappy";
    private final String filePath = "./data/sappy.txt";

    private final Ui ui;
    private final ChatBot chatbot;

    public Sappy() {
        this.ui = new Ui();
        this.chatbot = new ChatBot(botName, filePath, ui);
    }

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

    public static void main(String[] args) {
        new Sappy().run();
    }
}