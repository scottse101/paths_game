package edu.ntnu.idatt2001.view.gui;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.view.utility.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import edu.ntnu.idatt2001.model.designpatterns.PlayerBuilder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class for the player info page.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class PlayerInfo extends Application {
  private List<Player> players;
  private Image playerImage;
  private Image healthImage;
  private Image goldImage;
  private Image scoreImage;
  private Image swordImage;
  private Image shieldImage;
  private Image bowImage;
  private Image arrowImage;
  private Image axeImage;
  private Image spearImage;
  private Image borderImage;
  private Image gold4Image;
  private Image gold1000Image;
  private Player player;
  private TextField nameField;
  private TextField healthField;
  private TextField goldField;
  private TextField scoreField;
  private TextField inventoryList;
  private ListView<String> inventoryList1;
  private final String[] characterModelImages = {
    "/images/playermodels/player.png",
    "/images/playermodels/old_man.png",
    "/images/playermodels/mage.png",
    "/images/playermodels/woman.png",
    "/images/playermodels/general_graardor.png",
    "/images/playermodels/goblin.png"
  };
  private Image characterModelImage;
  private int currentModelIndex = 0;
  private ImageView characterModelImageView;
  private AlertHelper alertHelper = new AlertHelper();
  private Stage primaryStage;
  private boolean isPlayerCreated;
  private HomePage homePage;
  private List<String> inventoryItemsList;
  private GridPane inventoryGridPane;
  private final SoundPlayer soundPlayer;
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
  private final ImageView arrowImageView = new ImageView(new Image(new FileInputStream("src"
        + "/main/resources/images/icons/arrows.png")));
  private final ImageView axeImageView = new ImageView(new Image(new FileInputStream("src/main"
        + "/resources/images/icons/axe.png")));
  private final ImageView spearImageView = new ImageView(new Image(new FileInputStream("src"
        + "/main/resources/images/icons/spear.png")));
  private final ImageView gold4ImageView = new ImageView(new Image(new FileInputStream("src"
        + "/main/resources/images/icons/coins4.png")));
  private final ImageView gold1000ImageView = new ImageView(new Image(new FileInputStream("src"
        + "/main/resources/images/icons/coins_1000.png")));
  private String imagePath = "";
  private URL imageUrl = null;
  private final Font osrsFontSize20 = LoadCustomFont.getInstance().loadGielenorFont(20);
  private final Font osrsFontSize30 = LoadCustomFont.getInstance().loadGielenorFont(30);
  private final Font osrsFontSize16 = LoadCustomFont.getInstance().loadGielenorFont(16);

  /**
   * Constructor for the player info page.
   *
   * @param homePage the home page
   * @param primaryStage the primary stage
   * @param players the players
   * @param soundPlayer the sound player
   * @throws FileNotFoundException if the file is not found
   */
  public PlayerInfo(HomePage homePage, Stage primaryStage, List<Player> players,
                    SoundPlayer soundPlayer) throws FileNotFoundException {
    this.homePage = homePage;
    this.primaryStage = primaryStage;
    this.players = players;
    this.soundPlayer = soundPlayer;
    soundPlayer.play("/soundtracks/newbie_melody.mp3");
  }

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
    gold4ImageView.setFitWidth(imageViewWidth);
    gold4ImageView.setFitHeight(imageViewHeight);
    gold1000ImageView.setFitWidth(imageViewWidth);
    gold1000ImageView.setFitHeight(imageViewHeight);

    swordImageView.setFitWidth(40);
    swordImageView.setFitHeight(40);

    shieldImageView.setFitWidth(40);
    shieldImageView.setFitHeight(40);

    bowImageView.setFitWidth(40);
    bowImageView.setFitHeight(40);

    arrowImageView.setFitWidth(40);
    arrowImageView.setFitHeight(40);

    axeImageView.setFitWidth(40);
    axeImageView.setFitHeight(40);

    spearImageView.setFitWidth(40);
    spearImageView.setFitHeight(40);

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

    swordImageView.setVisible(false);
    shieldImageView.setVisible(false);
    bowImageView.setVisible(false);
    arrowImageView.setVisible(false);
    axeImageView.setVisible(false);
    spearImageView.setVisible(false);

    GridPane inventoryGridPane = new GridPane();
    inventoryGridPane.setHgap(10);
    inventoryGridPane.setVgap(10);
    inventoryGridPane.setPrefSize(150, 150);
    inventoryGridPane.setAlignment(Pos.BOTTOM_CENTER);

    Pane inventoryPane = new Pane();
    inventoryPane.setPrefSize(200, 200);
    inventoryPane.setPadding(new Insets(10, 0, 10, 0));
    inventoryPane.getStyleClass().add("item-background");


    inventoryGridPane.getChildren().add(inventoryPane);
    GridPane.setConstraints(inventoryPane, 0, 0, 3, 3, HPos.RIGHT, VPos.BOTTOM);

    inventoryItemsList = new ArrayList<>();

    Button addItemButton = new Button("Add Item");
    addItemButton.setStyle("-fx-background-color: #ff6600;");
    addItemButton.setFont(osrsFontSize16);

    Label nameLabel = new Label("Type Name:");
    nameLabel.setFont(osrsFontSize20);
    nameLabel.setStyle("-fx-text-fill: #ff6600;");
    nameField = new TextField();
    nameField.setStyle("-fx-background-color: #8c8b7c");

    Label healthLabel = new Label("Type Health:");
    healthLabel.setFont(osrsFontSize20);
    healthLabel.setStyle("-fx-text-fill: #ff6600;");
    healthField = new TextField();
    healthField.setStyle("-fx-background-color: #8c8b7c");

    Label scoreLabel = new Label("Type Score:");
    scoreLabel.setFont(osrsFontSize20);
    scoreLabel.setStyle("-fx-text-fill: #ff6600;");
    scoreField = new TextField();
    scoreField.setStyle("-fx-background-color: #8c8b7c");

    Label goldLabel = new Label("Type Gold:");
    goldLabel.setFont(osrsFontSize20);
    goldLabel.setStyle("-fx-text-fill: #ff6600;");
    goldField = new TextField();
    goldField.setStyle("-fx-background-color: #8c8b7c");

    AtomicReference<ObservableList<String>> inventoryItems =
        new AtomicReference<>(FXCollections.observableArrayList(
        "Sword", "Shield", "Bow", "Arrows", "Axe", "Spear"
      ));
    Label inventoryLabel = new Label("Add Item To Inventory:");
    inventoryLabel.setFont(osrsFontSize20);
    inventoryLabel.setStyle("-fx-text-fill: #ff6600;");
    ComboBox<String> inventoryComboBox = new ComboBox<>(inventoryItems.get());

    HBox addItemHbox = new HBox();
    addItemHbox.setSpacing(10);
    addItemHbox.getChildren().addAll(inventoryComboBox, addItemButton);

    inventoryComboBox.setStyle("-fx-background-color: #8c8b7c;");

    Label healthLabelText = new Label("Health: ");
    healthLabelText.setFont(osrsFontSize20);
    healthLabelText.setStyle("-fx-text-fill: #ff3333;");
    healthLabelText.setWrapText(true);

    Label scoreLabelText = new Label("Score: ");
    scoreLabelText.setFont(osrsFontSize20);
    scoreLabelText.setStyle("-fx-text-fill: #ffb400;");
    scoreLabelText.setWrapText(true);

    Label goldLabelText = new Label("Gold: ");
    goldLabelText.setFont(osrsFontSize20);
    goldLabelText.setStyle("-fx-text-fill: #FFFFFF;");
    goldLabelText.setWrapText(true);

    GridPane statsGridPane = new GridPane();
    statsGridPane.setHgap(10);
    statsGridPane.setVgap(10);
    statsGridPane.setPadding(new Insets(10));
    statsGridPane.setAlignment(Pos.TOP_CENTER);

    statsGridPane.add(healthImageView, 0, 0);
    statsGridPane.add(healthLabelText, 1, 0);
    statsGridPane.add(healthBar, 0, 1, 2, 1);
    statsGridPane.add(healthField, 1, 1);

    statsGridPane.add(scoreImageView, 0, 2);
    statsGridPane.add(scoreLabelText, 1, 2);
    statsGridPane.add(scoreBar, 0, 3, 2, 1);
    statsGridPane.add(scoreField, 1, 3);

    statsGridPane.add(gold4ImageView, 0, 4);
    statsGridPane.add(goldLabelText, 1, 4);
    statsGridPane.add(goldField, 1, 5);

    double imageColumnWidth = 20;
    double progressBarColumnWidth = 40;
    double labelColumnWidth = 30;
    ColumnConstraints imageColumn = new ColumnConstraints();
    imageColumn.setPercentWidth(imageColumnWidth);
    ColumnConstraints progressBarColumn = new ColumnConstraints();
    progressBarColumn.setPercentWidth(progressBarColumnWidth);
    ColumnConstraints labelColumn = new ColumnConstraints();
    labelColumn.setPercentWidth(labelColumnWidth);
    double textFieldColumnWidth = 10;
    ColumnConstraints textFieldColumn = new ColumnConstraints();
    textFieldColumn.setPercentWidth(textFieldColumnWidth);
    statsGridPane.getColumnConstraints().addAll(imageColumn, progressBarColumn,
          labelColumn, textFieldColumn);


    healthField.textProperty().addListener((observable, oldValue, newValue) -> {
      ((Label) statsGridPane.getChildren().get(1)).setText("Health: " + newValue);
      if (!newValue.matches("[0-9]+") && !newValue.equals("")) {
        PopUp popUp = new PopUp();
        popUp.showMediumPopUp("Invalid input. Please enter a valid number.", 3, primaryStage);
        healthField.setText(oldValue);
      }
      if (newValue.length() > 9) {
        healthField.setText(oldValue);
        PopUp popUp = new PopUp();
        popUp.showMediumPopUp("Maximum limit of 9 characters reached.", 3, primaryStage);
      }
    });

    scoreField.textProperty().addListener((observable, oldValue, newValue) -> {
      ((Label) statsGridPane.getChildren().get(4)).setText("Score: " + newValue);
      if (!newValue.matches("[0-9]+") && !newValue.equals("")) {
        PopUp popUp = new PopUp();
        popUp.showMediumPopUp("Invalid input. Please enter a valid number.", 3, primaryStage);
        scoreField.setText(oldValue);
      }
      if (newValue.length() > 9) {
        scoreField.setText(oldValue);
        PopUp popUp = new PopUp();
        popUp.showMediumPopUp("Maximum limit of 9 characters reached.", 3, primaryStage);
      }
    });

    goldField.textProperty().addListener((observable, oldValue, newValue) -> {
      ((Label) statsGridPane.getChildren().get(7)).setText("Gold: " + newValue);
      if (!newValue.isBlank()) {
        if (newValue.length() > 9) {
          goldField.setText(oldValue);
          PopUp popUp = new PopUp();
          popUp.showMediumPopUp("Maximum limit of 9 characters reached.", 3, primaryStage);
        } else if (newValue.matches("[0-9]+")) {
          int goldValue = Integer.parseInt(newValue);
          if (goldValue < 100) {
            goldLabelText.setStyle("-fx-text-fill: #FFFFFF;");
          } else if (goldValue >= 100 && goldValue <= 10000) {
            goldLabelText.setStyle("-fx-text-fill: #F0D848;");
          } else if (goldValue > 10000) {
            goldLabelText.setStyle("-fx-text-fill: #00FF00;");
          }
        } else {
          goldLabelText.setStyle("-fx-text-fill: #FFFFFF;");
          PopUp popUp = new PopUp();
          popUp.showMediumPopUp("Invalid input. Please enter a valid number.", 3, primaryStage);
          goldField.setText(oldValue);
        }
      }
    });


    addItemButton.setOnAction(event -> {
      String selectedItem = inventoryComboBox.getSelectionModel().getSelectedItem();
      if (selectedItem != null && !inventoryItemsList.contains(selectedItem)) {
        inventoryItemsList.add(selectedItem);
        inventoryComboBox.getItems().remove(selectedItem);

        inventoryList1.getItems().add(selectedItem);
      }

      for (int i = 0; i < inventoryItemsList.size(); i++) {
        String item = inventoryItemsList.get(i);

        ImageView imageView = null;
        if (item.equals("Sword")) {
          imageView = swordImageView;
        } else if (item.equals("Shield")) {
          imageView = shieldImageView;
        } else if (item.equals("Gold")) {
          imageView = goldImageView;
        } else if (item.equals("Bow")) {
          imageView = bowImageView;
        } else if (item.equals("Arrows")) {
          imageView = arrowImageView;
        } else if (item.equals("Axe")) {
          imageView = axeImageView;
        } else if (item.equals("Spear")) {
          imageView = spearImageView;
        }

        if (imageView != null && !inventoryGridPane.getChildren().contains(imageView)) {
          inventoryGridPane.add(imageView, i % 3, i / 3);
        }
        if (imageView != null) {
          imageView.setVisible(true);
        }
      }
    });

    MuteButton muteButton = new MuteButton(soundPlayer);
    muteButton.setPrefSize(45, 45);
    Tooltip muteButtonTooltip = new Tooltip("Mute sound");
    muteButtonTooltip.setShowDelay(Duration.millis(250));
    Tooltip.install(muteButton, muteButtonTooltip);
    muteButton.getStyleClass().add("audio-button");
    muteButton.setAlignment(Pos.BOTTOM_RIGHT);

    BorderPane rightBorderPane = new BorderPane();
    rightBorderPane.setPadding(new Insets(10, 10, 10, 10));
    rightBorderPane.setTop(statsGridPane);
    rightBorderPane.setLeft(inventoryGridPane);
    rightBorderPane.setRight(muteButton);
    BorderPane.setAlignment(muteButton, Pos.BOTTOM_RIGHT);
    BorderPane.setAlignment(inventoryGridPane, Pos.BOTTOM_LEFT);

    Label inventoryLabel1 = new Label("List of Inventory:");
    inventoryLabel1.setFont(osrsFontSize20);
    inventoryLabel1.setStyle("-fx-text-fill: #ff6600;");
    inventoryList1 = new ListView<>();
    inventoryList1.setPrefHeight(75);
    inventoryList1.setStyle("-fx-background-color: #8c8b7c;");

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

    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(10));
    vbox.getChildren().addAll(
          homeButton,
          nameLabel, nameField,
          healthLabel, healthField,
          scoreLabel, scoreField,
          goldLabel, goldField,
          inventoryLabel, addItemHbox,
          inventoryLabel1, inventoryList1,
          helpButton
    );

    int secondToLastIndex = vbox.getChildren().size() - 2;

    VBox.setMargin(vbox.getChildren().get(secondToLastIndex), new Insets(0, 0, 20, 0));
    SplitPane splitPane = new SplitPane();

    Scene scene = new Scene(splitPane, 800, 600);
    helpButton.setAlignment(Pos.BOTTOM_LEFT);
    scene.heightProperty().addListener((observable, oldValue, newValue) -> {
      double newMargin = (newValue.doubleValue() - 600 + 20);
      VBox.setMargin(vbox.getChildren().get(secondToLastIndex), new Insets(0, 0, newMargin, 0));
    });

    vbox.getStyleClass().add("player-info");

    imagePath = characterModelImages[currentModelIndex];
    imageUrl = getClass().getResource(imagePath);
    if (imageUrl != null) {
      Image image = new Image(imageUrl.toExternalForm());
      characterModelImageView = new ImageView(image);
    }

    Button leftButton = new Button("");
    leftButton.setPrefSize(43, 30);
    leftButton.getStyleClass().add("left-option");
    leftButton.setOnAction(e -> {
      currentModelIndex = (currentModelIndex == 0)
          ? characterModelImages.length - 1 : currentModelIndex - 1;
      imagePath = characterModelImages[currentModelIndex];
      imageUrl = getClass().getResource(imagePath);
      if (imageUrl != null) {
        Image image = new Image(imageUrl.toExternalForm());
        characterModelImageView.setImage(image);
      }
    });

    Button rightButton = new Button("");
    rightButton.setPrefSize(43, 30);
    rightButton.getStyleClass().add("right-option");
    rightButton.setOnAction(e -> {
      currentModelIndex = (currentModelIndex + 1) % characterModelImages.length;
      imagePath = characterModelImages[currentModelIndex];
      imageUrl = getClass().getResource(imagePath);
      if (imageUrl != null) {
        Image image = new Image(imageUrl.toExternalForm());
        characterModelImageView.setImage(image);
      }
    });

    StackPane characterModelStackPane = new StackPane();
    characterModelStackPane.getChildren().add(characterModelImageView);
    StackPane.setAlignment(characterModelImageView, Pos.CENTER);

    Label chooseModelLabel = new Label("Choose character");
    chooseModelLabel.setFont(osrsFontSize20);
    chooseModelLabel.setStyle("-fx-text-fill: #ff6600;");

    Label characterCreatorLabel = new Label("Character creator");
    characterCreatorLabel.setFont(osrsFontSize30);
    characterCreatorLabel.setStyle("-fx-text-fill: #ff6600;");

    HBox labelsBox = new HBox(10);
    labelsBox.getChildren().addAll(characterCreatorLabel);
    HBox.setMargin(characterCreatorLabel, new Insets(10, 0, 0, 0));
    labelsBox.setAlignment(Pos.TOP_CENTER);

    HBox buttonsBox = new HBox(10);
    buttonsBox.getChildren().addAll(leftButton, chooseModelLabel, rightButton);
    buttonsBox.setAlignment(Pos.TOP_CENTER);

    VBox labelsAndButtonsBox = new VBox(20);
    labelsAndButtonsBox.getChildren().addAll(labelsBox, buttonsBox);
    labelsAndButtonsBox.setAlignment(Pos.TOP_CENTER);

    StackPane labelsAndButtonsStackPane = new StackPane();
    labelsAndButtonsStackPane.getChildren().add(labelsAndButtonsBox);
    StackPane.setAlignment(labelsAndButtonsBox, Pos.CENTER);

    VBox characterModelVbox = new VBox(20);
    characterModelVbox.getChildren().addAll(characterModelStackPane);
    characterModelVbox.setAlignment(Pos.CENTER);

    Button confirmButton = new Button("");
    confirmButton.getStyleClass().add("confirm-button");
    confirmButton.setPrefSize(253, 68);
    StackPane confirmButtonStackPane = new StackPane();
    confirmButtonStackPane.getChildren().add(confirmButton);
    StackPane.setAlignment(confirmButton, Pos.CENTER);
    confirmButton.setOnAction(event -> {
      savePlayerProfile();
      if (isPlayerCreated) {
        PopUp popUp = new PopUp();
        goldLabelText.setStyle("-fx-text-fill: #FFFFFF;");
        popUp.showPopUpWithBackground("Character created!", 2, primaryStage);
        if (inventoryGridPane.getChildren().size() > 0) {
          inventoryGridPane.getChildren().removeIf(node -> node instanceof ImageView);
        }
        inventoryItems.set(FXCollections.observableArrayList(
              "Sword", "Shield", "Bow", "Arrows", "Axe", "Spear"
        ));
        inventoryComboBox.setItems(inventoryItems.get());
        inventoryItemsList.clear();
        inventoryList1.getItems().clear();
      }
    });

    BorderPane rootPane = new BorderPane();
    rootPane.setTop(labelsAndButtonsStackPane);
    rootPane.setCenter(characterModelVbox);
    rootPane.setBottom(confirmButtonStackPane);

    rootPane.getStyleClass().add("playerinfo-page");
    rootPane.setPrefHeight(splitPane.getHeight());

    double initialWidth = 130;
    double initialHeight = 350;
    characterModelImageView.setPreserveRatio(true);
    characterModelImageView.setFitWidth(initialWidth);
    characterModelImageView.setFitHeight(initialHeight);

    double stackPaneWidth = scene.getWidth() * 0.3;
    double stackPaneHeight = scene.getHeight() * 0.8;
    characterModelStackPane.setPrefSize(stackPaneWidth, stackPaneHeight);

    characterModelImageView.fitWidthProperty().bind(characterModelStackPane
          .widthProperty().multiply(0.9));
    characterModelImageView.fitHeightProperty().bind(characterModelStackPane
          .heightProperty().multiply(0.9));


    Bounds bounds = scene.getRoot().getLayoutBounds();

    double prefWidth = bounds.getWidth() / 3;

    splitPane.setDividerPositions((double) 1  / 3, (double) 2 /  3);
    splitPane.setOrientation(Orientation.HORIZONTAL);

    scene.getStylesheets().add("/styling.CSS");
    splitPane.getStyleClass().add("playerinfo-page");
    splitPane.getItems().addAll(vbox, rootPane, rightBorderPane);

    primaryStage.setTitle("Character creator");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Saves the character created if all fields are filled out with correct information.
   */
  private void savePlayerProfile() {
    isPlayerCreated = false;
    String name = nameField.getText();
    String healthStr = healthField.getText();
    String scoreStr = scoreField.getText();

    String missingFields = "";
    if (name.isEmpty()) {
      missingFields += "Name, ";
    }
    if (healthStr.isEmpty()) {
      missingFields += "Health, ";
    }
    if (scoreStr.isEmpty()) {
      missingFields += "Score, ";
    }
    String goldStr = goldField.getText();
    if (goldStr.isEmpty()) {
      missingFields += "Gold, ";
    }
    if (!missingFields.isEmpty()) {
      missingFields = missingFields.substring(0, missingFields.length() - 2);
      alertHelper.showAlert("All fields are required. Please fill out: " + missingFields);
      return;
    }

    int health;
    int score;
    int gold;

    try {
      health = Integer.parseInt(healthStr);
      score = Integer.parseInt(scoreStr);
      gold = Integer.parseInt(goldStr);
    } catch (NumberFormatException e) {
      alertHelper.showAlert("Health, score, and gold must be integers");
      return;
    }

    PlayerBuilder playerbuilder = new PlayerBuilder();
    playerbuilder
          .withName(name)
          .withHealth(health)
          .withScore(score)
          .withGold(gold)
          .addItemsToInventory((ArrayList<String>) inventoryItemsList)
          .withCharacterImage(characterModelImageView.getImage());
    List<String> errors = playerbuilder.validate();
    if (!errors.isEmpty()) {
      alertHelper.showAlert("Invalid player data: " + String.join("\n", errors));
      return;
    }
    player = playerbuilder.build();

    nameField.clear();
    healthField.clear();
    scoreField.clear();
    goldField.clear();
    inventoryList1.getSelectionModel().clearSelection();

    currentModelIndex = 0;
    String imagePath = characterModelImages[currentModelIndex];
    URL imageUrl = getClass().getResource(imagePath);
    if (imageUrl != null) {
      Image image = new Image(imageUrl.toExternalForm());
      characterModelImageView.setImage(image);
    }

    isPlayerCreated = true;
    addPlayer(player);
  }

  /**
   * Adds the player to player list.
   *
   * @param player the player
   */
  public void addPlayer(Player player) {
    players.add(player);
  }
}

