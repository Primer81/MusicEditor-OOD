package cs3500.music;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.IMusicEditorView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * Application that runs the music editor.
 */
public class MusicEditor {
  /**
   * Main method for application running.
   *
   * @param args The arguments
   * @throws IOException Exception
   * @throws InvalidMidiDataException Exception
   */
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    MusicEditorModel.Builder builder = new MusicEditorModel.Builder();
    Readable fileReader = null;
    Scanner in = new Scanner(System.in);
    String fileName;

    while (fileReader == null) {
      System.out.print("Input a file: ");
      try {
        fileName = in.next();
        fileReader = new FileReader(new File(fileName));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (NoSuchElementException n) {
        n.printStackTrace();
      }
    }
    IMusicEditorModel model = MusicReader.parseFile(fileReader, builder);

    IMusicEditorView view = null;

    while (view == null) {
      System.out.print("\nEnter 'g' for GUI view, 't' for textual view, or 'm' for MIDI view.");
      switch (in.next()) {
        case "g":
          try {
            view = new IMusicEditorView.ReturnView(model).init("gui");
          } catch (MidiUnavailableException e) {
            e.printStackTrace();
          }
          break;
        case "t":
          try {
            view = new IMusicEditorView.ReturnView(model).init("textual");
          } catch (MidiUnavailableException e) {
            e.printStackTrace();
          }
          break;
        case "m":
          try {
            view = new IMusicEditorView.ReturnView(model).init("midi");
          } catch (MidiUnavailableException e) {
            e.printStackTrace();
          }
          break;
        default:
          System.out.append("Invalid view type.");
      }
    }
    view.display();
  }
}
