import java.util.Scanner;

public class Nig {
    public static void main(String[] args) {
        String LOGO = "____________________________________________________________\n"
                + " Hello! I'm NIG\n"
                + " What can I do for you?\n"
                + "____________________________________________________________\n";

        String GOODBYE = "____________________________________________________________\n"
                + " Bye. Hope to see you again soon!\n"
                + "____________________________________________________________\n";

        String SEPARATOR = "____________________________________________________________\n";

        System.out.println(LOGO);

        String line;
        Scanner in = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            line = in.nextLine();
            if (line.equals("bye")) {
                break;
            }
            if (line.equals("list")) {
                System.out.println(SEPARATOR);
                for (int i = 0; i < taskCount; i++) {
                    Task currentTask = tasks[i];
                    System.out.print(" " + (i + 1) + ". ");
                    System.out.print("[" + currentTask.getStatusIcon() + "] ");
                    System.out.println(currentTask.getDescription());
                }
                System.out.println(SEPARATOR);
            } else if (line.split(" ")[0].equals("mark")) {
                int taskNumber = Integer.parseInt(line.split(" ")[1]);
                Task taskToMark = tasks[taskNumber - 1];
                taskToMark.markAsDone();
                System.out.println(SEPARATOR);
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   [X] " + taskToMark.getDescription());
                System.out.println(SEPARATOR);
            } else if (line.split(" ")[0].equals("unmark")) {
                int taskNumber = Integer.parseInt(line.split(" ")[1]);
                Task taskToMark = tasks[taskNumber - 1];
                taskToMark.markAsUndone();
                System.out.println(SEPARATOR);
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   [ ] " + taskToMark.getDescription());
                System.out.println(SEPARATOR);
            } else {
                tasks[taskCount] = new Task(line);
                taskCount++;
                System.out.println(SEPARATOR + " added: " + line + "\n" + SEPARATOR);
            }
        }

        System.out.println(GOODBYE);
    }
}
