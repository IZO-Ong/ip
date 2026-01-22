package sappy.command;

public enum Command {
    BYE(true),
    LIST(false),
    MARK(false),
    UNMARK(false),
    TODO(false),
    DEADLINE(false),
    EVENT(false),
    REMOVE(false),
    FIND(false),
    UNKNOWN(false);

    private final boolean isExit;

    Command(boolean isExit) {
        this.isExit = isExit;
    }
    
    public boolean isExit() {
        return this.isExit;
    }

    public static Command fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return UNKNOWN;
        }
        String action = input.split(" ")[0].toUpperCase();
        try {
            return Command.valueOf(action);
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}