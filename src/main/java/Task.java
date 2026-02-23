/**
 * Represents an abstract task in the NIG task manager.
 * All task types (Todo, Deadline, Event) extend this base class.
 */
public class Task {

    /** The description text of the task. */
    protected String description;

    /** Whether the task has been completed. */
    protected boolean isDone;

    /**
     * Constructs a Task with the given description, defaulting to not done.
     *
     * @param description A non-empty string describing the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns "X" if the task is done, or a blank space if not done.
     *
     * @return A single-character status icon.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns a formatted string representation of the task showing its status and description.
     *
     * @return A string in the format {@code [STATUS] description}.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }

    /**
     * Returns a string representation of the task suitable for saving to file.
     * The base format is {@code 1} if done or {@code 0} if not done.
     * Subclasses extend this with their specific fields.
     *
     * @return The file-format string for this task.
     */
    public String toFileFormat() {
        return isDone ? "1" : "0";
    }
}

