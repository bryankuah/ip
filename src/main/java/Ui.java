import java.util.Scanner;

public class Ui {

    private static final String SEPARATOR = "____________________________________________________________\n";

    private static final String LOGO = SEPARATOR
            + " Hello! I'm NIG\n"
            + " What can I do for you?\n"
            + SEPARATOR;

    private static final String GOODBYE = SEPARATOR
            + " Bye. Hope to see you again soon!\n"
            + SEPARATOR;

    private Scanner in;

    public Ui() {
        in = new Scanner(System.in);
    }

    public String readCommand() {
        return in.nextLine();
    }

    public void showWelcomeMessage() {
        System.out.print(LOGO);
    }

    public void showGoodbyeMessage() {
        System.out.print(GOODBYE);
    }

    public void showLine() {
        System.out.print(SEPARATOR);
    }

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

    public void showTaskAdded(Task task, int size) {
        showLine();
        System.out.println(" Got it. I've added this task:");
        System.out.print("   ");
        System.out.println(task);
        System.out.println(" Now you have " + size + " tasks in the list.");
        showLine();
    }

    public void showTaskDeleted(Task task, int size) {
        showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.print("   ");
        System.out.println(task);
        System.out.println(" Now you have " + size + " tasks in the list.");
        showLine();
    }

    public void handleUnknownCommand() {
        showLine();
        System.out.println(" ERROR: command unknown");
        showLine();}

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