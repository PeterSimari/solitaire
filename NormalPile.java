import javafx.scene.canvas.Canvas;

/**
 * Normal pile denotes the piles that are in the center of the screen.
 * The cards that are mostly moved around.
 */
public class NormalPile extends Pile {

  /**
   * Creates a regular pile, but setting the pile type to Normal.
   */
  public NormalPile() {
    setPileType(PileType.Normal);
  }

  /**
   * Overridden willMove function
   * Checks to see if the piles could actually move.
   * @param p The pile that you are trying to move to.
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
    // System.out.println("We're in willMove Normal"); // Debugging purposes
    for (int i = this.cards.size() - 1; i >= 0; i--) {
      // System.out.println("i = " + i); // Debugging
      topCard = this.cards.get(i);
      // System.out.println("Top: " + topCard.toString()); // Debugging
      // System.out.println("topCard rank? " + topCard.rank); // Debugging
      if (p.cards.isEmpty() && topCard.rank == 13 && p.type == PileType.Normal && topCard.face) {
        return i;
      } else if (p.cards.isEmpty() && topCard.rank == 13 && p.type == PileType.Normal && !topCard.face) {
        return -1;
      } else if (p.cards.isEmpty() && topCard.rank == 1 && p.type == PileType.Final) {
        // System.out.println("Is p.cards empty?: " + p.cards.isEmpty()); // Debugging
        return i;
      } else if (p.cards.isEmpty() && topCard.rank != 1 && p.type == PileType.Final) {
        return -1;
      } else if (p.cards.isEmpty() && topCard.rank != 13 && p.type != PileType.Final) {
        continue;
      }
      newCard = p.peekTop();
      if (!topCard.face) {
        return -1;
      }
      if (p.type == PileType.Final || this.type == PileType.Final) {
        if (topCard.rank - 1 == newCard.rank && topCard.suit == newCard.suit) {
          // System.out.println("P.type = " + p.type); // Debugging
          // System.out.println("this.type = " + this.type); // Debugging
          // System.out.println("If the toprank == newCard rank"); // Debugging
          return 1;
        }
      }
      // System.out.println("Top: " + topCard.toString()); // Debugging
      // System.out.println("New:" + newCard.toString()); // Debugging

      if (topCard.black ^ newCard.black) {
        if (topCard.rank + 1 == newCard.rank) {
          if (p.type == PileType.Final || this.type == PileType.Final) {
            return -1;
          } else {
            // System.out.println("CAN MOVE TRUE"); // Debugging
            return i;
          }
        }
      }
    }
    // System.out.println("Last resort with Stan"); // Debugging
    return -1;
  }

  /**
   * Draws the normal piles with the offset.
   */
  @Override
  public Canvas toNode(int offset) {
    Canvas canvas = new Canvas(100, 500);

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