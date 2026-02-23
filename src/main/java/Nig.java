import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Nig {
    private static final String FILE_PATH = "data/nig.txt";

    private Ui ui;

    private TaskList tasks;

    public static boolean isNotInteger(String str) {
        if (str == null) {
            return true;
        }
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    // Mark a specific task as done
    private void handleMark(String[] words)
            throws BadFormatException, EmptyBodyException {
        if (words.length < 2) {
            throw new EmptyBodyException();
        }
        if (isNotInteger(words[1])) {
            throw new BadFormatException();
        }
        int taskNumber = Integer.parseInt(words[1]);
        if (taskNumber > tasks.getSize()) {
            throw new BadFormatException();
        }
        Task taskToMark = tasks.getTask(taskNumber - 1);
        taskToMark.markAsDone();
        ui.showMarkedTask(taskToMark, true);
        saveToFile();
    }

    // Mark a specific task as undone
    private void handleUnmark(String[] words)
            throws BadFormatException, EmptyBodyException {
        if (words.length < 2) {
            throw new EmptyBodyException();
        }
        if (isNotInteger(words[1])) {
            throw new BadFormatException();
        }
        int taskNumber = Integer.parseInt(words[1]);
        if (taskNumber > tasks.getSize()) {
            throw new BadFormatException();
        }
        Task taskToMark = tasks.getTask(taskNumber - 1);
        taskToMark.markAsUndone();
        ui.showMarkedTask(taskToMark, false);
        saveToFile();
    }

    // Print latest task and increment taskCount
    private void handleNewTask() {
        Task newTaskAdded = tasks.getTask(tasks.getSize() - 1);
        ui.showTaskAdded(newTaskAdded, tasks.getSize());
        saveToFile();
    }

    private void deleteTask(String[] words)
            throws EmptyBodyException, BadFormatException {
        if (words.length < 2) {
            throw new EmptyBodyException();
        }
        if (isNotInteger(words[1])) {
            throw new BadFormatException();
        }
        int taskNumber = Integer.parseInt(words[1]);
        if (taskNumber > tasks.getSize()) {
            throw new BadFormatException();
        }
        Task taskDeleted = tasks.deleteTask(taskNumber - 1);
        ui.showTaskDeleted(taskDeleted, tasks.getSize());
        saveToFile();
    }

    // Add a new Todo object to tasks
    private void addTodo(String[] words)
            throws EmptyBodyException {
        if (words.length < 2) {
            throw new EmptyBodyException();
        }
        String description = String.join(" ", Arrays.copyOfRange(words, 1, words.length));
        tasks.addTask(new Todo(description));
    }

    // Add a new Deadline object to tasks
    private void addDeadline(String[] words)
            throws EmptyBodyException {
        if (words.length < 2) {
            throw new EmptyBodyException();
        }
        int byIndex = 0;
        while (!words[byIndex].equals("/by")) {
            byIndex++;
        }
        String description = String.join(" ", Arrays.copyOfRange(words, 1, byIndex));
        String by = String.join(" ", Arrays.copyOfRange(words, byIndex + 1, words.length));
        tasks.addTask(new Deadline(description, by));
    }

    // Add a new Event object to tasks
    private void addEvent(String[] words)
            throws EmptyBodyException {
        if (words.length < 2) {
            throw new EmptyBodyException();
        }
        int fromIndex = 0;
        int toIndex = 0;
        while (!words[fromIndex].equals("/from")) {
            fromIndex++;
        }
        while (!words[toIndex].equals("/to")) {
            toIndex++;
        }
        String description = String.join(" ", Arrays.copyOfRange(words, 1, fromIndex));
        String from = String.join(" ", Arrays.copyOfRange(words, fromIndex + 1, toIndex));
        String to = String.join(" ", Arrays.copyOfRange(words, toIndex + 1, words.length));
        tasks.addTask(new Event(description, from, to));
    }

    // Read user command and calls the relevant command handler
    private void handleCommand(String line) {
        String[] words = line.split(" ");
        String command = words[0];
        try {
            if (command.equals("list")) {
                ui.showTaskList(tasks);
            } else if (command.equals("mark")) {
                handleMark(words);
            } else if (command.equals("unmark")) {
                handleUnmark(words);
            } else if (command.equals("todo")) {
                addTodo(words);
                handleNewTask();
            } else if (command.equals("deadline")) {
                addDeadline(words);
                handleNewTask();
            } else if (command.equals("event")) {
                addEvent(words);
                handleNewTask();
            } else if (command.equals("delete")) {
                deleteTask(words);
            } else {
                throw new UnknownCommandException();
            }
        } catch (UnknownCommandException e) {
            ui.handleUnknownCommand();
        } catch (EmptyBodyException e) {
            ui.handleEmptyBody(command);
        } catch (BadFormatException | IndexOutOfBoundsException e) {
            ui.handleBadFormat(command);
        }
    }

    private void runCommandLoop() {
        while (true) {
            String command = ui.readCommand();
            // loops until user enters "bye"
            if (command.equals("bye")) {
                break;
            }
            handleCommand(command);
        }
    }

    // Read line from file and adds to task list
    private void handleFileLine(String line) {
        String[] words = line.split(" ");
        String command = words[0];
        try {
            if (command.equals("todo")) {
                addTodo(words);
            } else if (command.equals("deadline")) {
                addDeadline(words);
            } else if (command.equals("event")) {
                addEvent(words);
            } else {
                throw new UnknownCommandException();
            }
        } catch (UnknownCommandException e) {
            ui.handleUnknownCommand();
        } catch (EmptyBodyException e) {
            ui.handleEmptyBody(command);
        } catch (IndexOutOfBoundsException e) {
            ui.handleBadFormat(command);
        }
    }

    private void loadSavedContent() {
        try {
            File f = new File(FILE_PATH);
            if (!f.exists()) {
                return;
            }
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] words = line.split(" \\| ");
                handleFileLine(words[1]);
                Task newlyAdded = tasks.getTask(tasks.getSize() - 1);
                if (Integer.parseInt(words[0]) == 1) {
                    newlyAdded.markAsDone();
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println(" New user detected");
            return;
        }
    }

    private void saveToFile() {
        try {
            File directory = new File("data");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            FileWriter fw = new FileWriter(FILE_PATH);
            for (int i = 0; i < tasks.getSize(); i++) {
                Task currentTask = tasks.getTask(i);
                fw.write(currentTask.toFileFormat() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            ui.showLine();
            System.out.println(" ERROR: Unable to save to file");
            ui.showLine();
        }
    }

    public Nig(String filePath) {
        ui = new Ui();
        tasks = new TaskList();

        loadSavedContent();
    }

    public void run() {
        ui.showWelcomeMessage();

        runCommandLoop();

        ui.showGoodbyeMessage();
    }

    public static void main(String[] args) {
        new Nig(FILE_PATH).run();
    }
}
