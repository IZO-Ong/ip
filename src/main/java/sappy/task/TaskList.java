package sappy.task;

import sappy.SappyException;
import java.util.ArrayList;

/**
 * Manages a collection of tasks.
 * Provides methods to add, remove and modify tasks within the list.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Initializes an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Initializes a task list with an existing collection of tasks.
     *
     * @param tasks An ArrayList of Task objects.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param t The task to be added.
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Removes a task from the list at the specified index.
     *
     * @param index The 0-based index of the task.
     * @return The task that was removed.
     * @throws SappyException If the index is out of bounds.
     */
    public Task remove(int index) throws SappyException {
        validateIndex(index);
        return tasks.remove(index);
    }

    /**
     * Marks the task at the specified index as completed.
     *
     * @param index The 0-based index of the task.
     * @return A confirmation message.
     * @throws SappyException If the index is out of bounds.
     */
    public String markDone(int index) throws SappyException {
        validateIndex(index);
        return tasks.get(index).markDone();
    }

    /**
     * Marks the task at the specified index as incomplete.
     *
     * @param index The 0-based index of the task.
     * @return A confirmation message.
     * @throws SappyException If the index is out of bounds.
     */
    public String markUndone(int index) throws SappyException {
        validateIndex(index);
        return tasks.get(index).markUndone();
    }

    /**
     * Returns the entire list of tasks.
     *
     * @return An ArrayList of all tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return this.tasks;
    }

    /**
     * Returns the current number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Retrieves a task from the list at the specified index.
     *
     * @param index The 0-based index of the task.
     * @return The task at the given index.
     * @throws SappyException If the index is out of bounds.
     */
    public Task get(int index) throws SappyException {
        validateIndex(index);
        return tasks.get(index);
    }

    /**
     * Checks if the given index is within the valid range of the task list.
     *
     * @param index The index to validate.
     * @throws SappyException If the index is negative or exceeds the list size.
     */
    private void validateIndex(int index) throws SappyException {
        if (index < 0 || index >= tasks.size()) {
            throw new SappyException("That task does not exist!");
        }
    }
}