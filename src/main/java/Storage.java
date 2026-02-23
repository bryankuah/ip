import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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
