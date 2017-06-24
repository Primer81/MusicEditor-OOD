package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.Note;

/**
 * View that provides an audible view synced up with a visual view. The red line of the visual
 * portion moves along with the beat of the audible portion.
 */
public class CompositeView implements IMusicEditorView {

  private GuiViewFrame gui;
  private MidiViewImpl midi;
  private List<Note> music;
  private int tempo;
  private Integer curBeat;
  private Boolean paused;

  /**
   * Constructs a CompositeView.
   */
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
    this.gui.setMusic(music);
    this.midi.setMusic(music);
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
    this.gui.setTempo(tempo);
    this.midi.setTempo(tempo);
  }

  @Override
  public void setCurBeat(int curBeat) {
    this.curBeat = curBeat;
    this.gui.setCurBeat(curBeat);
    this.midi.setCurBeat(curBeat);
  }

  @Override
  public void setTickPosition(int tickPosition) {
    this.gui.setTickPosition(tickPosition);
    this.midi.setTickPosition(tickPosition);
  }

  @Override
  public void setPaused(boolean paused) {
    this.paused = paused;
    this.gui.setPaused(paused);
    this.midi.setPaused(paused);
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
  public void addMetaEventListener(MetaEventListener listener) {
    this.gui.addMetaEventListener(listener);
    this.midi.addMetaEventListener(listener);
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
}
