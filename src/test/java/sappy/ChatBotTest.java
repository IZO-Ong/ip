package sappy;

import sappy.ui.Ui;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatBotTest {
    private final String testPath = "./data/test.txt";
    private final Ui ui = new Ui();

    @Test
    public void getResponse_byeCommand_returnsLonelyMessage() {
        ChatBot bot = new ChatBot("Sappy", testPath, ui);
        String response = bot.getResponse("bye");
        assertEquals("Bye! Sappy will be very lonely until you come back!", response);
    }

    @Test
    public void getResponse_emptyTodo_returnsErrorMessage() {
        ChatBot bot = new ChatBot("Sappy", testPath, ui);
        String response = bot.getResponse("todo ");
        assertEquals("Sappy got an error! The description cannot be empty.", response);
    }

    @Test
    public void getResponse_unknownCommand_returnsErrorMessage() {
        ChatBot bot = new ChatBot("Sappy", testPath, ui);
        String response = bot.getResponse("gibberish");
        assertEquals("I'm sorry, I don't know what that means.", response);
    }
}