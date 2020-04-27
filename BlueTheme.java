import javafx.scene.Scene;

public class BlueTheme implements ThemeView {

  Scene pane;

  String theme = "./BlueTheme.css";

  public BlueTheme(Scene pane) {
    this.pane = pane;
  }

  @Override
  public void handleTheme() {
    pane.getStylesheets().clear();
    pane.getStylesheets().add(getClass().getResource(this.theme).toExternalForm());
  }

  public String toString() {
    return "Blue";
  }

}
