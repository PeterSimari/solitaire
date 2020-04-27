import java.util.*;
import javafx.scene.canvas.Canvas;

/**
 * Polymorphic Class
 * DrawPile, NormalPile, FinalPile, and GetPile all inherit from this class.
 */
public class Pile {

  ArrayList<Card> cards;
  PileType type;
  Card base;
  Card.Suit suitFilter;
  Pile parent;

  boolean selected;

  /**
   * Determines what type of pile it is. 
   * Helps with polymorphism.
   */
  enum PileType {
    Normal, Draw, Get, Final;
  }

  /**
   * Initializes the pile. with an empty arrayList and some base cards.
   * The base is there so we can draw empty cards.
   */
  public Pile() {
    cards = new ArrayList<Card>();
    base = new Card(2, Card.Suit.SPADES);
  }

  /**
   * toString to help read what cards are being added into each Pile.
   */
  public String toString() {
    String whole = "";
    for(Card c : cards) {
      whole += c.toString();
    }
    return whole;
  }

  /**
   * Base toNode class. Allows you to draw each pile.
   * Overwritten in each sub-class.
   * @param offset the offset for drawing cards under each other. How many pixels between cards
   */
  public Canvas toNode(int offset) {
    Canvas canvas = new Canvas(100, 300); 
    if (cards.size() == 0) {
      base.drawEmptyOnCanvas(canvas, 0, 0);
      return canvas;
    }

    for(int i = 0; i < cards.size(); i++) {
      if (this.selected) {
        cards.get(i).drawEmptyOnCanvas(canvas, 0, i * offset);
      } else {
        cards.get(i).drawOnCanvas(canvas, 0, i*offset);
      }
    }
    return canvas;
  }

  /**
   * Sets the pile type.
   * @param a The PileType object, whether it is Final, Normal, Draw, or Get.
   */
  public void setPileType(PileType a) {
    type = a;
  }

  /**
   * Adds a card to the pile.
   * @param c The card you are adding to the pile.
   */
  public void addCard(Card c) {
    cards.add(c);
  }

  /**
   * Removes a card from the pile.
   * @param c The Card you would want to remove.
   */
  public void removeCard(Card c) {
    cards.remove(c);
  }

  /**
   * All the cards in the pile
   * @return Returns an ArrayList of cards in the current Pile.
   */
  public ArrayList<Card> getCards() {
    return this.cards;
  }

  /**
   * If you want to peek to top of the Pile. Useful for the get or draw piles.
   * @return The Card on the top of the pile.
   */
  public Card peekTop() {
    return cards.get(cards.size() -1);
  }

  /**
   * Draws a specific card from the deck and removes it from the deck.
   * @return The card on the top of the deck.
   */
  public Card drawCard() {
    Card c = cards.get(0);
    removeCard(c);
    return c;
  }

  /**
   * Splitting cards from piles. If you want to move a pile from one to the other, you have to use this and merge.
   * @param first The card that is going to be split at. Could be the middle of the pile, the top, or the bottom.
   * @return The new pile that has been split from.
   */
  public Pile split(Card first) {
    Pile p = new Pile();
    for (int i = 0; i < cards.size(); i++) {
      if (cards.get(i) == first) {
        for(; i < cards.size();) {
          p.addCard(cards.get(i));
          removeCard(cards.get(i));
        }
      }
    }
    p.parent = this;
    return p;
  }

  /**
   * Merges two piles together.
   * @param p The pile you would like to merge with.
   */
  public void merge(Pile p) {
    if (!this.isEmpty()) {
      for (Card c : p.cards) {
        addCard(c);
      }
    } else {
      ArrayList<Card> merge = new ArrayList<Card>();
      for (Card c : p.cards) {
        merge.add(c);
      }
      this.cards = merge;
    }
  }

  /**
   * Whether or not a pile could merge with another.
   * @param p The pile you are comparing with.
   * @return The integer of how many cards could move. 
   * If it returns 0, it means the bottom of the deck, if it returns -1 it means none can move.
   */
  public int willMove(Pile p) {
    return -1;
  }

  /**
   * Lets you search for a card. This was built for the Neural network.
   * @param rank What rank you want to look for.
   * @param suitName What suit you are looking for.
   * @return The card if you can find it. Otherwise, it will return null when it can't find it.
   */
  public Card searchCard(int rank, String suitName) {
    for(Card c : cards) {
      if(c.rank == rank && c.suit.name().equals(suitName)) {
        return c;
      }
    }
    return null;
  }

  /**
   * Whether or not the pile has cards in it.
   * @return True or False if the pile has any cards in it.
   */
  public boolean isEmpty() {
    return cards.size() == 0;
  }

}