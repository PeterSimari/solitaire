import javafx.scene.Scene;

/**
 * Greentheme sets the background to a green base.
 * Also the default theme
 * Inherits from themeview, essentially polymorphism.
 */
public class GreenTheme implements ThemeView {

  Scene pane;
  String theme = "./GreenTheme.css";

  /**
   * Initializes the green theme by setting the pane with this theme.
   */
  public GreenTheme(Scene pane) {
    this.pane = pane;
  }

  /**
   * Overridden from the ThemeView class. Erases the stylesheet at hand and sets it to greenTheme.
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
    return "Green";
  }

}
