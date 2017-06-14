package cs3500.music.model;

/**
 * Represents a musical note. A Note has a duration, an octave, a starting beat, a Pitch, an
 * instrument, and a volume.
 */
public class Note {

  private int duration;
  private int octave;
  private int start;
  private Pitch pitch;
  private int instrument;
  private int volume;

  /**
   * Constructs a Note.
   *
   * @param duration The duration of the note
   * @param octave The octave of the note
   * @param start The starting beat of the note
   * @param pitch The pitch of the note
   * @param instrument The instrument that plays this note
   * @param volume The volume of this note
   */
  public Note(int duration, int octave, int start, Pitch pitch, int instrument, int volume) {
    if (duration < 0) {
      throw new IllegalArgumentException("Error: Note duration cannot be less than zero.");
    }
    if (octave < 0) {
      throw new IllegalArgumentException("Error: Octave cannot be less than zero.");
    }
    if (start < 0) {
      throw new IllegalArgumentException("Error: Starting beat cannot be less than zero.");
    }
    if (pitch == null) {
      throw new IllegalArgumentException("Error: Pitch cannot be null.");
    }
    if (instrument < 0) {
      throw new IllegalArgumentException("Error: Instrument value cannot be less than zero.");
    }
    if (volume < 0) {
      throw new IllegalArgumentException("Error: Volume cannot be less than zero.");
    }
    this.duration = duration;
    this.octave = octave;
    this.start = start;
    this.pitch = pitch;
    this.instrument = instrument;
    this.volume = volume;
  }

  /**
   * Gets the duration.
   *
   * @return The duration.
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * Gets the octave.
   *
   * @return The octave
   */
  public int getOctave() {
    return this.octave;
  }

  /**
   * Gets the starting beat.
   *
   * @return The start
   */
  public int getStart() {
    return this.start;
  }

  /**
   * Gets the pitch.
   *
   * @return The pitch.
   */
  public Pitch getPitch() {
    return this.pitch;
  }

  /**
   * Gets the instrument.
   *
   * @return The instrument
   */
  public int getInstrument() {
    return this.instrument;
  }

  /**
   * Gets the volume.
   *
   * @return The volume
   */
  public int getVolume() {
    return this.volume;
  }

  /**
   * Sets the starting beat of this note. Throws an exception if the given value is negative.
   *
   * @param start the new start value
   */
  public void setStart(int start) {
    if (start < 0) {
      throw new IllegalArgumentException("Error: Start value cannot be negative.");
    }
    this.start = start;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Note)) {
      return false;
    }
    Note n = (Note)o;
    return this.duration == n.getDuration()
            && this.octave == n.getOctave()
            && this.start == n.getStart()
            && this.pitch == n.getPitch()
            && this.instrument == n.getInstrument()
            && this.volume == n.getVolume();
  }

  @Override
  public int hashCode() {

    return this.duration * 10 + this.octave * 10 + this.start * 10;
  }

  /**
   * Compares this Note and the given Note.
   *
   * @param n2 The other note
   * @return An integer to be interpreted by other methods
   */
  public int compare(Note n2) {
    if (octave == n2.getOctave()) {
      return pitch.compareTo(n2.getPitch());
    }
    return octave - n2.getOctave();
  }

  @Override
  public String toString() {
    return pitch.getPitch().concat(Integer.toString(octave));
  }
}
