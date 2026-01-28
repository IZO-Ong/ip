package sappy;

import sappy.command.Command;
import sappy.parser.Parser;
import sappy.storage.Storage;
import sappy.task.Deadline;
import sappy.task.Event;
import sappy.task.Task;
import sappy.task.TaskList;
import sappy.task.ToDo;
import sappy.ui.Ui;

/**
 * Encapsulates the main logic of Sappy chatbot.
 * Handles task management, user interaction logic and automated saving.
 */
public class ChatBot {
    private final TaskList taskList;
    private final String name;
    private final Storage storage;
    private final Ui ui;

    /**
     * Initialises a new ChatBot instance.
     * Loads existing tasks from the specified file path or starts with an empty list.
     *
     * @param name Name of the chatbot.
     * @param filePath Path to the file where tasks are stored.
     * @param ui The user interface component for displaying messages.
     */
    public ChatBot(String name, String filePath, Ui ui) {
        this.name = name;
        this.ui = ui;
        this.storage = new Storage(filePath);

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (SappyException e) {
            ui.printResponse("Warning: " + e.getMessage() + "\nStarting with an empty list.");
            loadedTasks = new TaskList();
        }
        this.taskList = loadedTasks;
    }

    /**
     * Initializes a ChatBot instance with a specific storage object.
     * Use this constructor for testing with stubs.
     *
     * @param name Name of the chatbot.
     * @param storage The storage component to use.
     * @param ui The user interface component for displaying messages.
     */
    public ChatBot(String name, Storage storage, Ui ui) {
        this.name = name;
        this.ui = ui;
        this.storage = storage;

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (SappyException e) {
            ui.printResponse("Warning: " + e.getMessage() + "\nStarting with an empty list.");
            loadedTasks = new TaskList();
        }
        this.taskList = loadedTasks;
    }

    /**
     * Returns a formatted string listing all current tasks.
     *
     * @return String representation of the task list.
     */
    public String listTasks() throws SappyException {
        StringBuilder output = new StringBuilder();
        output.append("Here are the tasks in your list:\n");
        for (int i = 0; i < taskList.getSize(); i++) {
            if (i > 0) {
                output.append("\n");
            }
            output.append(i + 1).append(". ").append(taskList.get(i).toString());
        }
        return output.toString();
    }

    /**
     * Adds a new ToDo task to the list.
     *
     * @param description The description of the todo.
     * @return Success message containing the added task.
     * @throws SappyException If the description is empty.
     */
    public String addToDo(String description) throws SappyException {
        if (description.trim().isEmpty()) {
            throw new SappyException("The description cannot be empty.");
        }
        Task t = new ToDo(description);
        return addTask(t);
    }

    /**
     * Adds a new Deadline task to the list.
     *
     * @param input The raw input containing description and /by date.
     * @return Success message containing the added task.
     * @throws SappyException If format is invalid or parts are missing.
     */
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

    /**
     * Adds a new Event task to the list.
     *
     * @param input The raw input containing description, /from, and /to dates.
     * @return Success message containing the added task.
     * @throws SappyException If format is invalid or dates are missing.
     */
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
        taskList.add(t);
        autoSave();
        return getSuccessMessage(t);
    }

    private void autoSave() {
        try {
            storage.save(taskList.getAllTasks());
        } catch (java.io.IOException e) {
            ui.printResponse("Error: Could not save task: " + e.getMessage());
        }
    }

    private String getSuccessMessage(Task t) {
        return "I've added this task:\n  " + t.toString()
                + "\nNow you have " + taskList.getSize() + " task(s) in the list.";
    }

    /**
     * Marks a specific task as completed.
     *
     * @param taskId The 1-based index of the task in the list.
     * @return Confirmation message from the TaskList.
     * @throws SappyException If the taskId is invalid.
     */
    public String markTaskDone(int taskId) throws SappyException {
        String response = taskList.markDone(taskId - 1);
        autoSave();
        return response;
    }

    /**
     * Marks a specific task as not completed.
     *
     * @param taskId The 1-based index of the task in the list.
     * @return Confirmation message from the TaskList.
     * @throws SappyException If the taskId is invalid.
     */
    public String markTaskUndone(int taskId) throws SappyException {
        String response = taskList.markUndone(taskId - 1);
        autoSave();
        return response;
    }

    /**
     * Removes a task from the list permanently.
     *
     * @param taskId The 1-based index of the task to be removed.
     * @return Confirmation message containing the removed task details.
     * @throws SappyException If the taskID is invalid.
     */
    public String removeTask(int taskId) throws SappyException {
        Task removed = taskList.remove(taskId - 1);
        autoSave();
        return "I've removed this task:\n" + removed.toString()
                + "\nNow you have " + taskList.getSize() + " tasks in the list.";
    }

    /**
     * Returns a formatted string of tasks that match the specified keyword.
     *
     * @param keyword The string to search for within task descriptions.
     * @return A formatted list of matching tasks.
     */
    public String findTasks(String keyword) {
        StringBuilder output = new StringBuilder();
        int count = 1;

        for (int i = 0; i < taskList.getSize(); i++) {
            try {
                Task task = taskList.get(i);
                if (task.toString().contains(keyword)) {
                    if (count > 1) {
                        output.append("\n");
                    }
                    output.append(count).append(".").append(task);
                    count++;
                }
            } catch (SappyException ignored) {
                // index validation is handled by TaskList
            }
        }

        if (count == 1) {
            return "No matching tasks found in your list.";
        }

        return "Here are the matching tasks in your list:\n" + output;
    }

    /**
     * Processes user input and returns the appropriate response string.
     *
     * @param input The raw user command string.
     * @return The response generated by the chatbot logic.
     */
    public String getResponse(String input) {
        try {
            Command cmd = Command.fromString(input);

            if (cmd.isExit()) {
                return "Bye! " + this.name + " will be very lonely until you come back!";
            }

            switch (cmd) {
            case LIST:
                return listTasks();
            case MARK:
                return markTaskDone(Parser.parseId(input, 5));
            case UNMARK:
                return markTaskUndone(Parser.parseId(input, 7));
            case REMOVE:
                return removeTask(Parser.parseId(input, 7));
            case FIND:
                return findTasks(Parser.parseKeyword(input, 5));
            case TODO:
                return addToDo(Parser.parseDescription(input, 5));
            case DEADLINE:
                return addDeadline(input.substring(9));
            case EVENT:
                return addEvent(input.substring(6));
            default:
                return "I'm sorry, I don't know what that means.";
            }
        } catch (SappyException e) {
            return e.getMessage();
        }
    }
}
