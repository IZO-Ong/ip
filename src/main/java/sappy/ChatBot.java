package sappy;

import sappy.command.Command;
import sappy.storage.Storage;
import sappy.task.TaskList;
import sappy.task.Task;
import sappy.task.ToDo;
import sappy.task.Deadline;
import sappy.task.Event;
import sappy.parser.Parser;
import sappy.ui.Ui;
import java.lang.StringBuilder;

public class ChatBot {
    private final TaskList taskList;
    private final String name;
    private final Storage storage;
    private final Ui ui;

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

    public String listTasks() {
        StringBuilder output = new StringBuilder();
        output.append("Here are the tasks in your list:\n");
        for (int i = 0; i < taskList.getSize(); i++) {
            try {
                if (i > 0) output.append("\n");
                output.append(i + 1).append(". ").append(taskList.get(i).toString());
            } catch (SappyException ignored) {}
        }
        return output.toString();
    }

    public String addToDo(String description) throws SappyException {
        if (description.trim().isEmpty()) {
            throw new SappyException("The description cannot be empty.");
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
        return "I've added this task:\n  " + t.toString() +
                "\nNow you have " + taskList.getSize() + " task(s) in the list.";
    }

    public String markTaskDone(int taskID) throws SappyException {
        String response = taskList.markDone(taskID - 1);
        autoSave();
        return response;
    }

    public String markTaskUndone(int taskID) throws SappyException {
        String response = taskList.markUndone(taskID - 1);
        autoSave();
        return response;
    }

    public String removeTask(int taskID) throws SappyException {
        Task removed = taskList.remove(taskID - 1);
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
                    output.append(count).append(".").append(task.toString());
                    count++;
                }
            } catch (SappyException ignored) {
                // index validation is handled by TaskList
            }
        }

        if (count == 1) {
            return "No matching tasks found in your list.";
        }

        return "Here are the matching tasks in your list:\n" + output.toString();
    }

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
                return addDeadline(input.substring(9)); // You can further refine these too
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
