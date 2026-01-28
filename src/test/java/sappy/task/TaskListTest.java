package sappy.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import sappy.SappyException;

public class TaskListTest {
    @Test
    public void getSize_newTaskList_zero() {
        assertEquals(0, new TaskList().getSize());
    }

    @Test
    public void remove_invalidIndex_exceptionThrown() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("test"));

        assertThrows(SappyException.class, () -> tasks.remove(5));
    }
}
