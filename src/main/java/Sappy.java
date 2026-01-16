import java.util.Scanner;

public class Sappy {

    public static void main(String[] args) {
        Scanner myScan = new Scanner(System.in);
        ChatBot sappy = new ChatBot("Sappy");
        sappy.startUp();

        boolean running = true;

        while (running) {
            String userInput = myScan.nextLine();

            String response = sappy.getResponse(userInput);
            sappy.printResponse(response);

            if (sappy.isExitCommand(userInput)) {
                running = false;
            }
        }

        myScan.close();
    }
}
