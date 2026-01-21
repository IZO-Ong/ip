public enum Command {
    BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, REMOVE, UNKNOWN;

    public static Command fromString(String input) {
        String action = input.split(" ")[0].toUpperCase();
        try {
            return Command.valueOf(action);
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
