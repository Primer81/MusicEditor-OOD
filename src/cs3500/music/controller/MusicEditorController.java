package cs3500.music.controller;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.view.IMusicEditorView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MusicEditorController {
  private IMusicEditorModel model;
  private IMusicEditorView view;

  public MusicEditorController(IMusicEditorModel m)
  {
    model = m;
  }

  public void setView(IMusicEditorView v)
  {
    view = v;
    //create and set the keyboard listener and mouse listener
    configureKeyBoardListener();
  }

  /**
   * Creates and sets a keyboard listener for the view
   * In effect it creates snippets of code as Runnable object, one for each time a key
   * is typed, pressed and released, only for those that the program needs.
   * Last we create our KeyboardListener object, set all its maps and then give it to
   * the view.
   */
  private void configureKeyBoardListener()
  {
    Map<Character,Runnable> keyTypes = new HashMap<Character,Runnable>();
    Map<Integer,Runnable> keyPresses = new HashMap<Integer,Runnable>();
    Map<Integer,Runnable> keyReleases = new HashMap<Integer,Runnable>();

    keyTypes.put(',',() ->
        {this.view.keyTyped("left");}
    );
    keyTypes.put('.',() ->
        {this.view.keyTyped("right");}
    );
    keyTypes.put(' ',() ->
        {this.view.keyTyped("pause/play");}
    );
    keyTypes.put('s',() ->
        {this.view.keyTyped("toStart");}
    );
    keyTypes.put('e',() ->
        {this.view.keyTyped("toEnd");}
    );

    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    view.addKeyListener(kbd);
  }

  /**
   * Creates and set a mouse listener for the view.
   * In effect it creates snippets of code as Runnable object, one for each time a mouse is clicked,
   * pressed, and released, only for those that the program needs.
   * Last we create our MouseKeyListener object, set all its maps and then give it to
   * the view.
   */
  private void configureMouseKeyListener() {
    Map<Integer,Runnable> mouseClicks = new HashMap<Integer,Runnable>();
    Stack<Point> points = new Stack<>();

    mouseClicks.put(MouseEvent.BUTTON1, () -> {
      this.view.mouseClicked(points.peek());
    });
  }
}