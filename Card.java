import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;

/**
  * A Card object that holds it's Suit and rank.
 */
public class Card extends Parent {

  /**
   * Suit enum to easily track the suit of each card
   */
  public enum Suit {
    HEARTS, SPADES, CLUBS, DIAMONDS;
  }

  final private static int minRank = 1;
  final private static int maxRank = 13;

  /**
   * Uses the enum to give each card a specific Suit
   */
  public Suit suit;
  /**
   * 1 to 13 for what the card is. 1 becomes and Ace, 11 = Jack, 12 = Queen, and 13 = King
   */
  public int rank;
  
  boolean black;
  boolean face;

  /**
   * Back is the image that is on the back of each card
   * It is a field because we can rewrite this when selecting a new back through the GUI
   */
  public String back = "/art/back.png";
  /**
   * Set just for the back as well
   * Creating an image with set dimensions to add the back to so I don't have to do any real resizing.
   */
  public Image b = new Image(back, 79, 124, false, false);

  /**
   * Creates the Card object itself
   * @param rank Sets the Rank of the card
   * @param suit The suit of the card, Hearts, Spades, Clubs, or Diamonds. Made with the Suit Enum
   */
  public Card(int rank, Suit suit) {
    setRank(rank);
    setSuit(suit);
    if (suit == Suit.SPADES || suit == Suit.CLUBS) {
      black = true;
    } else {
      black = false;
    }
  }

  /**
   * The toString helps read what cards are being selected in the GUI through print statements
   */
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
    return rankSet + " of " + this.suit;
  }

  /**
   * toImage creates the path to draw each card
   */
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
    Image finished = new Image("/art/" + rankSet);
    return finished;
  }


  /**
   * Allows us to draw the empty card on the canvas.
   * @param canvas The canvas it will draw to gets passed through here
   * @param x x position
   * @param y y position - used to create an offset
   */
  public void drawEmptyOnCanvas(Canvas canvas, int x, int y) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Image empty = new Image("/art/empty.png");
    gc.drawImage(empty, x, y);
    return;
  }

  /**
   * Allows us to draw the actual card on the canvas.
   * @param canvas The canvas it will draw to gets passed through here
   * @param x      x position
   * @param y      y position - used to create an offset
   */
  public void drawOnCanvas(Canvas canvas, int x, int y) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    if (this.face == true) {
      gc.drawImage(this.toImage(), x, y);
    } else if (this.face != true) {
      gc.drawImage(b, x, y);
    }
    
  }

  /**
   * Allows us to draw the selected card on the canvas, so it is easy to determine
   * @param canvas The canvas it will draw to gets passed through here
   * @param x      x position
   * @param y      y position - used to create an offset
   */
  public void drawSelectOnCanvas(Canvas canvas, int x, int y) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    if (this.face == true) {
      ColorAdjust color = new ColorAdjust();
      color.setBrightness(-.5);
      gc.setEffect(color);
      gc.drawImage(this.toImage(), x, y);
    } else if (this.face != true) {
      gc.drawImage(b, x, y);
    }

  }
  
  /**
   * Allows you to check if the cards are equal to each other.
   * @param check the card that you are checking against the current one.
   */
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

  /**
   * Sets the suit of the card
   * @param suit the Suit you would like the card to be.
   */
  public void setSuit(Suit suit) {
    if (suit == null) {
      throw new RuntimeException("Suit cannot be null :(");
    }
    this.suit = suit;
  }

  /**
   * Returns the suit of the current card
   * @return What the Suit is
   */
  public Suit getSuit() {
    return this.suit;
  }

  /**
   * Shows all the options for the Suit enum
   * @return a list of all the possible Suits
   */
  public static Suit[] getSuits() {
    return Suit.values();
  }

  /**
   * Sets the rank of the card, checks to see if it is too high or too low as well.
   * @param rank Put in the rank and it will set it.
   */
  public void setRank(int rank) {
    if (rank > getMaxRank() || rank < getMinRank()) {
      throw new RuntimeException("Rank is either too high or too low.");
    }
    this.rank = rank;
  }

  /**
   * Returns the rank of the current card
   * @return the rank of the current card
   */
  public int getRank() {
    return this.rank;
  }

  /**
   * Checks the max rank for the card. Always returns 13
   */
  public static int getMaxRank() {
    return maxRank;
  }

  /**
   * Checks the max rank for the card. Always returns 1
   */
  public static int getMinRank() {
    return minRank;
  }

  /**
   * Makes it so the cards face value is true.
   * Allows you to flip cards in the GUI
   */
  public void show() {
    face = true;
  }

  /**
   * Opposite of show()
   * For hiding cards when drawing, or even pulling from the draw deck once it's empty.
   */
  public void hide() {
    face = false;
  }

  /**
   * Sets the image on the back of the card.
   * @param url The for where the file is.
   */
  public void setBack(String url) {
    this.back = url;
    this.b = new Image(url, 79, 124, false, false);
  }

  /**
   * Made to see where the back image is being set.
   * @return The URL for what image is selected to be drawn on back.
   */
  public String getBack() {
    return this.back;
  }

}