package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTest {
    public static final String EMPTY_NOTE_MESSAGE = "<Note is empty. Please use the edit command to modify the note>";
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void constuctor_empty_equalsEmptyNoteMessage() {
        Note note = new Note("");
        assertEquals(EMPTY_NOTE_MESSAGE, note.value);
    }

    @Test
    public void equals() {
        Note note = new Note("Test");

        // same values -> returns true
        assertTrue(note.equals(new Note("Test")));

        // same object -> returns true
        assertTrue(note.equals(note));

        // null -> returns false
        assertFalse(note.equals(null));

        // different types -> returns false
        assertFalse(note.equals(5.0f));

        // different values -> returns false
        assertFalse(note.equals(new Subject("Test2")));
    }
}
