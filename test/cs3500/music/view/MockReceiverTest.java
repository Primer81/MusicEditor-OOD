package cs3500.music.view;

import org.junit.Test;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import static org.junit.Assert.assertEquals;

/**
 * Testing class for MockReceiver.
 */
public class MockReceiverTest {

  @Test
  public void testSend() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    MockReceiver receiver = new MockReceiver(log);
    MockReceiver receiver2 = new MockReceiver(log2);

    MidiMessage msg = null;
    MidiMessage msg2 = null;

    try {
      msg = new ShortMessage(ShortMessage.NOTE_ON, 1, 4, 5);
      msg2 = new ShortMessage(ShortMessage.NOTE_OFF, 4, 5, 1);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }

    long timeStamp = 100;

    receiver.send(msg, timeStamp);
    receiver2.send(msg2, timeStamp);

    assertEquals("Current note: 1 4 5 100 \n", receiver.log.toString());
    assertEquals("Current note: 4 5 1 100 \n", receiver2.log.toString());
  }

  @Test
  public void testClose() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();

    MockReceiver receiver = new MockReceiver(log);
    MockReceiver receiver2 = new MockReceiver(log2);

    receiver.close();
    receiver2.close();

    assertEquals("Closing.", receiver.log.toString());
    assertEquals("Closing.", receiver2.log.toString());
  }
}