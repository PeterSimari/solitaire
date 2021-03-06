import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Pos;

/**
 * GUI class builds the GUI
 * Allows you to select specific cards
 * Change themes, edit the back of each card
 */
public class GUI extends Application {

  private int width = 1400;
  private int height = 800;

  
  private VBox main = new VBox(10);
  private HBox gap = new HBox(100);
  private HBox top = new HBox(10);
  private HBox normal = new HBox(10);
  private BorderPane window = new BorderPane();

  private VBox left = new VBox(10);
  HBox score = new HBox(10);
  private HBox time = new HBox(50);
  private HBox moves = new HBox(10);

  private ThemeView theme;
  ArrayList<ThemeView> choices;

  Scene base;
  Stage chooserStage;

  private int move = 0;

  Klondike game;

  private void setStyles() {
    left.getStyleClass().add("left");
    main.getStyleClass().add("main");

  }

  /**
   * Initializes the GUI with the stage.
   * Creates the game, adds all of the VBoxes and HBoxes into each other
   * It then builds the game itself .
   * Also updates the timer object and moves on the GUI's left hand side.
   * Sets the theme of the GUI as well.
   */
  public void start(Stage primaryStage) {
    setStyles();
    game = new Klondike();

    left.setPrefWidth(150);
    gap.setPrefHeight(30);

    createMenu();
    drawGame();
    top.setAlignment(Pos.CENTER);
    normal.setAlignment(Pos.CENTER);
    
    window.setCenter(main);
    window.setLeft(left);

    main.getChildren().addAll(gap, top, normal);
    
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(.2), event -> {
      time.getChildren().clear();
      final Label timey = new Label(game.timer.toString());
      time.setAlignment(Pos.CENTER);
      time.getChildren().addAll(timey);
      moves.getChildren().clear();
      Label mover = new Label("Moves: " + move);
      moves.setAlignment(Pos.CENTER);
      moves.getChildren().addAll(mover);
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();

    left.getChildren().addAll(time, moves);

    base = new Scene(window, width, height);
    theme = new GreenTheme(base);

    theme.handleTheme();
    
    primaryStage.setTitle("Solitaire");
    primaryStage.setScene(base);
    primaryStage.show();
    primaryStage.setResizable(false);
  }

  /**
   * Draws the game.
   * Creates canvases for each Pile from the allPiles field from Klondike class/game field here.
   * Also initializes what happens when you click on each pile as well.
   * This is where willMove is ran.
   */
  public void drawGame() {
    for (Pile source : game.finalPiles) {
      Canvas mawn = new Canvas(80, 124);
      mawn = source.toNode(0);
      mawn.setOnMouseClicked(event -> {
        if (game.selecter != null) {
          if (game.selecter.willMove(source) >= 0) {
            move++;
            source.merge(game.selecter.split(game.selecter.peekTop()));
            game.selecter.selected = false;
            game.selecter = null;
            refresh();
            winCheck();
          }
        } else {
          if (source.isEmpty()) {
            refresh();
          } else {
            game.selecter = source;
            game.selecter.selected = true;
            refresh();
          }
        }
        refresh();
      });
      top.getChildren().addAll(mawn);
    }

    Canvas empty = new Canvas(100, 124);
    top.getChildren().addAll(empty);

    Canvas get = new Canvas(80, 124);
    get = game.getPile.toNode(0);
    get.setOnMouseClicked(event -> {
      if (game.selecter == null) {
        if (game.getPile.isEmpty()) {
          refresh();
        } else {
          game.selecter = game.getPile;
          game.selecter.selected = true;
          refresh();
          winCheck();
        }
      } else if (game.getPile.isEmpty()) {
        refresh();
      } else {
        game.selecter.selected = false;
        game.selecter = game.getPile;
        game.selecter.selected = true;
        refresh();
      }
      refresh();
    });
    top.getChildren().addAll(get);
    

    Canvas draw = new Canvas(80, 124);
    draw = game.drawPile.toNode(0);
    draw.setOnMouseClicked(event -> {
      if (game.drawPile.isEmpty() && game.getPile.isEmpty()) {
      } else {
        move++;
        game.drawCard();
        if (game.selecter != null) { 
          game.selecter.selected = false;
          game.selecter = null;
        }
      }
      refresh();
    });
    top.getChildren().addAll(draw);

    for (Pile normalPile : game.piles) {
      Canvas mawn = normalPile.toNode(25);
      mawn.setOnMouseClicked(event -> {
        if (!normalPile.isEmpty()) {
          if (!normalPile.peekTop().face) {
            normalPile.peekTop().show();
            refresh();
            return;
          }
        }
        if (game.selecter != null) {
          int splitter = game.selecter.willMove(normalPile);
          if (splitter > -1) {
            move++;
            normalPile.merge(game.selecter.split(game.selecter.getCards().get(splitter)));
            game.selecter.selected = false;
            game.selecter = null;
            refresh();
            winCheck();
          } else {
            game.selecter.selected = false;
            game.selecter = null;
          }
        } else {
          if (normalPile.isEmpty()) {
            refresh();
          } else {
            game.selecter = normalPile;
            game.selecter.selected = true;
            refresh();
          }
        }
        refresh();

      });
      normal.getChildren().addAll(mawn);
    }
  }

  /**
   * Refreshes the game board.
   * It clears what was there, and now draws the current fields from game.
   */
  private void refresh() {
    clearBoard();
    drawGame();
  }

  /**
   * Clears the entire main board of cards.
   */
  private void clearBoard() {
    top.getChildren().clear();
    normal.getChildren().clear();
  }

  /**
   * Sets the game field to a new game. Sets up a whole new game.
   */
  private void newGame() {
    move = 0;
    String beans = "/art/back.png";
    for (Pile name : game.allPiles) {
      if(!name.isEmpty()) {
        beans = name.getCards().get(0).getBack();
        break;
      }
    }
    this.game = new Klondike();
    this.game.setBacks(beans);
    refresh();
  }

  /**
   * Creates the menu bar at the top instead of having all of this in the Start function
   */
  private void createMenu() {
    MenuBar menuBar = new MenuBar();

    Menu reset = new Menu("New");
    MenuItem newGame = new MenuItem("New Game");
    reset.getItems().addAll(newGame);
    newGame.setOnAction(actionEvent -> {
      newGame();
    });

    Menu theme = new Menu("Themes");
    MenuItem chooseTheme = new MenuItem("Choose Themes");
    MenuItem changeBack = new MenuItem("Custom Card Backs");
    theme.getItems().addAll(chooseTheme, changeBack);
    
    chooseTheme.setOnAction(actionEvent -> {
      ArrayList<ThemeView> choices = new ArrayList<ThemeView>();
      choices.add(new GreenTheme(base));
      choices.add(new BlueTheme(base));
      choices.add(new RedTheme(base));
      ChoiceDialog<ThemeView> dialog = new ChoiceDialog<ThemeView>(choices.get(0), choices);
      dialog.setTitle("Choose a Theme!"); // setting the fields for dialog
      dialog.setHeaderText("Select a Theme by Color:");
      dialog.setContentText("Themes:");
      Optional<ThemeView> result = dialog.showAndWait();
      this.theme = result.get();
      this.theme.handleTheme();
    });

    changeBack.setOnAction(actionEvent -> {
      FileChooser fc = new FileChooser();
      File selectedFile = fc.showOpenDialog(chooserStage);
      if (selectedFile != null) {
        fc.setInitialDirectory(selectedFile.getParentFile());
        // System.out.println(selectedFile.toURI().toString()); //Debugging tool
        game.setBacks(selectedFile.toURI().toString());
        refresh();
      } else {
        System.out.println("Select something next time!");
        refresh();
      }
      
    });

    menuBar.getMenus().addAll(reset, theme);
    window.setTop(menuBar);
  }

  /**
   * Checks to see if you have won.
   * If you have, it will create a new Alert telling the player they won, and pausing the timer.
   * Prompts you with the option to play another game.
   */
  private void winCheck() {
    if (game.win()) {
      game.timer.pause();
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Congrats! You've won!");
      alert.setHeaderText("Good Job!\nTime: " + game.timer.toString()+ "\nMoves: " + move);
      alert.setContentText("Would you like to play again?");

      alert.setGraphic(new ImageView(("./art/a_spades.png")));

      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK) {
        newGame();
      } else {
        stop();
      }
    }
  }

  /**
   * Stops the game.
   */
  public void stop() {
    game.timer.stop();
  }
} 