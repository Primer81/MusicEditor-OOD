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

  private final int beatWidth = 20;
  private final int noteHeight = 20;
  private final int sideWidth = 20;
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
    this.redLineLoc = beatWidth + sideWidth + 5;
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    this.drawNotes(g);
    this.drawLabels(g);
    this.drawGrid(g);
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
      rect = new Rectangle(n.getStart() * beatWidth + beatWidth + sideWidth + 5, noteY,
              beatWidth * n.getDuration(), noteHeight);
      rectangles.add(rect);
      g.setColor(Color.GREEN);
      g.fillRect(rect.x, rect.y, rect.width, rect.height);
      g.setColor(Color.BLACK);
      g.fillRect((n.getStart()) * beatWidth + beatWidth + sideWidth + 5, noteY, beatWidth,
              noteHeight);
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
    Pitch p = getNotePitch(last);
    int octave = getNoteOctave(last);
    int y = (octave - n.getOctave()) * 12 + p.ordinal() - n.getPitch().ordinal();
    y = y * noteHeight + noteHeight;
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
      p = Pitch.valueOf(pitch.substring(0, 1) + "Sharp");
    } else {
      p = Pitch.valueOf(pitch.substring(0, 1));
    }
    return p;
  }

  /**
   * Helper method. Draws the note labels on the left side of the panel.
   *
   * @param g The graphics
   */
  private void drawLabels(Graphics g) {
    int r = 0;
    int songLength = model.getSongLength();
    List<String> range = model.getRange();
    for (int i = range.size() - 1; i >= 0; i--) {
      String currRange = range.get(i);
      int y = r * noteHeight + noteHeight * 2;
      g.drawString(currRange, sideWidth / 3, y - 5);
      g.drawLine(beatWidth + sideWidth + 5, y, ((songLength + (songLength % 4))
              * beatWidth) + 5, y);
      r++;
    }
  }

  /**
   * Helper method. Draws the grid.
   *
   * @param g The graphics
   */
  private void drawGrid(Graphics g) {
    List<String> range = model.getRange();
    int songLength = model.getSongLength();
    g.setColor(Color.BLACK);
    g.drawLine(beatWidth + sideWidth + 5, noteHeight, ((songLength + (songLength % 4))
                    * beatWidth) + 5, noteHeight);
    for (int i = 0; i <= songLength + (songLength % 4); i++) {
      int xValue = (i + 1) * beatWidth + sideWidth + 5;
      if (i % 4 == 0) {
        g.drawLine(xValue, noteHeight, xValue, (range.size() + 1) * noteHeight);
        g.drawString(Integer.toString(i), xValue, noteHeight);
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
    g.drawLine(redLineLoc, noteHeight, redLineLoc, noteHeight + noteHeight * range.size());
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(beatWidth + sideWidth + 5 + model.getSongLength() * 21, beatWidth
            + sideWidth + 5 + model.getRange().size()
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
    return (redLineLoc - beatWidth + sideWidth + 5) / 20;
  }

}

