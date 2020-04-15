import javafx.scene.canvas.Canvas;

public class NormalPile extends Pile {

  public NormalPile() {
    setPileType(PileType.Normal);
  }

  public int willMove(Pile p) {
    if (this == p) {
      return -1;
    }

    // newCard is where topCard will be moving to
    Card newCard;
    // topCard is the card the is moving to the new stack
    Card topCard;


    System.out.println("We're in willMove Normal");
    for (int i = this.cards.size() - 1; i >= 0; i--) {
      System.out.println(i);
      topCard = this.cards.get(i);

      // System.out.println("topCard rank? " + topCard.rank);
      if (p.cards.isEmpty() && topCard.rank == 13) {
        return i;
        // newCard = p.cards.get(p.cards.size() - 1);
      } else if (p.cards.isEmpty() && topCard.rank != 13) {
        System.out.println("Is p.cards empty?: " + p.cards.isEmpty());
        System.out.println("This is a lot of damage");
        // return -1;
        continue;
      }
      newCard = p.peekTop();
      // System.out.println("Top: " + topCard.toString());
      // System.out.println("New:" + newCard.toString());
      if (!topCard.face) {
        return -1;
      }

      if (topCard.black ^ newCard.black) {
        if (topCard.rank + 1 == newCard.rank) {
          // System.out.println("CAN MOVE TRUE");
          return i;
        }
      }
    }
    return -1;
  }


  public Canvas toNode(int offset) {
    Canvas canvas = new Canvas(100, 500);

    if (cards.size() == 0) {
      base.drawEmptyOnCanvas(canvas, 0, 0);
      return canvas;
    }

    for (int i = 0; i < cards.size(); i++) {
      // canvas.drawImage(c.toImage(), 0, 0);
      if (this.selected) {

        cards.get(i).drawEmptyOnCanvas(canvas, 0, i * offset);
        // System.out.println("Selected");
      } else {
        cards.get(i).drawOnCanvas(canvas, 0, i * offset);
      }

    }

    return canvas;
  }
}