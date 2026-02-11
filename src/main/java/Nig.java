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
    private static void handleMark(String[] words)
            throws BadFormatException, EmptyBodyException {
        if (words.length < 2) {
            throw new EmptyBodyException();
        }
        if (isNotInteger(words[1])) {
            throw new BadFormatException();
        }
        int taskNumber = Integer.parseInt(words[1]);
        if (taskNumber > taskCount) {
            throw new BadFormatException();
        }
        Task taskToMark = tasks[taskNumber - 1];
        taskToMark.markAsDone();
        System.out.println(SEPARATOR);
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   [X] " + taskToMark.getDescription());
        System.out.println(SEPARATOR);
    }

    // Mark a specific task as undone
    private static void handleUnmark(String[] words)
            throws BadFormatException, EmptyBodyException {
        if (words.length < 2) {
            throw new EmptyBodyException();
        }
        if (isNotInteger(words[1])) {
            throw new BadFormatException();
        }
        int taskNumber = Integer.parseInt(words[1]);
        if (taskNumber > taskCount) {
            throw new BadFormatException();
        }
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
    private static void addTodo(String[] words)
            throws EmptyBodyException {
        if (words.length < 2) {
            throw new EmptyBodyException();
        }
        String description = String.join(" ", Arrays.copyOfRange(words, 1, words.length));
        tasks[taskCount] = new Todo(description);
        handleNewTask();
    }

    // Add a new Deadline object to tasks
    private static void addDeadline(String[] words)
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
        tasks[taskCount] = new Deadline(description, by);
        handleNewTask();
    }

    // Add a new Event object to tasks
    private static void addEvent(String[] words)
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
        tasks[taskCount] = new Event(description, from, to);
        handleNewTask();
    }

    // Read user command and calls the relevant command handler
    private static void handleCommand(String line) {
        String[] words = line.split(" ");
        String command = words[0];
        try {
            if (command.equals("list")) {
                listTasks();
            } else if (command.equals("mark")) {
                handleMark(words);
            } else if (command.equals("unmark")) {
                handleUnmark(words);
            } else if (command.equals("todo")) {
                addTodo(words);
            } else if (command.equals("deadline")) {
                addDeadline(words);
            } else if (command.equals("event")) {
                addEvent(words);
            } else {
                throw new UnknownCommandException();
            }
        } catch (UnknownCommandException e) {
            handleUnknownCommand();
        } catch (EmptyBodyException e) {
            handleEmptyBody(command);
        } catch (BadFormatException | IndexOutOfBoundsException e) {
            handleBadFormat(command);
        }
    }

    private static void handleUnknownCommand() {
        System.out.println(SEPARATOR);
        System.out.println(" ERROR: command unknown");
        System.out.println(SEPARATOR);
    }

    private static void handleEmptyBody(String command) {
        System.out.println(SEPARATOR);
        System.out.print(" ERROR: ");
        if (command.equals("mark")) {
            System.out.println("please input a task number to mark");
        } else if (command.equals("unmark")) {
            System.out.println("please input a task number to unmark");
        } else if (command.equals("todo")) {
            System.out.println("description of todo cannot be empty");
        } else if (command.equals("deadline")) {
            System.out.println("description of deadline cannot be empty");
        } else if (command.equals("event")) {
            System.out.println("description of event cannot be empty");
        }
        System.out.println(SEPARATOR);
    }

    private static void handleBadFormat(String command) {
        System.out.println(SEPARATOR);
        System.out.print(" ERROR: incorrect usage of ");
        if (command.equals("mark")) {
            System.out.println("mark");
            System.out.println(" Proper Format: mark <number>");
        } else if (command.equals("unmark")) {
            System.out.println("unmark");
            System.out.println(" Proper Format: unmark <number>");
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
        System.out.println(SEPARATOR);
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
