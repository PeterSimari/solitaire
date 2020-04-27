import javafx.scene.Scene;

/**
 * RedTheme sets the background to a red base.
 * Inherits from themeview, essentially polymorphism.
 */
public class RedTheme implements ThemeView {

  Scene pane;

  String theme = "./RedTheme.css";
 /**
   * Initializes the red theme by setting the pane with this theme.
   */ 
  public RedTheme(Scene pane) {
    this.pane = pane;
  }
  /**
   * Overridden from the ThemeView class. Erases the stylesheet at hand and sets it to redTheme.
   */
  @Override
  public void handleTheme() {
    pane.getStylesheets().clear();
    pane.getStylesheets().add(getClass().getResource(this.theme).toExternalForm());
  }

  /**
   * Just to have one for debugging purposes.
   * Also gives the name of the theme in the dropdown list.
   */
  public String toString() {
    return "Red";
  }

}
