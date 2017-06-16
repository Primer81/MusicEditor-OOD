package cs3500.music.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Note class.
 */
public class NoteTest {
  // Sets Rule to expect no exception unless stated otherwise.
  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  // Tests that the beatNum cannot be set to a negative number.
  @Test
  public void testSetStart01() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Error: Start value cannot be negative.");

    Note note = new Note(1, 1, 0, Pitch.A, 1, 1);
    note.setStart(-1);
  }

  // Tests that the setStart method can set a beatNum to a non-negative value
  @Test
  public void testSetStart02() {
    Note note = new Note(1, 1, 0, Pitch.A, 1, 1);
    note.setStart(0);
    assertEquals(0, note.getStart());
    note.setStart(1);
    assertEquals(1, note.getStart());
    note.setStart(30);
    assertEquals(30, note.getStart());
  }

  // Tests that the equals method can determine if two notes are equal.
  @Test
  public void testEquals() {
    Note note1;
    Note note2;

    note1 = new Note(1, 1, 0, Pitch.A, 1, 1);
    note2 = new Note(1, 1, 0, Pitch.A, 1, 1);
    assertEquals(true, note1.equals(note2));

    note1 = new Note(1, 1, 0, Pitch.A, 1, 1);
    note2 = new Note(1, 1, 0, Pitch.B, 1, 1);
    assertEquals(false, note1.equals(note2));

    note1 = new Note(1, 1, 0, Pitch.A, 1, 1);
    note2 = new Note(1, 1, 1, Pitch.A, 1, 1);
    assertEquals(false, note1.equals(note2));

    note1 = new Note(1, 1, 0, Pitch.A, 1, 1);
    note2 = new Note(1, 0, 0, Pitch.A, 1, 1);
    assertEquals(false, note1.equals(note2));

    note1 = new Note(1, 1, 0, Pitch.A, 1, 1);
    note2 = new Note(2, 1, 0, Pitch.A, 1, 1);
    assertEquals(false, note1.equals(note2));

    note1 = new Note(1, 1, 0, Pitch.A, 1, 1);
    note2 = new Note(2, 1, 0, Pitch.A, 1, 2);
    assertEquals(false, note1.equals(note2));

    note1 = new Note(1, 1, 0, Pitch.A, 1, 1);
    note2 = new Note(2, 1, 0, Pitch.A, 2, 1);
    assertEquals(false, note1.equals(note2));
  }

  // Tests that equal notes have the same hashCodes.
  @Test
  public void testHashCode() {
    Note note1;
    Note note2;

    note1 = new Note(1, 1, 0, Pitch.A, 1, 1);
    note2 = new Note(1, 1, 0, Pitch.A, 1, 1);
    assertEquals(true, note1.equals(note2));
    assertEquals(true, note1.hashCode() == note2.hashCode());
  }

  @Test
  public void testToString() {
    Note note;
    note = new Note(1, 1, 0, Pitch.A, 1, 1);
    assertEquals("A1", note.toString());
    note = new Note(1, 2, 0, Pitch.C_Sharp, 1, 1);
    assertEquals("C#2", note.toString());
    note = new Note(1, 15, 0, Pitch.C_Sharp, 1, 1);
    assertEquals("C#15", note.toString());
  }
}