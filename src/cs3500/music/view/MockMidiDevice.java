package cs3500.music.view;

import java.util.List;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Receiver;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.sound.midi.VoiceStatus;

/**
 * Mock object that emulates a MIDI Synthesizer.
 */
public class MockMidiDevice implements Synthesizer {

  StringBuilder log;

  /**
   * Constructs a MockMidiDevice.
   *
   * @param log The StringBuilder that stores the log
   */
  public MockMidiDevice(StringBuilder log) {
    this.log = log;
  }

  @Override
  public VoiceStatus[] getVoiceStatus() {
    return new VoiceStatus[0];
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return new MockReceiver(log);
  }

  @Override
  public Instrument[] getLoadedInstruments() {
    return new Instrument[0];
  }

  @Override
  public void close() {
    log.append("close");
  }

  @Override
  public long getLatency() {
    return 0;
  }

  @Override
  public boolean loadInstrument(Instrument instrument) {
    return false;
  }

  @Override
  public void unloadInstruments(Soundbank soundbank, Patch[] patches) {
    // do nothing.
  }

  @Override
  public List<Transmitter> getTransmitters() {
    return null;
  }

  @Override
  public boolean remapInstrument(Instrument from, Instrument to) {
    return false;
  }

  @Override
  public void unloadAllInstruments(Soundbank soundbank) {
    // do nothing.
  }

  @Override
  public List<Receiver> getReceivers() {
    return null;
  }

  @Override
  public boolean isSoundbankSupported(Soundbank soundbank) {
    return false;
  }

  @Override
  public int getMaxPolyphony() {
    return 0;
  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public int getMaxReceivers() {
    return 0;
  }

  @Override
  public int getMaxTransmitters() {
    return 0;
  }

  @Override
  public void open() throws MidiUnavailableException {
    log.append("open \n");
  }

  @Override
  public Info getDeviceInfo() {
    return null;
  }

  @Override
  public MidiChannel[] getChannels() {
    return new MidiChannel[0];
  }

  @Override
  public void unloadInstrument(Instrument instrument) {
    // do nothing.
  }

  @Override
  public boolean loadInstruments(Soundbank soundbank, Patch[] patches) {
    return false;
  }
  
  @Override
  public boolean loadAllInstruments(Soundbank soundbank) {
    return false;
  }

  @Override
  public long getMicrosecondPosition() {
    log.append("Microsecond position \n");
    return 0;
  }

  @Override
  public Instrument[] getAvailableInstruments() {
    return new Instrument[0];
  }

  @Override
  public Soundbank getDefaultSoundbank() {
    return null;
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return null;
  }


}
