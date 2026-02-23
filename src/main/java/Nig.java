/**
 * Main class for the NIG task manager chatbot.
 * Manages the lifecycle of the application including loading data,
 * running the command loop, and printing to console
 */
public class Nig {
    private static final String FILE_PATH = "data/nig.txt";

    private Ui ui;

    private TaskList tasks;

    private Storage storage;

    /**
     * Runs the main command loop, reading and processing user commands
     * until the user enters "bye".
     */
    private void runCommandLoop() {
        while (true) {
            String command = ui.readCommand();
            // loops until user enters "bye"
            if (command.equals("bye")) {
                break;
            }
            Parser.handleCommand(command, tasks, ui, storage);
        }
    }

    /**
     * Constructs a Nig instance and loads existing tasks from file.
     *
     * @param filePath Path to the data file for persisting tasks.
     */
    public Nig(String filePath) {
        ui = new Ui();
        tasks = new TaskList();
        storage = new Storage(filePath);
        storage.load(tasks);
    }

    /**
     * Starts the chatbot: shows welcome, runs loop, then shows goodbye.
     */
    public void run() {
        ui.showWelcomeMessage();

        runCommandLoop();

        ui.showGoodbyeMessage();
    }

    /**
     * Entry point of the NIG application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Nig(FILE_PATH).run();
    }
}
