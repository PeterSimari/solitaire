import javafx.scene.Scene;

/**
 * BlueTheme sets the background to a blue base
 * Inherits from themeview, essentially polymorphism.
 */
public class BlueTheme implements ThemeView {

  Scene pane;
  String theme = "./BlueTheme.css";

  /**
   * Initializes the blue theme by setting the pane with this theme.
   */
  public BlueTheme(Scene pane) {
    this.pane = pane;
  }

  /**
   * Overridden from themeview class. Erases the stylesheet at hand and sets it to the blueTheme.
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
    return "Blue";
  }

}
