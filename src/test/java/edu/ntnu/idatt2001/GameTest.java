package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.model.*;
import edu.ntnu.idatt2001.model.designpatterns.GoalFactory;
import edu.ntnu.idatt2001.model.designpatterns.PlayerBuilder;
import edu.ntnu.idatt2001.model.goals.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
  private Player player;
  private Story story;
  private List<Goal> goals;
  private Game game;

  @BeforeEach
  public void setUp() {
    PlayerBuilder playerBuilder = new PlayerBuilder();
    this.player = playerBuilder
        .withName("Lionel")
        .withHealth(100)
        .withScore(0)
        .withGold(10)
        .addToInventory("Bronze sword")
        .addToInventory("Wooden shield")
        .build();
    Passage openingPassage = new Passage("Opening Passage", "Welcome to the game!");
    story = new Story("My Game", openingPassage);
    goals = new ArrayList<>();
  }

  @Test
  @DisplayName("Test constructor with null player")
  public void testConstructorWithNullPlayer() {
    assertThrows(IllegalArgumentException.class, () -> new Game(null, story, goals));
  }

  @Test
  @DisplayName("Test constructor with null story")
  public void testConstructorWithNullStory() {
    assertThrows(IllegalArgumentException.class, () -> new Game(player, null, goals));
  }

  @Test
  @DisplayName("Test constructor with null goals")
  public void testConstructorWithNullGoals() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Game(player, story, null);
    });
  }


  @Test
  @DisplayName("Test beginning of the story is null")
  public void testBeginWithNoOpeningPassage() {
    story.setOpeningPassage(null);
    Game game = new Game(player, story, goals);
    assertNull(game.begin());
  }

  @Test
  @DisplayName("Test a game with no passages")
  public void testGoWithNoPassages() {
    Game game = new Game(player, story, goals);
    Link link = new Link("Go to Passage 2", "Passage 2");
    assertNull(game.go(link));
  }

  @Test
  @DisplayName("Test a game with no links in passage")
  public void testGoWithNoLinksInPassage() {
    Passage passage = new Passage("Passage", "This is a passage.");
    story.addPassage(passage);
    Game game = new Game(player, story, goals);
    assertNull(game.go(new Link("Invalid Link", null)));
  }

  @Test
  @DisplayName("Test a game with adding goals to the game")
  public void testAddGoalToGame() {
    GoalFactory goalFactory = GoalFactory.getInstance();
    List<Goal> goals = new ArrayList<>();

    Goal goldGoal = goalFactory.createGoal("gold", 100);
    goals.add(goldGoal);

    Goal healthGoal = goalFactory.createGoal("health", 50);
    goals.add(healthGoal);

    Goal scoreGoal = goalFactory.createGoal("score", 1000);
    goals.add(scoreGoal);

    Game game = new Game(player, story, goals);
    assertTrue(game.getGoals().contains(goldGoal));
    assertTrue(game.getGoals().contains(healthGoal));
    assertTrue(game.getGoals().contains(scoreGoal));
  }

  @Test
  @DisplayName("Test a game with adding null goal to the game")
  public void testAddNullGoalToGame() {
    Goal nullGoal = null;
    List<Goal> goals = new ArrayList<>();
    goals.add(nullGoal);

    assertThrows(IllegalArgumentException.class, () -> new Game(player, story, goals));
  }

  @Test
  @DisplayName("Test a game with creating goldgoal to the game")
  public void testCreateGoldGoal() {
    int targetGold = 100;
    GoalFactory goalFactory = GoalFactory.getInstance();
    Goal goal = goalFactory.createGoal("gold", targetGold);
    goals.add(goal); // Add the goal to the list of goals in the Game instance
    Game game = new Game(player, story, goals);
    assertEquals(goals, game.getGoals()); // Verify if the goal is added to the Game instance
  }

  @Test
  @DisplayName("Test a game with creating healthgoal to the game")
  public void testCreateHealthGoal() {
    int targetHealth = 50;
    GoalFactory goalFactory = GoalFactory.getInstance();
    Goal goal = goalFactory.createGoal("health", targetHealth);
    goals.add(goal); // Add the goal to the list of goals in the Game instance
    Game game = new Game(player, story, goals);
    assertEquals(goals, game.getGoals()); // Verify if the goal is added to the Game instance
  }

  @Test
  @DisplayName("Test a game with creating scoregoal to the game")
  public void testCreateScoreGoal() {
    int targetScore = 500;
    GoalFactory goalFactory = GoalFactory.getInstance();
    Goal goal = goalFactory.createGoal("score", targetScore);
    goals.add(goal); // Add the goal to the list of goals in the Game instance
    Game game = new Game(player, story, goals);
    assertEquals(goals, game.getGoals()); // Verify if the goal is added to the Game instance
  }

  @Test
  @DisplayName("Test a game with creating inventorygoal to the game")
  public void testCreateInventoryGoal() {
    List<String> targetItems = Arrays.asList("Sword", "Shield", "Potion");
    GoalFactory goalFactory = GoalFactory.getInstance();
    Goal goal = goalFactory.createGoal("inventory", targetItems);
    goals.add(goal); // Add the goal to the list of goals in the Game instance
    Game game = new Game(player, story, goals);
    assertEquals(goals, game.getGoals()); // Verify if the goal is added to the Game instance
  }

}
