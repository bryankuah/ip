import java.util.Scanner;

/**
 * Handles all user-facing interactions for the NIG chatbot.
 * Reads user input and displays formatted output messages and task information.
 */
public class Ui {

    private static final String SEPARATOR = "____________________________________________________________";

    private static final String LOGO = SEPARATOR + "\n"
            + " Hello! I'm NIG\n"
            + " What can I do for you?\n"
            + SEPARATOR;

    private static final String GOODBYE = SEPARATOR + "\n"
            + " Bye. Hope to see you again soon!\n"
            + SEPARATOR;

    private Scanner in;

    /**
     * Constructs an Ui object and initializes the input scanner.
     */
    public Ui() {
        in = new Scanner(System.in);
    }

    /**
     * Reads and returns a single line of user input.
     *
     * @return The command string entered by the user.
     */
    public String readCommand() {
        return in.nextLine();
    }

    /**
     * Displays the welcome message at application startup.
     */
    public void showWelcomeMessage() {
        System.out.println(LOGO);
    }

    /**
     * Displays the goodbye message when the user exits.
     */
    public void showGoodbyeMessage() {
        System.out.println(GOODBYE);
    }

    /**
     * Prints a horizontal separator line.
     */
    public void showLine() {
        System.out.println(SEPARATOR);
    }

    /**
     * Displays all tasks in the given TaskList with their index numbers.
     *
     * @param tasks The TaskList to display.
     */
    public void showTaskList(TaskList tasks) {
        showLine();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.getSize(); i++) {
            Task currentTask = tasks.getTask(i);
            System.out.print(" " + (i + 1) + ".");
            System.out.println(currentTask);
        }
        showLine();
    }

    /**
     * Displays a confirmation message after marking or unmarking a task.
     *
     * @param task   The task that was marked or unmarked.
     * @param isDone True if marked as done, false if marked as not done.
     */
    public void showMarkedTask(Task task, boolean isDone) {
        showLine();
        if (isDone) {
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   [X] " + task.getDescription());
        } else {
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   [ ] " + task.getDescription());
        }
        showLine();
    }

    /**
     * Displays a confirmation message after a new task is added.
     *
     * @param task The task that was added.
     * @param size The updated number of tasks in the list.
     */
    public void showTaskAdded(Task task, int size) {
        showLine();
        System.out.println(" Got it. I've added this task:");
        System.out.print("   ");
        System.out.println(task);
        System.out.println(" Now you have " + size + " tasks in the list.");
        showLine();
    }

    /**
     * Displays a confirmation message after a task is deleted.
     *
     * @param task The task that was removed.
     * @param size The updated number of tasks remaining in the list.
     */
    public void showTaskDeleted(Task task, int size) {
        showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.print("   ");
        System.out.println(task);
        System.out.println(" Now you have " + size + " tasks in the list.");
        showLine();
    }

    /**
     * Displays an error message when an unrecognized command is entered.
     */
    public void handleUnknownCommand() {
        showLine();
        System.out.println(" ERROR: command unknown");
        showLine();
    }

    /**
     * Displays an error message when a command is entered without required arguments.
     *
     * @param command The command that was entered without its required body.
     */
    public void handleEmptyBody(String command) {
        showLine();
        System.out.print(" ERROR: ");
        if (command.equals("mark")) {
            System.out.println("please input a task number to mark");
        } else if (command.equals("unmark")) {
            System.out.println("please input a task number to unmark");
        } else if (command.equals("delete")) {
            System.out.println("please input a task number to delete");
        } else if (command.equals("todo")) {
            System.out.println("description of todo cannot be empty");
        } else if (command.equals("deadline")) {
            System.out.println("description of deadline cannot be empty");
        } else if (command.equals("event")) {
            System.out.println("description of event cannot be empty");
        }
        showLine();
    }

    /**
     * Displays an error message with correct usage when a command is badly formatted.
     *
     * @param command The command that was used with incorrect format.
     */
    public void handleBadFormat(String command) {
        showLine();
        System.out.print(" ERROR: incorrect usage of ");
        if (command.equals("mark")) {
            System.out.println("mark");
            System.out.println(" Please use a valid task number");
            System.out.println(" Proper Format: mark <number>");
        } else if (command.equals("unmark")) {
            System.out.println("unmark");
            System.out.println(" Please use a valid task number");
            System.out.println(" Proper Format: unmark <number>");
        } else if (command.equals("delete")) {
            System.out.println("delete");
            System.out.println(" Please use a valid task number");
            System.out.println(" Proper Format: delete <number>");
        } else if (command.equals("todo")) {
            System.out.println("todo");
            System.out.println(" Proper Format: todo <description>");
        } else if (command.equals("deadline")) {
            System.out.println("deadline");
            System.out.println(" Proper Format: deadline <description> /by <date>");
        } else if (command.equals("event")) {
            System.out.println("event");
            System.out.println(" Proper Format: event <description> /from <date> /to <date>");
        }
        showLine();
    }
}