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

  @Test
  public void testMaryMidiTranscript() {
    midiView.initialize();
    assertEquals("Current note: 0 64 72 0 \n"
            + "Current note: 0 64 72 400000 \n"
            + "Current note: 0 55 70 0 \n"
            + "Current note: 0 55 70 1400000 \n"
            + "Current note: 0 62 72 400000 \n"
            + "Current note: 0 62 72 800000 \n"
            + "Current note: 0 60 71 800000 \n"
            + "Current note: 0 60 71 1200000 \n"
            + "Current note: 0 62 79 1200000 \n"
            + "Current note: 0 62 79 1600000 \n"
            + "Current note: 0 55 79 1600000 \n"
            + "Current note: 0 55 79 3000000 \n"
            + "Current note: 0 64 85 1600000 \n"
            + "Current note: 0 64 85 2000000 \n"
            + "Current note: 0 64 78 2000000 \n"
            + "Current note: 0 64 78 2400000 \n"
            + "Current note: 0 64 74 2400000 \n"
            + "Current note: 0 64 74 3000000 \n"
            + "Current note: 0 55 77 3200000 \n"
            + "Current note: 0 55 77 4800000 \n"
            + "Current note: 0 62 75 3200000 \n"
            + "Current note: 0 62 75 3600000 \n"
            + "Current note: 0 62 77 3600000 \n"
            + "Current note: 0 62 77 4000000 \n"
            + "Current note: 0 62 75 4000000 \n"
            + "Current note: 0 62 75 4800000 \n"
            + "Current note: 0 55 79 4800000 \n"
            + "Current note: 0 55 79 5200000 \n"
            + "Current note: 0 64 82 4800000 \n"
            + "Current note: 0 64 82 5200000 \n"
            + "Current note: 0 67 84 5200000 \n"
            + "Current note: 0 67 84 5600000 \n"
            + "Current note: 0 67 75 5600000 \n"
            + "Current note: 0 67 75 6400000 \n"
            + "Current note: 0 55 78 6400000 \n"
            + "Current note: 0 55 78 8000000 \n"
            + "Current note: 0 64 73 6400000 \n"
            + "Current note: 0 64 73 6800000 \n"
            + "Current note: 0 62 69 6800000 \n"
            + "Current note: 0 62 69 7200000 \n"
            + "Current note: 0 60 71 7200000 \n"
            + "Current note: 0 60 71 7600000 \n"
            + "Current note: 0 62 80 7600000 \n"
            + "Current note: 0 62 80 8000000 \n"
            + "Current note: 0 55 79 8000000 \n"
            + "Current note: 0 55 79 9600000 \n"
            + "Current note: 0 64 84 8000000 \n"
            + "Current note: 0 64 84 8400000 \n"
            + "Current note: 0 64 76 8400000 \n"
            + "Current note: 0 64 76 8800000 \n"
            + "Current note: 0 64 74 8800000 \n"
            + "Current note: 0 64 74 9200000 \n"
            + "Current note: 0 64 77 9200000 \n"
            + "Current note: 0 64 77 9600000 \n"
            + "Current note: 0 55 78 9600000 \n"
            + "Current note: 0 55 78 11200000 \n"
            + "Current note: 0 62 75 9600000 \n"
            + "Current note: 0 62 75 10000000 \n"
            + "Current note: 0 62 74 10000000 \n"
            + "Current note: 0 62 74 10400000 \n"
            + "Current note: 0 64 81 10400000 \n"
            + "Current note: 0 64 81 10800000 \n"
            + "Current note: 0 62 70 10800000 \n"
            + "Current note: 0 62 70 11200000 \n"
            + "Current note: 0 52 72 11200000 \n"
            + "Current note: 0 52 72 12800000 \n"
            + "Current note: 0 60 73 11200000 \n"
            + "Current note: 0 60 73 12800000 \n", mockReceiver.log.toString());
  }
}