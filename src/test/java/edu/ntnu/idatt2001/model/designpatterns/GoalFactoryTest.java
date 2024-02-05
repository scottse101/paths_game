package edu.ntnu.idatt2001.model.designpatterns;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.goals.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GoalFactoryTest {

  private GoalFactory goalFactory;

  private PlayerBuilder playerBuilder;

  @BeforeEach
  public void setUp() {
    goalFactory = GoalFactory.getInstance();
  }

  @Test
  @DisplayName("Test if the health goal is a true instance of the goal interface")
  public void testCreateGoalHealth() {
    setUp();
    Goal goal = goalFactory.createGoal("health", 10);
    assertTrue(goal instanceof HealthGoal);
  }

  @Test
  @DisplayName("Test if the score goal is a true instance of the goal interface")
  public void testCreateGoalScore() {
    Goal goal = goalFactory.createGoal("score", 20);
    assertTrue(goal instanceof ScoreGoal);
  }

  @Test
  @DisplayName("Test if the gold goal is a true instance of the goal interface")
  public void testCreateGoalGold() {
    Goal goal = goalFactory.createGoal("gold", 10);
    assertTrue(goal instanceof GoldGoal);
  }

  @Test
  @DisplayName("Test if the inventory goal is a true instance of the goal interface")
  public void testCreateGoalInventory() {
    playerBuilder = new PlayerBuilder();
    List<String> mandatoryItems = new ArrayList<>();
    mandatoryItems.add("Bronze sword");
    mandatoryItems.add("Wooden shield");
    Goal goal = goalFactory.createGoal("Inventory", mandatoryItems);
    assertTrue(goal instanceof InventoryGoal);
    InventoryGoal inventoryGoal = (InventoryGoal) goal;
    Player player =
        playerBuilder
            .withName("Lionel")
            .withHealth(100)
            .withScore(0)
            .withGold(10)
            .addToInventory("Bronze sword")
            .addToInventory("Wooden shield")
            .build();
    assertTrue(inventoryGoal.isFullfilled(player));
  }

  @Test
  @DisplayName("Test if a invalid type of goal throws an exception")
  public void testCreateGoalInvalidType() {
    assertThrows(IllegalArgumentException.class, () -> {
      goalFactory.createGoal("InvalidType", null);
    });
  }
}