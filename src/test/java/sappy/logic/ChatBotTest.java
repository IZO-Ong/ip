package sappy.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
        assertEquals("Bye! Sappy will be very lonely until you come back!", response);
    }

    @Test
    public void getResponse_emptyTodo_returnsErrorMessage() {
        ChatBot bot = createBot();
        String response = bot.getResponse("todo ");
        assertEquals("Sappy got an error! The description cannot be empty.", response);
    }

    @Test
    public void getResponse_unknownCommand_returnsErrorMessage() {
        ChatBot bot = createBot();
        String response = bot.getResponse("gibberish");
        assertEquals("I'm sorry, I don't know what that means.", response);
    }

    @Test
    public void getResponse_findCommand_returnsMatchingTasks() throws SappyException {
        ChatBot bot = createBot();
        bot.getResponse("todo read book");
        bot.getResponse("todo buy bread");

        String response = bot.getResponse("find book");
        assertTrue(response.contains("read book"));
        assertFalse(response.contains("buy bread"));
    }

    @Test
    public void getResponse_findNoMatch_returnsNoMatchMessage() {
        ChatBot bot = createBot();
        String response = bot.getResponse("find non-existent");
        assertEquals("No matching tasks found in your list.", response);
    }

    @Test
    public void markTask_validIndex_returnsSuccessMessage() {
        ChatBot bot = createBot();
        bot.getResponse("todo read book");

        String response = bot.getResponse("mark 1");
        assertTrue(response.contains("[X]"));
        assertTrue(response.contains("read book"));
    }

    @Test
    public void unmarkTask_validIndex_returnsSuccessMessage() {
        ChatBot bot = createBot();
        bot.getResponse("todo read book");
        bot.getResponse("mark 1");

        String response = bot.getResponse("unmark 1");
        assertTrue(response.contains("[ ]"));
        assertFalse(response.contains("[X]"));
    }

    @Test
    public void markTask_invalidIndex_returnsErrorMessage() {
        ChatBot bot = createBot();
        String response = bot.getResponse("mark 1");
        assertEquals("Sappy got an error! That task does not exist!", response);
    }

    @Test
    public void listTasks_emptyList_returnsEmptyMessage() {
        ChatBot bot = createBot();
        String response = bot.getResponse("list");
        assertEquals("Your task list is currently empty!", response);
    }

    @Test
    public void listTasks_twoItems_returnsFormattedList() {
        ChatBot bot = createBot();
        bot.getResponse("todo read book");
        bot.getResponse("todo buy bread");

        String response = bot.getResponse("list");

        String expected = "Here are the tasks in your list:\n"
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
        assertEquals("Sappy got an error! This task already exists in your list!", response);
    }
}
