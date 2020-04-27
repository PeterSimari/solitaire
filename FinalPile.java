import javafx.scene.canvas.Canvas;

/**
 * Final pile denotes the piles that are along the top of the screen.
 * The final destination for each card in the deck.
 */
public class FinalPile extends Pile {

  /**
   * Creates a regular pile but sets the pile type to Final.
   */
  public FinalPile() {
    setPileType(PileType.Final);
  }

  /**
   * Overridden willMove function
   * Checks to see if the top card of the selected pile could come to this Final Pile.
   * @param p The pile that you are tying to move to (This final pile)
   * @return whteher or not one card could move to this final pile.
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
    // System.out.println("We're in willMove Final"); // Debugging purposes
    newCard = p.peekTop();
    // System.out.println("P.type = " + p.type); // Debugging purposes
    // System.out.println("this.type = " + this.type); // Debugging purposes
    if (cards.isEmpty() && newCard.rank == 1) {
      // System.out.println("If this.cards is empty & new rank == 1"); // Debugging purposes
      suitFilter = newCard.suit;
      return 1;
    } else if (p.cards.isEmpty() && newCard.rank == 13 && p.type == PileType.Normal) {
      return this.cards.size() - 1;
    } else if (cards.isEmpty() && newCard.rank != 13 && p.type == PileType.Normal) {
      return -1;
    }
    topCard = cards.get(cards.size() - 1);
    if (p.type == PileType.Normal) {
      if (!topCard.face) {
        return -1;
      }
      if (topCard.black ^ newCard.black) {
        if (topCard.rank + 1 == newCard.rank) {
          // System.out.println("This.piletype = " + this.type.toString()); // Debugging purposes
          // System.out.println("p.piletype = " + p.type.toString()); // Debugging purposes
          if (p.type == PileType.Final) {
            return -1;
          } else {
            return this.cards.size() - 1;
          }
        }
      }
    }
    // System.out.println("This is before the check"); // Debugging purposes
    if (topCard.rank == newCard.rank - 1 && topCard.suit == newCard.suit && p.type != PileType.Normal && this.type != PileType.Normal) {
      // System.out.println("P.type = " + p.type); // Debugging purposes
      // System.out.println("this.type = " + this.type ); // Debugging purposes
      // System.out.println("If the toprank == newCard rank"); // Debugging purposes
      return 1;
    }
    return -1;
  }

  /**
   * Draws the Final Piles with no offset. It is still given the option, though.
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