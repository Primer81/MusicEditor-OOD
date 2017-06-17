package cs3500.music.view;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;


/**
 * Visual representation of a piano keyboard.
 */
public class PianoView extends JPanel {

  private ConcreteGuiViewPanel concrete;

  /**
   * Constructs a PianoView.
   *
   * @param concrete The ConcreteGuiViewPanel to draw information about the notes from
   */
  public PianoView(ConcreteGuiViewPanel concrete) {
    super();
    this.concrete = concrete;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.drawKeys(g);
    repaint();
  }

  /**
   * Helper method. Draws the keyboard.
   *
   * @param g The graphics
   */
  private void drawKeys(Graphics g) {

    HashMap<Note, Rectangle> keyMap = new HashMap<>();
    Rectangle key;
    int pitch;
    int octave;
    int blackPitch;
    int temp = -1;

    for (int i = 0; i < 70; i++) {
      temp++;
      pitch = temp % 12;
      octave = temp / 12;
      blackPitch = i % 7;
      key = new Rectangle(i * 20 + 20, 100, 18, 200);
      Note tempNote = new Note(1, octave, 0, Pitch.values()[pitch], 0 , 0);
      keyMap.put(tempNote, key);
      g.setColor(Color.white);
      g.fillRect(key.x, key.y, 18, 200);
      g.setColor(Color.BLACK);
      g.drawRect(key.x, key.y, 18, 200);

      if (blackPitch == 0 || blackPitch == 1 || blackPitch == 3 || blackPitch == 4 || blackPitch
              == 5) {
        temp++;
        pitch = temp % 12;
        octave = temp / 12;
        Note tempNote2 = new Note(1, octave, 0, Pitch.values()[pitch], 0 , 0);
        key = new Rectangle(i * 20 + 35, 100, 9, 90);
        keyMap.put(tempNote2, key);
        g.setColor(Color.black);
        g.drawRect(key.x, key.y,9, 90);
        g.fillRect(key.x, key.y,9, 90);
      }
    }

    List<Note> orangeNotes = concrete.getNotesAtRedLine();
    ArrayList<Note> notes = new ArrayList<>();
    ArrayList<Integer> indices = new ArrayList<>();
    for (Note n : orangeNotes) {
      for (Note n2 : keyMap.keySet()) {
        if (n.getPitch() == n2.getPitch() && n.getOctave() == n2.getOctave()) {
          notes.add(n2);
        }
      }
    }

    for (int i = 0; i < keyMap.size(); i++) {
      for (int j = 0; j < notes.size(); j++) {
        if (notes.get(j).equals(keyMap.keySet().toArray()[i])) {
          indices.add(i);
        }
      }
    }

    for (int i : indices) {
      Rectangle orange = (Rectangle) keyMap.values().toArray()[i];
      g.setColor(Color.orange);
      g.fillRect(orange.x, orange.y, orange.width, orange.height);
    }
  }
}
