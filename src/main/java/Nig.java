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

        while (true) {
            line = in.nextLine();
            if (line.equals("bye")) {
                break;
            }
            System.out.println(separator + " " + line + "\n" + separator);
        }

        System.out.println(goodbye);
    }
}
