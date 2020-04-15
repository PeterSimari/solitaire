
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
      return 1;
    }

    topCard = cards.get(cards.size() - 1);
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
    System.out.println("This is before the check");
    if (topCard.rank == newCard.rank - 1 && topCard.suit == newCard.suit && this.type != PileType.Normal) {
      System.out.println("P.type = " + p.type);
      System.out.println("this.type = " + this.type );
      System.out.println("If the toprank == newCard rank");
      return 1;
    }
    return -1;
  }
}