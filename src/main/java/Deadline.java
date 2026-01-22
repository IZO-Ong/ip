import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private final LocalDate endDate;

    public Deadline(String description, String endDateString) throws SappyException {
        super(description);
        try {
            this.endDate = LocalDate.parse(endDateString.trim());
        } catch (DateTimeParseException e) {
            throw new SappyException("Please provide the date in yyyy-mm-dd format.");
        }
    }

    @Override
    public String toString() {
        String formattedDate = endDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        return super.toString() + " (by: " + formattedDate + ")";
    }

    @Override
    public String getTypeIcon() {
        return "[D]";
    }

    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + endDate;
    }
}