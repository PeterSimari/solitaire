import javafx.scene.canvas.Canvas;

/**
 * Get pile denotes the pile that is to the left of drawPile.
 * The cards that are pulled from the extra deck.
 */
public class GetPile extends Pile {

  /**
   * Creates a regular pile, but setting this pile type to Get.
   */
  public GetPile() {
    setPileType(PileType.Get);
  }

  /**
   * Overridden willMove function.
   * Checks to see if the piles will move from GetPile to Normal/Final Piles.
   * If the other way around, return -1.
   * @param p The pile that you are trying to move
   * @return The number of cards in the pile that could move from this pile to pile p.
   */
  @Override
  public int willMove(Pile p) {
    if (this == p) {
      return -1;
    }
    // newCard is where topCard will be moving to
    Card newCard;
    // topCard is the card the is moving to the new stack
    Card topCard;
    topCard = this.cards.get(this.cards.size()-1);
    // System.out.println("We're in willMove Get"); // Debugging purposes
    // System.out.println("This.piletype = " + this.type.toString()); // Debugging purposes
    // System.out.println("p.piletype = " + p.type.toString()); // Debugging purposes
    if (p.cards.isEmpty() && topCard.rank == 13 && p.type == PileType.Normal) {
      return this.cards.size()-1;
    } else if (p.cards.isEmpty() && topCard.rank != 13 && p.type == PileType.Normal) {
      return -1;
    } else if (p.cards.isEmpty() && topCard.rank != 1 && p.type == PileType.Final) {
      return -1;
    } else if (p.cards.isEmpty() && topCard.rank == 1 && p.type == PileType.Final) {
      return this.cards.size() - 1;
    }

    newCard = p.peekTop();
    if (p.type == PileType.Final || this.type == PileType.Final) {
      if (topCard.rank - 1 == newCard.rank && topCard.suit == newCard.suit) {
        // System.out.println("P.type = " + p.type); // Debugging purposes
        // System.out.println("this.type = " + this.type); // Debugging purposes
        // System.out.println("If the toprank == newCard rank"); // Debugging purposes
        return 1;
      }
    }
    if (!topCard.face) {
      return -1;
    }

    if (topCard.black ^ newCard.black) {
      if (topCard.rank + 1 == newCard.rank) {
        return this.cards.size()-1;
      }
    }
    return -1;
  }

  /**
   * Draws the getPile with 0 offset.
   */
  @Override
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