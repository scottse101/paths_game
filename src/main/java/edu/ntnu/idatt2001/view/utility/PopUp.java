package edu.ntnu.idatt2001.view.utility;

import edu.ntnu.idatt2001.model.Game;
import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.view.gui.HomePage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class is used to create popups.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class PopUp {
  /**
   * This method creates a popup with a message and a duration, with a background.
   *
   * @param message the message to be displayed.
   * @param duration the duration of the popup.
   * @param stage the stage to be displayed on.
   */
  public void showPopUpWithBackground(String message, int duration, Stage stage) {
    LoadCustomFont loadCustomFont = LoadCustomFont.getInstance();

    Label label = new Label(message);
    label.getStyleClass().add("popup-message");
    label.setFont(loadCustomFont.loadGielenorFont(30));

    Popup popup = new Popup();
    popup.getContent().add(label);
    popup.setAutoHide(true);
    popup.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_TOP_LEFT);

    // Apply fade-in animation
    FadeTransition fadeIn = new FadeTransition(Duration.millis(500), label);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);
    fadeIn.play();

    popup.show(stage);

    PauseTransition delay = new PauseTransition(Duration.seconds(duration));
    delay.setOnFinished(event -> popup.hide());
    delay.play();
  }

  /**
   * This method creates a popup with a message and a duration, medium size font.
   *
   * @param message the message to be displayed.
   * @param duration the duration of the popup.
   * @param stage the stage to be displayed on.
   */
  public void showMediumPopUp(String message, int duration, Stage stage) {
    LoadCustomFont loadCustomFont = LoadCustomFont.getInstance();

    Label label = new Label(message);
    label.getStyleClass().add("popup-message2");
    label.setFont(loadCustomFont.loadGielenorFont(30));

    Popup popup = new Popup();
    popup.getContent().add(label);
    popup.setAutoHide(true);
    popup.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_TOP_LEFT);

    // Apply fade-in animation
    FadeTransition fadeIn = new FadeTransition(Duration.millis(500), label);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);
    fadeIn.play();

    popup.show(stage);

    PauseTransition delay = new PauseTransition(Duration.seconds(duration));
    delay.setOnFinished(event -> popup.hide());
    delay.play();
  }

  /**
   * This method creates a popup with a message and a duration, large size font.
   *
   * @param message the message to be displayed.
   * @param duration the duration of the popup.
   * @param stage the stage to be displayed on.
   */
  public void showLargePopUp(String message, int duration, Stage stage) {
    LoadCustomFont loadCustomFont = LoadCustomFont.getInstance();

    Label label = new Label(message);
    label.getStyleClass().add("popup-message2");
    label.setFont(loadCustomFont.loadGielenorFont(40));

    Popup popup = new Popup();
    popup.getContent().add(label);
    popup.setAutoHide(true);
    popup.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_TOP_LEFT);

    // Apply fade-in animation
    FadeTransition fadeIn = new FadeTransition(Duration.millis(500), label);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);
    fadeIn.play();

    popup.show(stage);

    PauseTransition delay = new PauseTransition(Duration.seconds(duration));
    delay.setOnFinished(event -> popup.hide());
    delay.play();
  }

  /**
  * This method creates a popup with a message and a duration, with 2 options.
  *
  * @param message the message to be displayed.
  * @param option1Text the text of the first option.
  * @param option2Text the text of the second option.
  * @param primaryStage the stage to be displayed on.
  * @param game the game to be displayed.
  * @param chosenPlayer the player to be displayed.
  * @param players the list of players.
  * @param soundPlayer the soundplayer.
  */
  public void showOptionsPopUp(String message, String option1Text, String option2Text,
                             Stage primaryStage, Game game, Player chosenPlayer,
                             List<Player> players, SoundPlayer soundPlayer) {
    Stage popUpStage = new Stage();
    popUpStage.initModality(Modality.APPLICATION_MODAL);
    popUpStage.initOwner(primaryStage);

    Label messageLabel = new Label(message);
    messageLabel.getStyleClass().add("popup-message2");
    Button option1Button = new Button(option1Text);
    option1Button.getStyleClass().add("popup-message2");
    Button option2Button = new Button(option2Text);
    option2Button.getStyleClass().add("popup-message2");

    option1Button.setOnAction(event -> {
      game.getGoals().removeIf(goal -> goal.isFullfilled(chosenPlayer));
      popUpStage.close();
    });

    option2Button.setOnAction(event -> {
      soundPlayer.stop();
      try (ObjectInputStream deserializedList =
          new ObjectInputStream(new FileInputStream("data.ser"))) {
        List<String> deserializedItems = (List<String>) deserializedList.readObject();
        players.get(players.indexOf(chosenPlayer)).setInventory(deserializedItems);
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
      HomePage homePage = new HomePage(primaryStage, players, soundPlayer);
      Scene homeScene = new Scene(homePage, 800, 600);
      homeScene.getStylesheets().add(getClass().getResource("/styling.CSS").toExternalForm());
      primaryStage.setResizable(true);
      primaryStage.setScene(homeScene);
      primaryStage.centerOnScreen(); // Center the stage on the screen
      primaryStage.setTitle("Paths Game");
      primaryStage.show();
      popUpStage.close();
    });

    VBox popUpLayout = new VBox(10);
    popUpLayout.setAlignment(Pos.CENTER);
    popUpLayout.setPadding(new Insets(20));
    popUpLayout.getChildren().addAll(messageLabel, option1Button, option2Button);

    Scene popUpScene = new Scene(popUpLayout);
    popUpStage.setScene(popUpScene);

    popUpStage.showAndWait();
  }

  /**
   * This method creates a popup with a message and a duration, with 2 options.
   *
   * @param message the message to be displayed.
   * @param optionText the text of the first option.
   * @param primaryStage the stage to be displayed on.
   * @param game the game to be displayed.
   * @param chosenPlayer the player to be displayed.
   * @param players the list of players.
   * @param soundPlayer the soundplayer.
   */
  public void showOptionPopUp(String message, String optionText,
                               Stage primaryStage, Game game, Player chosenPlayer,
                               List<Player> players, SoundPlayer soundPlayer) {
    Stage popUpStage = new Stage();
    popUpStage.initModality(Modality.APPLICATION_MODAL);
    popUpStage.initOwner(primaryStage);

    Label messageLabel = new Label(message);
    messageLabel.getStyleClass().add("popup-message2");
    Button optionButton = new Button(optionText);
    optionButton.getStyleClass().add("popup-message2");

    // Set styles and properties for the UI elements...

    optionButton.setOnAction(event -> {
      soundPlayer.stop();
      try (ObjectInputStream deserializedList =
             new ObjectInputStream(new FileInputStream("data.ser"))) {
        List<String> deserializedItems = (List<String>) deserializedList.readObject();
        players.get(players.indexOf(chosenPlayer)).setInventory(deserializedItems);
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
      HomePage homePage = new HomePage(primaryStage, players, soundPlayer);
      Scene homeScene = new Scene(homePage, 800, 600);
      homeScene.getStylesheets().add(getClass().getResource("/styling.CSS").toExternalForm());
      primaryStage.setResizable(true);
      primaryStage.setScene(homeScene);
      primaryStage.centerOnScreen(); // Center the stage on the screen
      primaryStage.setTitle("Paths Game");
      primaryStage.show();
      popUpStage.close();
    });

    VBox popUpLayout = new VBox(10);
    popUpLayout.setAlignment(Pos.CENTER);
    popUpLayout.setPadding(new Insets(20));
    popUpLayout.getChildren().addAll(messageLabel, optionButton);

    Scene popUpScene = new Scene(popUpLayout);
    popUpStage.setScene(popUpScene);

    popUpStage.showAndWait();
  }
}
