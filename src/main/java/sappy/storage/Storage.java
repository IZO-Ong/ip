package sappy.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import sappy.SappyException;
import sappy.task.Deadline;
import sappy.task.Event;
import sappy.task.Task;
import sappy.task.ToDo;

/**
 * Handles the loading and saving of task data to a local file.
 * This class ensures that task lists persist across different application sessions.
 */
public class Storage {
    private final String filePath;

    /**
     * Initializes a Storage instance with a specified file path.
     *
     * @param filePath The path to the file where task data is stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the provided list of tasks to the storage file.
     * If the directory for the file does not exist, it will be created.
     *
     * @param tasks The list of tasks to be saved.
     * @throws IOException If an error occurs during the file writing process.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        File f = new File(filePath);

        // if data folder does not exist yet, create folder
        if (f.getParentFile() != null && !f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }

        try (FileWriter fw = new FileWriter(f)) {
            for (Task t : tasks) {
                fw.write(t.toFileFormat() + System.lineSeparator());
            }
        }
    }

    /**
     * Converts a single line from the storage file into a Task object.
     *
     * @param line The raw string line from the file.
     * @return A Task object representing the stored data.
     * @throws SappyException If the line format is unrecognized or corrupted.
     */
    public static Task parseLineToTask(String line) throws SappyException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new SappyException("Corrupted task found in storage file.");
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;

        switch (type) {
        case "[T]":
            task = new ToDo(description);
            break;
        case "[D]":
            task = new Deadline(description, parts[3]);
            break;
        case "[E]":
            task = new Event(description, parts[3], parts[4]);
            break;
        default:
            throw new SappyException("Unknown task type: " + type);
        }

        if (isDone) {
            task.markDone();
        }

        return task;
    }

    /**
     * Loads the task list from the storage file.
     * Returns an empty list if the file does not exist.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws SappyException If the file content cannot be correctly parsed.
     */
    public ArrayList<Task> load() throws SappyException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File f = new File(filePath);

        if (!f.exists()) {
            return loadedTasks;
        }

        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                String line = s.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                loadedTasks.add(parseLineToTask(line));
            }
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            throw new SappyException("Error loading file: " + e.getMessage());
        }
        return loadedTasks;
    }
}
