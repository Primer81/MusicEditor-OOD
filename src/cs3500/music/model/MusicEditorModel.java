package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.util.CompositionBuilder;

/**
 * An implementation of the IMusicEditorModel interface. A sheet of music
 * in this class is represented by a List of Note objects. Each Note object is aware of
 * its timings, durations, octaves, pitches, instrument and volume. This class enforces that
 * notes of same octave, pitch, and instrument have no overlapping durations.
 * <p>
 * UPDATE: This music editor now implements the hasNext, hasPrev, nextBeat, prevBeat, playBeat,
 * setBeat, and getBeat methods. As a result, a new field called 'curBeat' has been added to
 * keep track of the current beat. This is a modification on hw05.
 * </p>
 */
public class MusicEditorModel implements IMusicEditorModel {

  private List<Note> music;
  private int tempo;
  private int curBeat;

  /**
   * Default constructor for a MusicEditorModel.
   */
  public MusicEditorModel() {
    this.music = new ArrayList<>();
    this.tempo = 1;
    this.curBeat = 0;
  }

  /**
   * Constructor for a MusicEditorModel that sets tempo for MIDI playback. Verifies that
   * the given list of notes has no overlapping notes.
   *
   * @param notes The music of the model
   * @param tempo The tempo of the model
   * @throws IllegalArgumentException if given null values, an invalid list of notes,
   *         or negative values
   */
  public MusicEditorModel(List<Note> notes, int tempo) {
    if (notes == null) {
      throw new IllegalArgumentException("Error: Music cannot be null.");
    }
    if (!this.validSheet(notes)) {
      throw new IllegalArgumentException("Error: Music cannot have overlapping notes.");
    }
    if (tempo < 0) {
      throw new IllegalArgumentException("Error: Tempo cannot be less than zero.");
    }
    this.music = notes;
    this.tempo = tempo;
    this.curBeat = 0;
  }

  /**
   * UPDATE: This method is a modification on hw05.
   * <p>
   * Takes in an ArrayList of Note objects and checks to see if any notes of same octave, pitch,
   * and instrument in that ArrayList have overlapping timings. Returns true if no overlaps;
   * false otherwise. No parameters are modified by this method. Used by the constructor,
   * the merge method, and the append method.
   * </p>
   *
   * @param music the sheet to be validated
   * @return whether the sheet is valid or not
   */
  private boolean validSheet(List<Note> music) {
    ArrayList<Note> clone = new ArrayList<>(music);
    for (Note n : music) {
      clone.remove(n);
      if (!this.noOverlap(n, clone)) {
        return false;
      }
    }
    return true;
  }

  /**
   * UPDATE: This method is a modification on hw05.
   * <p>
   * Returns true if the given note does not have an overlapping duration with any note of same
   * pitch and octave in the given arrayList of notes. No parameters are modified by this method.
   * Is used by the addNote method, and the validSheet protected method.
   * </p>
   *
   * @param n     the note being checked
   * @param music the music sheet being checked
   * @return true if the given note does not overlap with any notes in the given sheet of music;
   *         false otherwise
   */
  private boolean noOverlap(Note n, List<Note> music) {
    for (Note c : music) {
      if (n.getOctave() == c.getOctave()
          && n.getPitch().equals(c.getPitch())
          && n.getInstrument() == c.getInstrument()) {
        int nStart = n.getStart();
        int cStart = c.getStart();
        if (nStart == cStart
            || (nStart < cStart && nStart + n.getDuration() > cStart)
            || (cStart < nStart && cStart + c.getDuration() > nStart)) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public void addNote(Note n) {
    if (n == null) {
      throw new IllegalArgumentException("Error: Cannot add null note.");
    }
    if (this.music.contains(n)) {
      throw new IllegalArgumentException("Error: This note already exists.");
    }
    if (!this.noOverlap(n, this.music)) {
      throw new IllegalArgumentException("Error: This note overlaps with an existing note.");
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
    return new ArrayList<>(this.music);
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

  @Override
  public boolean hasNext() {
    if (!this.music.isEmpty()) {
      return this.curBeat < this.getSongLength() - 1;
    }
    return false;
  }

  @Override
  public boolean hasPrev() {
    return this.curBeat > 0;
  }

  @Override
  public boolean isEmpty() {
    return this.music.isEmpty();
  }

  @Override
  public void nextBeat() throws IllegalStateException {
    if (!this.hasNext()) {
      throw new IllegalStateException("No next beat.");
    }
    this.curBeat += 1;
  }

  @Override
  public void prevBeat() throws IllegalStateException {
    if (!this.hasPrev()) {
      throw new IllegalStateException("No previous beat.");
    }
    this.curBeat -= 1;
  }

  @Override
  public List<Note> playBeat() throws IllegalStateException {
    if (this.music.size() == 0) {
      throw new IllegalStateException("No notes have been added to the editor.");
    }
    ArrayList<Note> curBeats = new ArrayList<>();
    for (Note n: this.music) {
      if (n.getStart() == this.curBeat
          || (n.getStart() < this.curBeat
          && n.getStart() + n.getDuration() > this.curBeat)) {
        curBeats.add(n);
      }
    }
    return curBeats;
  }

  @Override
  public void setBeat(int curBeat) throws IllegalArgumentException, IllegalStateException {
    if (this.music.isEmpty()) {
      throw new IllegalStateException("No beats exist.");
    }
    if (curBeat < 0 || curBeat > this.getSongLength() - 1) {
      throw new IllegalArgumentException("Cannot set the current beat to the given value.");
    }
    this.curBeat = curBeat;
  }

  @Override
  public int getBeat() throws IllegalStateException {
    if (this.music.isEmpty()) {
      throw new IllegalStateException("No beats exist.");
    }
    return this.curBeat;
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
