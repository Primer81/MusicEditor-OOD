package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.sound.midi.MidiUnavailableException;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.controller.MouseKeyListener;
import cs3500.music.model.IMusicEditorModel;

/**
 * View that provides an audible view synced up with a visual view.
 */
public class CompositeView implements IMusicEditorView {

  private GuiViewFrame gui;
  private MidiViewImpl midi;
  private boolean playing;

  public CompositeView(IMusicEditorModel model) throws MidiUnavailableException {
    this.gui = new GuiViewFrame(model);
    this.midi = new MidiViewImpl(model);
    this.playing = true;
  }

  @Override
  public void display() {
    if (playing) {
      midi.display();
    }
    gui.display();
  }

  @Override
  public void initialize() {
    // do nothing.
  }

  @Override
  public void addKeyListener(KeyListener key) {
    // do nothing, for now.
  }

  @Override
  public void addMouseListener(MouseListener mouse) {
    // do nothing, for now.
  }

  @Override
  public void keyTyped(String cmd) {
    // do nothing, for now.
  }

  @Override
  public void mouseClicked(Point point) {
    // do nothing, for now.
  }
}
