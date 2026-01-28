package sappy.task;

/**
 * Represents a generic task in the Sappy chatbot.
 * Serves as the base class for specific task types like ToDo, Deadline, and Event.
 */
public abstract class Task {
    private final String description;
    private boolean isDone;

    /**
     * Initializes a task with the specified description.
     *
     * @param description The textual description of the task.
     */
    public Task(String description) {
        this.description = description;
        isDone = false;
    }

    /**
     * Returns the icon representing the specific type of task.
     *
     * @return A string representing the task type (e.g., "[T]").
     */
    public abstract String getTypeIcon();

    /**
     * Formats the task data for saving into a local text file.
     *
     * @return A formatted string suitable for storage.
     */
    public String toFileFormat() {
        return String.format("%s | %d | %s",
                getTypeIcon(),
                isDone ? 1 : 0,
                description);
    }

    /**
     * Marks the task as completed.
     *
     * @return A message confirming the completion status.
     */
    public String markDone() {
        if (!isDone) {
            isDone = true;
            return "I've marked this task as done:\n  " + this.toString();
        }
        return "This task is already marked as done!";
    }

    private String getCheckboxIcon() {
        return "[" + (isDone ? "X" : " ") + "]";
    }

    /**
     * Marks the task as incomplete.
     *
     * @return A message confirming the incomplete status.
     */
    public String markUndone() {
        if (isDone) {
            isDone = false;
            return "OK, I've marked this task as not done yet:\n  " + this.toString();
        }
        return "This task is already not done!";
    }

    /**
     * Returns a string representation of the task for display to the user.
     *
     * @return A formatted string containing the type, status, and description.
     */
    @Override
    public String toString() {
        return getTypeIcon() + getCheckboxIcon() + " " + description;
    }
}
