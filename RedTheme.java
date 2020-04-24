import javafx.scene.Scene;

public class RedTheme implements ThemeView {

  Scene pane;

  String theme = "./RedTheme.css";

  public RedTheme(Scene pane) {
    this.pane = pane;
  }

  @Override
  public void handleTheme() {
    pane.getStylesheets().clear();
    pane.getStylesheets().add(getClass().getResource(this.theme).toExternalForm());
  }

  public String toString() {
    return "Red";
  }

}
