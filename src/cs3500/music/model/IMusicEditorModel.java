package cs3500.music.model;

import java.util.List;

/**
 * Represents the interface for a music editor.
 * Allows the user to add a note, remove a note, and get a text rendering of the music.
 * <p>
 * UPDATE: This interface now requires the implementation of the hasNext, hasPrev, hasBeat,
 * nextBeat, prevBeat, playBeat, setBeat, and getBeat methods. This is a modification on hw05.
 * </p>
 * <p>
 * UPDATE: The methods of this interface enforce that no note of same octave or pitch may
 * have overlapping durations within the piece of music being worked on. This is a modification on
 * hw05.
 * </p>
 */
public interface IMusicEditorModel {

  /**
   * Adds the given note to the music sheet. Does not allow notes of same octave and pitch
   * to have overlapping durations or beatLengths.
   *
   * @param n the music note to be added
   * @throws IllegalArgumentException if a note with the same octave, pitch, and instrument
   *         already exists within the duration of the note being added
   */
  void addNote(Note n);

  /**
   * Removes a note equal to the given note from the music sheet. Throws an exception if the note
   * could not be found.
   * <p>
   *   UPDATE: This method now resets the current beat to zero to avoid the case where
   *   the current beat may be beyond the total number of beats after removing a note comprising
   *   the last beat. This method does this in all cases to avoid performance issues and to make
   *   the method more predictable. This is a modification on hw05.
   * </p>
   *
   * @param n the music note to be removed
   * @throws IllegalArgumentException if the note does not exist within the music sheet
   */
  void removeNote(Note n);

  /**
   * Gets the state of the music.
   *
   * @return The music's state as a String.
   */
  String getState();

  /**
   * Gets a copy of the list of all the notes in this music editor.
   *
   * @return The List representing the music.
   */
  List<Note> getMusic();

  /**
   * Gets the List of Strings signifying the musical range.
   *
   * @return The range of the music
   */
  List<String> getRange();

  /**
   * Returns the length of the song in beats.
   *
   * @return The length of the song in beats
   */
  int getSongLength();

  /**
   * Returns true if there is a next beat in this piece of music; false otherwise.
   *
   * @return whether or not there is a next beat
   */
  boolean hasNext();

  /**
   * Returns true if there is a previous beat in this piece of music; false otherwise.
   *
   * @return whether or not there is a previous beat
   */
  boolean hasPrev();

  /**
   * Returns true if there are no notes in this piece of music; false otherwise.
   *
   * @return whether or not this piece of music has no notes
   */
  boolean isEmpty();

  /**
   * Progresses the model's current beat. Throws an exception if no beats remain.
   *
   * @throws IllegalStateException if no beats remain
   */
  void nextBeat() throws IllegalStateException;

  /**
   * Regresses the model's current beat. Throws an exception if no beats remain.
   *
   * @throws IllegalStateException if no beats remain
   */
  void prevBeat() throws IllegalStateException;

  /**
   * Returns a list of notes within the current beat in this piece of music and doesn't change the
   * model's current beat. Returns an empty list if there is a rest. Throws an exception if there
   * are no notes in the current sheet of music.
   *
   * @return the set of notes in the next beat
   * @throws IllegalStateException if no notes exist
   */
  List<Note> playBeat() throws IllegalStateException;

  /**
   * Sets the current beat of this model to the specified value. Throws an exception if the given
   * value is negative or if it is greater than the total number of beats in the music. Throws an
   * exception if there are no notes in the editor.
   * @param curBeat the value the current beat will be set to
   * @throws IllegalArgumentException if the given value is negative or if the given value is
   *         greater than the total number of beats in the music
   * @throws IllegalStateException if no beats exist in the editor
   */
  void setBeat(int curBeat) throws IllegalArgumentException, IllegalStateException;

  /**
   * Gets the current beat that the model is on. Throws an exception if no beats have been added.
   * @return the current beat
   * @throws IllegalStateException if no beats exist
   */
  int getBeat() throws IllegalStateException;
}
