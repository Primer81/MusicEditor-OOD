package cs3500.music.view;

import java.awt.*;
import java.util.*;
import java.util.List;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.controller.MouseKeyListener;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;

/**
 * Text view of the model to be printed to the console.
 */
public class TextualView extends JFrame implements IMusicEditorView {

  private List<Note> music;

  /**
   * Constructs a TextualView.
   */
  public TextualView() {
    this.music = new ArrayList<>();
  }

  @Override
  public void setMusic(List<Note> music) {
    this.music = music;
  }

  @Override
  public void setTempo(int tempo) {
    // does nothing
  }

  @Override
  public void setCurBeat(Integer curBeat) {
    // does nothing
  }

  @Override
  public void setPaused(boolean paused) {
    // do nothing
  }

  @Override
  public void initialize() {
    System.out.print(this.getMusicState());
  }

  @Override
  public boolean isPaused() {
    return false; // means nothing
  }

  @Override
  public void play() throws MidiUnavailableException {
    // do nothing
  }

  @Override
  public void pause() {
    // do nothing
  }

  @Override
  public void prevBeat() {
    // does nothing
  }

  @Override
  public void nextBeat() {
    // does nothing
  }

  /**
   * Returns the textual representation of the model's music.
   *
   * @return Text view of the music.
   */
  private String getMusicState() {
    if (this.music.isEmpty()) {
      return "";
    }
    int length = this.getSongLength();
    StringBuilder sb = new StringBuilder(
        String.format("%" + Integer.toString(length).length() + "s", ""));
    ArrayList<String> range = (ArrayList) this.getRange();
    for (int i = 0; i < range.size(); i++) {
      sb.append(String.format("%5s", String.format("%-4s", String.format("%3s", range.get(i)))));
    }
    for (int i = 0; i < length; i++) {
      sb.append("\n" + this.buildRow(i, range));
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
    String row = String.format("%" + (Integer.toString(this.getSongLength()).length()) + "s",
        current);
    for (int i = 0; i < noteRange.size(); i++) {
      currBeat = "     ";
      for (int j = 0; j < this.music.size(); j++) {
        Note n = this.music.get(j);
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

  /**
   * Returns a list of strings containing the range of pitches in the list of notes.
   * @return range of pitches
   */
  private ArrayList<String> getRange() {
    boolean validRange;
    boolean greaterThanFirst;
    boolean lessThanLast;

    Note firstNote = this.firstOrLast(true);
    int firstOctave = firstNote.getOctave();
    Note lastNote = this.firstOrLast(false);
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
    if (this.music.isEmpty()) {
      throw new IllegalStateException("Error: No notes.");
    }
    Note n = this.music.get(0);
    for (int i = 0; i < this.music.size(); i++) {
      int current = this.music.get(i).compare(n);
      if (first) {
        if (current < 0) {
          n = this.music.get(i);
        }
      } else {
        if (current > 0) {
          n = this.music.get(i);
        }
      }
    }
    return n;
  }

  /**
   * Returns the number of beats in the list of notes.
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

  @Override
  public void display() {
    // does nothing
  }
}
