package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.IMusicEditorModel;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
   * This code should execute when the controller for this view receives a keyEvent.
   * The given string is used to identify that keyEvent and respond to it.
   * Does nothing if no events should occur.
   */
  void keyTyped(String cmd);

  /**
   * This code should execute when the controller for this view receives a keyEvent.
   * The given string is used to identify that keyEvent and respond to it.
   * Does nothing if no events should occur.
   * @param point
   */
  void mouseClicked(Point point);

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
