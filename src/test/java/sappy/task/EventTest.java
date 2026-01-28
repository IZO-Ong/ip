package sappy.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import sappy.ChatBot;
import sappy.storage.StorageStub;

public class EventTest {
    private ChatBot createBot() {
        return new ChatBot("Sappy", new StorageStub());
    }

    @Test
    public void getResponse_invalidEventDate_returnsFormatError() {
        ChatBot bot = createBot();
        // User provides "today" instead of "yyyy-mm-dd"
        String response = bot.getResponse("event orientation /from today /to 2026-01-30");
        assertEquals("Sappy got an error! Please provide dates in yyyy-mm-dd format.", response);
    }

    @Test
    public void getResponse_validEventDates_success() {
        ChatBot bot = createBot();
        String response = bot.getResponse("event career fair /from 2026-02-10 /to 2026-02-12");
        assertTrue(response.contains("Feb 10 2026"));
        assertTrue(response.contains("Feb 12 2026"));
    }
}
