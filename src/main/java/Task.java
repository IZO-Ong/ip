public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        isDone = false;
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
        return getCheckboxIcon() + " " + description;
    }
}
