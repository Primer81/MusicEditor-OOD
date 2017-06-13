package cs3500.music.view;

import java.awt.*;

import javax.swing.*;


/**
 * Visual representation of a piano keyboard.
 */
public class PianoView extends JPanel {

  private ConcreteGuiViewPanel concrete;

  public PianoView(ConcreteGuiViewPanel concrete) {
    super();
    this.concrete = concrete;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.drawKeys(g);
  }

  /**
   * Helper method. Draws the keyboard.
   *
   * @param g The graphics
   */
  private void drawKeys(Graphics g) {
    for (int i = 1; i < 71; i++) {
      g.setColor(Color.white);
      g.fillRect(i * 20 + 20, 100, 18, 200);
      g.setColor(Color.BLACK);
      g.drawRect(i * 20 + 20, 100, 18, 200);
    }

    for (int i = 1; i < 71; i++) {
      if (i % 7 == 1 || i % 7 == 2 || i % 7 == 4 || i % 7 == 5 || i % 7 == 6) {
        g.setColor(Color.black);
        g.drawRect(i * 20 + 35, 100, 9, 90);
        g.fillRect(i * 20 + 35, 100, 9, 90);
      }
    }
  }
}
