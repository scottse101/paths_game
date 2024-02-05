package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.goals.HealthGoal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthGoalTest {
  private HealthGoal healthGoal;

  @BeforeEach
  public void setUp() {
    healthGoal = new HealthGoal(50); // set the minimum health to 50
  }

  @Test
  void testPlayerHealth() {
    // create a player with 75 health
    Player player = new Player("Player1", 75, 0, 0);
    player.getHealth();

    // the goal should be fulfilled for the player
    Assertions.assertTrue(healthGoal.isFullfilled(player));

    // create a player with 25 health
    Player player2 = new Player("Player2", 25, 0, 0);
    player2.getHealth();

    // the goal should not be fulfilled for the player
    Assertions.assertFalse(healthGoal.isFullfilled(player2));
  }

  @Test
  @DisplayName("Test if the minimum health can not be negative")
  void healthCanNotBeANegativeValue() {
    // create a health goal with a minimum health of -123
    try {
      HealthGoal healthGoal = new HealthGoal(-123);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Health goal can not be negative.", e.getMessage());
    }
  }
  @Test
  @DisplayName("Testing if the HealthGoal() returns the correct value")
  public void testGetGoal() {
    // Arrange
    HealthGoal healthGoal = new HealthGoal(50);

    // Act
    Integer goal = healthGoal.getGoal();

    // Assert
    assertEquals(50, goal);
  }

  @Test
  @DisplayName("Testing if the toString() returns the correct value")
  public void testToString() {
    // Arrange
    HealthGoal healthGoal = new HealthGoal(50);

    // Act
    String result = healthGoal.toString();

    // Assert
    assertEquals("Health goal: 50", result);
  }
}


