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

  // Tests that the constructor throws an exception if given any invalid arguments.
  @Test
  public void testConstructor01() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Octave must be between 1 and 20 inclusive.");

    Note note = new Note(Pitch.A, 0, 1, 0);
  }

  // Tests that the constructor throws an exception if given any invalid arguments.
  @Test
  public void testConstructor02() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Octave must be between 1 and 20 inclusive.");

    Note note = new Note(Pitch.A, 21, 1, 0);
  }

  // Tests that the constructor throws an exception if given any invalid arguments.
  @Test
  public void testConstructor03() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("beatLength cannot be less than 1.");

    Note note = new Note(Pitch.A, 1, 0, 0);
  }

  // Tests that the constructor throws an exception if given any invalid arguments.
  @Test
  public void testConstructor04() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("beatNum cannot be negative.");

    Note note = new Note(Pitch.A, 1, 1, -1);
  }

  // Tests that the beatNum cannot be set to a negative number.
  @Test
  public void testSetBeatNum01() {
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Given beatNum is negative.");

    Note note = new Note(Pitch.A, 1, 1, 0);
    note.setBeatNum(-1);
  }

  // Tests that the setBeatNum method can set a beatNum to a non-negative value
  @Test
  public void testSetBeatNum02() {
    Note note = new Note(Pitch.A, 1, 1, 0);
    note.setBeatNum(0);
    assertEquals(0, note.getBeatNum());
    note.setBeatNum(1);
    assertEquals(1, note.getBeatNum());
    note.setBeatNum(30);
    assertEquals(30, note.getBeatNum());
  }

  // Tests that nextNote method throws an exception if the next note would have an
  // octave of 21 or more
  @Test
  public void testNextNote01() {
    expectedEx.expect(IllegalStateException.class);
    expectedEx.expectMessage("This Note object only supports octaves up to 20.");

    Note note = new Note(Pitch.G_S, 20, 1, 0);
    note.nextNote();
  }

  // Tests that nextNote method can retrieve the next note.
  @Test
  public void testNextNote02() {
    Note note;
    note = new Note(Pitch.G_S, 1, 1, 0);
    assertEquals(new Note(Pitch.A, 2, 1, 0), note.nextNote());
    note = new Note(Pitch.A, 4, 1, 0);
    assertEquals(new Note(Pitch.A_S, 4, 1, 0), note.nextNote());
  }

  // Tests that the equals method can determine if two notes are equal.
  @Test
  public void testEquals() {
    Note note1;
    Note note2;

    note1 = new Note(Pitch.A, 1, 1, 0);
    note2 = new Note(Pitch.A, 1, 1, 0);
    assertEquals(true, note1.equals(note2));

    note1 = new Note(Pitch.A, 1, 1, 0);
    note2 = new Note(Pitch.B, 1, 1, 0);
    assertEquals(false, note1.equals(note2));

    note1 = new Note(Pitch.A, 1, 1, 0);
    note2 = new Note(Pitch.A, 2, 1, 0);
    assertEquals(false, note1.equals(note2));

    note1 = new Note(Pitch.A, 1, 1, 0);
    note2 = new Note(Pitch.A, 1, 2, 0);
    assertEquals(false, note1.equals(note2));

    note1 = new Note(Pitch.A, 1, 1, 0);
    note2 = new Note(Pitch.A, 1, 1, 1);
    assertEquals(false, note1.equals(note2));
  }

  // Tests that equal notes have the same hashCodes.
  @Test
  public void testHashCode() {
    Note note1;
    Note note2;

    note1 = new Note(Pitch.A, 1, 1, 0);
    note2 = new Note(Pitch.A, 1, 1, 0);
    assertEquals(true, note1.equals(note2));
    assertEquals(true, note1.hashCode() == note2.hashCode());
  }

  @Test
  public void testToString() {
    Note note;
    note = new Note(Pitch.A, 1, 1, 0);
    assertEquals("A1", note.toString());
    note = new Note(Pitch.C_S, 2, 1, 0);
    assertEquals("C#2", note.toString());
    note = new Note(Pitch.C_S, 15, 1, 0);
    assertEquals("C#15", note.toString());
  }
}