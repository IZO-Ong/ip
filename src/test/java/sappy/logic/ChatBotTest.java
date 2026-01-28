package sappy.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import sappy.storage.StorageStub;

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
}
