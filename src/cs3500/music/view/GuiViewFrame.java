package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import javax.swing.text.View;

import cs3500.music.model.Note;
import org.w3c.dom.css.Rect;

/**
 * The top level frame for the GUI. Displays the editor panel (ConcreteGuiViewPanel) and the
 * Piano panel (PianoView).
 */
public class GuiViewFrame extends JFrame implements IMusicEditorView {
  private ConcreteGuiViewPanel displayPanel;
  private JPanel pianoViewPanel;
  private JScrollPane scrollPane;
  private List<Note> music;
  private int curBeat;
  private boolean paused;

  /**
   * Constructs a new GuiViewFrame.
   */
  public GuiViewFrame() {
    this.displayPanel = new ConcreteGuiViewPanel();
    this.pianoViewPanel = new PianoView(this.displayPanel);
    this.scrollPane = new JScrollPane(displayPanel, ScrollPaneConstants
        .VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.scrollPane.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE);
    this.music = new ArrayList<>();
    this.curBeat = 0;
    this.paused = true;
  }

  @Override
  public void setMusic(List<Note> music) {
    this.music = music;
    this.displayPanel.setMusic(this.music);
  }

  @Override
  public void setTempo(int tempo) {
    // does nothing
  }

  /**
   * In addition to setting the current beat, the GUI also sets the current beat of the
   * display panel and redraws where the redline in the displayPanel used to be and
   * where it is now. In addition, if the redline goes to move offscreen, the viewport's
   * view of the scroll pane will be moved to wherever the red line is. Otherwise, if the
   * editor is paused, it will simply make the redline visible.
   * @param curBeat the current beat
   */
  @Override
  public void setCurBeat(int curBeat) {
    Rectangle oldRedLineRect = this.displayPanel.getRedLineRectangle();
    this.curBeat = curBeat;
    this.displayPanel.setCurBeat(this.curBeat);
    Rectangle newRedLineRect = this.displayPanel.getRedLineRectangle();
    this.displayPanel.repaint(new Rectangle(oldRedLineRect));
    this.displayPanel.repaint(new Rectangle(newRedLineRect));

    JViewport viewport = this.scrollPane.getViewport();
    Rectangle viewRect = viewport.getViewRect();
    if ((viewRect.getX() + viewRect.getWidth() <
        this.displayPanel.getRedLineLoc() + 40
        && !this.isPaused())
        || this.isPaused()) {
      Point viewPos = viewport.getViewPosition();
      viewPos.setLocation(this.displayPanel.getRedLineLoc() - 40, viewPos.y);
      this.scrollPane.getViewport().setViewPosition(viewPos);
      this.scrollPane.getViewport().getView().repaint();
    }
  }

  @Override
  public void setPaused(boolean paused) {
    this.paused = paused;
  }

  @Override
  public void setTickPosition(int tickPosition) {
    // does nothing
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    this.scrollPane.addKeyListener(listener);
    this.displayPanel.addKeyListener(listener);
    this.pianoViewPanel.addKeyListener(listener);
  }

  @Override
  public void addMouseListener(MouseListener listener) {
    this.pianoViewPanel.addMouseListener(listener);
  }

  @Override
  public void addMetaEventListener(MetaEventListener listener) {
    // does nothing
  }

  @Override
  public void initialize() {
    this.setResizable(true);
    this.setLayout(new BorderLayout());
    this.add(this.scrollPane, BorderLayout.CENTER);
    this.add(this.pianoViewPanel, BorderLayout.SOUTH);
    this.setTitle("Music Editor");
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.pack();
    this.setFocusable(true);
    this.requestFocus();
    scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
    scrollPane.getVerticalScrollBar().setUnitIncrement(20);
    this.setVisible(true);
  }

  @Override
  public boolean isPaused() {
    return this.paused;
  }

  @Override
  public void play() throws MidiUnavailableException {
    this.paused = false;
  }

  @Override
  public void pause() {
    this.paused = true;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(20 * 71, 1000);
  }
}
