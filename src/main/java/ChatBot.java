import java.lang.StringBuilder;

public class ChatBot {
    private final Task[] tasks = new Task[100];
    private final String name;
    private int taskIDCounter = 0;

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
        printResponse("Hello! I'm " + this.name + "\n" +
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

    public String addToDo(String description) throws SappyException {
        if (taskIDCounter >= tasks.length) {
            throw new SappyException("Too many tasks! Delete some before adding new tasks.");
        }
        if (description.trim().isEmpty()) {
            throw new SappyException("A description of a todo is required.");
        }
        Task t = new ToDo(description);
        tasks[taskIDCounter++] = t;
        return getSuccessMessage(t);
    }

    public String addDeadline(String input) throws SappyException {
        if (!input.contains("/by")) {
            throw new SappyException("A deadline must have a /by date.");
        }
        if (taskIDCounter >= tasks.length) {
            throw new SappyException("Too many tasks! Delete some before adding new tasks.");
        }
        String[] parts = input.split(" /by ");

        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new SappyException("A description and date of a deadline are required.");
        }
        Task t = new Deadline(parts[0], parts[1]);
        tasks[taskIDCounter++] = t;
        return getSuccessMessage(t);
    }

    public String addEvent(String input) throws SappyException {
        if (!input.contains("/from") || !input.contains("/to")) {
            throw new SappyException("An Event must have a /from date and /to date.");
        }
        if (taskIDCounter >= tasks.length) {
            throw new SappyException("Too many tasks! Delete some before adding new tasks.");
        }
        String[] parts = input.split(" /from | /to ");
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new SappyException("A description, from and to date of an event is required.");
        }
        Task t = new Event(parts[0], parts[1], parts[2]);
        tasks[taskIDCounter++] = t;
        return getSuccessMessage(t);
    }

    private String getSuccessMessage(Task t) {
        return "I've added this task:\n  " + t.toString() +
                "\nNow you have " + taskIDCounter + " task(s) in the list.";
    }

    public String markTaskDone(int taskID) throws SappyException {
        if (taskID > taskIDCounter || taskID <= 0) {
            throw new SappyException("That task does not exist!");
        }
        return tasks[taskID - 1].markDone();
    }

    public String markTaskUndone(int taskID) throws SappyException {
        if (taskID > taskIDCounter || taskID <= 0) {
            throw new SappyException("That task does not exist!");
        }
        return tasks[taskID - 1].markUndone();
    }

    public String getResponse(String input) {
        try {
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
            } else if (input.startsWith("todo ")) {
                return addToDo(input.substring(5));
            } else if (input.startsWith("deadline ")) {
                return addDeadline(input.substring(9));
            } else if (input.startsWith("event ")) {
                return addEvent(input.substring(6));
            } else {
                throw new SappyException("I'm sorry, I don't know what that means.");
            }
        } catch (SappyException e) {
            return e.getMessage();
        }
    }
}
