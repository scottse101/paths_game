package edu.ntnu.idatt2001.view.gui;

import edu.ntnu.idatt2001.controller.FileHandling;
import edu.ntnu.idatt2001.model.*;
import edu.ntnu.idatt2001.model.actions.*;
import edu.ntnu.idatt2001.model.goals.*;
import edu.ntnu.idatt2001.view.utility.*;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Labeled;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;
import org.apache.commons.lang3.SerializationUtils;

/**
 * Class for the PlayGame GUI.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @Version 1.0
 */
public class PlayGame extends Application {
  private Story story;
  private Story chosenStory;
  private Player chosenPlayer;
  private Passage currentPassage;
  private VBox passageBox;
  private GridPane linkGrid;
  private FileHandling readFile;
  private PlayerInfo playerInfo;
  private SoundPlayer soundPlayer;
  private Stack<Passage> previousPassages;
  private List<Player> players;
  private List<Goal> goals;
  private Game game;
  private Font osrsExtraSmall = (LoadCustomFont.getInstance().loadGielenorFont(16));
  private Font osrsSmall = (LoadCustomFont.getInstance().loadGielenorFont(16));
  private Font osrsMedium = (LoadCustomFont.getInstance().loadGielenorFont(24));
  private Font osrsLarge = (LoadCustomFont.getInstance().loadGielenorFont(32));
  private final ImageView healthImageView = new ImageView(new Image(new FileInputStream("src"
        + "/main/resources/images/icons/health.png")));
  private final ImageView scoreImageView = new ImageView(new Image(new FileInputStream("src"
        + "/main/resources/images/icons/score.png")));
  private final ImageView goldImageView = new ImageView(new Image(new FileInputStream("src/main"
        + "/resources/images/icons/gold.png")));
  private final ImageView swordImageView = new ImageView(new Image(new FileInputStream("src"
        + "/main/resources/images/icons/sword.png")));
  private final ImageView shieldImageView = new ImageView(new Image(new FileInputStream("src"
        + "/main/resources/images/icons/shield.png")));
  private final ImageView bowImageView = new ImageView(new Image(new FileInputStream("src/main"
        + "/resources/images/icons/bow.png")));
  private final ImageView arrowImageView = new ImageView(new Image(new FileInputStream("src/main"
        + "/resources/images/icons/arrows.png")));
  private final ImageView axeImageView = new ImageView(new Image(new FileInputStream("src/main"
        + "/resources/images/icons/axe.png")));
  private final ImageView spearImageView = new ImageView(new Image(new FileInputStream("src"
        + "/main/resources/images/icons/spear.png")));
  private final ImageView inventoryIconView = new ImageView(new Image(new FileInputStream("src"
        + "/main/resources/images/icons/inventory_icon.png")));
  private Stack<Action> previousActions = new Stack<>();
  private String popUpMessage = "";
  private Stage primaryStage;
  private GoldGoal goldGoal;
  private ScoreGoal scoreGoal;
  private HealthGoal healthGoal;
  private InventoryGoal inventoryGoal;
  private Label healthLabelText = new Label();
  private Label scoreLabelText = new Label();
  private Label goldLabelText = new Label();
  private ProgressBar scoreBar = new ProgressBar();
  private ProgressBar healthBar = new ProgressBar();
  private String[] itemNames = {"Sword", "Shield", "Bow", "Arrows", "Axe", "Spear"};
  private ImageView[] itemImageViews = {swordImageView, shieldImageView, bowImageView,
      arrowImageView, axeImageView, spearImageView};
  private GridPane inventoryGridPane = new GridPane();
  private BorderPane gamePane = new BorderPane();
  private StackPane topStackPane = new StackPane();
  private final List<String> startingItems;
  private boolean isFullScreen = false;
  private double osrsLargeOldValue;
  private double osrsMediumOldValue;
  private double osrsSmallOldValue;
  private double osrsExtraSmallOldValue;

