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
    ERROR,
    UNKNOWN;
}
