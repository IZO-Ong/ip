public abstract class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        isDone = false;
    }

    public abstract String getTypeIcon();

    public String toFileFormat() {
        return String.format("%s | %d | %s",
                getTypeIcon(),
                isDone ? 1 : 0,
                description);
    }

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

    public String markUndone() {
        if (isDone) {
            isDone = false;
            return "OK, I've marked this task as not done yet:\n  " + this.toString();
        }
        return "This task is already not done!";
    }

    @Override
    public String toString() {
        return getTypeIcon() + getCheckboxIcon() + " " + description;
    }
}
