
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
    // if (p.cards.size() > 1) return false;
    newCard = p.peekTop();
    // System.out.println("New:" + newCard.toString());
    if (cards.isEmpty() && newCard.rank == 1) {
      System.out.println("If this.cards is empty & new rank == 1");
      suitFilter = newCard.suit;
      // test
      // return this.cards.size() - 1;
      return 1;
    }

    if (suitFilter != newCard.suit) {
      return -1;
    }
    topCard = cards.get(cards.size() - 1);
    // System.out.println("Top: " + topCard.toString());
    if (topCard.rank == newCard.rank - 1) {
      System.out.println("If the toprank == newCard rank");
      // test
      // return this.cards.size() - 1;
      return 1;
    }
    return -1;
  }
}