package cs3500.music.view;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import cs3500.music.model.IMusicEditorModel;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends JFrame implements IMusicEditorView {

  private final JPanel displayPanel;

  /**
   * Constructs a new GuiViewFrame.
   *
   * @param model The model to be displayed
   */
  public GuiViewFrame(IMusicEditorModel model) {
    this.displayPanel = new ConcreteGuiViewPanel(model);
    IMusicEditorModel editorModel = model;
    ArrayList<Integer> fake = new ArrayList();
    fake.add(1);
    fake.add(4);
    fake.add(20);
    ConcreteGuiViewPanel concrete = new ConcreteGuiViewPanel(editorModel);
    JPanel piano = new PianoView(concrete);
    JScrollPane scrollPane = new JScrollPane(displayPanel, ScrollPaneConstants
            .VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.setResizable(true);
    this.setLayout(new GridLayout(2, 1));
    this.add(scrollPane);
    this.add(piano);
    this.setTitle("Music Editor");
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.pack();
    this.setFocusable(true);
    this.requestFocus();
    scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
    scrollPane.getVerticalScrollBar().setUnitIncrement(20);
  }

  @Override
  public void initialize() {
    this.setVisible(true);
  }

  @Override
  public void addMouseListener(MouseListener mouse) {

  }

  @Override
  public void keyTyped(String cmd) {

  }

  @Override
  public void mouseClicked(Point point) {

  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1200, 1200);
  }

  @Override
  public void display() {
    initialize();
    displayPanel.repaint();
  }
}
