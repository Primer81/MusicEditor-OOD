package cs3500.music.controller;

import com.sun.javafx.scene.control.skin.IntegerFieldSkin;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.view.IMusicEditorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

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
   *
   * Last we create our KeyboardListener object, set all its maps and then give it to
   * the view.
   */
  private void configureKeyBoardListener()
  {
    Map<Integer,Runnable> keyTypes = new HashMap<Integer,Runnable>();
    Map<Integer,Runnable> keyPresses = new HashMap<Integer,Runnable>();
    Map<Integer,Runnable> keyReleases = new HashMap<Integer,Runnable>();

    keyTypes.put(KeyEvent.VK_LEFT,() ->
        {/* TODO: Add code for what left keyboard press should do to view */}
    );
    keyTypes.put(KeyEvent.VK_RIGHT,() ->
        {/* TODO: Add code for what right keyboard press should do to view */}
    );
    keyTypes.put(KeyEvent.VK_SPACE,() ->
        {/* TODO: Add code to toggle whether the music is paused or playing in view */}
    );

    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    view.addKeyListener(kbd);
  }
}