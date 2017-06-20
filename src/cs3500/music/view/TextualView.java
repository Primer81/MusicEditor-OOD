package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;

import cs3500.music.controller.KeyboardListener;
import cs3500.music.controller.MouseKeyListener;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;

import javax.swing.*;

/**
 * Text view of the model to be printed to the console.
 */
public class TextualView extends JFrame implements IMusicEditorView {

  private IMusicEditorModel model;

  /**
   * Constructs a TextualView.
   *
   * @param model The model to be displayed
   */
  public TextualView(IMusicEditorModel model) {
    this.model = model;
  }

  @Override
  public void display() {
    System.out.print(this.getMusicState());
  }

  /**
   * Returns the textual representation of the model's music.
   *
   * @return Text view of the music.
   */
  private String getMusicState() {
    if (model.getMusic().isEmpty()) {
      return "";
    }
    int length = model.getSongLength();
    StringBuilder sb = new StringBuilder(
            String.format("%" + Integer.toString(length).length() + "s", ""));
    ArrayList<String> range = (ArrayList) model.getRange();
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
    String row = String.format("%" + (Integer.toString(model.getSongLength()).length()) + "s",
            current);
    for (int i = 0; i < noteRange.size(); i++) {
      currBeat = "     ";
      for (int j = 0; j < model.getMusic().size(); j++) {
        Note n = model.getMusic().get(j);
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
  public void initialize() {
    // do nothing.
  }

  @Override
  public void keyTyped(String cmd) {

  }

  @Override
  public void mouseClicked(Point point) {

  }
}
