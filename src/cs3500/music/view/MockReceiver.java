package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * Mock object that emulates a MIDI Receiver.
 */
public class MockReceiver implements Receiver {

  StringBuilder log;

  /**
   * Constructs a MockReceiver.
   *
   * @param log The StringBuilder where the log output will be written
   */
  public MockReceiver(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void close() {
    log.append("Closing.");
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    ShortMessage m = (ShortMessage) message;
    int channel = m.getChannel();
    int data1 = m.getData1();
    int data2 = m.getData2();
    log.append("Current note: " + channel + " " + data1 + " " + data2 + " " + timeStamp + " "
            + "\n");
  }
}
