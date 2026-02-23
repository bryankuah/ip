/**
 * Represents a Todo task — a basic task with a description and no date/time attached.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the given description.
     *
     * @param description A non-empty string describing the todo.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of this Todo, prefixed with the type indicator {@code [T]}.
     *
     * @return A string in the format {@code [T][STATUS] description}.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the file-format string for this Todo task.
     * Format: {@code DONE_STATUS | todo DESCRIPTION}
     *
     * @return The file-format string used for saving to disk.
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | todo " + description;
    }
}