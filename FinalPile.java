
public class FinalPile extends Pile{

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
    if (cards.isEmpty() && newCard.rank == 1) {
      System.out.println("If this.cards is empty & new rank == 1");
      suitFilter = newCard.suit;
      // test
      // return this.cards.size() - 1;
      return 1;
    }

    // if (suitFilter != newCard.suit) {
    //   System.out.println("Its the filter");
    //   return -1;
    // }
    topCard = cards.get(cards.size() - 1);
    // System.out.println("Top: " + topCard.toString());
    if (p.type == PileType.Normal) {
      if (!topCard.face) {
        return -1;
      }

      if (topCard.black ^ newCard.black) {
        if (topCard.rank + 1 == newCard.rank) {
          // System.out.println("CAN MOVE TRUE");
          return this.cards.size()-1;
        }
      }
    }
    if (topCard.rank == newCard.rank - 1 && topCard.suit == newCard.suit) {
      System.out.println("If the toprank == newCard rank");
      // test
      // return this.cards.size() - 1;
      return 1;
    }
    return -1;
  }
}