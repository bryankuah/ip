import java.util.Scanner;
import java.util.Arrays;

public class Nig {
    private static final String LOGO = "____________________________________________________________\n"
            + " Hello! I'm NIG\n"
            + " What can I do for you?\n"
            + "____________________________________________________________\n";

    private static final String GOODBYE = "____________________________________________________________\n"
            + " Bye. Hope to see you again soon!\n"
            + "____________________________________________________________\n";

    private static final String SEPARATOR = "____________________________________________________________";


    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;

    // Prints out all the tasks
    private static void listTasks() {
        System.out.println(SEPARATOR);
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            Task currentTask = tasks[i];
            System.out.print(" " + (i + 1) + ".");
            System.out.println(currentTask);
        }
        System.out.println(SEPARATOR);
    }

    // Mark a specific task as done
    private static void handleMark(int taskNumber) {
        Task taskToMark = tasks[taskNumber - 1];
        taskToMark.markAsDone();
        System.out.println(SEPARATOR);
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   [X] " + taskToMark.getDescription());
        System.out.println(SEPARATOR);
    }

    // Mark a specific task as undone
    private static void handleUnmark(int taskNumber) {
        Task taskToMark = tasks[taskNumber - 1];
        taskToMark.markAsUndone();
        System.out.println(SEPARATOR);
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   [ ] " + taskToMark.getDescription());
        System.out.println(SEPARATOR);
    }

    // Print latest task and increment taskCount
    private static void handleNewTask() {
        System.out.println(SEPARATOR);
        System.out.println(" Got it. I've added this task:");
        System.out.print("   ");
        System.out.println(tasks[taskCount]);
        taskCount++;
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    // Add a new Todo object to tasks
    private static void addTodo(String[] words) {
        String description = String.join(" ", Arrays.copyOfRange(words, 1, words.length));
        tasks[taskCount] = new Todo(description);
        handleNewTask();
    }

    // Add a new Deadline object to tasks
    private static void addDeadline(String[] words) {
        int byIndex = 0;
        while (!words[byIndex].equals("/by")) {
            byIndex++;
        }
        String description = String.join(" ", Arrays.copyOfRange(words, 1, byIndex));
        String by = String.join(" ", Arrays.copyOfRange(words, byIndex + 1, words.length));
        tasks[taskCount] = new Deadline(description, by);
        handleNewTask();
    }

    // Add a new Event object to tasks
    private static void addEvent(String[] words) {
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
        tasks[taskCount] = new Event(description, from, to);
        handleNewTask();
    }

    // Read user command and calls the relevant command handler
    private static void handleCommand(String line) {
        String[] words = line.split(" ");
        String command = words[0];

        if (command.equals("list")) {
            listTasks();
        } else if (command.equals("mark")) {
            handleMark(Integer.parseInt(words[1]));
        } else if (command.equals("unmark")) {
            handleUnmark(Integer.parseInt(words[1]));
        } else if (command.equals("todo")) {
            addTodo(words);
        } else if (command.equals("deadline")) {
            addDeadline(words);
        } else if (command.equals("event")) {
            addEvent(words);
        }
    }

    private static void runCommandLoop() {
        String line;
        Scanner in = new Scanner(System.in);
        while (true) {
            line = in.nextLine();
            // loops until user enters "bye"
            if (line.equals("bye")) {
                break;
            }
            handleCommand(line);
        }
    }

    public static void main(String[] args) {
        System.out.println(LOGO);

        runCommandLoop();

        System.out.println(GOODBYE);
    }
}
