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
    this.paused = false;
  }

  /**
   * Creates a new sequence and converts the notes in the list of notes into a sequence and
   * stores them in the sequence field. Sets the sequencer's sequence to the new sequence.
   */
  void reSequence() {
    try {
      this.sequence = new Sequence(Sequence.PPQ, 1);
      Track track = sequence.createTrack();
      // Add midi messages to the track in the sequence to represent notes.
      for (Note n: this.music) {
        int instrument = n.getInstrument() - 1;
        int midiValue = ((n.getOctave() + 1) * 12) + n.getPitch().ordinal();
        int volume = n.getVolume();
        MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, instrument, midiValue, volume);
        MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, instrument, midiValue, volume);
        track.add(new MidiEvent(start, n.getStart()));
        track.add(new MidiEvent(stop, (n.getStart() + n.getDuration())));
      }
      // Add meta messages to the sequencer so that the controller is notified every beat.
      for (int i = 0; i < this.getSongLength(); i++) {
        byte[] data = "beat".getBytes();
        MidiMessage meta = new MetaMessage(0, data, data.length);
        track.add(new MidiEvent(meta, i));
      }
      // Add meta messages to the sequencer to notify controller when song ends.
      byte[] data = "end".getBytes();
      MidiMessage meta = new MetaMessage(0, data, data.length);
      track.add(new MidiEvent(meta, this.getSongLength()));
      // set the sequence
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
  public void setCurBeat(int curBeat) {
    this.curBeat = curBeat;
  }

  @Override
  public void setTickPosition(int tickPosition) {
    this.sequencer.setTickPosition(tickPosition);
    this.sequencer.setTempoInMPQ(this.tempo);
  }

  @Override
  public void setPaused(boolean paused) {
    this.paused = paused;
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
  public void addMetaEventListener(MetaEventListener listener) {
    this.sequencer.addMetaEventListener(listener);
  }

  @Override
  public void initialize() {
    this.reSequence();
    try {
      if (!this.paused) {
        this.play(); // wont play unless unpaused
        this.sequencer.setTempoInMPQ(this.tempo);
      }
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean isPaused() {
    return this.paused;
  }

  @Override
  public void play() throws MidiUnavailableException {
    this.sequencer.start();
    this.sequencer.setTempoInMPQ(this.tempo);
    this.paused = false;
  }

  @Override
  public void pause() {
    this.sequencer.stop();
    this.paused = true;
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
