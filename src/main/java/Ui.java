import java.util.Scanner;

public class Ui {
    private final String indent = "    ";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }
    
    public void showLine() {
        String line = "____________________________________________________________";
        System.out.println(indent + line);
    }
    
    public void startUp(String botName) {
        printResponse("Hello! I'm " + botName + "\nWhat can I do for you?");
    }
    
    public String readCommand() {
        return scanner.nextLine();
    }
    
    public void printResponse(String text) {
        showLine();
        String indentedText = indent + text.replace("\n", "\n" + indent);
        System.out.println(indentedText);
        showLine();
    }
}
