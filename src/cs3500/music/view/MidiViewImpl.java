package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.*;

import cs3500.music.model.Note;

/**
 * Provides an audible representation of the music, using Java's built-in MIDI support.
 */
public class MidiViewImpl implements IMusicEditorView {
  private Sequence sequence;
  private Sequencer sequencer;
  private List<Note> music;
  private int tempo;
  private int curBeat;
  private boolean paused;

  /**
   * Constructs a MidiViewImpl.
   *
   * @throws MidiUnavailableException If the MIDI data is not available
   */
  public MidiViewImpl() throws MidiUnavailableException {
    try {
      this.sequence = new Sequence(Sequence.PPQ, 1);
      this.sequence.createTrack();
      this.sequencer = MidiSystem.getSequencer();
      this.sequencer.open();
      this.sequencer.setSequence(this.sequence);

    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    this.music = new ArrayList<>();
    this.tempo = 15000;
    this.curBeat = 0;
    this.paused = true;
  }

  /**
   * Creates a new sequence and converts the notes in the list of notes into a sequence and
   * stores them in the sequence field. Sets the sequencer's sequence to the new sequence.
   */
  void reSequence() {
    try {
      this.sequence = new Sequence(Sequence.PPQ, 1);
      Track track = sequence.createTrack();
      for (Note n: this.music) {
        int instrument = n.getInstrument() - 1;
        int midiValue = ((n.getOctave() + 1) * 12) + n.getPitch().ordinal();
        int volume = n.getVolume();
        MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, instrument, midiValue, volume);
        MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, instrument, midiValue, volume);
        track.add(new MidiEvent(start, n.getStart()));
        track.add(new MidiEvent(stop, (n.getStart() + n.getDuration())));
      }
      this.sequencer.setSequence(this.sequence);
    } catch (InvalidMidiDataException e) {
        e.printStackTrace();
    }
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
  public void addKeyListener(KeyListener listener) {
    // do nothing
  }

  @Override
  public void addMouseListener(MouseListener mouse) {
    // do nothing
  }

  @Override
  public void initialize() {
    this.reSequence();
  }

  @Override
  public boolean isPaused() {
    return this.paused;
  }

  @Override
  public void play() throws MidiUnavailableException {
    if (this.isPaused()) {
      this.sequencer.start();
      this.sequencer.setTempoInMPQ(this.tempo);
      this.paused = false;
    }
  }

  @Override
  public void pause() {
    if (!this.isPaused()) {
      this.sequencer.stop();
      this.paused = true;
    }
  }

  @Override
  public void prevBeat() {
    this.curBeat -= 1;
    this.sequencer.setTickPosition(this.sequencer.getTickPosition() - 1);
  }

  @Override
  public void nextBeat() {
    this.curBeat += 1;
    this.sequencer.setTickPosition(this.sequencer.getTickPosition() + 1);
  }

  /**
   * Gets how many beats long the music is.
   * @return number of beats
   */
  private int getSongLength() {
    if (this.music.isEmpty()) {
      return 0;
    }
    int length = 0;
    for (Note n : this.music) {
      if (n.getStart() + n.getDuration() >= length) {
        length = n.getStart() + n.getDuration();
      }
    }
    return length;
  }

  /**
   * Gets the list of notes starting at the specified beat.
   * @param beat the starting beat
   * @return the list of notes
   */
  private ArrayList<Note> getNotesAtBeat(int beat) {
    if (this.music.isEmpty()) {
      throw new IllegalStateException("Error: No beats exist.");
    }
    if (beat < 0 || beat > this.getSongLength() - 1) {
      throw new IllegalStateException("Error: Given beat does not exist.");
    }
    ArrayList<Note> notes = new ArrayList<>();
    for (Note n : music) {
      int start = n.getStart();
      if (start == beat) {
        notes.add(n);
      }
    }
    return notes;
  }

  @Override
  public void display() {
    // nothing to display
  }
}
