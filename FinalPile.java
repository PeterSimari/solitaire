import javafx.scene.canvas.Canvas;

public class FinalPile extends Pile {

  public FinalPile() {
    setPileType(PileType.Final);
  }
  

  public int willMove(Pile p) {
    if (this == p) {
      return -1;
    }

    // newCard is where topCard will be moving to
    Card newCard;
    // topCard is the card the is moving to the new stack
    Card topCard;

    System.out.println("We're in willMove Final");
    newCard = p.peekTop();
    System.out.println("P.type = " + p.type);
    System.out.println("this.type = " + this.type);
    if (cards.isEmpty() && newCard.rank == 1) {
      System.out.println("If this.cards is empty & new rank == 1");
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
          System.out.println("This.piletype = " + this.type.toString());
          System.out.println("p.piletype = " + p.type.toString());
          if (p.type == PileType.Final) {
            return -1;
          } else {
            return this.cards.size() - 1;
          }
          // System.out.println("CAN MOVE TRUE");
        }
      }
    }
    System.out.println("This is before the check");
    // System.out.println("This is to check the suits: \nTopcard: " + topCard.suit + "\nnewCard: " + newCard.suit);
    if (topCard.rank == newCard.rank - 1 && topCard.suit == newCard.suit && p.type != PileType.Normal && this.type != PileType.Normal) {
      System.out.println("P.type = " + p.type);
      System.out.println("this.type = " + this.type );
      System.out.println("If the toprank == newCard rank");
      return 1;
    }
    return -1;
  }

  public Canvas toNode(int offset) {
    Canvas canvas = new Canvas(100, 150);

    if (cards.size() == 0) {
      base.drawEmptyOnCanvas(canvas, 0, 0);
      return canvas;
    }

    for (int i = 0; i < cards.size(); i++) {
      // canvas.drawImage(c.toImage(), 0, 0);
      if (this.selected) {

        cards.get(i).drawSelectOnCanvas(canvas, 0, i * offset);
        // System.out.println("Selected");
      } else {
        cards.get(i).drawOnCanvas(canvas, 0, i * offset);
      }

    }

    return canvas;
  }
}