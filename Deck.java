import java.util.*;

/**
 * The Deck class, it creates a deck of 52 cards and randomly shuffles them.
 * Used to create the piles for the Klondike Game
 */
public class Deck {
  ArrayList<Card> deck;

  /**
   * Constructs the deck of cards and shuffles them.
   */
  public Deck() {
    ArrayList<Card> deck = new ArrayList<Card>();

    for(Card.Suit suit : Card.getSuits()) {
      for (int i = Card.getMinRank(); i <= Card.getMaxRank(); i++) {
        deck.add(new Card(i, suit));
      }
    }
    setDeck(deck);
    shuffle(200);
    setDeck(deck);
  }

  /**
   * Constructs the deck of cards to whatever you would like it to be.
   * @param set The ArrayList of Cards you would like to set the deck to be.
   */
  public Deck(ArrayList<Card> set) {
    if (set.size() == 52) {
      setDeck(set);
    }
  }

  /**
   * Easier to debug with a toString showing each card in the deck
   */
  public String toString() {
    String list = "";
    for(Card card : this.deck) {
      list += card.toString() + "\n";
    }
    return list;
  }

  /**
   * Sets the decks.
   * @param decks ArrayList of Cards to set the deck to.
   */
  public void setDeck(ArrayList<Card> decks) {
    this.deck = decks;
  }

  /**
   * Returns the ArrayList of Cards the deck is holding.
   */
  public ArrayList<Card> getDeck() {
    return this.deck;
  }

  /**
   * Size of the deck.
   * @return How many cards are in the deck.
   */
  public int size() {
    return this.deck.size();
  }

  /**
   * Shuffles the deck for however many cards are in the deck, once.
   */
  public void shuffle() {
    for (int i = 0; i < deck.size(); i++) {
      Random random = new Random();
      Collections.swap(this.deck, i, random.nextInt(deck.size()));
    }
  }

  /**
   * Shuffles the deck for however many times you would like to shuffle the deck.
   * @param shuffles How many times you would like to shuffle the deck.
   */
  public void shuffle(int shuffles) {
    for (int i = 1; i <= shuffles; i++) {
      shuffle();
    }
  }

  /**
   * Gets the 0th card off the top of the deck.
   * @return The card off the top of the deck.
   */
  public Card drawCard() {
    Card c = deck.get(0);
    deck.remove(0);
    return c;
  }
}