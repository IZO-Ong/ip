package sappy.task;

/**
 * Represents a simple task without any specific date or time associated with it.
 */
public class ToDo extends Task {

    /**
     * Initializes a ToDo task with the specified description.
     *
     * @param description The description of the task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns the type icon for a ToDo task.
     *
     * @return The string "[T]".
     */
    @Override
    public String getTypeIcon() {
        return "[T]";
    }
}
