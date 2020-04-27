/**
 * State pattern to determine what theme is currently active.
 */
public interface ThemeView {

  /**
   * Updates what theme is being used. Different for each theme java class.
   */
  public void handleTheme();

}
