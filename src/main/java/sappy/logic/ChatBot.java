package sappy.logic;

import java.util.ArrayList;

import sappy.command.Command;
import sappy.parser.Parser;
import sappy.storage.Storage;
import sappy.task.Deadline;
import sappy.task.Event;
import sappy.task.Task;
import sappy.task.TaskList;
import sappy.task.ToDo;

/**
 * Encapsulates the main logic of Sappy chatbot.
 * Handles task management, user interaction logic and automated saving.
 */
public class ChatBot {
    private final TaskList taskList;
    private final String name;
    private final Storage storage;
    private Command lastCommand;

    /**
     * Initialises a new ChatBot instance.
     * Loads existing tasks from the specified file path or starts with an empty list.
     *
     * @param name Name of the chatbot.
     * @param filePath Path to the file where tasks are stored.
     */
    public ChatBot(String name, String filePath) {
        this.name = name;
        this.storage = new Storage(filePath);

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (SappyException e) {
            System.out.println("Warning: " + e.getMessage() + "\nStarting with an empty list.");
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
     */
    public ChatBot(String name, Storage storage) {
        this.name = name;
        this.storage = storage;

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (SappyException e) {
            System.out.println("Warning: " + e.getMessage() + "\nStarting with an empty list.");
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
     * Adds a new Deadline task using parsed details.
     *
     * @param details Array where [0] is description and [1] is the /by date.
     * @return Success message.
     */
    public String addDeadline(String[] details) throws SappyException {
        assert details.length >= 2 : "Deadline details must contain description and date";
        Task t = new Deadline(details[0], details[1]);
        return addTask(t);
    }

    /**
     * Adds a new Event task using parsed details.
     *
     * @param details Array where [0] is description, [1] is /from, and [2] is /to.
     * @return Success message.
     */
    public String addEvent(String[] details) throws SappyException {
        assert details.length >= 3 : "Event details must contain description, from, and to dates";
        Task t = new Event(details[0], details[1], details[2]);
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
            System.err.println("Error: Could not save task: " + e.getMessage());
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
                + "\nNow you have " + taskList.getSize() + " task(s) in the list.";
    }

    /**
     * Returns a formatted string of tasks that match the specified keyword.
     *
     * @param keyword The string to search for within task descriptions.
     * @return A formatted list of matching tasks.
     */
    public String findTasks(String keyword) throws SappyException {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (int i = 0; i < taskList.getSize(); i++) {
            if (taskList.get(i).toString().contains(keyword)) {
                matchingTasks.add(taskList.get(i));
            }
        }

        if (matchingTasks.isEmpty()) {
            return "No matching tasks found.";
        }

        StringBuilder output = new StringBuilder("Here are the matching tasks:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            output.append(i + 1).append(".").append(matchingTasks.get(i));
            if (i < matchingTasks.size() - 1) {
                output.append("\n");
            }
        }
        return output.toString();
    }

    /**
     * Returns the type of the last executed command.
     */
    public Command getLastCommand() {
        return lastCommand;
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
            this.lastCommand = cmd;

            String commandWord = input.trim().split(" ")[0];
            int offset = commandWord.length() + 1;

            switch (cmd) {
            case BYE:
                return "Bye! " + this.name + " will be very lonely until you come back!";
            case LIST:
                return listTasks();
            case MARK:
                return markTaskDone(Parser.parseId(input, offset));
            case UNMARK:
                return markTaskUndone(Parser.parseId(input, offset));
            case REMOVE:
                return removeTask(Parser.parseId(input, offset));
            case FIND:
                return findTasks(Parser.parseKeyword(input, offset));
            case TODO:
                return addToDo(Parser.parseToDoDetails(input, offset));
            case DEADLINE:
                return addDeadline(Parser.parseDeadlineDetails(input, offset));
            case EVENT:
                return addEvent(Parser.parseEventDetails(input, offset));
            default:
                return "I'm sorry, I don't know what that means.";
            }
        } catch (SappyException e) {
            return e.getMessage();
        }
    }
}
