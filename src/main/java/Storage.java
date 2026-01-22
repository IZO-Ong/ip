import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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

    public ArrayList<Task> load() throws SappyException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File f = new File(filePath);

        if (!f.exists()) {
            return loadedTasks;
        }

        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                String line = s.nextLine();
                if (line.trim().isEmpty()) continue;
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