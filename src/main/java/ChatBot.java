import java.lang.StringBuilder;

public class ChatBot {
    String[] tasks = new String[100];
    String name;
    int taskID = 0;

    public ChatBot(String name) {
        this.name = name;
    }

    public void printResponse(String text) {
        String indent = "    ";
        String line = "____________________________________________________________";

        System.out.println(indent + line);

        String indentedText = indent + text.replace("\n", "\n" + indent);
        System.out.println(indentedText);

        System.out.println(indent + line);
    }

    public void startUp() {
        printResponse("Hello! I'm " + this.name + " \n" +
                "What can I do for you?");
    }

    public boolean isExitCommand(String input) {
        return input.equalsIgnoreCase("bye");
    }

    public String listTasks() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == null) {
                break;
            }

            if (i > 0) {
                output.append("\n");
            }

            output.append(i + 1).append(". ").append(tasks[i]);
        }
        return output.toString();
    }

    public String addTask(String input) {
        tasks[taskID] = input;
        taskID++;

        return "Added: " + input;
    }

    public String getResponse(String input) {
        if (input.equalsIgnoreCase("bye")) {
            return "Bye! " + this.name + " will be very lonely until you come back!";
        } else if (input.equalsIgnoreCase("list")) {
            return listTasks();
        }
        return addTask(input);
    }
}
