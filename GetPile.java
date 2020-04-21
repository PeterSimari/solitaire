import javafx.scene.canvas.Canvas;

public class GetPile extends Pile {

  public GetPile() {
    setPileType(PileType.Get);
  }

  public int willMove(Pile p) {
    if (this == p) {
      return -1;
    }

    // newCard is where topCard will be moving to
    Card newCard;
    // topCard is the card the is moving to the new stack
    Card topCard;

    topCard = this.cards.get(this.cards.size()-1);
    System.out.println("We're in willMove Get");
    System.out.println("This.piletype = " + this.type.toString());
    System.out.println("p.piletype = " + p.type.toString());
    
    if (p.cards.isEmpty() && topCard.rank == 13 && p.type == PileType.Normal) {
      return this.cards.size()-1;
    } else if (p.cards.isEmpty() && topCard.rank != 1 && p.type == PileType.Final) {
      return -1;
    } else if (p.cards.isEmpty() && topCard.rank == 1 && p.type == PileType.Final) {
      return this.cards.size() - 1;
    }
    // else if (p.cards.isEmpty() && topCard.rank != 13) {
    //   System.out.println("Is p.cards empty?: " + p.cards.isEmpty());
    //   System.out.println("This is a lot of damage");
    //   // return -1;
    // }

    newCard = p.peekTop();
    if (p.type == PileType.Final || this.type == PileType.Final) {
      if (topCard.rank - 1 == newCard.rank && topCard.suit == newCard.suit) {
        System.out.println("P.type = " + p.type);
        System.out.println("this.type = " + this.type);
        System.out.println("If the toprank == newCard rank");
        return 1;
      }
    }
    // System.out.println("Top: " + topCard.toString());
    // System.out.println("New:" + newCard.toString());
    if (!topCard.face) {
      return -1;
    }

    if (topCard.black ^ newCard.black) {
      if (topCard.rank + 1 == newCard.rank) {
        // System.out.println("CAN MOVE TRUE");
        return this.cards.size()-1;
      }
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