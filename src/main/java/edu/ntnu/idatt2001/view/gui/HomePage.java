package edu.ntnu.idatt2001.view.gui;

import edu.ntnu.idatt2001.controller.FileHandling;
import edu.ntnu.idatt2001.model.Link;
import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.Story;
import edu.ntnu.idatt2001.view.utility.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class for the home page of the application.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @Version 1.0
 */
public class HomePage extends BorderPane {
  private HomePage homePage;
  private Story storyFromPc;
  private String selectedStory = "";
  private AlertHelper showAlert = new AlertHelper();
  private List<Player> players = new ArrayList<>();
  private Player chosenPlayer;
  private Stage primaryStage;
  private final SoundPlayer soundPlayer;

  /**
   * Constructor for the home page.
   *
   * @param primaryStage the primary stage
   * @param players the list of players
   * @param soundPlayer the sound player
   */
  public HomePage(Stage primaryStage, List<Player> players, SoundPlayer soundPlayer) {
    this.soundPlayer = soundPlayer;
    soundPlayer.play("/soundtracks/scape_main.mp3");

    this.players = players;
    this.primaryStage = primaryStage;
    this.homePage = this;
    Font osrsFontSize20 = LoadCustomFont.getInstance().loadGielenorFont(20);
    Font osrsFontSize30 = LoadCustomFont.getInstance().loadGielenorFont(20);

    Text chosenStoryText = new Text("");
    chosenStoryText.setFont(osrsFontSize20);
    chosenStoryText.setFill(Color.rgb(255, 255, 255));
    chosenStoryText.getStyleClass().add("link-button");

    double prefWidth = 75 + chosenStoryText.getText().length() * 7;

    double maxWidth = 200;

    HBox chosenStoryPane = new HBox(chosenStoryText);
    chosenStoryPane.setAlignment(Pos.TOP_RIGHT);

    chosenStoryPane.setPrefWidth(prefWidth);

    chosenStoryText.setWrappingWidth(maxWidth);

    TypewriterAnimation animation = new TypewriterAnimation(chosenStoryText,
        "Selected story: " + selectedStory + "\nBroken links:", 0.05, 0);
    animation.play();

    MuteButton muteButton = new MuteButton(soundPlayer);
    muteButton.setPrefSize(45, 45);
    Tooltip muteButtonTooltip = new Tooltip("Mute sound");
    muteButtonTooltip.setShowDelay(Duration.millis(250));
    Tooltip.install(muteButton, muteButtonTooltip);
    muteButton.getStyleClass().add("audio-button");

    Button helpButton = new Button("");
    helpButton.setPrefSize(45, 45);
    Tooltip helpButtonTooltip = new Tooltip("How to use the application");
    helpButtonTooltip.setShowDelay(Duration.millis(250));
    Tooltip.install(helpButton, helpButtonTooltip);
    helpButton.getStyleClass().add("help-button");
    helpButton.setOnAction(event -> {
      WebView browser = new WebView();
      WebEngine webEngine = browser.getEngine();
      webEngine.load("https://github.com/1Cezzo/paths-game/wiki");

      VBox vbox = new VBox(browser);
      Scene scene = new Scene(vbox, 800, 600);

      Stage helpStage = new Stage();
      helpStage.setTitle("Help");
      helpStage.setScene(scene);

      browser.setPrefSize(1920, 1080);

      helpStage.setMaxWidth(Double.MAX_VALUE);
      helpStage.setMaxHeight(Double.MAX_VALUE);

      helpStage.show();
    });



    double spacing = (800 - helpButton.getPrefWidth() - muteButton.getPrefWidth() - 40);

    VBox bottomLeftBox = new VBox(helpButton);
    bottomLeftBox.setAlignment(Pos.BOTTOM_LEFT);

    VBox bottomRightBox = new VBox(muteButton);
    bottomRightBox.setAlignment(Pos.BOTTOM_RIGHT);

    HBox bottomBox = new HBox(bottomLeftBox, bottomRightBox);
    bottomBox.setSpacing(spacing);
    bottomBox.setPadding(new Insets(10));

    BorderPane borderPane = new BorderPane();
    borderPane.sceneProperty().addListener((observable, oldScene, newScene) -> {
      if (newScene != null) {
        newScene.widthProperty().addListener((obs, oldVal, newVal) -> {
          double newSpacing =
                (newVal.doubleValue() - helpButton.getPrefWidth() - muteButton.getPrefWidth() - 20);
          bottomBox.setSpacing(newSpacing);
        });
      }
    });

    Button uploadFileButton = new Button("Upload your own story");
    uploadFileButton.getStyleClass().add("link-button");
    uploadFileButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
              try {
                storyFromPc = FileHandling.getInstance().loadStoryFromFile(file);
                selectedStory = storyFromPc.getTitle();
                chosenStoryText.setText("Selected story: " + selectedStory + "\nBroken links:");

                double prefWidth = 75 + ("Selected story: " + selectedStory).length() * 7;

                String brokenLinks = storyFromPc.getBrokenLinks()
                    .stream()
                    .map(Link::getText)
                    .distinct()
                    .collect(Collectors.joining("\n"));

                chosenStoryText.setText("Selected story: " + selectedStory + "\nBroken "
                    + "links:\n" + brokenLinks);
                chosenStoryPane.setPrefWidth(prefWidth);

                TypewriterAnimation animation = new TypewriterAnimation(chosenStoryText,
                    chosenStoryText.getText(), 0.05, 0);
                animation.play();
              } catch (IOException e) {
                if (file.getName().endsWith(".paths") || file.getName().endsWith(".txt")) {
                  showAlert.uploadFileError(e);
                } else {
                  showAlert.uploadFileError(new IOException("File is not a .paths file") {});
                }
              }
            }

        }
    });
    uploadFileButton.setFont(osrsFontSize20);

    Button chooseFromMadeStoriesButton = new Button("Choose from made stories");
    chooseFromMadeStoriesButton.getStyleClass().add("link-button");
    chooseFromMadeStoriesButton.setFont(osrsFontSize20);

    ContextMenu contextMenu = new ContextMenu();
    ObservableList<MenuItem> items = contextMenu.getItems();

    File resourcesFolder = new File("src/main/resources/stories");
    File[] files = resourcesFolder.listFiles();
    for (File file : files) {
      if (file.isFile() && file.getName().endsWith(".paths")) {
        MenuItem item = new MenuItem(file.getName());
        item.getStyleClass().add("menu-item");
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                  storyFromPc = FileHandling.getInstance().loadStoryFromFile(file);
                  selectedStory = storyFromPc.getTitle();
                  chosenStoryText.setText("Selected story: " + selectedStory + "\nBroken links:");

                  double prefWidth = 75 + ("Selected story: " + selectedStory).length() * 7;

                  String brokenLinks = storyFromPc.getBrokenLinks()
                      .stream()
                      .map(Link::getText)
                      .distinct()
                      .collect(Collectors.joining("\n"));

                  chosenStoryText.setText("Selected story: " + selectedStory + "\nBroken "
                      + "links:\n" + brokenLinks);

                  chosenStoryPane.setPrefWidth(prefWidth);

                  TypewriterAnimation animation = new TypewriterAnimation(chosenStoryText,
                      chosenStoryText.getText(), 0.05, 0);
                  animation.play();
                } catch (IOException e) {
                showAlert.uploadFileError(e);
                }
            }
        });
        items.add(item);
      }
    }

    chooseFromMadeStoriesButton.setOnAction(event -> {
      contextMenu.show(chooseFromMadeStoriesButton, Side.BOTTOM, 0, 0);
    });

    HBox fileButtonsHbox = new HBox(0, uploadFileButton, chooseFromMadeStoriesButton);
    fileButtonsHbox.setAlignment(Pos.CENTER);

    Button characterButton = new Button("Create a new character");
    characterButton.getStyleClass().add("link-button");
    characterButton.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
          soundPlayer.stop();
          PlayerInfo playerInfo = null;
          try {
              playerInfo = new PlayerInfo(homePage, primaryStage, players, soundPlayer);
          } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
          }
          Stage playerStage = new Stage();
          playerStage.setResizable(true);
          try {
              playerInfo.start(playerStage);
              primaryStage.close();
          } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
          }
        }
    });
    characterButton.setFont(osrsFontSize20);

    Button chooseCharacterButton = new Button("Choose character");
    chooseCharacterButton.getStyleClass().add("link-button");
    characterButton.setFont(osrsFontSize20);
    double textMaxWidth = 200;

    ContextMenu characterMenu = new ContextMenu();
    ObservableList<MenuItem> characters = characterMenu.getItems();
    for (Player player : players) {
      MenuItem item = new MenuItem(player.getName());
      item.getStyleClass().add("menu-item");
      item.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            chosenPlayer = player;
            String playerName = chosenPlayer.getName();

            if (playerName.length() > 8) {

                Tooltip tooltip = new Tooltip(playerName);
                tooltip.setFont(osrsFontSize30);
                Tooltip.install(chooseCharacterButton, tooltip);

                String abbreviatedText = playerName.substring(0, 8) + "...";
                chooseCharacterButton.setText("Character chosen: " + abbreviatedText);
            } else {
                chooseCharacterButton.setText("Character chosen: " + playerName);
            }
        }
      });
      characters.add(item);
    }

    chooseCharacterButton.setOnAction(event -> {
      if (players.isEmpty()) {
        PopUp popUp = new PopUp();
        popUp.showMediumPopUp("No characters found.\nYou have not created any characters "
            + "yet.\n" + "Please create a new character first", 3, primaryStage);
        return;
      }
      characterMenu.show(chooseCharacterButton, Side.BOTTOM, 0, 0);
    });

    HBox characterButtonsHbox = new HBox(0, characterButton, chooseCharacterButton);
    characterButtonsHbox.setAlignment(Pos.CENTER);
    chooseCharacterButton.setFont(osrsFontSize20);

    Button continueButton = new Button("Confirm");
    continueButton.getStyleClass().add("link-button");
    continueButton.setFont(osrsFontSize20);

    continueButton.setOnAction(event -> {
      if (chosenPlayer != null && storyFromPc != null) {
        soundPlayer.stop();
        try {
          ContinuePage continuePage = new ContinuePage(chosenPlayer, storyFromPc, players,
              soundPlayer);
          Stage continueStage = new Stage();
          continuePage.start(continueStage);
          primaryStage.close();
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        }
      } else {
        PopUp popUp = new PopUp();
        popUp.showMediumPopUp("Choose character and story before continuing please",
            2, primaryStage);
      }
    });



    VBox buttonsVbox = new VBox(20, characterButtonsHbox, fileButtonsHbox, continueButton);
    buttonsVbox.setAlignment(Pos.CENTER);

    StackPane stackPane = new StackPane(chosenStoryPane);

    StackPane buttonsStackPane = new StackPane(buttonsVbox);
    StackPane.setAlignment(buttonsVbox, Pos.CENTER);

    stackPane.getChildren().add(buttonsStackPane);

    borderPane.getStyleClass().add("home-page");
    borderPane.setCenter(stackPane);
    borderPane.setBottom(bottomBox);

    setCenter(borderPane);
  }
}