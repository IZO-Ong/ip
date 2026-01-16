import java.util.Scanner;

public class Sappy {

    public static void pprint(String text) {
        String indent = "    ";
        String line = "____________________________________________________________";

        System.out.println(indent + line);

        String indentedText = indent + text.replace("\n", "\n" + indent);
        System.out.println(indentedText);

        System.out.println(indent + line);
    }

    public static void main(String[] args) {
        Scanner myScan = new Scanner(System.in);
        pprint("Hello! I'm Sappy \n" +
                "What can I do for you?");

        String userInput = myScan.nextLine();
        while (!userInput.equalsIgnoreCase("bye")) {
            pprint(userInput);
            userInput = myScan.nextLine();
        }
        pprint("Bye! Sappy will be very lonely until you come back!");
    }
}
