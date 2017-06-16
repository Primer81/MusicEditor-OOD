package cs3500.music.view;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


import cs3500.music.model.IMusicEditorModel;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IMusicEditorView {

  private final JPanel displayPanel; // You may want to refine this to a subtype of JPanel
  private JScrollPane scrollPane;
  private ConcreteGuiViewPanel concrete;
  private JPanel piano;
  private IMusicEditorModel model;

  /**
   * Constructs a new GuiViewFrame.
   *
   * @param model The model to be displayed
   */
  public GuiViewFrame(IMusicEditorModel model) {
    this.displayPanel = new ConcreteGuiViewPanel(model);
    this.model = model;
    ArrayList<Integer> fake = new ArrayList();
    fake.add(1);
    fake.add(4);
    fake.add(20);
    concrete = new ConcreteGuiViewPanel(this.model);
    piano = new PianoView(concrete);
    scrollPane = new JScrollPane(displayPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
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
  public void initialize(){
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(1200, 1200);
  }

  @Override
  public void display() {
    initialize();
    displayPanel.repaint();
  }
}
