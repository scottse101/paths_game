package edu.ntnu.idatt2001.view.gui;

import edu.ntnu.idatt2001.model.Game;
import edu.ntnu.idatt2001.model.Link;
import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.Story;
import edu.ntnu.idatt2001.model.designpatterns.GoalFactory;
import edu.ntnu.idatt2001.model.goals.Goal;
import edu.ntnu.idatt2001.view.utility.LoadCustomFont;
import edu.ntnu.idatt2001.view.utility.MuteButton;
import edu.ntnu.idatt2001.view.utility.PopUp;
import edu.ntnu.idatt2001.view.utility.SoundPlayer;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class for the continue page.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class ContinuePage extends Application {
  private final SoundPlayer soundPlayer;
  private List<Player> players;
  private Player chosenPlayer;
  private Story chosenStory;
  private Image healthImage;
  private Image goldImage;
  private Image scoreImage;
  private Image swordImage;
  private Image shieldImage;
  private Image bowImage;
  private Image arrowImage;
  private Image axeImage;
  private Image spearImage;
  private Font osrsSmall = (LoadCustomFont.getInstance().loadGielenorFont(16));
  private Font osrsMedium = (LoadCustomFont.getInstance().loadGielenorFont(24));
  private Font osrsLarge = (LoadCustomFont.getInstance().loadGielenorFont(32));
  private List<String> goalItems = new ArrayList<>();
  private TextField goldGoalField;
  private TextField scoreGoalField;
  private TextField healthGoalField;
  private int goldGoal;
  private int scoreGoal;
  private int healthGoal;
  private boolean errorShown = false;
  private final ImageView healthImageView = new ImageView(new Image(new FileInputStream("src"
      + "/main/resources/images/icons/health.png")));
  private final ImageView scoreImageView = new ImageView(new Image(new FileInputStream("src"
      + "/main/resources/images/icons/score.png")));
  private final ImageView goldImageView = new ImageView(new Image(new FileInputStream(
      "src/main/resources/images/icons/gold.png")));
  private final ImageView swordImageView = new ImageView(new Image(new FileInputStream("src"
      + "/main/resources/images/icons/sword.png")));
  private final ImageView shieldImageView = new ImageView(new Image(new FileInputStream("src"
      + "/main/resources/images/icons/shield.png")));
  private final ImageView bowImageView = new ImageView(new Image(new FileInputStream(
      "src/main/resources/images/icons/bow.png")));
  private final ImageView arrowImageView = new ImageView(new Image(new FileInputStream(
      "src/main/resources/images/icons/arrows.png")));
  private final ImageView axeImageView = new ImageView(new Image(new FileInputStream(
      "src/main/resources/images/icons/axe.png")));
  private final ImageView spearImageView = new ImageView(new Image(new FileInputStream(
      "src/main/resources/images/icons/spear.png")));
  private final ImageView inventoryIconView = new ImageView(new Image(new FileInputStream(
      "src/main/resources/images/icons/inventory_icon.png")));
  private double osrsLargeOldValue;
  private double osrsMediumOldValue;
  private double osrsSmallOldValue;

  /**
   * Constructor for the continue page.
   *
   * @param chosenPlayer the chosen player
   * @param chosenStory the chosen story
   * @param players the list of players
   * @param soundPlayer the sound player
   * @throws FileNotFoundException if the file is not found
  */
  public ContinuePage(Player chosenPlayer, Story chosenStory, List<Player> players,
                    SoundPlayer soundPlayer) throws FileNotFoundException {
    this.chosenPlayer = chosenPlayer;
    this.chosenStory = chosenStory;
    this.players = players;
    this.soundPlayer = soundPlayer;
    soundPlayer.play("/soundtracks/castle_wars.mp3");
  }

  /**
   * Method for starting the continue page.
   *
   * @param primaryStage the primary stage
   * @throws FileNotFoundException if the file is not found
   */
  @Override
  public void start(Stage primaryStage) throws FileNotFoundException {
    int imageViewWidth = 40;
    int imageViewHeight = 30;

    healthImageView.setFitWidth(imageViewWidth);
    healthImageView.setFitHeight(imageViewHeight);
    scoreImageView.setFitWidth(imageViewWidth);
    scoreImageView.setFitHeight(imageViewHeight);
    goldImageView.setFitWidth(imageViewWidth);
    goldImageView.setFitHeight(imageViewHeight);

    swordImageView.setFitWidth(imageViewWidth);
    swordImageView.setFitHeight(imageViewHeight);
    shieldImageView.setFitWidth(imageViewWidth);
    shieldImageView.setFitHeight(imageViewHeight);
    bowImageView.setFitWidth(imageViewWidth);
    bowImageView.setFitHeight(imageViewHeight);
    arrowImageView.setFitWidth(imageViewWidth);
    arrowImageView.setFitHeight(imageViewHeight);
    axeImageView.setFitWidth(imageViewWidth);
    axeImageView.setFitHeight(imageViewHeight);
    spearImageView.setFitWidth(imageViewWidth);
    spearImageView.setFitHeight(imageViewHeight);
    inventoryIconView.setFitWidth(imageViewWidth);
    inventoryIconView.setFitHeight(imageViewHeight);

    ProgressBar healthBar = new ProgressBar();
    healthBar.setPrefWidth(160);
    healthBar.setPrefHeight(20);
    healthBar.setProgress(1);
    healthBar.setStyle("-fx-accent: #ff3333;");

    ProgressBar scoreBar = new ProgressBar();
    scoreBar.setPrefWidth(160);
    scoreBar.setPrefHeight(20);
    scoreBar.setProgress(0.1);
    scoreBar.setStyle("-fx-accent: #ffb400;");

    SplitPane mainLayout = new SplitPane();
    mainLayout.setDividerPositions(0.62);
    mainLayout.setOrientation(Orientation.HORIZONTAL);
    mainLayout.setPadding(new Insets(0, 10, 0, 10));
    mainLayout.getStyleClass().add("continue-page");

    Label healthLabelText = new Label("Health: " + chosenPlayer.getHealth());

    healthLabelText.setFont(osrsMedium);
    healthLabelText.setStyle("-fx-text-fill: #ff3333;");
    healthLabelText.setWrapText(true);
    Label scoreLabelText = new Label("Score: " + chosenPlayer.getScore());
    scoreLabelText.setFont(osrsMedium);
    scoreLabelText.setStyle("-fx-text-fill: #ffb400;");
    scoreLabelText.setWrapText(true);
    Label goldLabelText = new Label("Gold: " + chosenPlayer.getGold());
    goldLabelText.setFont(osrsMedium);
    if (chosenPlayer.getGold() < 100) {
      goldLabelText.setStyle("-fx-text-fill: #FFFFFF;");
    } else if (chosenPlayer.getGold() >= 100 && chosenPlayer.getGold() <= 10000) {
      goldLabelText.setStyle("-fx-text-fill: #F0D848;");
    } else if (chosenPlayer.getGold() > 10000) {
      goldLabelText.setStyle("-fx-text-fill: #00FF00;");
    }
    goldLabelText.setWrapText(true);

    GridPane statsGridPane = new GridPane();
    statsGridPane.setHgap(10);
    statsGridPane.setVgap(10);
    statsGridPane.setPadding(new Insets(10));
    statsGridPane.setAlignment(Pos.TOP_CENTER);

    statsGridPane.add(healthImageView, 0, 0);
    statsGridPane.add(healthLabelText, 1, 0);
    statsGridPane.add(healthBar, 0, 1, 2, 1);

    statsGridPane.add(scoreImageView, 0, 2);
    statsGridPane.add(scoreLabelText, 1, 2);
    statsGridPane.add(scoreBar, 0, 3, 2, 1);

    statsGridPane.add(goldImageView, 0, 4);
    statsGridPane.add(goldLabelText, 1, 4);

    VBox middleVbox = new VBox();
    middleVbox.setPadding(new Insets(10, 0, 0, 0));
    middleVbox.getChildren().add(statsGridPane);
    middleVbox.setAlignment(Pos.TOP_CENTER);

    GridPane inventoryGridPane = new GridPane();
    inventoryGridPane.setHgap(20);
    inventoryGridPane.setVgap(20);
    inventoryGridPane.setPrefSize(204, 275);
    Scene scene = new Scene(mainLayout, 800, 600);
    inventoryGridPane.prefWidthProperty().bind(scene.widthProperty().multiply(204.0 / 800.0));
    inventoryGridPane.prefHeightProperty().bind(scene.heightProperty().multiply(275.0 / 600.0));
    inventoryGridPane.setAlignment(Pos.CENTER);

    int columnIndex = 0;
    int rowIndex = 0;

    for (String item : chosenPlayer.getInventory()) {
      ImageView itemImageView = null;

      if (item.equals("Sword")) {
        itemImageView = swordImageView;
      } else if (item.equals("Shield")) {
        itemImageView = shieldImageView;
      } else if (item.equals("Spear")) {
        itemImageView = spearImageView;
      } else if (item.equals("Bow")) {
        itemImageView = bowImageView;
      } else if (item.equals("Arrows")) {
        itemImageView = arrowImageView;
      } else if (item.equals("Axe")) {
        itemImageView = axeImageView;
      }

      if (itemImageView != null) {
        itemImageView.fitWidthProperty().bind(primaryStage.widthProperty()
            .multiply(25.0 / 800.0));
        itemImageView.fitHeightProperty().bind(primaryStage.heightProperty()
            .multiply(25.0 / 600.0));
        inventoryGridPane.add(itemImageView, columnIndex, rowIndex);
        columnIndex++;
        if (columnIndex >= 3) {
          columnIndex = 0;
          rowIndex++;
        }
      }
    }

    middleVbox.setSpacing(10);
    VBox inventoryVbox = new VBox();

    inventoryVbox.getChildren().add(inventoryIconView);
    inventoryVbox.getChildren().add(inventoryGridPane);
    inventoryVbox.setAlignment(Pos.CENTER);

    inventoryGridPane.getStyleClass().add("item-background");
    middleVbox.getChildren().add(inventoryVbox);

    ImageView playerCharacter = new ImageView(chosenPlayer.getCharacterImage());
    playerCharacter.setPreserveRatio(true);

    StackPane characterModelStackPane = new StackPane();
    characterModelStackPane.getChildren().add(playerCharacter);
    StackPane.setAlignment(playerCharacter, Pos.CENTER);

    VBox labelsVbox = new VBox(0);
    labelsVbox.setPadding(new Insets(10, 0, 0, 0));
    Label characterLabel = new Label("Character :");
    characterLabel.getStyleClass().add("character-label");

    characterLabel.setFont(osrsLarge);
    characterLabel.setStyle("-fx-text-fill: #ff6600;"
        + " -fx-effect: dropshadow(gaussian, black, 1, 1, 0, 0);");

    Label nameLabel = new Label(chosenPlayer.getName());
    nameLabel.setFont(osrsLarge);
    nameLabel.setStyle("-fx-text-fill: #ff6600;"
        + " -fx-effect: dropshadow(gaussian, black, 1, 1, 0, 0);");


    labelsVbox.getChildren().addAll(characterLabel, nameLabel);
    labelsVbox.setAlignment(Pos.CENTER);

    VBox playerImageVbox = new VBox();
    playerImageVbox.getChildren().addAll(characterModelStackPane);

    Button homeButton = new Button("");
    homeButton.setPrefSize(28, 40);
    homeButton.getStyleClass().add("logout-button");
    homeButton.setOnAction(event -> {
      soundPlayer.stop();
      HomePage homePage = new HomePage(primaryStage, players, soundPlayer);
      Scene homeScene = new Scene(homePage, 800, 600);
      homeScene.getStylesheets().add(getClass().getResource("/styling.CSS").toExternalForm());
      primaryStage.setResizable(true);
      primaryStage.setScene(homeScene);
      primaryStage.centerOnScreen();
      primaryStage.setTitle("Paths Game");
      primaryStage.show();
    });

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
      Scene helpscene = new Scene(vbox, 800, 600);

      Stage helpStage = new Stage();
      helpStage.setTitle("Help");
      helpStage.setScene(helpscene);

      browser.setPrefSize(1920, 1080);

      helpStage.setMaxWidth(Double.MAX_VALUE);
      helpStage.setMaxHeight(Double.MAX_VALUE);

      helpStage.show();
    });

    VBox rootPane = new VBox();
    rootPane.getChildren().addAll(labelsVbox, playerImageVbox);

    VBox.setVgrow(playerImageVbox, Priority.ALWAYS);

    rootPane.setAlignment(Pos.TOP_CENTER);
    VBox.setMargin(labelsVbox, new Insets(10, 0, 0, 0));

    HBox leftHbox = new HBox(0);
    leftHbox.setSpacing(10);
    leftHbox.setAlignment(Pos.TOP_LEFT);

    StackPane helpAndHomeStackPane = new StackPane();
    VBox helpButtonVbox = new VBox();
    helpButtonVbox.getChildren().add(helpButton);
    helpButtonVbox.setAlignment(Pos.CENTER);
    helpAndHomeStackPane.getChildren().addAll(helpButtonVbox, homeButton);
    helpButton.setMinHeight(45);
    helpButton.setMinWidth(45);
    helpAndHomeStackPane.setPadding(new Insets(15, 0, 0, 0));
    StackPane.setAlignment(homeButton, Pos.TOP_LEFT);
    StackPane.setAlignment(helpButtonVbox, Pos.BOTTOM_LEFT);
    StackPane.setMargin(helpButtonVbox, new Insets(520, 0, 0, 0));

    scene.heightProperty().addListener((observable, oldValue, newValue) -> {
      double newMargin = (newValue.doubleValue() - 600) + 520;
      StackPane.setMargin(helpButtonVbox, new Insets(newMargin, 0, 0, 0));
    });


    leftHbox.getChildren().add(helpAndHomeStackPane);
    leftHbox.getChildren().add(rootPane);
    leftHbox.getChildren().add(middleVbox);

    VBox rightVbox = new VBox();
    rightVbox.setSpacing(5);
    rightVbox.setPadding(new Insets(0, 0, 0, 10));

    Label storyLabel = new Label("Story: " + chosenStory.getTitle());
    storyLabel.setWrapText(true);
    storyLabel.setFont(osrsMedium);
    storyLabel.setStyle("-fx-text-fill: #ff6600;"
        + " -fx-effect: dropshadow(gaussian, black, 1, 1, 0, 0);");

    rightVbox.getChildren().addAll(storyLabel);

    ImageView previewImageView = null;
    String storyTitle = chosenStory.getTitle();
    if (storyTitle.equals("The Horror House")) {
      previewImageView = new ImageView(new Image(new FileInputStream("src/main"
          + "/resources/images/backgrounds/horror_house.png")));
    } else if (storyTitle.equals("The Knight and the Elder Dragon")){
      previewImageView = new ImageView(new Image(new FileInputStream("src/main/resources"
        + "/images/storypreviews/knight_vs_dragon.jpg")));
    }
    else {
      previewImageView = new ImageView(new Image(new FileInputStream("src/main"
        + "/resources/images/storypreviews/camelot_castle.jpg")));
    }
    previewImageView.setPreserveRatio(true);

    StackPane imagePreviewStackPane = new StackPane();
    imagePreviewStackPane.getChildren().add(previewImageView);
    StackPane.setAlignment(previewImageView, Pos.CENTER_LEFT);

    previewImageView.setFitWidth(250);

    rightVbox.getChildren().add(imagePreviewStackPane);

    Label brokenLinksLabel = new Label("Broken Links:");
    brokenLinksLabel.setFont(osrsSmall);
    brokenLinksLabel.setStyle("-fx-text-fill: #ff6600;"
        + " -fx-effect: dropshadow(gaussian, black, 1, 1, 0, 0);");

    StringBuilder brokenLinks = new StringBuilder();
    for (Link link : chosenStory.getBrokenLinks()) {
      brokenLinks.append(link.getReference()).append(", ");
    }
    if (brokenLinks.length() > 2) {
      brokenLinks.setLength(brokenLinks.length() - 2);
    }

    Label brokenLinksContent = new Label(brokenLinks.toString());
    brokenLinksContent.setWrapText(true);
    brokenLinksContent.setFont(osrsSmall);
    brokenLinksContent.setStyle("-fx-text-fill: #ff6600;"
        + " -fx-effect: dropshadow(gaussian, black, 1, 1, 0, 0);");

    HBox brokenLinksBox = new HBox(brokenLinksLabel, brokenLinksContent);
    brokenLinksBox.setSpacing(10);

    rightVbox.getChildren().add(brokenLinksBox);

    Separator separator = new Separator();
    rightVbox.getChildren().add(separator);
    separator.getStyleClass().add("separator");

    Label chooseGoalsLabel = new Label("Choose goals for your story:");
    chooseGoalsLabel.setFont(osrsMedium);
    chooseGoalsLabel.setStyle("-fx-text-fill: #ff6600;"
        + " -fx-effect: dropshadow(gaussian, black, 1, 1, 0, 0);");
    rightVbox.getChildren().add(chooseGoalsLabel);

    Label healthGoalLabel = new Label("Health Goal:");
    healthGoalLabel.setFont(osrsSmall);
    healthGoalLabel.setStyle("-fx-text-fill: #ff6600;"
        + " -fx-effect: dropshadow(gaussian, black, 1, 1, 0, 0);");

    TextField healthGoalField = new TextField();
    healthGoalField.setFont(osrsSmall);
    healthGoalField.setPromptText("Enter health goal");

    HBox healthGoalBox = new HBox(healthGoalLabel, healthGoalField);
    healthGoalBox.setSpacing(16);
    rightVbox.getChildren().add(healthGoalBox);

    Label scoreGoalLabel = new Label("Score Goal:");
    scoreGoalLabel.setFont(osrsSmall);
    scoreGoalLabel.setStyle("-fx-text-fill: #ff6600;"
        + " -fx-effect: dropshadow(gaussian, black, 1, 1, 0, 0);");

    TextField scoreGoalField = new TextField();
    scoreGoalField.setFont(osrsSmall);
    scoreGoalField.setPromptText("Enter score goal");

    HBox scoreGoalBox = new HBox(scoreGoalLabel, scoreGoalField);
    scoreGoalBox.setSpacing(20);
    rightVbox.getChildren().add(scoreGoalBox);

    Label goldGoalLabel = new Label("Gold Goal:");
    goldGoalLabel.setFont(osrsSmall);
    goldGoalLabel.setStyle("-fx-text-fill: #ff6600;"
        + " -fx-effect: dropshadow(gaussian, black, 1, 1, 0, 0);");

    TextField goldGoalField = new TextField();
    goldGoalField.setFont(osrsSmall);
    goldGoalField.setPromptText("Enter gold goal");

    HBox goldGoalBox = new HBox(goldGoalLabel, goldGoalField);
    goldGoalBox.setSpacing(29);
    rightVbox.getChildren().add(goldGoalBox);

    Label inventoryGoalLabel = new Label("Inventory Goal:");
    inventoryGoalLabel.setFont(osrsSmall);
    inventoryGoalLabel.setStyle("-fx-text-fill: #ff6600;"
        + " -fx-effect: dropshadow(gaussian, black, 1, 1, 0, 0);");
    Tooltip inventoryGoalLabelTooltip = new Tooltip("Inventory Goal");
    Tooltip.install(inventoryGoalLabel, inventoryGoalLabelTooltip);

    TextField inventoryGoalField = new TextField();
    inventoryGoalField.setFont(osrsSmall);
    inventoryGoalField.setPromptText("Enter inventory goal item");
    Tooltip inventoryGoalFieldTooltip = new Tooltip("Enter inventory goal item");
    Tooltip.install(inventoryGoalField, inventoryGoalFieldTooltip);

    Button addItemButton = new Button("Add Item");
    addItemButton.setFont(osrsSmall);

    String fullText = "Add Item";
    String abbreviatedText = "Add ...";
    Tooltip addItemButtonTooltip = new Tooltip(fullText);
    Tooltip.install(addItemButton, addItemButtonTooltip);

    addItemButton.setOnMouseEntered(e -> {
      Tooltip.install(addItemButton, new Tooltip(fullText));
    });

    addItemButton.setOnMouseExited(e -> {
      Tooltip.install(addItemButton, new Tooltip(abbreviatedText));
    });

    ListView<String> inventoryGoalList = new ListView<>();

    addItemButton.setOnAction(e -> {
      String item = inventoryGoalField.getText();
      if (!item.isEmpty()) {
        if (goalItems != null && goalItems.contains(item)) {
          PopUp popUp = new PopUp();
          popUp.showMediumPopUp("Please enter a valid item name.\n"
              + "No duplicate items in the list please.", 2, primaryStage);
          inventoryGoalField.clear();
          item = "";
          return;
        }
        inventoryGoalList.getItems().add(item);
        goalItems.add(item);
        inventoryGoalField.clear();
      }
    });

    HBox inventoryGoalBox = new HBox(inventoryGoalLabel, inventoryGoalField, addItemButton);
    inventoryGoalBox.setSpacing(10);
    rightVbox.getChildren().add(inventoryGoalBox);

    inventoryGoalList.setPrefSize(150, 100);
    String fontName = osrsSmall.getName();
    inventoryGoalList.setStyle("-fx-font-family: '" + fontName + "'; -fx-font-size: 16px;");

    HBox inventoryGoalListWrapper = new HBox(inventoryGoalList);
    inventoryGoalListWrapper.setPadding(new Insets(5, 0, 0, 0));
    rightVbox.getChildren().add(inventoryGoalListWrapper);

    Separator separator2 = new Separator();
    rightVbox.getChildren().add(separator2);
    separator2.getStyleClass().add("separator");

    Button playGameButton = new Button("Play Game");
    playGameButton.setOnAction(event -> {
      GoalFactory goalFactory = GoalFactory.getInstance();
      if (goldGoalField.getText().isEmpty() || healthGoalField.getText().isEmpty()
          || scoreGoalField.getText().isEmpty() || inventoryGoalList.getItems().isEmpty()) {
        PopUp popUp = new PopUp();
        popUp.showMediumPopUp("Please enter a value for each goal.", 2, primaryStage);
        errorShown = true;
        return;
      } else if (goalFactory.createGoal("inventory", inventoryGoalList
          .getItems()).isFullfilled(chosenPlayer)) {
        PopUp popUp = new PopUp();
        popUp.showMediumPopUp("Inventory goal is already fulfilled."
            + "\nPlease add some more items " + "to your inventory goal.",
            2, primaryStage);
        errorShown = true;
        return;
      } else {
        errorShown = false;
        List<Goal> goalList = Stream.of("health", "score", "gold", "inventory")
                .map(goalType -> goalFactory.createGoal(goalType, getValueForGoalType(goalType)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Game game = new Game(chosenPlayer, chosenStory, goalList);

        soundPlayer.stop();
        try {
          PlayGame playGame = new PlayGame(soundPlayer, game, players);
          Stage playGameStage = new Stage();
          playGame.start(playGameStage);
          primaryStage.close();
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        }
      }
    });

    playGameButton.setFont(osrsMedium);

    playGameButton.setAlignment(Pos.BOTTOM_LEFT);

    MuteButton muteButton = new MuteButton(soundPlayer);
    muteButton.setPrefSize(45, 45);
    Tooltip muteButtonTooltip = new Tooltip("Mute sound");
    muteButtonTooltip.setShowDelay(Duration.millis(250));
    Tooltip.install(muteButton, muteButtonTooltip);
    muteButton.getStyleClass().add("audio-button");
    muteButton.setAlignment(Pos.BOTTOM_RIGHT);

    rightVbox.getChildren().add(playGameButton);

    scoreGoalField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("[0-9]+") && !newValue.equals("")) {
        PopUp popUp = new PopUp();
        popUp.showMediumPopUp("Invalid input. Please enter a valid number.", 3, primaryStage);
        scoreGoalField.setText(oldValue);
      }
      if (newValue.length() > 9) {
        scoreGoalField.setText(oldValue);
        PopUp popUp = new PopUp();
        popUp.showMediumPopUp("Maximum limit of 9 characters reached.", 3, primaryStage);
      }
    });

    scoreGoalField.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        String scoreGoalText = scoreGoalField.getText();
        if (!scoreGoalText.isEmpty()) {
          scoreGoal = Integer.parseInt(scoreGoalText);
          int playerScore = chosenPlayer.getScore();
          if (scoreGoal <= playerScore) {
            scoreGoalField.setText("");
            if (!errorShown) {
              PopUp popUp = new PopUp();
              popUp.showMediumPopUp("Score goal must be greater than current score.",
                  3, primaryStage);
              errorShown = true;
            }
          } else {
            scoreBar.setProgress((double) playerScore / scoreGoal);
            errorShown = false;
          }
        }
      }
    });

    healthGoalField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("[0-9]+") && !newValue.equals("")) {
        PopUp popUp = new PopUp();
        popUp.showMediumPopUp("Invalid input. Please enter a valid number.",
            3, primaryStage);
        healthGoalField.setText(oldValue);
      }
      if (newValue.length() > 9) {
        healthGoalField.setText(oldValue);
        PopUp popUp = new PopUp();
        popUp.showMediumPopUp("Maximum limit of 9 characters reached.",
            3, primaryStage);
      }
    });

    healthGoalField.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        String healthGoalText = healthGoalField.getText();
        if (!healthGoalText.isEmpty()) {
          healthGoal = Integer.parseInt(healthGoalText);
          int playerHealth = chosenPlayer.getHealth();
          if (healthGoal <= playerHealth) {
            healthGoalField.setText("");
            if (!errorShown) {
              PopUp popUp = new PopUp();
              popUp.showMediumPopUp("Score goal must be greater than current score.", 3,
                  primaryStage);
              errorShown = true;
            }
          } else {
            healthBar.setProgress((double) playerHealth / healthGoal);
            errorShown = false;
          }
        }
      }
    });

    goldGoalField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("[0-9]+") && !newValue.equals("")) {
        PopUp popUp = new PopUp();
        popUp.showMediumPopUp("Invalid input. Please enter a valid number.",
            3, primaryStage);
        goldGoalField.setText(oldValue);
      }
      if (newValue.length() > 9) {
        goldGoalField.setText(oldValue);
        PopUp popUp = new PopUp();
        popUp.showMediumPopUp("Maximum limit of 9 characters reached.",
            3, primaryStage);
      }
    });

    goldGoalField.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        String goldGoalText = goldGoalField.getText();
        if (!goldGoalText.isEmpty()) {
          goldGoal = Integer.parseInt(goldGoalText);
          int playerGold = chosenPlayer.getGold();
          if (goldGoal <= playerGold) {
            goldGoalField.setText("");
            if (!errorShown) {
              PopUp popUp = new PopUp();
              popUp.showMediumPopUp("Score goal must be greater than current score.",
                  3, primaryStage);
              errorShown = true;
            }
          } else {
            errorShown = false;
          }
        }
      }
    });

    scene.getStylesheets().add("/styling.CSS");

    characterModelStackPane.prefWidthProperty().bind(scene.widthProperty().multiply(0.3));
    characterModelStackPane.prefHeightProperty().bind(scene.heightProperty().multiply(0.8));
    playerCharacter.fitWidthProperty()
        .bind(characterModelStackPane.widthProperty().multiply(0.9));
    playerCharacter.fitHeightProperty()
        .bind(characterModelStackPane.heightProperty().multiply(0.9));

    mainLayout.setResizableWithParent(rightVbox, Boolean.TRUE);
    rightVbox.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(rightVbox, Priority.ALWAYS);

    DoubleBinding rightVboxMaxWidth = scene.widthProperty().multiply(0.38);
    rightVbox.maxWidthProperty().bind(rightVboxMaxWidth);
    rightVbox.minWidthProperty().bind(rightVboxMaxWidth);

    imagePreviewStackPane.prefWidthProperty().bind(rightVbox.widthProperty().multiply(0.7));
    previewImageView.fitWidthProperty().bind(imagePreviewStackPane.widthProperty().multiply(0.7));

    DoubleProperty screenWidth = new SimpleDoubleProperty();
    screenWidth.set(scene.getWidth());
    screenWidth.addListener((observable, oldValue, newValue) -> {
      helpAndHomeStackPane.setPadding(new Insets(10, 0, 0, 0));
      double fontSize = newValue.doubleValue() / 1000 * 32;
      osrsLargeOldValue = osrsLarge.getSize();
      osrsMediumOldValue = osrsMedium.getSize();
      osrsSmallOldValue = osrsSmall.getSize();
      osrsLarge = (LoadCustomFont.getInstance().loadGielenorFont((int) fontSize));
      osrsMedium = (LoadCustomFont.getInstance().loadGielenorFont((int) (fontSize * 0.8)));
      osrsSmall = (LoadCustomFont.getInstance().loadGielenorFont((int) (fontSize * 0.7)));
      iterateLabels(mainLayout, osrsLargeOldValue, osrsMediumOldValue, osrsSmallOldValue);
    });

    scene.widthProperty().addListener((observable, oldValue, newValue) -> {
      double spacingMultiplier = newValue.doubleValue() / 800.0;
      goldGoalBox.setSpacing(29 * spacingMultiplier);
      healthGoalBox.setSpacing(16 * spacingMultiplier);
      scoreGoalBox.setSpacing(20 * spacingMultiplier);
      inventoryGoalBox.setSpacing(10 * spacingMultiplier);
      screenWidth.set(newValue.doubleValue());
    });

    StackPane rightStackPane = new StackPane();
    rightStackPane.getChildren().addAll(rightVbox, muteButton);
    StackPane.setAlignment(muteButton, Pos.BOTTOM_RIGHT);
    StackPane.setAlignment(rightVbox, Pos.CENTER);
    rightStackPane.setPadding(new Insets(0, 0, 10, 0));

    mainLayout.getItems().addAll(leftHbox, rightStackPane);

    primaryStage.setTitle("Confirm character and story");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Iterates through all the labels in the scene and changes the font to the new font.
   *
   * @param node The node to iterate through
   * @param osrsLargeOldValue The old value of the large font
   * @param osrsMediumOldValue The old value of the medium font
   * @param osrsSmallOldValue The old value of the small font
   */
  private void iterateLabels(Node node, double osrsLargeOldValue, double osrsMediumOldValue,
                            double osrsSmallOldValue) {
    if (node instanceof Label) {
      Label label = (Label) node;
      double fontSize = label.getFont().getSize();
      if (fontSize == osrsLargeOldValue) {
        label.setFont(osrsLarge);
      } else if (fontSize == osrsMediumOldValue) {
        label.setFont(osrsMedium);
      } else if (fontSize == osrsSmallOldValue) {
        label.setFont(osrsSmall);
      }
    }

    if (node instanceof Parent) {
      Parent parent = (Parent) node;
      for (Node child : parent.getChildrenUnmodifiable()) {
        iterateLabels(child, osrsLargeOldValue, osrsMediumOldValue, osrsSmallOldValue);
      }
    }
  }

  /**
   * Gets the value for the goal type.
   *
   * @param <T> The type of the value
   * @param goalType The goal type
   * @return The value for the goal type
   */
  private <T> T getValueForGoalType(String goalType) {
    switch (goalType) {
      case "gold":
        return (T) Integer.valueOf(goldGoal);
      case "health":
        return (T) Integer.valueOf(healthGoal);
      case "score":
        return (T) Integer.valueOf(scoreGoal);
      case "inventory":
        return (T) goalItems;
      default:
        throw new IllegalArgumentException("Unknown goal type: " + goalType);
    }
  }
}
