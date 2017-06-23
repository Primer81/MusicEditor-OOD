package cs3500.music.view;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * Class that renders everything for the top half of the GUI view (everything except the keyboard).
 */
public class ConcreteGuiViewPanel extends JPanel {

  private final int BEAT_WIDTH = 20;
  private final int NOTE_HEIGHT = 20;
  private final int SIDE_WIDTH = 20;
  private int redLineLoc;
  private List<Rectangle> rectangles = new ArrayList<>();
  private List<Note> music;
  private int curBeat;

  /**
   * Constructs a ConcreteGuiViewPanel.
   */
  public ConcreteGuiViewPanel() {
    super();
    this.redLineLoc = this.BEAT_WIDTH + this.SIDE_WIDTH + 5;
    this.music = new ArrayList<>();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.drawNotes(g);
    this.drawPitches(g);
    this.drawMeasures(g);
    this.drawRedLine(g);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(this.BEAT_WIDTH + this.SIDE_WIDTH + 5 + this.getSongLength() * 21,
        this.BEAT_WIDTH + this.SIDE_WIDTH + 5 + this.getRange().size() * 20 + 100);
  }

  @Override
  public void addNotify() {
    super.addNotify();
    requestFocus();
  }

  /**
   * Public method used to set the list of notes to the specified value.
   * @param music new list of notes
   */
  public void setMusic(List<Note> music) {
    this.music = music;
  }

  /**
   * Sets the current beat and moves the red line to the new beat.
   *
   * @param curBeat The new current beat
   */
  public void setCurBeat(int curBeat) {
    this.curBeat = curBeat;
    this.redLineLoc = (this.BEAT_WIDTH + this.SIDE_WIDTH + 5) + this.BEAT_WIDTH * this.curBeat;
  }

  /**
   * Returns a rectangle specifying the location of the redline in the form of a rectangle
   * so that the repaint method can simply repaint the redline instead of repainting the
   * entire composition.
   * @return the rectangle location of the redline
   */
  public Rectangle getRedLineRectangle() {
    ArrayList<String> range = this.getRange();
    return new Rectangle(this.redLineLoc, this.NOTE_HEIGHT, redLineLoc,
        this.NOTE_HEIGHT + this.NOTE_HEIGHT * range.size());
  }

  /**
   * Returns the redLineLoc so that the controller can know where it is on the GUI.
   * @return the redLineloc
   */
  public int getRedLineLoc() {
    return this.redLineLoc;
  }

  /**
   * Helper method. Draws the note heads and their bodies.
   *
   * @param g The graphics
   */
  private void drawNotes(Graphics g) {
    Note n;
    int noteY;
    Rectangle rect;
    this.rectangles.clear();
    for (int i = 0; i < this.music.size(); i++) {
      n = this.music.get(i);
      noteY = getNoteY(n);
      rect = new Rectangle(n.getStart() * this.BEAT_WIDTH + this.BEAT_WIDTH + this.SIDE_WIDTH + 5,
              noteY, this.BEAT_WIDTH * n.getDuration(), this.NOTE_HEIGHT);
      rectangles.add(rect);
      g.setColor(Color.GREEN);
      g.fillRect(rect.x, rect.y, rect.width, rect.height);
      g.setColor(Color.BLACK);
      g.fillRect((n.getStart()) * this.BEAT_WIDTH + this.BEAT_WIDTH + this.SIDE_WIDTH + 5, noteY,
              this.BEAT_WIDTH, this.NOTE_HEIGHT);
    }
  }

  /**
   * Helper method. Returns an appropriate y-coordinate for the given Note.
   *
   * @param n The note
   * @return The y-coordinate of the given Note
   */
  private int getNoteY(Note n) {
    List<String> range = this.getRange();
    String last = range.get(range.size() - 1);
    Pitch p = this.getNotePitch(last);
    int octave = this.getNoteOctave(last);
    int y = (octave - n.getOctave()) * 12 + p.ordinal() - n.getPitch().ordinal();
    y = y * this.NOTE_HEIGHT + this.NOTE_HEIGHT;
    return y;
  }

