package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

import javax.sound.midi.MidiUnavailableException;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.controller.MouseKeyListener;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;

/**
 * View that provides an audible view synced up with a visual view.
 */
public class CompositeView implements IMusicEditorView {

  private GuiViewFrame gui;
  private MidiViewImpl midi;
  private List<Note> music;
  private int tempo;
  private Integer curBeat;
  private Boolean paused;

  public CompositeView() throws MidiUnavailableException {
    this.gui = new GuiViewFrame();
    this.midi = new MidiViewImpl();
    this.music = new ArrayList<>();
    this.tempo = 1000;
    this.curBeat = 0;
    this.paused = true;
  }

  @Override
  public void setMusic(List<Note> music) {
    this.music = music;
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public void setCurBeat(Integer curBeat) {
    this.curBeat = curBeat;
  }

  @Override
  public void setPaused(boolean paused) {
    this.paused = paused;
  }

  @Override
  public void addKeyListener(KeyListener key) {
    this.gui.addKeyListener(key);
    this.midi.addKeyListener(key);
  }

  @Override
  public void addMouseListener(MouseListener mouse) {
    this.gui.addMouseListener(mouse);
    this.midi.addMouseListener(mouse);
  }

  @Override
  public void initialize() {
    this.gui.setMusic(this.music);
    this.gui.setTempo(this.tempo);
    this.gui.setCurBeat(this.curBeat);
    this.gui.setPaused(this.paused);
    this.midi.setMusic(this.music);
    this.midi.setTempo(this.tempo);
    this.midi.setCurBeat(this.curBeat);
    this.midi.setPaused(this.paused);
    this.gui.initialize();
    this.midi.initialize();
  }

  @Override
  public boolean isPaused() {
    return this.gui.isPaused() && this.midi.isPaused();
  }

  @Override
  public void play() throws MidiUnavailableException {
    this.gui.play();
    this.midi.play();
  }

  @Override
  public void pause() {
    this.gui.pause();
    this.midi.pause();
  }

  @Override
  public void prevBeat() {
    this.curBeat -= 1;
    this.gui.prevBeat();
    this.midi.prevBeat();
  }

  @Override
  public void nextBeat() {
    this.curBeat += 1;
    this.gui.nextBeat();
    this.midi.nextBeat();
  }

  @Override
  public void display() {
  }
}
