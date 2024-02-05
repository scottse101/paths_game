package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.view.gui.HomePage;
import edu.ntnu.idatt2001.view.utility.SoundPlayer;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class of the application. This class is responsible for starting the application and
 * setting up the first scene.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Starts the application and sets up the first scene.
   *
   * @param primaryStage the primary stage of the application.
   */
  @Override
  public void start(Stage primaryStage) {
    SoundPlayer soundPlayer = SoundPlayer.getInstance();

    List<Player> players = new ArrayList<>();

    HomePage homePage = new HomePage(primaryStage, players, soundPlayer);
    Scene scene = new Scene(homePage, 800, 600);
    scene.getStylesheets().add(getClass().getResource("/styling.CSS").toExternalForm());
    primaryStage.setResizable(true);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Paths Game");
    primaryStage.show();
  }
}
