package cs3500.music.controller;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.view.IMusicEditorView;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.awt.*;
import java.awt.event.MouseEvent;
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

  public void setView(IMusicEditorView v) throws InvalidMidiDataException {
    view = v;
    v.setMusic(this.model.getMusic());
    v.setTempo(this.model.getTempo());
    v.setCurBeat(this.model.getBeat());
    //create and set the keyboard listener and mouse listener
    this.configureKeyBoardListener();
    this.configureMouseKeyListener();
    v.initialize();
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

    keyTypes.put(',',() -> {
      if (this.model.hasPrev()) {
        this.model.prevBeat();
        this.view.prevBeat();
      }
    });
    keyTypes.put('.',() -> {
      if (this.model.hasNext()) {
        this.model.nextBeat();
        this.view.nextBeat();
      }
    });
    keyTypes.put(' ',() -> {
      if (this.view.isPaused()) {
        try {
          this.view.play();
        } catch (MidiUnavailableException e) {
          e.printStackTrace();
        }
      }
      else {
        this.view.pause();
      }
    });
    keyTypes.put('s',() -> {
    });
    keyTypes.put('e',() -> {
    });

    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    this.view.addKeyListener(kbd);
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
    });

    MouseKeyListener mkl = new MouseKeyListener();
    mkl.setMouseClickedMap(mouseClicks);
    mkl.setPoints(points);

    this.view.addMouseListener(mkl);
  }
}