package sappy.command;

/**
 * Represents the various commands supported by the Sappy chatbot.
 * Each command contains a flag indicating whether it should trigger
 * the application to exit.
 */
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

    /**
     * Constructs a Command with the specified exit status.
     *
     * @param isExit True if the command terminates the program, false otherwise.
     */
    Command(boolean isExit) {
        this.isExit = isExit;
    }

    /**
     * Returns whether this command is an exit command.
     *
     * @return True if the application should terminate, false otherwise.
     */
    public boolean isExit() {
        return this.isExit;
    }

    /**
     * Parses a raw string input to determine the corresponding Command.
     * If the input is null, empty or unrecognized, returns UNKNOWN.
     *
     * @param input The raw string input from the user.
     * @return The matching Command enum constant.
     */
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
