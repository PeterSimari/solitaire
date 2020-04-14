import java.util.*;
import javafx.scene.canvas.Canvas;

public class Pile {

  ArrayList<Card> cards;
  PileType type;
  Card base;
  Card.Suit suitFilter;
  Pile parent;

  boolean selected;

  enum PileType {
    Normal, Draw, Get, Final;
  }

  public Pile() {
    cards = new ArrayList<Card>();
    base = new Card(2, Card.Suit.SPADES);
    // type = PileType.Normal;
  }

  public String toString() {
    String whole = "";
    for(Card lawn : cards) {
      // System.out.println("Why");
      // whole += lawn.toImage();
      whole += lawn.toString();
    }
    return whole;
  }

  // Draw each card in here
  // Return the canvas itself
  // Call this for each pile in their respected areas.

  public Canvas toNode(int offset) {
    Canvas canvas = new Canvas(100, 300); 

    if (cards.size() == 0) {
      base.drawEmptyOnCanvas(canvas, 0, 0);
      return canvas;
    }

    for(int i = 0; i < cards.size(); i++) {
      // canvas.drawImage(c.toImage(), 0, 0);
      if (this.selected) {

        cards.get(i).drawEmptyOnCanvas(canvas, 0, i * offset);
        // System.out.println("Selected");
      } else {
        cards.get(i).drawOnCanvas(canvas, 0, i*offset);
      }
      
    }
    
    return canvas;
  }

  public void setPileType(PileType a) {
    type = a;
  }

  public void addCard(Card c) {
    cards.add(c);
  }

  public void removeCard(Card c) {
    cards.remove(c);
  }

  public ArrayList<Card> getCards() {
    return this.cards;
  }

  public Card peekTop() {
    return cards.get(cards.size() -1);
  }

  public Card peekBottom() {
    return cards.get(0);
  }

  public Card drawCard() {
    Card c = cards.get(0);
    removeCard(c);
    return c;
  }

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

  public int willMove(Pile p) {
    System.out.println("Fuckin beans");
    return -1;
  }

  public Card searchCard(int rank, String suitName) {
    for(Card c : cards) {
      if(c.rank == rank && c.suit.name().equals(suitName)) {
        return c;
      }
    }

    return null;
  }

  public boolean isEmpty() {
    return cards.size() == 0;
  }
  
  public static void main(String[] args) {

  }

}