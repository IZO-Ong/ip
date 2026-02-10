package sappy.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import sappy.command.Command;
import sappy.storage.StorageStub;
import sappy.task.Deadline;
import sappy.task.ToDo;

public class ChatBotTest {

    private ChatBot createBot() {
        return new ChatBot("Sappy", new StorageStub());
    }

    @Test
    public void getResponse_byeCommand_returnsLonelyMessage() {
        ChatBot bot = createBot();
        String response = bot.getResponse("bye");
        assertEquals("Tweet tweet! Sappy will be waiting until you fly back!", response);
    }

    @Test
    public void getResponse_emptyTodo_returnsErrorMessage() {
        ChatBot bot = createBot();
        String response = bot.getResponse("todo ");
        assertEquals("Coo coo! Sappy hit a window: The description cannot be empty.", response);
    }

    @Test
    public void getResponse_unknownCommand_returnsErrorMessage() {
        ChatBot bot = createBot();
        String response = bot.getResponse("gibberish");
        assertEquals("Coo? Sappy doesn't understand that bird call.", response);
    }

    @Test
    public void getResponse_findCommand_returnsMatchingTasks() throws SappyException {
        ChatBot bot = createBot();
        bot.getResponse("todo read book");
        bot.getResponse("todo buy bread");

        String response = bot.getResponse("find book");
        assertTrue(response.contains("read book"));
        assertFalse(response.contains("buy bread"));
        assertTrue(response.contains("Peep! I've scouted the matching tasks:"));
    }

    @Test
    public void getResponse_findNoMatch_returnsNoMatchMessage() {
        ChatBot bot = createBot();
        String response = bot.getResponse("find non-existent");
        assertEquals("Sappy looked everywhere, but couldn't find any twigs matching 'non-existent'!", response);
    }

    @Test
    public void markTask_validIndex_returnsSuccessMessage() {
        ChatBot bot = createBot();
        bot.getResponse("todo read book");

        String response = bot.getResponse("mark 1");
        assertTrue(response.contains("Chirp chirp!"));
        assertTrue(response.contains("[X]"));
        assertTrue(response.contains("read book"));
    }

    @Test
    public void unmarkTask_validIndex_returnsSuccessMessage() {
        ChatBot bot = createBot();
        bot.getResponse("todo read book");
        bot.getResponse("mark 1");

        String response = bot.getResponse("unmark 1");
        assertTrue(response.contains("Coo..."));
        assertTrue(response.contains("[ ]"));
        assertFalse(response.contains("[X]"));
    }

    @Test
    public void markTask_invalidIndex_returnsErrorMessage() {
        ChatBot bot = createBot();
        String response = bot.getResponse("mark 1");
        assertEquals("Coo coo! Sappy hit a window: Sappy can't find that task!", response);
    }

    @Test
    public void listTasks_emptyList_returnsEmptyMessage() {
        ChatBot bot = createBot();
        String response = bot.getResponse("list");
        assertEquals("Chirp! Your nest is empty! Time to gather some tasks?", response);
    }

    @Test
    public void listTasks_twoItems_returnsFormattedList() {
        ChatBot bot = createBot();
        bot.getResponse("todo read book");
        bot.getResponse("todo buy bread");

        String response = bot.getResponse("list");

        String expected = "Peep! Here are the seeds in your garden:\n"
                + "1. [T][ ] read book\n"
                + "2. [T][ ] buy bread";

        assertEquals(expected, response);
    }

    @Test
    public void testTaskEquality() throws SappyException {
        // Test ToDo Equality
        ToDo todo1 = new ToDo("read book");
        ToDo todo2 = new ToDo("read book");
        ToDo todo3 = new ToDo("buy bread");

        assertEquals(todo1, todo2);
        assertNotEquals(todo1, todo3);
        assertEquals(todo1.hashCode(), todo2.hashCode());

        Deadline d1 = new Deadline("return book", "2026-02-04");
        Deadline d2 = new Deadline("return book", "2026-02-04");
        Deadline d3 = new Deadline("return book", "2026-02-05");

        assertEquals(d1, d2);
        assertNotEquals(d1, d3);
    }

    @Test
    public void addDuplicateTask_throwsException() {
        ChatBot bot = createBot();
        bot.getResponse("todo read book");

        String response = bot.getResponse("todo read book");
        assertEquals("Coo coo! Sappy hit a window: Sappy already has this twig in the nest!", response);
    }

    @Test
    public void getLastCommand_updatesCorrectly() {
        ChatBot bot = createBot();

        bot.getResponse("todo sleep");
        assertEquals(Command.TODO, bot.getLastCommand());

        bot.getResponse("list");
        assertEquals(Command.LIST, bot.getLastCommand());

        bot.getResponse("invalid command");
        assertEquals(Command.UNKNOWN, bot.getLastCommand());
    }

    /**
     * AI-Assisted by Gemini.
     * All logic and assertions have been manually verified
     * and refined to meet project requirements.
     */
    @Test
    public void findTasks_partialKeyword_returnsMatches() {
        ChatBot bot = createBot();
        bot.getResponse("todo reading");
        String response = bot.getResponse("find read");
        assertTrue(response.contains("reading"));
    }

    /**
     * AI-Assisted by Gemini.
     * All logic and assertions have been manually verified
     * and refined to meet project requirements.
     */
    @Test
    public void findTasks_multipleMatches_returnsAll() {
        ChatBot bot = createBot();
        bot.getResponse("todo book A");
        bot.getResponse("todo book B");
        bot.getResponse("todo car");

        String response = bot.getResponse("find book");
        assertTrue(response.contains("book A"));
        assertTrue(response.contains("book B"));
        assertFalse(response.contains("car"));
    }

    /**
     * AI-Assisted by Gemini.
     * All logic and assertions have been manually verified
     * and refined to meet project requirements.
     */
    @Test
    public void getResponse_extraSpacesInInput_processesCorrectly() {
        ChatBot bot = createBot();
        String response = bot.getResponse("todo     spaced out task");
        assertTrue(response.contains("spaced out task"));
    }

    /**
     * AI-Assisted by Gemini.
     * All logic and assertions have been manually verified
     * and refined to meet project requirements.
     */
    @Test
    public void getResponse_indexZero_returnsErrorMessage() {
        ChatBot bot = createBot();
        bot.getResponse("todo valid task");
        String response = bot.getResponse("mark 0");
        assertEquals("Coo coo! Sappy hit a window: Sappy can't find that task!", response);
    }

    /**
     * AI-Assisted by Gemini.
     * All logic and assertions have been manually verified
     * and refined to meet project requirements.
     */
    @Test
    public void getResponse_indexTooHigh_returnsErrorMessage() {
        ChatBot bot = createBot();
        bot.getResponse("todo valid task");
        String response = bot.getResponse("remove 2");
        assertEquals("Coo coo! Sappy hit a window: Sappy can't find that task!", response);
    }
}
