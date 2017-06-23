package cs3500.music.controller;

import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import static org.junit.Assert.assertTrue;

/**
 * Testing class for KeyboardListener.
 */
public class KeyboardListenerTest {

  KeyboardListener kbd;

  int beat;
  boolean paused;

  /**
   * Initializes the testing data.
   */
  @Before
  public void init() {
    beat = 0;
    paused = true;
    kbd = new KeyboardListener();
  }

  @Test
  public void testSpace() {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        paused = !paused;
      }
    };
    assertTrue(paused == true);
    Map<Character, Runnable> mockKeyTypes = new HashMap<>();
    mockKeyTypes.put(' ', r);
    kbd.setKeyTypedMap(mockKeyTypes);

    kbd.keyTyped(new KeyEvent(new JFrame(), 0, 0, 0, KeyEvent.VK_SPACE));
    assertTrue(paused == false);
  }

  @Test
  public void testRightArrow() {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        beat++;
      }
    };
    assertTrue(beat == 0);
    Map<Integer, Runnable> mockKeyPresses = new HashMap<>();
    mockKeyPresses.put(KeyEvent.VK_RIGHT, r);
    kbd.setKeyPressedMap(mockKeyPresses);

    kbd.keyPressed(new KeyEvent(new JFrame(), 0, 0, 0, KeyEvent.VK_RIGHT));
    assertTrue(beat == 1);
  }

}