  /**
   * Helper method. Gets the octave of the note from the String.
   *
   * @param note The String representing the note
   * @return The octave of the note
   */
  private int getNoteOctave(String note) {
    String sharp = note.substring(2);
    int octave;
    String letter = note.substring(1);
    if (note.substring(1, 2).equals("#")) {
      octave = Integer.valueOf(sharp);
    } else {
      octave = Integer.valueOf(letter);
    }
    return octave;
  }

  /**
   * Helper method. Parses the given string into a Pitch enumeration.
   *
   * @param pitch The String representing the pitch.
   * @return The pitch of the note
   */
  private Pitch getNotePitch(String pitch) {
    Pitch p;
    if (pitch.substring(1, 2).equals("#")) {
      p = Pitch.valueOf(pitch.substring(0, 1) + "_Sharp");
    } else {
      p = Pitch.valueOf(pitch.substring(0, 1));
    }
    return p;
  }

  /**
   * Gets the notes being played at the red line's current location.
   *
   * @return The notes being played
   */
    public List<Note> getNotesAtRedLine() {
      return this.playBeat();
    }

  /**
   * Helper method. Draws the note labels (pitches) on the left side of the panel.
   *
   * @param g The graphics
   */
  private void drawPitches(Graphics g) {
    int r = 0;
    int songLength = this.getSongLength();
    List<String> range = this.getRange();
    g.drawLine(this.BEAT_WIDTH + this.SIDE_WIDTH + 5, this.NOTE_HEIGHT,
        ((songLength + 2) * this.BEAT_WIDTH) + 5, this.NOTE_HEIGHT);
    for (int i = range.size() - 1; i >= 0; i--) {
      String currRange = range.get(i);
      int y = r * this.NOTE_HEIGHT + this.NOTE_HEIGHT * 2;
      g.drawString(currRange, this.SIDE_WIDTH / 3, y - 5);
      g.drawLine(this.BEAT_WIDTH + this.SIDE_WIDTH + 5, y, ((songLength + 2)
              * this.BEAT_WIDTH) + 5, y);
      r++;
    }
  }

  /**
   * Helper method. Draws the starting beat of each measure and lines to
   * represent the start of each measure.
   *
   * @param g The graphics
   */
  private void drawMeasures(Graphics g) {
    List<String> range = this.getRange();
    int songLength = this.getSongLength();
    g.setColor(Color.BLACK);
    for (int i = 0; i <= songLength + (songLength % 4); i++) {
      int xValue = (i + 1) * this.BEAT_WIDTH + this.SIDE_WIDTH + 5;
      if (i % 4 == 0) {
        g.drawLine(xValue, this.NOTE_HEIGHT, xValue, (range.size() + 1) * this.NOTE_HEIGHT);
        g.drawString(Integer.toString(i), xValue, this.NOTE_HEIGHT);
      }
    }
  }

  /**
   * Helper method. Draws the red line.
   *
   * @param g The graphics
   */
  private void drawRedLine(Graphics g) {
    List<String> range = this.getRange();
    g.setColor(Color.RED);
    g.drawLine(redLineLoc, this.NOTE_HEIGHT, redLineLoc, this.NOTE_HEIGHT + this.NOTE_HEIGHT
            * range.size());
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

  /**
   * Returns the notes at the specified beat.
   * @param beat the beat specified
   * @return the notes at the given beat
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

  /**
   * Plays the notes at the current beat.
   *
   * @return The notes playing at the current beat
   * @throws IllegalStateException If the music is empty
   */
  private List<Note> playBeat() throws IllegalStateException {
    if (this.music.size() == 0) {
      throw new IllegalStateException("Error: No notes have been added to the editor.");
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
}

