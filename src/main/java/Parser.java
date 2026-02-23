import java.util.Arrays;

/**
 * Parses user input strings and dispatches the appropriate command logic.
 * Also handles parsing of task strings read from storage.
 */
public class Parser {

    /**
     * Returns true if the given string cannot be parsed as an integer.
     *
     * @param str The string to check.
     * @return True if not a valid integer, false otherwise.
     */
    public static boolean isNotInteger(String str) {
        if (str == null) return true;
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    /**
     * Creates a Todo task from the parsed command words.
     *
     * @param words The command split by spaces; words[0] is "todo".
     * @return A new Todo task.
     */
    private static Todo createTodo(String[] words) {
        String description = String.join(" ", Arrays.copyOfRange(words, 1, words.length));
        return new Todo(description);
    }

    /**
     * Creates a Deadline task from the parsed command words.
     * Expects a "/by" token separating description from the due date.
     *
     * @param words The command split by spaces; words[0] is "deadline".
     * @return A new Deadline task.
     */
    private static Deadline createDeadline(String[] words) {
        int byIndex = 0;
        while (!words[byIndex].equals("/by")) {
            byIndex++;
        }
        String description = String.join(" ", Arrays.copyOfRange(words, 1, byIndex));
        String by = String.join(" ", Arrays.copyOfRange(words, byIndex + 1, words.length));
        return new Deadline(description, by);
    }

    /**
     * Creates an Event task from the parsed command words.
     * Expects "/from" and "/to" tokens to delimit the event's time range.
     *
     * @param words The command split by spaces; words[0] is "event".
     * @return A new Event task.
     */
    private static Event createEvent(String[] words) {
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
        return new Event(description, from, to);
    }

    /**
     * Parses a task from a raw command string (used when loading from file).
     *
     * @param line The raw task string e.g. "todo read book".
     * @return The corresponding Task object.
     * @throws EmptyBodyException      If the task has no description.
     * @throws UnknownCommandException If the task type is unrecognized.
     */
    public static Task parseTaskFromString(String line) throws EmptyBodyException, UnknownCommandException {
        String[] words = line.split(" ");
        String command = words[0];
        if (words.length < 2) throw new EmptyBodyException();

        if (command.equals("todo")) {
            return createTodo(words);
        } else if (command.equals("deadline")) {
            return createDeadline(words);
        } else if (command.equals("event")) {
            return createEvent(words);
        }
        throw new UnknownCommandException();
    }

    /**
     * Parses and executes the user's command, updating tasks, UI, and storage as needed.
     *
     * @param line    The full command string entered by the user.
     * @param tasks   The current task list to operate on.
     * @param ui      The UI object used for displaying output.
     * @param storage The storage object used for persisting tasks.
     */
    public static void handleCommand(String line, TaskList tasks, Ui ui, Storage storage) {
        String[] words = line.split(" ");
        String command = words[0];

        try {
            if (command.equals("list")) {
                ui.showTaskList(tasks);
            } else if (command.equals("mark") || command.equals("unmark") || command.equals("delete")) {
                if (words.length < 2) {
                    throw new EmptyBodyException();
                }
                if (isNotInteger(words[1])) {
                    throw new BadFormatException();
                }
                int taskNum = Integer.parseInt(words[1]);
                if (taskNum > tasks.getSize() || taskNum < 1) {
                    throw new BadFormatException();
                }

                if (command.equals("delete")) {
                    Task deletedTask = tasks.deleteTask(taskNum - 1);
                    ui.showTaskDeleted(deletedTask, tasks.getSize());
                } else {
                    Task taskToMark = tasks.getTask(taskNum - 1);
                    if (command.equals("mark")) {
                        taskToMark.markAsDone();
                        ui.showMarkedTask(taskToMark, true);
                    } else {
                        taskToMark.markAsUndone();
                        ui.showMarkedTask(taskToMark, false);
                    }
                }
                storage.save(tasks);
            } else if (command.equals("todo") || command.equals("deadline") || command.equals("event")) {
                Task newTask = parseTaskFromString(line);
                tasks.addTask(newTask);
                ui.showTaskAdded(newTask, tasks.getSize());
                storage.save(tasks);
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
}
