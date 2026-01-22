import java.util.Scanner;

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

        boolean isRunning = true;
        
        while (isRunning) {
            String userInput = ui.readCommand();
            
            if (chatbot.isExitCommand(userInput)) {
                isRunning = false;
            }

            String response = chatbot.getResponse(userInput);
            ui.printResponse(response);
        }
    }

    public static void main(String[] args) {
        new Sappy().run();
    }
}