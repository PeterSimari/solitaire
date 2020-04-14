import java.util.*;

public class Deck {
  ArrayList<Card> deck;

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

  public Deck(ArrayList<Card> set) {
    if (set.size() == 52) {
      setDeck(set);
    }
  }

  public String toString() {
    String list = "";
    for(Card card : this.deck) {
      list += card.toString() + "\n";
    }
    return list;
  }

  public void setDeck(ArrayList<Card> decks) {
    this.deck = decks;
  }

  public ArrayList<Card> getDeck() {
    return this.deck;
  }

  public int size() {
    return this.deck.size();
  }

  public void shuffle() {
    for (int i = 0; i < deck.size(); i++) {
      Random random = new Random();
      Collections.swap(this.deck, i, random.nextInt(deck.size()));
    }
  }

  public void shuffle(int shuffles) {
    for (int i = 1; i <= shuffles; i++) {
      shuffle();
    }
  }

  public Card drawCard() {
    Card c = deck.get(0);
    deck.remove(0);
    return c;
  }

  public static void main(String[] args) {
    Deck jawn = new Deck();
    System.out.println(jawn);
  }
}