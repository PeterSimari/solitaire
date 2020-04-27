import java.util.ArrayList;

/**
 * Klondike class structures how the Game is meant to be played.
 */
public class Klondike {
  
  /**
   * Deck creates all the cards to be pulled from.
   */
  public Deck deck;
  ArrayList<NormalPile> piles;
  ArrayList<FinalPile> finalPiles;
  ArrayList<Pile> allPiles;
  Pile getPile;
  Pile drawPile;
  /**
   * Selects how many normal piles are going to be drawn.
   * Always set to 7, but if you would like a solitaire with 8 piles, or 3 piles, you could have it.
   */
  public final int pileNumber = 7;

  /**
   * A single pile is selected at a time, this helps keep track.
   */
  Pile selecter;

  /**
   * A timer to tell how long you have been playing the game.
   */
  StopWatch timer = new StopWatch();
  
  /**
   * The constructor sets up the cards and starts the timer.
   */
  public Klondike() {
    setCards();
    setup();

    timer.start();
  }

  /**
   * Shows what all of the piles in the game are holding.
   */
  public String toString() {
    String whole = "";
    for(Pile jawn : allPiles) {
      whole += jawn.toString() + "\n";
    }
    return whole;
  }

  /**
   * Sets all of the cards from the Deck field.
   * This, in turn, emptie sthe deck field.
   */
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

  /**
   * Initializes all of the piles and adds them to the piles.
   */
  public void setup() {
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

  /**
   * Where the cards are drawn from the Draw Pile.
   */
  public void drawCard() {
    if(!drawPile.cards.isEmpty()) {
      Card draw = drawPile.drawCard();
      draw.show();
      getPile.addCard(draw);
    } else {
      turnGet();
    }
  }

  /**
   * Resets the Draw Pile.
   */
  public void turnGet() {
    if(!drawPile.cards.isEmpty()) {
      return;
    }
    while(!getPile.cards.isEmpty()) {
      Card c = getPile.drawCard();
      c.hide();
      drawPile.addCard(c);
    }
  }

  /**
   * Checks to see if you've won the game
   * Checks by seeing if each final Pile has 13 cards in each.
   */
  public boolean win() {
    for (Pile p : finalPiles) {
      if(p.cards.size() < 13) {
        return false;
      }
    }
    timer.stop();
    return true;
  }

  /**
   * Allows you to set the back of each card
   * @param url The URL selected to set the back for each card in the list.
   * Iterates through all piles, setting each card to have the same URL back
   */
  public void setBacks(String url) {
    for(Pile jawn : allPiles) {
      ArrayList<Card> iterate = jawn.getCards();
      for (Card back : iterate) {
        back.setBack(url);
      }
      jawn.cards = iterate;
    }
  }
}