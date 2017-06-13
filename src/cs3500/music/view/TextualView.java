package cs3500.music.view;

import cs3500.music.model.IMusicEditorModel;

/**
 * Text view of the model to be printed to the console.
 */
public class TextualView implements IMusicEditorView {

  private IMusicEditorModel model;

  public TextualView(IMusicEditorModel model) {
    this.model = model;
  }

  @Override
  public void display() {
    System.out.print(model.getState());
  }
}
