import javafx.scene.canvas.Canvas;

/**
 * Draw pile denotes the pile that is in the top right of the screen.
 * The cards you pull from to add to the GetPile.
 */
public class DrawPile extends Pile {

  /**
   * Creates a regular pile, but setting the pile type to Draw.
   */
  public DrawPile() {
    setPileType(PileType.Draw);
  }

  /**
   * Draws the drawPile without an offset.
   */
  public Canvas toNode(int offset) {
    Canvas canvas = new Canvas(100, 150);

    if (cards.size() == 0) {
      base.drawEmptyOnCanvas(canvas, 0, 0);
      return canvas;
    }

    for (int i = 0; i < cards.size(); i++) {
      if (this.selected) {
        cards.get(i).drawSelectOnCanvas(canvas, 0, i * offset);
      } else {
        cards.get(i).drawOnCanvas(canvas, 0, i * offset);
      }
    }
    return canvas;
  }
}