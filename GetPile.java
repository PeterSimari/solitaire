
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
    if (p.cards.isEmpty() && topCard.rank == 13) {
      return this.cards.size()-1;
    } else if (p.cards.isEmpty() && topCard.rank != 13) {
      System.out.println("Is p.cards empty?: " + p.cards.isEmpty());
      System.out.println("This is a lot of damage");
      // return -1;
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
        return this.cards.size()-1;
      }
    }
    return -1;
  }
}