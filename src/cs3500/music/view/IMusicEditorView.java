package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.IMusicEditorModel;

/**
 * Interface for Music Editor views. Allows the user to display the music using their chosen view.
 * Utilizes a factory class to facilitate parsing view type from String input.
 */
public interface IMusicEditorView {

  /**
   * Displays the music, either visually or audibly.
   */
  void display() throws InvalidMidiDataException;

  /**
   * Factory class for parsing view type from a String input.
   */
  class ReturnView {
    private IMusicEditorModel model;

    public ReturnView(IMusicEditorModel model) {
      this.model = model;
    }

    public IMusicEditorView init(String view) throws MidiUnavailableException {
      if (view.equals("gui")) {
        return new GuiViewFrame(model);
      }
      else if (view.equals("textual")) {
        return new TextualView(model);
      }
      else if (view.equals("midi")) {
        return new MidiViewImpl(model);
      }
      else {
        throw new IllegalArgumentException("Invalid view type.");
      }
    }
  }
}
