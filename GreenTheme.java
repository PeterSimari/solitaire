import javafx.scene.Scene;

public class GreenTheme implements ThemeView {

  Scene pane;

  String theme = "./GreenTheme.css";

  public GreenTheme(Scene pane) {
    this.pane = pane;
  }

  @Override
  public void handleTheme() {
    pane.getStylesheets().clear();
    pane.getStylesheets().add(getClass().getResource(this.theme).toExternalForm());
  }

  public String toString() {
    return "Green";
  }

}
