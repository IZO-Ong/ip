package sappy.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import sappy.SappyException;

/**
 * Represents a task that must be completed by a specific date.
 */
public class Deadline extends Task {
    private final LocalDate endDate;

    /**
     * Initializes a Deadline task with a description and a due date.
     *
     * @param description The description of the task.
     * @param endDateString The due date in yyyy-mm-dd format.
     * @throws SappyException If the date format is invalid.
     */
    public Deadline(String description, String endDateString) throws SappyException {
        super(description);
        try {
            this.endDate = LocalDate.parse(endDateString.trim());
        } catch (DateTimeParseException e) {
            throw new SappyException("Please provide the date in yyyy-mm-dd format.");
        }
    }

    /**
     * Returns a string representation of the deadline, including the formatted due date.
     *
     * @return A string containing task details and the deadline date.
     */
    @Override
    public String toString() {
        String formattedDate = endDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        return super.toString() + " (by: " + formattedDate + ")";
    }

    @Override
    public String getTypeIcon() {
        return "[D]";
    }

    /**
     * Formats the deadline data for local storage, preserving the ISO date format.
     *
     * @return A string formatted for file storage.
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + endDate;
    }
}
