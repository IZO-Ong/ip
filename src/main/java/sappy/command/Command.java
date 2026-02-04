package sappy.command;

/**
 * Represents the various commands supported by the Sappy chatbot.
 * Each command contains a flag indicating whether it should trigger
 * the application to exit.
 */
public enum Command {
    BYE,
    LIST,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    REMOVE,
    FIND,
    UNKNOWN;

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
