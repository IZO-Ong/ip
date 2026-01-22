import java.io.File;
import java.io.FileWriter;
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
        FileWriter fw = new FileWriter(f);
        
        for (Task t : tasks) {
            fw.write(t.toFileFormat() + System.lineSeparator());
        }
        
        fw.close();
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
                Task t = parseLineToTask(line);
                loadedTasks.add(t);
            }
        } catch (Exception e) {
            throw new SappyException("Error loading file.");
        }
        return loadedTasks;
    }
}
