/**
 * Represents a Deadline task — a task that must be completed by a specific date or time.
 */
public class Deadline extends Task {

    /** The due date or time for this deadline. */
    protected String by;

    /**
     * Constructs a Deadline task with the given description and due date/time.
     *
     * @param description A non-empty string describing the deadline task.
     * @param by          The due date or time as a string (e.g. "Friday 11pm").
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string representation of this Deadline, prefixed with {@code [D]}
     * and appended with the due date.
     *
     * @return A string in the format {@code [D][STATUS] description (by: DUE_DATE)}.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * Returns the file-format string for this Deadline task.
     * Format: {@code DONE_STATUS | deadline DESCRIPTION /by DUE_DATE}
     *
     * @return The file-format string used for saving to disk.
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | deadline " + description + " /by " + by;
    }
}
