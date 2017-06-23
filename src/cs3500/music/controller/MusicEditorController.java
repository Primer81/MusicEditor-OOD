package cs3500.music.controller;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.view.IMusicEditorView;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Controller for the Music Editor program. Allows key inputs to navigate the visual views as well
 * as play/pause the audio portions of the views. Can add new notes to the compositions through
 * mouse selection.
 */
public class MusicEditorController {
  private IMusicEditorModel model;
  private IMusicEditorView view;

  /**
   * Constructs a MusicEditorController.
   *
   * @param m The model
   */
  public MusicEditorController(IMusicEditorModel m)
  {
    model = m;
  }

  /**
   * Sets the view for the controller and displays it.
   * @param v The view
   * @throws InvalidMidiDataException If there is any invalid MIDI data
   */
  public void setView(IMusicEditorView v) throws InvalidMidiDataException {
    view = v;
    v.setMusic(this.model.getMusic());
    v.setTempo(this.model.getTempo());
    v.setCurBeat(this.model.getBeat());
    //create and set the listeners
    this.configureKeyBoardListener();
    this.configureMouseKeyListener();
    this.configureMetaEventsListener();
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

    keyPresses.put(KeyEvent.VK_HOME,() -> {
      this.model.setBeat(0);
      this.view.setCurBeat(this.model.getBeat());
      this.view.setTickPosition(this.model.getBeat());
    });
    keyPresses.put(KeyEvent.VK_END,() -> {
      this.model.setBeat(this.model.getSongLength() - 1);
      this.view.setPaused(true);
      this.view.setCurBeat(this.model.getBeat());
      this.view.setTickPosition(this.model.getBeat());
    });
    keyPresses.put(KeyEvent.VK_LEFT,() -> {
      if (this.model.hasPrev()) {
        this.model.prevBeat();
        this.view.setCurBeat(this.model.getBeat());
        this.view.setTickPosition(this.model.getBeat());
      }
    });
    keyPresses.put(KeyEvent.VK_RIGHT,() -> {
      if (this.model.hasNext()) {
        this.model.nextBeat();
        this.view.setCurBeat(this.model.getBeat());
        this.view.setTickPosition(this.model.getBeat());
      }
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
      if (this.view.isPaused()) {
        Point point = points.pop();
        int pitch;
        int octave;
        int blackPitch;
        int temp = -1;

        for (int i = 0; i < 70; i++) {
          temp++;
          pitch = temp % 12;
          octave = temp / 12;
          blackPitch = i % 7;
          Note tempNote = new Note(1, octave, this.model.getBeat(), Pitch.values()[pitch], 1, 50);
          if (point.x > i * 20 && point.x < (i + 1) * 20
              && !(point.x > i * 20 + 15 && point.y < 90)) {
            this.model.addNote(tempNote);
            this.model.nextBeat();
            this.view.setMusic(this.model.getMusic());
            this.view.setCurBeat(this.model.getBeat());
            break;
          }
          if (blackPitch == 0 || blackPitch == 1 || blackPitch == 3 || blackPitch == 4 || blackPitch
              == 5) {
            temp++;
            pitch = temp % 12;
            octave = temp / 12;
            tempNote = new Note(1, octave, this.model.getBeat(), Pitch.values()[pitch], 1, 50);
            if (point.x > i * 20 + 15 && point.x < (i + 1) * 20 && point.y < 90) {
              this.model.addNote(tempNote);
              this.model.nextBeat();
              this.view.setMusic(this.model.getMusic());
              this.view.setCurBeat(this.model.getBeat());
              break;
            }
          }
        }
      }
    });

    MouseKeyListener mkl = new MouseKeyListener();
    mkl.setMouseClickedMap(mouseClicks);
    mkl.setPoints(points);

    this.view.addMouseListener(mkl);
  }

  /**
   * Creates and set a meta listener for the view.
   * In effect it creates snippets of code as Runnable object, one for each time a meta event is
   * read by the sequencer.
   * Last we create our MetaEventsListener object, set all its maps and then give it to
   * the view.
   */
  private void configureMetaEventsListener() {
    Map<String, Runnable> metaRead = new HashMap<>();

    metaRead.put("beat", () -> {
      this.model.nextBeat();
      this.view.setCurBeat(this.model.getBeat());
    });
    metaRead.put("end", () -> {
      this.view.setPaused(true);
    });

    MetaEventsListener mel = new MetaEventsListener();
    mel.setMetaReadMap(metaRead);

    this.view.addMetaEventListener(mel);
  }
}