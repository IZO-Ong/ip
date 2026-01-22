public class Deadline extends Task {
    private final String endDate;

    public Deadline(String description, String endDate) {
        super(description);
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + this.endDate + ")";
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
