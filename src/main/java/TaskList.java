import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task remove(int index) throws SappyException {
        validateIndex(index);
        return tasks.remove(index);
    }

    public String markDone(int index) throws SappyException {
        validateIndex(index);
        return tasks.get(index).markDone();
    }

    public String markUndone(int index) throws SappyException {
        validateIndex(index);
        return tasks.get(index).markUndone();
    }

    public ArrayList<Task> getAllTasks() {
        return this.tasks;
    }

    public int getSize() {
        return tasks.size();
    }

    public Task get(int index) throws SappyException {
        validateIndex(index);
        return tasks.get(index);
    }

    private void validateIndex(int index) throws SappyException {
        if (index < 0 || index >= tasks.size()) {
            throw new SappyException("That task does not exist!");
        }
    }
}