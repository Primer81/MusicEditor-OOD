package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.util.CompositionBuilder;

/**
 * Implementation of an IMusicEditorModel.
 */
public class MusicEditorModel implements IMusicEditorModel {

  private List<Note> music;
  private int tempo;

  /**
   * Default constructor for a MusicEditorModel.
   */
  public MusicEditorModel() {
    this.music = new ArrayList<>();
  }

  /**
   * Constructor for a MusicEditorModel that sets tempo for MIDI playback.
   *
   * @param notes The music of the model
   * @param tempo The tempo of the model
   */
  public MusicEditorModel(List<Note> notes, int tempo) {
    if (notes == null) {
      throw new IllegalArgumentException("Error: Music cannot be null.");
    }
    if (tempo < 0) {
      throw new IllegalArgumentException("Error: Tempo cannot be less than zero.");
    }
    this.music = notes;
    this.tempo = tempo;
  }

  @Override
  public void addNote(Note n) {
    if (n == null) {
      throw new IllegalArgumentException("Error: Cannot add null note.");
    }
    if (music.contains(n)) {
      throw new IllegalArgumentException("Error: This note already exists.");
    }
    music.add(n);
  }

  @Override
  public void removeNote(Note n) {
    if (n == null) {
      throw new IllegalArgumentException("Error: Cannot remove null note.");
    }
    if (music.contains(n)) {
      music.remove(n);
    }
    else {
      throw new IllegalArgumentException("Error: Note does not exist.");
    }
  }

  @Override
  public String getState() {
    if (music.isEmpty()) {
      return "";
    }
    int length = getSongLength();
    StringBuilder sb = new StringBuilder(String.format("%" + Integer.toString(length).length()
            + "s", ""));
    ArrayList<String> range = this.getRange();
    for (int i = 0; i < range.size(); i++) {
      sb.append(String.format("%5s", String.format("%-2s", range.get(i))));
    }
    for (int i = 0; i <= length; i++) {
      sb.append("\n" + buildRow(i, range));
    }
    return sb.toString();
  }

  /**
   * Helper method that fills in the rows with notes.
   *
   * @param current The current beat
   * @param noteRange The range of the notes
   * @return The row of the current beat as a String
   */
  private String buildRow(int current, ArrayList<String> noteRange) {
    boolean isNoteHead;
    boolean isNoteBody;
    String currBeat;
    String row = String.format("%" + (Integer.toString(getSongLength()).length() + 1) + "s",
            current);
    for (int i = 0; i < noteRange.size(); i++) {
      currBeat = "     ";
      for (int j = 0; j < music.size(); j++) {
        Note n = music.get(j);
        int start = n.getStart();
        String noteString = n.toString();
        String range = noteRange.get(i);
        isNoteHead = noteString.equals(range) && start == current;
        isNoteBody = noteString.equals(range) && start < current && n.getDuration() + n.getStart()
                - 1 >= current;
        if (isNoteHead) {
          currBeat = "  X  ";
          break;
        } else if (isNoteBody) {
          currBeat = "  |  ";
          break;
        }
      }
      row = row + currBeat;
    }
    return row;
  }

  @Override
  public List<Note> getMusic() {
    return this.music;
  }

  @Override
  public ArrayList<String> getRange() {
    boolean validRange;
    boolean greaterThanFirst;
    boolean lessThanLast;

    Note firstNote = firstOrLast(true);
    int firstOctave = firstNote.getOctave();
    Note lastNote = firstOrLast(false);
    int lastOctave = lastNote.getOctave();
    ArrayList<String> range = new ArrayList<>();

    for (int i = firstOctave; i <= lastOctave; i++) {
      for (Pitch p : Pitch.values()) {
        boolean notLastOctave = i != lastOctave;
        greaterThanFirst = p.compareTo(firstNote.getPitch()) >= 0;
        lessThanLast = p.compareTo(lastNote.getPitch()) <= 0;
        if (i == firstOctave && i == lastOctave) {
          validRange = greaterThanFirst && lessThanLast;
        } else if (i == firstOctave) {
          validRange = greaterThanFirst;
        } else {
          validRange = lessThanLast || notLastOctave;
        }
        if (validRange) {
          range.add(p.getPitch() + i);
        }
      }
    }
    return range;
  }

  /**
   * Returns the first or last note in the music.
   *
   * @param first Whether the note to be returned is the first or not
   * @return The first or last note
   */
  private Note firstOrLast(boolean first) {
    if (music.size() == 0) {
      throw new IllegalStateException("Error: Music has no notes.");
    }
    Note n = music.get(0);
    for (int i = 0; i < music.size(); i++) {
      int current = music.get(i).compare(n);
      if (first) {
        if (current < 0) {
          n = music.get(i);
        }
      } else {
        if (current > 0) {
          n = music.get(i);
        }
      }
    }
    return n;
  }

  @Override
  public int getSongLength() {
    if (music.isEmpty()) {
      return 0;
    }
    int length = 0;
    List<Note> music = this.music;
    for (Note n : music) {
      if (n.getStart() + n.getDuration() >= length) {
        length = n.getStart() + n.getDuration();
      }
    }
    return length;
  }

  /**
   * Builder class that constructs a music composition.
   */
  public static final class Builder implements CompositionBuilder<MusicEditorModel> {
    private int tempo;
    private List<Note> notes = new ArrayList<>();
    private IMusicEditorModel model;

    public Builder() {
      this.model = new MusicEditorModel();
    }

    /**
     * Builds the MusicEditorModel.
     */
    @Override
    public MusicEditorModel build() {
      return new MusicEditorModel(this.notes, this.tempo);
    }

    @Override
    public CompositionBuilder<MusicEditorModel> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    @Override
    public CompositionBuilder<MusicEditorModel> addNote(int start, int end, int instrument,
                                                            int pitch, int volume) {

      int octave = (pitch / 12) - 1;
      Pitch currentPitch = Pitch.values()[pitch - (octave + 1) * 12];

      Note note = new Note(end - start, octave, start, currentPitch, instrument, volume);
      notes.add(note);
      return this;
    }
  }
}
