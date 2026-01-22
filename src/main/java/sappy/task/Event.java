package sappy.task;

import sappy.SappyException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that occurs within a specific time range.
 */
public class Event extends Task {
    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Initializes an Event task with a description and a start/end date range.
     *
     * @param description The description of the event.
     * @param startStr The start date in yyyy-mm-dd format.
     * @param endStr The end date in yyyy-mm-dd format.
     * @throws SappyException If any date format is invalid.
     */
    public Event(String description, String startStr, String endStr) throws SappyException {
        super(description);
        try {
            this.startDate = LocalDate.parse(startStr.trim());
            this.endDate = LocalDate.parse(endStr.trim());
        } catch (DateTimeParseException e) {
            throw new SappyException("Please provide dates in yyyy-mm-dd format.");
        }
    }

    /**
     * Returns a string representation of the event with formatted start and end dates.
     *
     * @return A string containing event details and the date range.
     */
    @Override
    public String toString() {
        String startFormat = startDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        String endFormat = endDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        return super.toString() + " (from: " + startFormat + " to: " + endFormat + ")";
    }

    @Override
    public String getTypeIcon() {
        return "[E]";
    }

    /**
     * Formats the event data for storage, including the start and end dates.
     *
     * @return A string formatted for file storage.
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + startDate + " | " + endDate;
    }
}