  /**
   * Constructor for PlayGame.
   *
   * @param soundPlayer the single soundplayer instance
   * @param game the game object
   * @param players the list of players
   * @throws FileNotFoundException if the file is not found
   */
  public PlayGame(SoundPlayer soundPlayer, Game game, List<Player> players)
        throws FileNotFoundException {
    this.game = game;
    this.story = game.getStory();
    this.chosenPlayer = game.getPlayer();
    this.goals = game.getGoals();
    this.players = players;
    this.soundPlayer = soundPlayer;
    if (story.getTitle().equals("The Horror House")) {
      soundPlayer.play("/soundtracks/spooky.mp3");
    } else {
      soundPlayer.play("/soundtracks/spirit.mp3");
    }
    startingItems = chosenPlayer.getInventory();
    try (ObjectOutputStream serializedList =
             new ObjectOutputStream(new FileOutputStream("data.ser"))) {
      serializedList.writeObject(startingItems);
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.goldGoal = (GoldGoal) goals.stream().filter(goal ->
      goal instanceof GoldGoal).findFirst().orElse(null);
    this.scoreGoal = (ScoreGoal) goals.stream().filter(goal ->
      goal instanceof ScoreGoal).findFirst().orElse(null);
    this.healthGoal = (HealthGoal) goals.stream().filter(goal ->
      goal instanceof HealthGoal).findFirst().orElse(null);
    this.inventoryGoal = (InventoryGoal) goals.stream().filter(goal ->
      goal instanceof InventoryGoal).findFirst().orElse(null);
  }

  @Override
  public void start(Stage primaryStage) throws FileNotFoundException {
    currentPassage = story.getOpeningPassage();
    previousPassages = new Stack<>();

    passageBox = new VBox();
    passageBox.setAlignment(Pos.TOP_CENTER);
    passageBox.setSpacing(10);
    passageBox.setFillWidth(true);

    topStackPane.setAlignment(Pos.TOP_CENTER);
    topStackPane.getChildren().add(passageBox);

    linkGrid = new GridPane();
    linkGrid.setAlignment(Pos.TOP_CENTER);
    linkGrid.setHgap(10);
    linkGrid.setVgap(10);
    linkGrid.setMaxWidth(Double.MAX_VALUE);
    ColumnConstraints col1 = new ColumnConstraints();
    col1.setHgrow(Priority.ALWAYS);
    linkGrid.getColumnConstraints().add(col1);

    gamePane.setRight(linkGrid);

    gamePane.setPadding(new Insets(10));

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
      Scene helpScene = new Scene(vbox, 800, 600);

      Stage helpStage = new Stage();
      helpStage.setTitle("Help");
      helpStage.setScene(helpScene);

      browser.setPrefSize(1920, 1080);

      helpStage.setMaxWidth(Double.MAX_VALUE);
      helpStage.setMaxHeight(Double.MAX_VALUE);

      helpStage.show();
    });

