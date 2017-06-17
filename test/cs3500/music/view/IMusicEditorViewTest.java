package cs3500.music.view;

import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

import static org.junit.Assert.assertEquals;

/**
 * Testing class that tests the interface's factory class.
 */
public class IMusicEditorViewTest {

  IMusicEditorModel model;
  IMusicEditorView.ReturnView returnView;

  /**
   * Initializes the testing data.
   */
  @Before
  public void init() {
    model = new MusicEditorModel();
    Note a3 = new Note(3, 3, 0, Pitch.A, 1, 1);
    Note b4 = new Note(2, 4, 3, Pitch.B, 1, 1);
    Note c3 = new Note(3, 3, 5, Pitch.C, 1, 1);
    model.addNote(a3);
    model.addNote(b4);
    model.addNote(c3);
    returnView = new IMusicEditorView.ReturnView(model);
  }

  @Test
  public void testInitGui() {
    try {
      assertEquals(returnView.init("gui") instanceof GuiViewFrame, true);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testInitText() {
    try {
      assertEquals(returnView.init("textual") instanceof TextualView, true);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void testInitMidi() {
    try {
      assertEquals(returnView.init("midi") instanceof MidiViewImpl, true);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }
}