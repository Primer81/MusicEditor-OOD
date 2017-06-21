package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.model.Note;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends JFrame implements IMusicEditorView {
  private ConcreteGuiViewPanel displayPanel;
  private PianoView pianoViewPanel;
  private JScrollPane scrollPane;
  private List<Note> music;
  private int tempo;
  private int curBeat;
  private boolean paused;

  /**
   * Constructs a new GuiViewFrame.
   */
  public GuiViewFrame() {
    this.displayPanel = new ConcreteGuiViewPanel();
    this.pianoViewPanel = new PianoView(this.displayPanel);
    this.scrollPane = new JScrollPane(displayPanel, ScrollPaneConstants
        .VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.music = new ArrayList<>();
    this.tempo = 1000;
    this.paused = true;
  }

  @Override
  public void setMusic(List<Note> music) {
    this.music = music;
    this.displayPanel.setMusic(this.music);
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    this.scrollPane.addKeyListener(listener);
    this.displayPanel.addKeyListener(listener);
    this.pianoViewPanel.addKeyListener(listener);
  }

  @Override
  public void setCurBeat(Integer curBeat) {
    this.curBeat = curBeat;
  }

  @Override
  public void initialize() {
    this.setResizable(true);
    this.setLayout(new GridLayout(2, 1));
    this.add(this.scrollPane);
    this.add(this.pianoViewPanel);
    this.setTitle("Music Editor");
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.pack();
    this.setFocusable(true);
    this.requestFocus();
    scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
    scrollPane.getVerticalScrollBar().setUnitIncrement(20);
    this.setVisible(true);
  }

  @Override
  public boolean isPaused() {
    return this.paused;
  }

  @Override
  public void play() throws MidiUnavailableException {
    this.paused = false;
  }

  @Override
  public void pause() {
    this.paused = true;
  }

  @Override
  public void prevBeat() {
    this.curBeat -= 1;
    this.displayPanel.prevBeat();
    this.displayPanel.repaint();
  }

  @Override
  public void nextBeat() {
    this.curBeat += 1;
    this.displayPanel.nextBeat();
    this.displayPanel.repaint();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1200, 1200);
  }

  @Override
  public void display() {
    initialize();
    this.displayPanel.repaint();
  }
}
