package sappy.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import sappy.logic.SappyException;

public class ParserTest {
    @Test
    public void parseId_validInput_success() throws SappyException {
        assertEquals(5, Parser.parseId("mark 5", 5));
    }

    @Test
    public void parseId_invalidInput_exceptionThrown() {
        SappyException thrown = assertThrows(SappyException.class, () -> {
            Parser.parseId("mark abc", 5);
        });
        assertEquals("Sappy got an error! Please provide a valid task number.", thrown.getMessage());
    }
}
