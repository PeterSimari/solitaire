import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;


public class GUI extends Application {

  private int width = 1400;
  private int height = 800;

  VBox main = new VBox(10);
  HBox top = new HBox(10);
  HBox normal = new HBox(10);
  BorderPane window = new BorderPane();

  VBox left = new VBox(10);
  HBox score = new HBox(10);
  HBox time = new HBox(50);
  HBox moves = new HBox(10);

  private int move = 0;

  Klondike game;
  Klondike undo;

  public void setStyles() {
    left.getStyleClass().add("left");
    main.getStyleClass().add("main");

  }


  public void start(Stage primaryStage) {
    setStyles();
    game = new Klondike();
    // game.timer.start();

    left.setPrefWidth(150);

    createMenu();
    drawGame();
    top.setAlignment(Pos.CENTER);
    normal.setAlignment(Pos.CENTER);
    
    window.setCenter(main);
    window.setLeft(left);

    main.getChildren().addAll(top, normal);
    // left.getChildren().addAll(score, time, moves);

    
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(.1), event -> {
      time.getChildren().clear();
      final Label timey = new Label(game.timer.toString());
      time.setAlignment(Pos.CENTER);


      time.getChildren().addAll(timey);
      // System.out.println("THIS IS WORKING");

      moves.getChildren().clear();
      Label mover = new Label("Moves: " + move);
      moves.setAlignment(Pos.CENTER);
      moves.getChildren().addAll(mover);
      
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();


    

    left.getChildren().addAll(time, moves);

    Scene base = new Scene(window, width, height);

    base.getStylesheets().add(getClass().getResource("./Style.css").toExternalForm());
    
    primaryStage.setTitle("Solitaire");
    primaryStage.setScene(base);
    primaryStage.show();
    primaryStage.setResizable(false);

  }

  public void drawGame() {
    for (Pile source : game.finalPiles) {
      Canvas mawn = new Canvas(80, 124);
      mawn = source.toNode(0);
      mawn.setOnMouseClicked(event -> {
        // Pile localSource = source;
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
          game.selecter = source;
          game.selecter.selected = true;
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
      if (game.selecter != null) {
        game.selecter = game.getPile;
        game.selecter.selected = true;
        refresh();
        winCheck();
      } else {
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

    // Check to see if a pile is selected, if not then set selected to be true 
    // if it is selected, see if you can move the whole pile over
    for (Pile normalPile : game.piles) {
      Canvas mawn = normalPile.toNode(20);
      // System.out.println("Initializing a pile");
      mawn.setOnMouseClicked(event -> {
        if (!normalPile.isEmpty()) {
          // System.out.println("normalPile is not empty");
          if (!normalPile.peekTop().face) {
            // System.out.println("normalPile peekTop was hidden - should be shown");
            normalPile.peekTop().show();
            refresh();
            return;
          }
        }
        if (game.selecter != null) {
          // int splitter = normalPile.willMove(game.selecter);
          int splitter = game.selecter.willMove(normalPile);
          System.out.println("SPLITTER: " + splitter);
          if (splitter > -1) {
            move++;
            // System.out.println("BEFORE MERGE");
            // System.out.println("N Selecter Pile: " + game.selecter.toString());
            // System.out.println("N normalPile Pile: " + normalPile.toString());
            normalPile.merge(game.selecter.split(game.selecter.getCards().get(splitter)));
            // System.out.println("AFTER MERGE");
            // System.out.println("N Selecter Pile: " + game.selecter.toString());
            // System.out.println("N normalPile Pile: " + normalPile.toString());
            game.selecter.selected = false;
            game.selecter = null;
            refresh();
            winCheck();
          } else {
            game.selecter.selected = false;
            game.selecter = null;
          }
        } else {
          game.selecter = normalPile;
          game.selecter.selected = true;
          refresh();
        }
        refresh();

      });
      normal.getChildren().addAll(mawn);
    }
  }

  public void refresh() {
    clearBoard();
    drawGame();
  }

  public void clearBoard() {
    top.getChildren().clear();
    normal.getChildren().clear();
  }

  public void newGame() {
    move = 0;
    this.game = new Klondike();
    refresh();
  }

  public void createMenu() {
    MenuBar menuBar = new MenuBar();

    Menu reset = new Menu("New");
    MenuItem newGame = new MenuItem("New Game");
    reset.getItems().addAll(newGame);
    newGame.setOnAction(actionEvent -> {
      newGame();
    });

    Menu theme = new Menu("Themes");
    MenuItem chooseTheme = new MenuItem("Choose Themes");
    MenuItem makeTheme = new MenuItem("Custom Cards");

    menuBar.getMenus().addAll(reset);
    window.setTop(menuBar);
  }

  public void winCheck() {
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

  public void stop() {

  }

  

}