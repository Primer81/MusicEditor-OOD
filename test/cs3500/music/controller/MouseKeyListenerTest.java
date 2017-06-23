package cs3500.music.controller;

import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.swing.*;

import static org.junit.Assert.*;

/**
 * Testing class for MouseKeyListener.
 */
public class MouseKeyListenerTest {

  MouseKeyListener mouse;

  boolean clicked;

  Point p1;
  Point p2;

  Stack<Point> points = new Stack<>();

  Runnable r;

  /**
   * Initializes the testing data.
   */
  @Before
  public void init() {
    mouse = new MouseKeyListener();
    clicked = false;

    r = new Runnable() {
      @Override
      public void run() {
        clicked = !clicked;
      }
    };

    p1 = new Point(0, 0);
    p2 = new Point(0, 1);
    points.push(p1);
    points.push(p2);
  }

  @Test
  public void testMouseClick() {
    assertTrue(clicked == false);
    Map<Integer, Runnable> mockClicks = new HashMap<>();
    mockClicks.put(MouseEvent.BUTTON1, r);
    mouse.setMouseClickedMap(mockClicks);
    mouse.setPoints(points);
    mouse.mouseClicked(new MouseEvent(new JFrame(), 0, 0, 0, 0, 0, 0,
            false, MouseEvent.BUTTON1));
    assertTrue(clicked == true);
    mouse.mouseClicked(new MouseEvent(new JFrame(), 0, 0, 0, 0, 1, 0,
            false, MouseEvent.BUTTON1));
    assertTrue(clicked == false);
    mouse.mouseClicked(new MouseEvent(new JFrame(), 0, 0, 0, 1, 1, 0,
            false, MouseEvent.BUTTON2));
    assertTrue(clicked == false);
  }
}