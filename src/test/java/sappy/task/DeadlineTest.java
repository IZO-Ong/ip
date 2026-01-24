package sappy.task;

import org.junit.jupiter.api.Test;
import sappy.ChatBot;
import sappy.storage.StorageStub;
import sappy.ui.Ui;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlineTest {
    private final Ui ui = new Ui();

    private ChatBot createBot() {
        return new ChatBot("Sappy", new StorageStub(), ui);
    }

    @Test
    public void getResponse_invalidDeadlineDate_returnsFormatError() {
        ChatBot bot = createBot();
        // User provides "tomorrow" instead of "yyyy-mm-dd"
        String response = bot.getResponse("deadline return book /by tomorrow");
        assertEquals("Sappy got an error! Please provide the date in yyyy-mm-dd format.", response);
    }

    @Test
    public void getResponse_validDeadlineDate_success() {
        ChatBot bot = createBot();
        String response = bot.getResponse("deadline return book /by 2026-12-05");
        assertTrue(response.contains("Dec 05 2026"));
    }
}