import javafx.util.Duration;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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


  public void start(Stage primaryStage) {
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

    time.setOnMouseClicked(event -> {
      System.out.println("You're in the zone");
    });

    
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
        for (Pile destination : game.allPiles) {
          if (destination.selected) {
            // try to move it here
            System.out.println("Destination Pile: " + destination.toString());
            if (source.willMove(destination) > 0) {
              // destination.peekTop() has to be changed in order for an entire pile to move.
              // maybe split?
              source.merge(destination.split(destination.peekTop()));
              destination.selected = false;
              source.selected = false;
              refresh();
              break;
            } else {
              destination.selected = false;
              System.out.println("Nothing- final");
            }
          } else {
            // System.out.println("Everythings getting selected");
            source.selected = true;
            // rawn.selected = true;
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
      for (Pile destination : game.piles) {
        if (destination.selected) {
          // try to move it here
          System.out.println("G Destination Pile: " + destination.toString());
          System.out.println("G Source Pile: " + game.getPile.toString());
          // System.out.println("Destination Pile: " + destination.toString());
          // if (destination.willMove(game.getPile)) {
          if (game.getPile.willMove(destination) > 0) {
            System.out.println("So it will move... but both are now selected to be false");
            game.getPile.merge(destination.split(destination.peekTop()));
            destination.selected = false;
            // game.getPile.selected = false;
            refresh();
            break;
          } else {
            destination.selected = false;
            System.out.println("Nothing- get");
          }
        } else {
          // System.out.println("Everythings getting selected");
          game.getPile.selected = true;
          // rawn.selected = true;
          refresh();
        }

      }
      // for (Pile destination : game.finalPiles) {
      //   if (destination.selected) {
      //     // try to move it here
      //     System.out.println("F Destination Pile: " + destination.toString());
      //     System.out.println("F Source Pile: " + game.getPile.toString());
      //     // System.out.println("Destination Pile: " + destination.toString());
      //     if (destination.willMove(game.getPile) > 0) {
      //       System.out.println("So it will move... but both are now selected to be false");
      //       game.getPile.merge(destination.split(destination.peekTop()));
      //       destination.selected = false;
      //       // game.getPile.selected = false;
      //       refresh();
      //       break;
      //     } else {
      //       destination.selected = false;
      //       System.out.println("Nothing- get");
      //     }
      //   } else {
      //     // System.out.println("Everythings getting selected");
      //     game.getPile.selected = true;
      //     // rawn.selected = true;
      //     refresh();
      //   }

      // }
      refresh();
    });
    top.getChildren().addAll(get);
    

    Canvas draw = new Canvas(80, 124);
    draw = game.drawPile.toNode(0);
    draw.setOnMouseClicked(event -> {
      move++;
      game.drawCard();
      refresh();
    });
    top.getChildren().addAll(draw);

    // Check to see if a pile is selected, if not then set selected to be true 
    // if it is selected, see if you can move the whole pile over
    for (Pile normalPile : game.piles) {
      Canvas mawn = normalPile.toNode(20);
      // mawn = 
      // System.out.println("Initializing a pile");
      mawn.setOnMouseClicked(event -> {
        for(Pile destination : game.allPiles) {
          // System.out.println("N before if normalPile Pile: " + normalPile.toString());
          if (!normalPile.isEmpty()) {
            // System.out.println("normalPile is not empty");
            if (!normalPile.peekTop().face) {
              // System.out.println("normalPile peekTop was hidden - should be shown");
              normalPile.peekTop().show();
              refresh();
              break;
            }
          }
          if (destination.selected) {
            // try to move it here
            System.out.println("N Destination Pile: " + destination.toString());
            System.out.println("N normalPile Pile: " + normalPile.toString());
            int splitter = destination.willMove(normalPile);
            System.out.println("Splitter: " + splitter);
            if (splitter != -1) {
              move++;
              System.out.println("Youre doing it right");
              // System.out.println("Source: " + source.toString());
              // System.out.println("Destination: " + destination.toString());
              // source.split(source.peekTop()).merge(destination);
              normalPile.merge(destination.split(destination.getCards().get(splitter)));
              // destination.merge(source.split(source.peekTop()));
              // destination.merge(source.split(source.peekTop()));
              // source.removeCard(source.peekTop());

              // System.out.println("1Source: " + source.toString());
              // System.out.println("1Destination: " + destination.toString());

              // System.out.println("Merged Pile: " + source.toString());
              destination.selected = false;
              normalPile.selected = false;
              refresh();
              break;
            } else {
              destination.selected = false;
              normalPile.selected = false;
              // source.selected = false;
              System.out.println("Nothing - normal");
            }
          } else {
            // System.out.println("Everythings getting selected");
            normalPile.selected = true;
            // rawn.selected = true;
            refresh();
          }

        }

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

  public void createMenu() {
    MenuBar menuBar = new MenuBar();
    Menu reset = new Menu("New");
    MenuItem newGame = new MenuItem("New Game");
    reset.getItems().addAll(newGame);
    newGame.setOnAction(actionEvent -> {
      move = 0;
      this.game = new Klondike();
      refresh();
    });

    menuBar.getMenus().addAll(reset);
    window.setTop(menuBar);
  }

  public void stop() {

  }

  

}