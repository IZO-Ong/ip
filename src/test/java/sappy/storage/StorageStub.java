package sappy.storage;

import sappy.task.Task;
import java.util.ArrayList;

public class StorageStub extends Storage {
    public StorageStub() {
        super("unused/path");
    }

    @Override
    public void save(ArrayList<Task> tasks) {
    }

    @Override
    public ArrayList<Task> load() {
        return new ArrayList<>();
    }
}