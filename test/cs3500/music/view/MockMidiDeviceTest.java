package cs3500.music.view;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;

import static org.junit.Assert.assertEquals;

/**
 * Testing class for MockMidiDevice.
 */
public class MockMidiDeviceTest {

  StringBuilder log = new StringBuilder();
  MockMidiDevice mockMidi = new MockMidiDevice(log);
  MockReceiver mockReceiver;
  MidiViewImpl midiView;
  FileReader fileReader;
  IMusicEditorModel model;
  CompositionBuilder<MusicEditorModel> builder = new MusicEditorModel.Builder();

  /**
   * Initializes the testing data.
   */
  @Before
  public void initData() {
    try {
      fileReader = new FileReader(new File("mary-little-lamb.txt"));
    } catch (FileNotFoundException f) {
      f.printStackTrace();
    }
    model = MusicReader.parseFile(fileReader, builder);

    try {
      mockReceiver = (MockReceiver) mockMidi.getReceiver();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    /*
    try {
      midiView = new MidiViewImpl(mockMidi);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    } */
  }
}