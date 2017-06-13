package cs3500.music.model;

import java.util.List;

/**
 * Represents the interface for a music editor.
 * Allows the user to add a note, remove a note, and get a text rendering of the music.
 */
public interface IMusicEditorModel {

  /**
   * Adds the note to the music.
   *
   * @param n The note to be added.
   */
  void addNote(Note n);

  /**
   * Removes the note from the music.
   *
   * @param n The note to be removed.
   */
  void removeNote(Note n);

  /**
   * Gets the state of the music.
   *
   * @return The music's state as a String.
   */
  String getState();

  /**
   * Gets the music.
   *
   * @return The List representing the music.
   */
  List<Note> getMusic();

  /**
   * Gets the List of Strings signifying the musical range.
   *
   * @return The range of the music.
   */
  List<String> getRange();

  /**
   * Returns the length of the song.
   *
   * @return The length of the song.
   */
  int getSongLength();
}
