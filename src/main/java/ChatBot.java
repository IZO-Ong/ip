import java.lang.StringBuilder;
import java.util.ArrayList;

public class ChatBot {
    private final ArrayList<Task> tasks;
    private final String name;
    private final Storage storage;
    private final Ui ui;

    public ChatBot(String name, String filePath, Ui ui) {
        this.name = name;
        this.ui = ui;
        this.storage = new Storage(filePath);

        ArrayList<Task> tempTasks;
        try {
            tempTasks = storage.load();
        } catch (SappyException e) {
            ui.printResponse("Warning: " + e.getMessage() + "\nStarting with an empty list.");
            tempTasks = new ArrayList<>();
        }
        this.tasks = tempTasks;
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
        return addTask(t);
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
        return addTask(t);
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
        return addTask(t);
    }
    
    private String addTask(Task t) {
        tasks.add(t);
        autoSave();
        return getSuccessMessage(t);
    }

    private void autoSave() {
        try {
            storage.save(this.tasks);
        } catch (java.io.IOException e) {
            ui.printResponse("Error: Could not save task: " + e.getMessage());
        }
    }

    private String getSuccessMessage(Task t) {
        return "I've added this task:\n  " + t.toString() +
                "\nNow you have " + tasks.size() + " task(s) in the list.";
    }

    public String markTaskDone(int taskID) throws SappyException {
        if (taskID > tasks.size() || taskID <= 0) {
            throw new SappyException("That task does not exist!");
        }
        String response = tasks.get(taskID - 1).markDone();
        autoSave();
        return response;
    }

    public String markTaskUndone(int taskID) throws SappyException {
        if (taskID > tasks.size() || taskID <= 0) {
            throw new SappyException("That task does not exist!");
        }
        String response = tasks.get(taskID - 1).markUndone();
        autoSave();
        return response;
    }

    public String removeTask(int taskID) throws SappyException {
        if (taskID > tasks.size() || taskID <= 0) {
            throw new SappyException("That task does not exist!");
        }
        
        String currTaskString = tasks.get(taskID - 1).toString();
        
        tasks.remove(taskID - 1);
        autoSave();
        
        return "I've removed this task:\n" + currTaskString
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    public String getResponse(String input) {
        try {
            Command cmd = Command.fromString(input);

            switch (cmd) {
            case BYE:
                return "Bye! " + this.name + " will be very lonely until you come back!";
            case LIST:
                return listTasks();
            case MARK:
                return markTaskDone(parseId(input, 5));
            case UNMARK:
                return markTaskUndone(parseId(input, 7));
            case REMOVE:
                return removeTask(parseId(input, 7));
            case TODO:
                return addToDo(input.substring(5));
            case DEADLINE:
                return addDeadline(input.substring(9));
            case EVENT:
                return addEvent(input.substring(6));
            default:
                throw new SappyException("I'm sorry, I don't know what that means.");
            }
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
