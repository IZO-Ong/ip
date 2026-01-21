import java.lang.StringBuilder;
import java.util.ArrayList;

public class ChatBot {
    private final ArrayList<Task> tasks;
    private final String name;

    public ChatBot(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
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
        printResponse("Hello! I'm " + this.name + "\n" +
                "What can I do for you?");
    }

    public boolean isExitCommand(String input) {
        return input.equalsIgnoreCase("bye");
    }

    public String listTasks() {
        StringBuilder output = new StringBuilder();
        output.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {

            if (i > 0) {
                output.append("\n");
            }

            output.append(i + 1).append(". ").append(tasks.get(i).toString());
        }
        return output.toString();
    }

    public String addToDo(String description) throws SappyException {
        if (description.trim().isEmpty()) {
            throw new SappyException("A description of a todo is required.");
        }
        Task t = new ToDo(description);
        tasks.add(t);
        return getSuccessMessage(t);
    }

    public String addDeadline(String input) throws SappyException {
        if (!input.contains("/by")) {
            throw new SappyException("A deadline must have a /by date.");
        }

        String[] parts = input.split(" /by ");

        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new SappyException("A description and date of a deadline are required.");
        }
        Task t = new Deadline(parts[0], parts[1]);
        tasks.add(t);
        return getSuccessMessage(t);
    }

    public String addEvent(String input) throws SappyException {
        if (!input.contains("/from") || !input.contains("/to")) {
            throw new SappyException("An Event must have a /from date and /to date.");
        }
        String[] parts = input.split(" /from | /to ");
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new SappyException("A description, from and to date of an event is required.");
        }
        Task t = new Event(parts[0], parts[1], parts[2]);
        tasks.add(t);
        return getSuccessMessage(t);
    }

    private String getSuccessMessage(Task t) {
        return "I've added this task:\n  " + t.toString() +
                "\nNow you have " + tasks.size() + " task(s) in the list.";
    }

    public String markTaskDone(int taskID) throws SappyException {
        if (taskID > tasks.size() || taskID <= 0) {
            throw new SappyException("That task does not exist!");
        }
        return tasks.get(taskID - 1).markDone();
    }

    public String markTaskUndone(int taskID) throws SappyException {
        if (taskID > tasks.size() || taskID <= 0) {
            throw new SappyException("That task does not exist!");
        }
        return tasks.get(taskID - 1).markUndone();
    }

    public String removeTask(int taskID) throws SappyException {
        if (taskID > tasks.size() || taskID <= 0) {
            throw new SappyException("That task does not exist!");
        }
        
        String currTaskString = tasks.get(taskID - 1).toString();
        
        tasks.remove(taskID - 1);
        
        return "I've removed this task:\n" + currTaskString
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    public String getResponse(String input) {
        try {
            Command cmd = Command.fromString(input);

            return switch (cmd) {
                case BYE -> "Bye! " + this.name + " will be very lonely until you come back!";
                case LIST -> listTasks();
                case MARK -> markTaskDone(parseId(input, 5));
                case UNMARK -> markTaskUndone(parseId(input, 7));
                case REMOVE -> removeTask(parseId(input, 7));
                case TODO -> addToDo(input.substring(5));
                case DEADLINE -> addDeadline(input.substring(9));
                case EVENT -> addEvent(input.substring(6));
                default -> throw new SappyException("I'm sorry, I don't know what that means.");
            };
        } catch (SappyException e) {
            return e.getMessage();
        }
    }

    private int parseId(String input, int offset) throws SappyException {
        try {
            return Integer.parseInt(input.substring(offset).trim());
        } catch (NumberFormatException e) {
            throw new SappyException("Please provide a valid task number.");
        }
    }
}
