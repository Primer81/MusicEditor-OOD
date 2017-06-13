package cs3500.music.model;

/**
 * Represents the twelve pitches in music.
 */
public enum Pitch {

  C("C"), C_Sharp("C#"), D("D"), D_Sharp("D#"), E("E"), F("F"), F_Sharp("F#"), G("G"),
  G_Sharp("G#"), A("A"), A_Sharp("A#"), B("B");

  private String pitch;

  Pitch(String pitch) {
    this.pitch = pitch;
  }

  /**
   * Gets the pitch.
   *
   * @return The pitch as a string.
   */
  public String getPitch() {
    return this.pitch;
  }
}
