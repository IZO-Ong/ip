package sappy.logic;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String listTasks() {
        List<Task> tasks = taskList.getAllTasks();

        if (tasks.isEmpty()) {
            return "Chirp! Your nest is empty! Time to gather some tasks?";
        }

        String formattedTasks = IntStream.range(0, tasks.size())
                .mapToObj(i -> (i + 1) + ". " + tasks.get(i).toString())
                .collect(Collectors.joining("\n"));

        return "Peep! Here are the seeds in your garden:\n" + formattedTasks;
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
            throw new SappyException("Coo? Perhaps you missed out on the description?");
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

    private String addTask(Task t) throws SappyException {
        assert t != null : "Cannot add a null task";
        int oldSize = taskList.getSize();

        if (taskList.contains(t)) {
            throw new SappyException("Sappy already has this twig in the nest!");
        }

        taskList.add(t);

        assert taskList.getSize() == oldSize + 1 : "Task list size should increase by 1";
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
        return "I've tucked this into the nest:\n  " + t.toString()
                + "\nYou have " + taskList.getSize() + " task(s) to look after now!";
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
        if (taskList.isEmpty()) {
            throw new SappyException("There's nothing in the nest to peck away!");
        }

        int oldSize = taskList.getSize();
        assert oldSize > 0 : "Cannot remove from an empty list";

        Task removed = taskList.remove(taskId - 1);
        assert taskList.getSize() == oldSize - 1 : "Task list size should decrease by 1";

        autoSave();
        return "Whoosh! That task has flown away:\n" + removed.toString()
                + "\nNow your nest has " + taskList.getSize() + " task(s) left!";
    }

    /**
     * Returns a formatted string of tasks that match the specified keyword.
     *
     * @param keyword The string to search for within task descriptions.
     * @return A formatted list of matching tasks.
     */
    public String findTasks(String keyword) throws SappyException {
        List<Task> matchingTasks = taskList.getAllTasks().stream()
                .filter(task -> task.containsInDescription(keyword))
                .toList();

        if (matchingTasks.isEmpty()) {
            return "Sappy looked everywhere, but couldn't find any twigs matching '" + keyword + "'!";
        }

        String formattedTasks = IntStream.range(0, matchingTasks.size())
                .mapToObj(i -> (i + 1) + "." + matchingTasks.get(i))
                .collect(Collectors.joining("\n"));

        return "Peep! I've scouted the matching tasks:\n" + formattedTasks;
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
        assert !Objects.equals(input, "") : "Input string to getResponse should not be empty";

        try {
            Command cmd = Parser.parseCommand(input);
            this.lastCommand = cmd;

            String commandWord = input.trim().split(" ")[0];
            int offset = commandWord.length() + 1;

            switch (cmd) {
            case BYE:
                return "Tweet tweet! " + this.name + " will be waiting until you fly back!";
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
                return "Coo? " + this.name + " doesn't understand that bird call.";
            }
        } catch (SappyException e) {
            this.lastCommand = Command.ERROR;
            return e.getMessage();
        }
    }
}
