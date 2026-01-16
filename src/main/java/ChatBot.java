import java.lang.StringBuilder;

public class ChatBot {
    Task[] tasks = new Task[100];
    String name;
    int taskIDCounter = 0;

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
        output.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == null) {
                break;
            }

            if (i > 0) {
                output.append("\n");
            }

            output.append(i + 1).append(". ").append(tasks[i].toString());
        }
        return output.toString();
    }

    public String addTask(String input) {
        tasks[taskIDCounter] = new Task(input);
        taskIDCounter++;

        return "Added: " + input;
    }

    public String markTaskDone(int taskID) {
        return tasks[taskID - 1].markDone();
    }

    public String markTaskUndone(int taskID) {
        return tasks[taskID - 1].markUndone();
    }

    public String getResponse(String input) {
        if (input.equalsIgnoreCase("bye")) {
            return "Bye! " + this.name + " will be very lonely until you come back!";
        } else if (input.equalsIgnoreCase("list")) {
            return listTasks();
        } else if (input.startsWith("mark ")) {
            int taskID = Integer.parseInt(input.substring(5));
            return markTaskDone(taskID);
        } else if (input.startsWith("unmark ")) {
            int taskID = Integer.parseInt(input.substring(7));
            return markTaskUndone(taskID);
        } else {
            return addTask(input);
        }
    }
}
