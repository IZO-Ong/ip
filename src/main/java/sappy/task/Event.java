package sappy.task;

import sappy.SappyException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Event(String description, String startStr, String endStr) throws SappyException {
        super(description);
        try {
            this.startDate = LocalDate.parse(startStr.trim());
            this.endDate = LocalDate.parse(endStr.trim());
        } catch (DateTimeParseException e) {
            throw new SappyException("Please provide dates in yyyy-mm-dd format.");
        }
    }

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

    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + startDate + " | " + endDate;
    }
}