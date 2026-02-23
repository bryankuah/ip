/**
 * Represents an Event task — a task that takes place over a time range with a start and end time.
 */

public class Event extends Task {

    /** The start date/time of the event. */
    protected String from;

    /** The end date/time of the event. */
    protected String to;

    /**
     * Constructs an Event task with the given description, start time, and end time.
     *
     * @param description A non-empty string describing the event.
     * @param from        The start date/time of the event (e.g. "Mon 2pm").
     * @param to          The end date/time of the event (e.g. "Mon 4pm").
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of this Event, prefixed with {@code [E]}
     * and appended with its time range.
     *
     * @return A string in the format {@code [E][STATUS] description (from: START to: END)}.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns the file-format string for this Event task.
     * Format: {@code DONE_STATUS | event DESCRIPTION /from START /to END}
     *
     * @return The file-format string used for saving to disk.
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | event " + description + " /from " + from + " /to " + to;
    }
}
