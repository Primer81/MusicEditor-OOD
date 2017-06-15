package cs3500.music.view;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

import static org.junit.Assert.assertEquals;

/**
 * Testing class for ConcreteGuiViewPanel.
 */
public class ConcreteGuiViewPanelTest {

  Note a3 = new Note(3, 3, 0, Pitch.A, 1, 1);
  Note b4 = new Note(2, 4, 3, Pitch.B, 1, 1);
  Note c3 = new Note(3, 3, 5, Pitch.C, 1, 1);
  IMusicEditorModel model;
  ConcreteGuiViewPanel concrete;

  /**
   * Initializes test data.
   */
  @Before
  public void initData() {
    model = new MusicEditorModel();
    concrete = new ConcreteGuiViewPanel(model);
    model.addNote(a3);
    model.addNote(b4);
    model.addNote(c3);
  }

  @Test
  public void testGetRedLine() {
    assertEquals(0, concrete.getRedLineBeat());
  }

  @Test
  public void testGetRedLineLoc() {
    assertEquals(45, concrete.getRedLineLocation());
  }

  @Test
  public void testGetNotesAtRedLine() {
    List<Note> result = new ArrayList<>();
    result.add(a3);
    assertEquals(result, concrete.getNotesAtRedLine());
  }
}