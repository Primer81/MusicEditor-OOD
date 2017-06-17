package cs3500.music.view;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;

/**
 * Provides an audible representation of the music, using Java's built-in MIDI support.
 */
public class MidiViewImpl implements IMusicEditorView {
  private final Synthesizer synth;
  private final Receiver receiver;
  private IMusicEditorModel model;

  /**
   * Constructs a MidiViewImpl.
   *
   * @param model The model to be displayed
   * @throws MidiUnavailableException If the MIDI data is not available
   */
  public MidiViewImpl(IMusicEditorModel model) throws MidiUnavailableException {
    if (model == null) {
      throw new IllegalArgumentException("Error: Model cannot be null.");
    }
    this.model = model;
    Synthesizer s;
    Receiver r;
    try {
      s = MidiSystem.getSynthesizer();
      r = s.getReceiver();
      s.open();
    } catch (MidiUnavailableException e) {
      s = null;
      r = null;
      e.printStackTrace();
    }
    this.synth = s;
    this.receiver = r;
  }

  /**
   * Alternate constructor for testing.
   *
   * @param model The model to be displayed.
   * @param synth The synthesizer.
   */
  public MidiViewImpl(IMusicEditorModel model, Synthesizer synth) throws MidiUnavailableException {
    if (model == null) {
      throw new IllegalArgumentException("Error: Model cannot be null.");
    }
    if (synth == null) {
      throw new IllegalArgumentException("Error: Synthesizer cannot be null.");
    }
    this.model = model;
    Synthesizer s;
    Receiver r;
    try {
      s = synth;
      r = s.getReceiver();
    } catch (MidiUnavailableException e) {
      s = null;
      r = null;
      e.printStackTrace();
    }
    this.synth = s;
    this.receiver = r;
  }

  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */

  public void playNote(Note n)  throws InvalidMidiDataException {
    int instrument = n.getInstrument() - 1;
    int midiValue = ((n.getOctave() + 1) * 12) + n.getPitch().ordinal();
    int volume = n.getVolume();
    int tempo = model.getTempo();
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, instrument, midiValue, volume);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, instrument, midiValue, volume);
    this.receiver.send(start, n.getStart() * tempo);
    this.receiver.send(stop, (n.getStart() + n.getDuration()) * tempo);
  }

  @Override
  public void display() {
    for (int i = 0; i < model.getSongLength(); i++) {
      List<Note> notes = model.getNotesStartingAtBeat(i);
      for (int j = 0; j < notes.size(); j++) {
        try {
          playNote(notes.get(j));
        } catch (InvalidMidiDataException e) {
          e.printStackTrace();
        }
      }
    }
    try {
      Thread.sleep(this.model.getSongLength() * this.model.getTempo() / 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize() {
    // do nothing.
  }
}
