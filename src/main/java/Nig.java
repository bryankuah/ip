public class Nig {
    private static final String FILE_PATH = "data/nig.txt";

    private Ui ui;

    private TaskList tasks;

    private Storage storage;

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

    public Nig(String filePath) {
        ui = new Ui();
        tasks = new TaskList();
        storage = new Storage(filePath);
        storage.load(tasks);
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
