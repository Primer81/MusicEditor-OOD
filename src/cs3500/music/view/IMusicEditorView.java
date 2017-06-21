package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;

import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

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
   * Initializes some settings, such as visibility.
   */
  void initialize();

  /**
   * Adds the given keyboard listener to this view.
   * @param listener the keyboard listener to be added
   */
  void addKeyListener(KeyListener listener);

  /**
   * Adds the given mouse listener to this view.
   * @param listener the mouse listener to be added
   */
  void addMouseListener(MouseListener listener);

  /**
   * A method for the controller to pass the list of notes from the model to the view.
   * @param music the list of notes
   */
  void setMusic(List<Note> music);

  /**
   * A method for the controller to pass the tempo from the model to the view.
   * @param tempo the tempo
   */
  void setTempo(int tempo);

  /**
   * Sets the current beat of the view to the specified Integer Object.
   */
  void setCurBeat(Integer curBeat);

  /**
   * Returns whether the music editor is paused or not.
   * @return whether the player is paused
   */
  boolean isPaused();

  /**
   * Starts to play the music in the editor from the current beat.
   */
  void play() throws MidiUnavailableException;

  /**
   * Pauses the music in the editor if it isn't already.
   */
  void pause();

  /**
   * Regresses the view to the previous beat in the song if it can.
   */
  void prevBeat();

  /**
   * Progresses the view to the next beat in the song if it can.
   */
  void nextBeat();

  /**
   * Factory class for parsing view type from a String input.
   */
  class ReturnView {
    public ReturnView() {
    }

    public IMusicEditorView init(String view) throws MidiUnavailableException {
      if (view.equals("gui")) {
        return new GuiViewFrame();
      }
      else if (view.equals("textual")) {
        return new TextualView();
      }
      else if (view.equals("midi")) {
        return new MidiViewImpl();
      }
      else if (view.equals("composite")) {
        return new CompositeView();
      }
      else {
        throw new IllegalArgumentException("Invalid view type.");
      }
    }
  }
}
