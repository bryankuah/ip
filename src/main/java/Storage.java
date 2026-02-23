import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Handles loading and saving of task data to and from a local text file.
 * Data is stored in a pipe-delimited format: {@code DONE_STATUS | TASK_COMMAND}.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage object targeting the given file path.
     *
     * @param filePath Path to the data file for reading and writing tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the data file into the provided TaskList.
     * Silently skips file-not-found (first-time user) and skips malformed entries.
     *
     * @param tasks The TaskList to populate with loaded tasks.
     */
    public void load(TaskList tasks) {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                throw new FileNotFoundException();
            }
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] words = line.split(" \\| ");
                if (words.length >= 2) {
                    Task task = Parser.parseTaskFromString(words[1]);
                    if (Integer.parseInt(words[0]) == 1) {
                        task.markAsDone();
                    }
                    tasks.addTask(task);
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            // new user; silently handles
        } catch (UnknownCommandException | EmptyBodyException e) {
            System.out.println("____________________________________________________________");
            System.out.println(" ERROR: Entry is in bad format, moving on to next");
            System.out.println("____________________________________________________________");
        }
    }

    /**
     * Saves all tasks from the TaskList to the data file.
     * Creates the {@code data/} directory if it does not exist.
     *
     * @param tasks The TaskList whose tasks should be saved.
     */
    public void save(TaskList tasks) {
        try {
            File directory = new File("data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            FileWriter fw = new FileWriter(filePath);
            for (int i = 0; i < tasks.getSize(); i++) {
                fw.write(tasks.getTask(i).toFileFormat() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("____________________________________________________________");
            System.out.println(" ERROR: Unable to save to file");
            System.out.println("____________________________________________________________");
        }
    }
}
