package cs3500.music.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testing class for MusicEditorModel.
 */
public class MusicEditorModelTest {

  Note a3 = new Note(3, 3, 0, Pitch.A, 1, 1);
  Note b4 = new Note(2, 4, 3, Pitch.B, 1, 1);
  Note c3 = new Note(3, 3, 5, Pitch.C, 1, 1);
  IMusicEditorModel model;

  /**
   * Initializes test data.
   */
  @Before
  public void init() {
    model = new MusicEditorModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteNullException() {
    model.addNote(null);
  }

  @Test
  public void testValidAddNote() {
    model.addNote(a3);
    model.addNote(b4);
    model.addNote(c3);
    assertEquals(model.getMusic().size(), 3);
    assertEquals(model.getMusic().get(0), a3);
    assertEquals(model.getMusic().get(1), b4);
    assertEquals(model.getMusic().get(2), c3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteExistsAlreadyException() {
    model.addNote(a3);
    model.addNote(a3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNoteNullException() {
    model.removeNote(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNoteDoesNotExistException() {
    model.addNote(a3);
    model.addNote(b4);
    model.addNote(c3);
    model.removeNote(new Note(0, 0, 0, Pitch.D,1 , 1));
  }

  @Test
  public void testValidRemoveNote() {
    model.addNote(a3);
    model.addNote(b4);
    model.addNote(c3);
    model.removeNote(a3);
    assertEquals(model.getMusic().size(), 2);
    assertEquals(model.getMusic().contains(a3), false);
  }
}