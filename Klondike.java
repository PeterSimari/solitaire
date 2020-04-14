import java.util.ArrayList;


public class Klondike {
  
  public Deck deck;
  ArrayList<NormalPile> piles;
  ArrayList<FinalPile> finalPiles;
  ArrayList<Pile> allPiles;
  Pile getPile;
  Pile drawPile;
  public final int pileNumber = 7;

  StopWatch timer = new StopWatch();
  
  public Klondike() {
    setCards();
    setup();
    timer.start();
  }

  public String toString() {
    String whole = "";
    for(Pile jawn : allPiles) {
      whole += jawn.toString() + "\n";
    }
    return whole;
  }

  public void setCards() {
    deck = new Deck();

    drawPile = new DrawPile();
    getPile = new GetPile();

    finalPiles = new ArrayList<FinalPile>();
    piles = new ArrayList<NormalPile>();
    allPiles = new ArrayList<Pile>();
    allPiles.add(drawPile);
    allPiles.add(getPile);
  }

  public void setup() {
    // drawPile.setPileType(Pile.PileType.Draw);
    // getPile.setPileType(Pile.PileType.Get);

    for (int i = 1; i <= pileNumber; i++) {
      NormalPile p = new NormalPile();

      for(int j = 1; j <= i; j++) {
        Card card = deck.drawCard();
        p.addCard(card);
        if(j==i) {
          card.show();
        } else {
          card.hide();
        }
      }
      piles.add(p);
      allPiles.add(p);
    }

    for(Card.Suit suit : Card.Suit.values()) {
      FinalPile p = new FinalPile();
      finalPiles.add(p);
      allPiles.add(p);
    }

    while(deck.size() > 0) {
      Card card = deck.drawCard();
      card.hide();
      drawPile.addCard(card);
    }
  }

  public void drawCard() {
    if(drawPile.cards.isEmpty() == false) {
      Card draw = drawPile.drawCard();
      draw.show();
      getPile.addCard(draw);
      System.out.println("tay keith, draw");
    } else {
      turnGet();
    }
  }

  public void nextGet() {
    if(drawPile.cards.isEmpty() == false) {
      return;
    }
    while(getPile.cards.isEmpty() == false) {
      Card c = getPile.drawCard();
      c.hide();
      drawPile.addCard(c);
    }
  }

  public void turnGet() {
    if(drawPile.cards.isEmpty() == false) {
      return;
    }
    while(getPile.cards.isEmpty() == false) {
      Card c = getPile.drawCard();
      c.hide();
      drawPile.addCard(c);
    }
  }





  // public void clickMove(Card move) {
  //   int rank = move.getRank();
  //   Card.Suit suit = move.getSuit();
    

  //   for(Pile all : piles) {
  //     if (move.black) {
  //       Card diamond = all.searchCard(rank + 1, "DIAMONDS");
  //       if (diamond.face == true) {
  //         System.out.println(diamond);
  //         return;
  //       }
  //       Card heart = all.searchCard(rank + 1, "HEARTS");
  //       if (heart.face) {
  //         System.out.println(heart);
  //         return;
  //       }
  //     }
  //     // Card mover = all.searchCard(rank + 1, suit.toString());
      
      
      
  //   }
  // }

  public boolean win() {
    for (Pile p : finalPiles) {
      if(p.cards.size() < 13) {
        return false;
      }
    }
    timer.stop();
    return true;
  }

  public static void main(String[] args) {
    Klondike game = new Klondike();
    System.out.println(game);
    game.drawCard();
    System.out.println(game);
  }
}