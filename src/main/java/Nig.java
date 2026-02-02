import java.util.Scanner;

public class Nig {
    public static void main(String[] args) {
        String logo = "____________________________________________________________\n"
                + " Hello! I'm NIG\n"
                + " What can I do for you?\n"
                + "____________________________________________________________\n";

        String goodbye = "____________________________________________________________\n"
                + " Bye. Hope to see you again soon!\n"
                + "____________________________________________________________\n";

        String separator = "____________________________________________________________\n";

        System.out.println(logo);

        String line;
        Scanner in = new Scanner(System.in);

        String[] tasks = new String[100];
        int taskCount = 0;

        while (true) {
            line = in.nextLine();
            if (line.equals("bye")) {
                break;
            }
            if (line.equals("list")) {
                System.out.println(separator);
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println(separator);
            } else {
                tasks[taskCount] = line;
                taskCount++;
                System.out.println(separator + " added: " + line + "\n" + separator);
            }
        }

        System.out.println(goodbye);
    }
}
