import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Card extends Parent {

  public enum Suit {
    HEARTS, SPADES, CLUBS, DIAMONDS;
  }

  final private static int minRank = 1;
  final private static int maxRank = 13;

  public Suit suit;
  public int rank;
  
  boolean black;
  boolean face;


  public Card(int rank, Suit suit) {
    setRank(rank);
    setSuit(suit);
    if (suit == Suit.SPADES || suit == Suit.CLUBS) {
      black = true;
    } else {
      black = false;
    }
  }

  public String toString() {
    String rankSet = "";
    if (rank < 11 && rank > 1) {
      rankSet = "" + rank;
    }
    if (rank == 1) {
      rankSet = "A";
    } else if (rank == 11) {
      rankSet = "J";
    } else if (rank == 12) {
      rankSet = "Q";
    } else if (rank == 13) {
      rankSet = "K";
    }
    // return "Rank: " + rankSet + ", Suit: " + getSuit();
    return rankSet + " of " + this.suit;
  }

  public Image toImage() {
    String rankSet = "";
    if (rank < 11 && rank > 1) {
      rankSet = "" + rank;
    }
    if (rank == 1) {
      rankSet = "a";
    } else if (rank == 11) {
      rankSet = "j";
    } else if (rank == 12) {
      rankSet = "q";
    } else if (rank == 13) {
      rankSet = "k";
    }
    String rank =  "_" + this.suit;
    rankSet += rank.toLowerCase() + ".png";
    // System.out.println("/art/" + rankSet);
    Image lawn = new Image("/art/" + rankSet);
    return lawn;
    
  }


  // figure out how to use a Canvas
  // Draw the card on that canvas. Call this in the GUI class.
  // The actual Canvas itself
  public void drawEmptyOnCanvas(Canvas canvas, int x, int y) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Image empty = new Image("/art/empty.png");
    gc.drawImage(empty, x, y);
    return;
  }
  public void drawOnCanvas(Canvas canvas, int x, int y) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    if (this.face == true) {
      gc.drawImage(this.toImage(), x, y);
    } else if (this.face != true) {
      Image rawn = new Image("/art/back.png");
      gc.drawImage(rawn, x, y);
    }
    
    canvas.setOnMouseClicked(event -> {
      // if (rawn.peekTop()) {

      // }
      // System.out.println(this);
    });
    
  }

  public boolean equals(Object check) {
    if (!(check instanceof Card)) {
      return false;
    }
    if (check == this) {
      return true;
    }

    Card ron = (Card)check;
    return ron.getRank() == getRank() && ron.getSuit() == getSuit();
  }

  public void setSuit(Suit suit) {
    if (suit == null) {
      throw new RuntimeException("Suit cannot be null :(");
    }
    this.suit = suit;
  }

  public Suit getSuit() {
    return this.suit;
  }

  public static Suit[] getSuits() {
    return Suit.values();
  }

  public void setRank(int rank) {
    if (rank > getMaxRank() || rank < getMinRank()) {
      throw new RuntimeException("Rank is either too high or too low.");
    }
    this.rank = rank;
  }

  public int getRank() {
    return this.rank;
  }

  public static int getMaxRank() {
    return maxRank;
  }

  public static int getMinRank() {
    return minRank;
  }

  public void show() {
    face = true;
  }

  public void hide() {
    face = false;
  }

  // public static void main(String[] args) {
  //   // Card jawn = new Card(13, Suit.CLUBS);
  //   Card hone = new Card(13, Suit.DIAMONDS);
  //   System.out.println(hone);
  //   // System.out.println(getSuits());
  // }
}