    Button homeButton = new Button("");
    homeButton.setPrefSize(28, 40);
    homeButton.getStyleClass().add("logout-button");
    homeButton.setOnAction(event -> {
      try (ObjectInputStream deserializedList =
               new ObjectInputStream(new FileInputStream("data.ser"))) {
        List<String> deserializedItems = (List<String>) deserializedList.readObject();
        players.get(players.indexOf(chosenPlayer)).setInventory(deserializedItems);
      } catch (Exception e) {
        e.printStackTrace();
      }
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

    GridPane topBar = new GridPane();
    topBar.setAlignment(Pos.TOP_LEFT);

    StackPane homeButtonContainer = new StackPane(homeButton);
    homeButtonContainer.setAlignment(Pos.TOP_LEFT);

    topBar.add(homeButtonContainer, 0, 0);

    topBar.add(topStackPane, 1, 0);
    GridPane.setHgrow(topStackPane, Priority.ALWAYS);

    ColumnConstraints column1 = new ColumnConstraints();
    ColumnConstraints column2 = new ColumnConstraints();
    column2.setHgrow(Priority.ALWAYS);
    topBar.getColumnConstraints().addAll(column1, column2);

    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
    Scene scene = new Scene(gamePane, (bounds.getWidth() - 10), (bounds.getHeight()) - 40);
    GridPane.setMargin(topStackPane, new Insets(0, scene.getWidth() / 25, 0, 0));

    passageBox.prefHeightProperty().addListener((observable, oldValue, newValue) -> {
      double padding = newValue.doubleValue() - homeButton.getHeight();
      homeButtonContainer.setPadding(new Insets(0, 0, padding, 0));
    });

    StackPane topBarStackPane = new StackPane(topBar);

    gamePane.setTop(topBarStackPane);

    ImageView playerImage = new ImageView(chosenPlayer.getCharacterImage());
    StackPane playerImageStack = new StackPane(playerImage);
    playerImage.setFitWidth(150);
    playerImage.setPreserveRatio(true);
    VBox playerBox = new VBox(playerImageStack);
    playerBox.setAlignment(Pos.CENTER_LEFT);
    VBox.setVgrow(playerImageStack, Priority.ALWAYS);
    HBox.setHgrow(playerBox, Priority.ALWAYS);
    playerImageStack.prefWidthProperty().bind(scene.widthProperty().multiply(0.15));
    playerImageStack.prefHeightProperty().bind(scene.heightProperty().multiply(0.8));
    playerImage.fitWidthProperty().bind(playerImageStack.widthProperty().multiply(0.9));
    playerImage.fitHeightProperty().bind(playerImageStack.heightProperty().multiply(0.6));

    healthBar.setPrefWidth(160);
    healthBar.setPrefHeight(20);
    healthImageView.setFitHeight(25);
    healthImageView.setPreserveRatio(true);
    int healthGoalValue = healthGoal.getGoal();
    double normalizedHealth = chosenPlayer.getHealth() / (double) healthGoalValue;
    healthBar.setProgress(normalizedHealth);
    healthBar.setStyle("-fx-accent: #ff3333;");

    scoreImageView.setFitHeight(25);
    scoreImageView.setPreserveRatio(true);
    scoreBar.setPrefWidth(160);
    scoreBar.setPrefHeight(20);
    int scoreGoalValue = scoreGoal.getGoal();
    double normalizedScore = chosenPlayer.getScore() / (double) scoreGoalValue;
    scoreBar.setProgress(normalizedScore);
    scoreBar.setStyle("-fx-accent: #ffb400;");

    goldImageView.setFitHeight(25);
    goldImageView.setPreserveRatio(true);
    healthLabelText.setText(("Health: " + chosenPlayer.getHealth()));
    scoreLabelText.setText("Score: " + chosenPlayer.getScore());
    goldLabelText.setText("Gold: " + chosenPlayer.getGold());

    healthLabelText.setFont(osrsMedium);
    healthLabelText.getStyleClass().add("health-label");
    scoreLabelText.setFont(osrsMedium);
    scoreLabelText.getStyleClass().add("score-label");
    goldLabelText.setFont(osrsMedium);
    if (chosenPlayer.getGold() < 100) {
      goldLabelText.getStyleClass().removeAll("gold-label-medium", "gold-label-high");
      goldLabelText.getStyleClass().add("gold-label-low");
    } else if (chosenPlayer.getGold() >= 100 && chosenPlayer.getGold() <= 10000) {
      goldLabelText.getStyleClass().removeAll("gold-label-low", "gold-label-high");
      goldLabelText.getStyleClass().add("gold-label-medium");
    } else if (chosenPlayer.getGold() > 10000) {
      goldLabelText.getStyleClass().removeAll("gold-label-medium", "gold-label-low");
      goldLabelText.getStyleClass().add("gold-label-high");
    }
    goldLabelText.setWrapText(true);

    SimpleIntegerProperty healthProperty = new SimpleIntegerProperty(chosenPlayer.getHealth());
    SimpleIntegerProperty scoreProperty = new SimpleIntegerProperty(chosenPlayer.getScore());
    SimpleIntegerProperty goldProperty = new SimpleIntegerProperty(chosenPlayer.getGold());

    ChangeListener<Number> playerStatsListener = (observable, oldValue, newValue) -> {
      healthLabelText.setText("Health: " + chosenPlayer.getHealth());
      scoreLabelText.setText("Score: " + chosenPlayer.getScore());
      goldLabelText.setText("Gold: " + chosenPlayer.getGold());
      healthBar.setProgress(chosenPlayer.getHealth() / (double) healthGoalValue);
      scoreBar.setProgress(chosenPlayer.getScore() / (double) scoreGoalValue);
    };

    healthProperty.addListener(playerStatsListener);
    scoreProperty.addListener(playerStatsListener);
    goldProperty.addListener(playerStatsListener);


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

    inventoryGridPane.setHgap(0);
    inventoryGridPane.setVgap(10);
    inventoryGridPane.prefWidthProperty().bind(scene.widthProperty().multiply(136.0 / 800.0));
    inventoryGridPane.prefHeightProperty().bind(scene.heightProperty().multiply(183.0 / 600.0));
    inventoryIconView.fitWidthProperty().bind(scene.widthProperty().multiply(25.0 / 800.0));
    inventoryIconView.fitHeightProperty().bind(scene.heightProperty().multiply(25.0 / 600.0));

    inventoryGridPane.setAlignment(Pos.CENTER);


    for (int i = 0; i < itemNames.length; i++) {
      String itemName = itemNames[i];
      ImageView itemImageView = itemImageViews[i];
      if (chosenPlayer.getInventory().contains(itemName)) {
        itemImageView.fitWidthProperty().bind(scene.widthProperty().multiply(25.0 / 800.0));
        itemImageView.fitHeightProperty().bind(scene.heightProperty().multiply(25.0 / 600.0));
        inventoryGridPane.add(itemImageView, i % 3, i / 3);
      }
    }
    VBox inventoryVbox = new VBox();

    inventoryVbox.getChildren().add(inventoryIconView);
    inventoryVbox.getChildren().add(inventoryGridPane);
    inventoryVbox.setAlignment(Pos.CENTER);

    inventoryGridPane.getStyleClass().add("item-background");

    VBox container = new VBox();
    container.setSpacing(10);
    container.setAlignment(Pos.CENTER_LEFT);

    container.getChildren().addAll(statsGridPane, inventoryVbox);


    HBox playerAndInventoryBox = new HBox();
    playerAndInventoryBox.setSpacing(-5);
    playerAndInventoryBox.setAlignment(Pos.CENTER_LEFT);
    playerAndInventoryBox.getChildren().addAll(container, playerBox);

    BorderPane leftPane = new BorderPane();
    leftPane.setCenter(playerAndInventoryBox);

    StackPane leftPaneStack = new StackPane(leftPane);
    leftPaneStack.setAlignment(Pos.TOP_CENTER);

    gamePane.setLeft(leftPaneStack);

    double spacing = (800 - helpButton.getPrefWidth() - muteButton.getPrefWidth() - 20);

    VBox bottomLeftBox = new VBox(helpButton);
    bottomLeftBox.setAlignment(Pos.BOTTOM_LEFT);

    VBox bottomRightBox = new VBox(muteButton);
    bottomRightBox.setAlignment(Pos.BOTTOM_RIGHT);

    HBox bottomBox = new HBox(bottomLeftBox, bottomRightBox);
    bottomBox.setSpacing(spacing);

    gamePane.setBottom(bottomBox);

    scene.widthProperty().addListener((obs, oldVal, newVal) -> {
      double newSpacing =
          (newVal.doubleValue() - helpButton.getPrefWidth() - muteButton.getPrefWidth() - 20);
      bottomBox.setSpacing(newSpacing);
      double newMargin = (newVal.doubleValue() / 25);
      GridPane.setMargin(topStackPane, new Insets(0, newMargin, 0, 0));
    });

    scene.getStylesheets().add(getClass().getResource("/styling.CSS").toExternalForm());

    DoubleProperty screenWidth = new SimpleDoubleProperty();
    screenWidth.bind(scene.widthProperty());
    screenWidth.addListener((observable, oldValue, newValue) -> {
      double minFontSize = 12.0;
      double scalingFactor = 0.95;
      double fontSize =
          newValue.doubleValue() / 1200 * (32 - minFontSize) * scalingFactor + minFontSize;

      osrsExtraSmallOldValue = osrsExtraSmall.getSize();
      osrsLargeOldValue = osrsLarge.getSize();
      osrsMediumOldValue  = osrsMedium.getSize();
      osrsSmallOldValue = osrsSmall.getSize();

      osrsLarge = (LoadCustomFont.getInstance().loadGielenorFont((int) fontSize));
      osrsMedium = (LoadCustomFont.getInstance().loadGielenorFont((int) (fontSize * 0.75)));
      osrsSmall = (LoadCustomFont.getInstance().loadGielenorFont((int) (fontSize * 0.6)));
      osrsExtraSmall = (LoadCustomFont.getInstance().loadGielenorFont((int) (fontSize * 0.3)));

      iterateLabels(gamePane, osrsLargeOldValue, osrsMediumOldValue,
            osrsSmallOldValue, osrsExtraSmallOldValue);
    });

    primaryStage.setScene(scene);
    scene.setOnKeyPressed(event -> {
      if (event.getCode().toString().equals("F11")) {
        toggleFullScreen(primaryStage);
      }
    });
    primaryStage.setFullScreen(true);
    primaryStage.setResizable(true);
    primaryStage.show();

    updateUi(primaryStage);
  }

  /**
   * Iterates through all the labels in the gamePane and updates their font size.
   *
   * @param primaryStage the stage
   */
  private void updateUi(Stage primaryStage) {
    if (story.getTitle().equals("The Horror House")) {
      gamePane.getStyleClass().add("horror-house");
    } else if (story.getTitle().equals("The Knight and the Elder Dragon")) {
      gamePane.getStyleClass().add("knight-and-dragon");
    } else {
      gamePane.getStyleClass().add("game-pane");
    }

    DropShadow outline = new DropShadow();
    outline.setColor(Color.BLACK);
    outline.setRadius(2);
    outline.setSpread(0.2);

    passageBox.getChildren().clear();
    linkGrid.getChildren().clear();

    updateInventory(primaryStage);

    Label passageTitle = new Label(currentPassage.getTitle());
    passageTitle.setFont(osrsLarge);
    passageTitle.setTextFill(Color.rgb(249, 220, 36));
    passageTitle.setEffect(outline);
    passageTitle.setTextAlignment(TextAlignment.CENTER);
    passageBox.getChildren().add(passageTitle);

    String passageTitleString = passageTitle.getText();
    String passageTitleCss = passageTitleString.toLowerCase().replace(" ", "-");

    Label passageText = new Label("");
    passageText.setFont(osrsMedium);
    passageText.setTextFill(Color.rgb(255, 255, 255));
    passageText.setEffect(outline);
    passageText.setTextAlignment(TextAlignment.JUSTIFY);
    passageText.setWrapText(true);
    passageBox.getChildren().add(passageText);

    String[] sentences = currentPassage.getContent().split("\\.");
    List<LabelAnimation> sentenceAnimations = new ArrayList<>();
    double delay = 0.0;

    StringBuilder concatenatedText = new StringBuilder();
    for (String sentence : sentences) {
      concatenatedText.append(sentence.trim()).append(". ");
    }

    LabelAnimation animation = new LabelAnimation(passageText, concatenatedText.toString(), 0.04,
          delay);
    sentenceAnimations.add(animation);

    for (int i = 0; i < sentenceAnimations.size(); i++) {
      LabelAnimation currentAnimation = sentenceAnimations.get(i);
      int nextIndex = i + 1;
      if (nextIndex < sentenceAnimations.size()) {
        LabelAnimation nextAnimation = sentenceAnimations.get(nextIndex);
        currentAnimation.setOnFinished(event -> nextAnimation.play());
      }
    }

    if (!sentenceAnimations.isEmpty()) {
      sentenceAnimations.get(0).play();
    }

    linkGrid.getChildren().clear();
    IntStream.range(0, currentPassage.getLinks().size())
        .filter(i -> {
          Link link = currentPassage.getLinks().get(i);
          return !link.getReference().equals(currentPassage.getTitle())
              && !story.getBrokenLinks().contains(link);
        })
        .forEach(i -> {
          Link link = currentPassage.getLinks().get(i);
          Button linkButton = new Button(link.getText());
          linkButton.setOnAction(event -> {
            previousPassages.push(currentPassage);
            popUpMessage = "";
            link.getActions().forEach(action -> {
              if (action instanceof RemoveHealthAction) {
                action.execute(chosenPlayer);
                if (chosenPlayer.getHealth() <= 0) {
                  PopUp popUp = new PopUp();
                  popUp.showMediumPopUp("You died!", 2, primaryStage);
                  popUp.showOptionPopUp("Oh dear, you are dead", "Go to home screen",
                        primaryStage, game, chosenPlayer, players, soundPlayer);
                } else {
                  updateLabelsAndProgressBars(action);
                  popUpMessage += action.toString() + "\n";
                  previousActions.push(action);
                }
              } else if (action instanceof InventoryAction) {
                if (chosenPlayer.getInventory().size() >= 9) {
                  PopUp popUp = new PopUp();
                  popUp.showMediumPopUp("You can't carry any more items!", 2, primaryStage);
                } else {
                  try {
                    action.execute(chosenPlayer);
                    popUpMessage += action.toString() + "\n";
                    previousActions.push(action);
                  } catch (IllegalArgumentException e) {
                    PopUp popUp = new PopUp();
                    popUp.showMediumPopUp(e.getMessage(), 3, primaryStage);
                  }
                }
              } else {
                action.execute(chosenPlayer);
                updateLabelsAndProgressBars(action);
                popUpMessage += action.toString() + "\n";
                previousActions.push(action);
              }
            });
            if (!popUpMessage.isEmpty()) {
              PopUp popUp = new PopUp();
              popUp.showLargePopUp(popUpMessage, 2, primaryStage);
            }
            currentPassage = story.getPassage(link);
            if (areGoalsFulfilled()) {
              PopUp popUp = new PopUp();
              popUp.showOptionsPopUp("Congratulations! You fulfilled one or more goals.",
                  "Continue Playing", "Go to Home Screen",
                  primaryStage, game, chosenPlayer, players, soundPlayer);
            } else {
              updateUi(primaryStage);
            }
            updateUi(primaryStage);
          });
          linkButton.setMaxWidth(Double.MAX_VALUE);
          linkButton.setFont(osrsMedium);
          linkButton.getStyleClass().add("link-button");
          linkGrid.add(linkButton, 0, i);
        });

    if (!previousPassages.isEmpty()) {
      Button undoButton = new Button("Undo");
      undoButton.setOnAction(event -> {
        currentPassage = previousPassages.pop();

        while (!previousActions.isEmpty()) {
          Action action = previousActions.pop();
          action.reverse(chosenPlayer);
          updateLabelsAndProgressBars(action);
        }
        updateUi(primaryStage);
      });
      undoButton.setFont(osrsMedium);
      undoButton.getStyleClass().add("undo-button");
      linkGrid.add(undoButton, 0, currentPassage.getLinks().size());
    }

    Label brokenLinksTitle = new Label("Broken links:");
    brokenLinksTitle.setTextFill(Color.rgb(249, 220, 36));
    brokenLinksTitle.setFont(osrsSmall);
    brokenLinksTitle.setTextAlignment(TextAlignment.LEFT);
    linkGrid.add(brokenLinksTitle, 0, currentPassage.getLinks().size() + 1);

    Label brokenLinksText = new Label();
    brokenLinksText.setTextFill(Color.rgb(255, 255, 255));
    brokenLinksText.setFont(osrsSmall);
    brokenLinksText.setTextAlignment(TextAlignment.LEFT);
    StringBuilder brokenLinksBuilder = new StringBuilder();
    for (Link link : story.getBrokenLinks()) {
      brokenLinksBuilder.append(link.getReference()).append(", ");
    }
    if (brokenLinksBuilder.length() > 0) {
      brokenLinksBuilder.setLength(brokenLinksBuilder.length() - 2);
    }
    brokenLinksText.setText(brokenLinksBuilder.toString());
    linkGrid.add(brokenLinksText, 0, currentPassage.getLinks().size() + 2);
    brokenLinksText.setWrapText(true);
    brokenLinksText.setTextAlignment(TextAlignment.LEFT);
    brokenLinksText.setMaxWidth(150);

    Label goalsTitle = new Label("Goals:");
    goalsTitle.setTextFill(Color.rgb(249, 220, 36));
    goalsTitle.setFont(osrsSmall);
    goalsTitle.setTextAlignment(TextAlignment.LEFT);
    linkGrid.add(goalsTitle, 0, currentPassage.getLinks().size() + 3);

    Label goalsText = new Label();
    goalsText.setFont(osrsSmall);
    goalsText.setTextFill(Color.rgb(255, 255, 255));
    goalsText.setTextAlignment(TextAlignment.LEFT);
    StringBuilder goalsBuilder = new StringBuilder();
    game.getGoals().stream()
        .map(Goal::toString)
        .forEach(goalString -> goalsBuilder.append(goalString).append("\n"));
    goalsText.setText(goalsBuilder.toString());
    goalsText.setWrapText(true);
    goalsText.setTextAlignment(TextAlignment.LEFT);
    goalsText.setMaxWidth(150);
    linkGrid.add(goalsText, 0, currentPassage.getLinks().size() + 4);
  }

  private void iterateLabels(Node node, double osrsLargeOldValue,
                             double osrsMediumOldValue, double osrsSmallOldValue,
                             double osrsExtraSmallOldValue) {
    if (node instanceof Labeled) {
      Labeled labeled = (Labeled) node;
      double fontSize = labeled.getFont().getSize();
      String fontFamily = labeled.getFont().getFamily();

      if (fontSize == osrsLargeOldValue) {
        labeled.setStyle("-fx-font-size: " + osrsLarge.getSize()
              + "px; -fx-font-family: '" + fontFamily + "';");
      } else if (fontSize == osrsMediumOldValue) {
        labeled.setStyle("-fx-font-size: " + osrsMedium.getSize()
              + "px; -fx-font-family: '" + fontFamily + "';");
      } else if (fontSize == osrsSmallOldValue) {
        labeled.setStyle("-fx-font-size: " + osrsSmall.getSize()
              + "px; -fx-font-family: '" + fontFamily + "';");
      } else if (fontSize == osrsExtraSmallOldValue) {
        labeled.setStyle("-fx-font-size: " + osrsSmall.getSize()
              + "px; -fx-font-family: '" + fontFamily + "';");
      }
    }

    if (node instanceof Parent) {
      Parent parent = (Parent) node;
      for (Node child : parent.getChildrenUnmodifiable()) {
        iterateLabels(child, osrsLargeOldValue, osrsMediumOldValue,
              osrsSmallOldValue, osrsExtraSmallOldValue);
      }
    }
  }

  private void updateLabelsAndProgressBars(Action action) {
    if (action instanceof HealthAction || action instanceof RemoveHealthAction) {
      healthLabelText.setText("Health: " + chosenPlayer.getHealth());
      int healthGoalValue = healthGoal.getGoal();
      double normalizedHealth = chosenPlayer.getHealth() / (double) healthGoalValue;
      healthBar.setProgress(normalizedHealth);
    } else if (action instanceof GoldAction || action instanceof RemoveGoldAction) {
      goldLabelText.setText("Gold: " + chosenPlayer.getGold());
    } else if (action instanceof ScoreAction || action instanceof RemoveScoreAction) {
      scoreLabelText.setText("Score: " + chosenPlayer.getScore());
      int scoreGoalValue = scoreGoal.getGoal();
      double normalizedScore = chosenPlayer.getScore() / (double) scoreGoalValue;
      scoreBar.setProgress(normalizedScore);
    }
  }

  /**
   * Updates the inventory grid pane.
   *
   * @param primaryStage the primary stage
   */
  public void updateInventory(Stage primaryStage) {
    inventoryGridPane.getChildren().clear();

    int columnIndex = 0;
    int rowIndex = 0;

    DoubleProperty maxLabelWidth = new SimpleDoubleProperty();
    DoubleProperty maxLabelHeight = new SimpleDoubleProperty();
    maxLabelWidth.bind(primaryStage.widthProperty().multiply(25.0 / 800.0));
    maxLabelHeight.bind(primaryStage.heightProperty().multiply(25.0 / 600.0));

    for (int i = 0; i < itemNames.length; i++) {
      String itemName = itemNames[i];
      ImageView itemImageView = itemImageViews[i];
      if (chosenPlayer.getInventory().contains(itemName)) {
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

    S3ImageLoader imageLoader = S3ImageLoader.getInstance();
    for (String item : chosenPlayer.getInventory()) {
      if (!Arrays.asList(itemNames).contains(item)) {
        Image image = imageLoader.loadImageFromS3("paths-game-storage", item + ".png", "eu"
              + "-north-1");
        if (image != null) {
          ImageView itemImageView = new ImageView(image);
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
        } else {
          Label label = new Label(item);
          label.setFont(osrsSmall);
          label.setTextFill(Color.rgb(255, 255, 255));
          label.setTextAlignment(TextAlignment.CENTER);
          label.setWrapText(true);
          label.setMaxWidth(maxLabelWidth.get());
          label.setMaxHeight(maxLabelHeight.get());
          inventoryGridPane.add(label, columnIndex, rowIndex);
          columnIndex++;
          if (columnIndex >= 3) {
            columnIndex = 0;
            rowIndex++;
          }
        }
      }
    }
  }

  /**
   * Checks if the player has fulfilled any goals.
   *
   */
  public boolean areGoalsFulfilled() {
    return game.getGoals().stream().anyMatch(goal -> goal.isFullfilled(chosenPlayer));
  }

  /**
   * Checks if the player has fulfilled any goals.
   *
   */
  private void toggleFullScreen(Stage primaryStage) {
    isFullScreen = !isFullScreen;
    primaryStage.setFullScreen(isFullScreen);
  }
}
