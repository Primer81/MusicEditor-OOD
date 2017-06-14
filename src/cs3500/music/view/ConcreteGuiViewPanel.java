package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * Class that renders everything for the GUI view.
 */
public class ConcreteGuiViewPanel extends JPanel {

  private final int BEAT_WIDTH = 20;
  private final int NOTE_HEIGHT = 20;
  private final int SIDE_WIDTH = 20;
  private int redLineLoc;
  private IMusicEditorModel model;
  private List<Rectangle> rectangles = new ArrayList<>();

  /**
   * Constructs a ConcreteGuiViewPanel.
   *
   * @param model The model to be displayed
   */
  public ConcreteGuiViewPanel(IMusicEditorModel model) {
    super();
    this.model = model;
    this.redLineLoc = this.BEAT_WIDTH + this.SIDE_WIDTH + 5;
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    this.drawNotes(g);
    this.drawPitches(g);
    this.drawMeasures(g);
    this.drawRedLine(g);
  }

  /**
   * Helper method. Draws the note heads and their bodies.
   *
   * @param g The graphics
   */
  private void drawNotes(Graphics g) {
    Note n;
    int noteY;
    List<Note> notes = model.getMusic();
    Rectangle rect;
    this.rectangles.clear();
    for (int i = 0; i < notes.size(); i++) {
      n = notes.get(i);
      noteY = getNoteY(n);
      rect = new Rectangle(n.getStart() * this.BEAT_WIDTH + this.BEAT_WIDTH + this.SIDE_WIDTH + 5, noteY,
              this.BEAT_WIDTH * n.getDuration(), this.NOTE_HEIGHT);
      rectangles.add(rect);
      g.setColor(Color.GREEN);
      g.fillRect(rect.x, rect.y, rect.width, rect.height);
      g.setColor(Color.BLACK);
      g.fillRect((n.getStart()) * this.BEAT_WIDTH + this.BEAT_WIDTH + this.SIDE_WIDTH + 5, noteY, this.BEAT_WIDTH,
              this.NOTE_HEIGHT);
    }
  }

  /**
   * Helper method. Returns an appropriate y-coordinate for the given Note.
   *
   * @param n The note
   * @return The y-coordinate of the given Note
   */
  private int getNoteY(Note n) {
    List<String> range = model.getRange();
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
   * Helper method. Gets the pitch of the note.
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
   * Helper method. Draws the note labels (pitches) on the left side of the panel.
   *
   * @param g The graphics
   */
  private void drawPitches(Graphics g) {
    int r = 0;
    int songLength = this.model.getSongLength();
    List<String> range = this.model.getRange();
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
   * Helper method. Draws the grid.
   *
   * @param g The graphics
   */
  private void drawMeasures(Graphics g) {
    List<String> range = model.getRange();
    int songLength = model.getSongLength();
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
    List<String> range = model.getRange();
    g.setColor(Color.RED);
    g.drawLine(redLineLoc, this.NOTE_HEIGHT, redLineLoc, this.NOTE_HEIGHT + this.NOTE_HEIGHT * range.size());
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(this.BEAT_WIDTH + this.SIDE_WIDTH + 5 + model.getSongLength() * 21, this.BEAT_WIDTH
            + this.SIDE_WIDTH + 5 + model.getRange().size()
            * 20 + 100);
  }

  /**
   * Gets the red line's location.
   *
   * @return The red line's location
   */
  public int getRedLineLocation() {
    return redLineLoc;
  }

  /**
   * Gets the beat the red line is at.
   *
   * @return The beat the red line is at
   */
  public int getRedLineBeat() {
    return (redLineLoc - this.BEAT_WIDTH + this.SIDE_WIDTH + 5) / 20;
  }

  /**
   * Gets the notes being played at the red line's current location.
   *
   * @return The notes being played
   */
  public List<Note> getNotesAtRedLine() {
    return model.getNotesAtBeat((redLineLoc - 45) / 20);
  }

}

