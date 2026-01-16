public class ChatBot {
    String[] memory = new String[100];
    String name;

    public ChatBot(String name) {
        this.name = name;
    }

    public void pprint(String text) {
        String indent = "    ";
        String line = "____________________________________________________________";

        System.out.println(indent + line);

        String indentedText = indent + text.replace("\n", "\n" + indent);
        System.out.println(indentedText);

        System.out.println(indent + line);
    }

    public void startUp() {
        pprint("Hello! I'm " + this.name + " \n" +
                "What can I do for you?");
    }

    public boolean isExitCommand(String input) {
        return input.equalsIgnoreCase("bye");
    }

    public String getResponse(String input) {
        if (input.equalsIgnoreCase("bye")) {
            return "Bye! " + this.name + " will be very lonely until you come back!";
        }
        return "You said: " + input;
    }
}
