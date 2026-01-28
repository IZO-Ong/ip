package sappy.storage;

import java.util.ArrayList;

import sappy.task.Task;

/**
 * A stub of Storage class used for testing.
 * This class simulates storage behavior without performing actual file I/O,
 * ensuring that tests remain isolated from the file system.
 */
public class StorageStub extends Storage {
    /**
     * Initialises a new StorageStub with a dummy file path.
     */
    public StorageStub() {
        super("unused/path");
    }

    /**
     * Simulates saving tasks. Does nothing to avoid file system interaction.
     *
     * @param tasks The list of tasks that would normally be saved.
     */
    @Override
    public void save(ArrayList<Task> tasks) {
    }

    /**
     * Simulates loading tasks. Always returns an empty list.
     *
     * @return An empty ArrayList of tasks.
     */
    @Override
    public ArrayList<Task> load() {
        return new ArrayList<>();
    }
}
