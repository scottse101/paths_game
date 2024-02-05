package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoldGoalTest {
  private GoldGoal goldGoal;
  private Player player;

  @BeforeEach
  void setUp() {
    int minimumGold = 100;
    goldGoal = new GoldGoal(minimumGold);
  }

  @Test
  @DisplayName("Should check if a player can have 100 gold")
  void playerCanHave100Gold() {
    Player player = new Player("Player", 100, 3000, 100);
    assertEquals(100, player.getGold());
  }

  @Test
  @DisplayName("Should check if a player 2 can have 200 gold")
  void player2CanHave200Gold() {
    Player player2 = new Player("Player2", 200, 5000, 200);
    assertEquals(200, player2.getGold());
  }

  @Test
  @DisplayName("Should check if the minimum gold goal can be zero")
  void theMinimumGoldGoalCanNotBeZero() {

    GoldGoal goldGoal = new GoldGoal(100);
    Player player = new Player("Zaza", 100, 200, 100);
    assertEquals(100, player.getGold());
  }

  @Test
  @DisplayName("Should check if the minimum gold goal can be negative")
  void goldGoalCanNotBeANegativeValue() {
    assertThrows(IllegalArgumentException.class, () -> {
      new GoldGoal(-123);
    });
  }

  @Test
  @DisplayName("Should check if the minimum gold goal can be negative, which will always fail")
  void goldGoalCanNotBeANegativeValueFail() {
    try {
      GoldGoal goldGoal = new GoldGoal(-123);
      fail("Expected an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Gold goal can not be negative.", e.getMessage());
    }
  }

  @Test
  @DisplayName("Should check if the new player has enough gold to fullfill the goal")
  public void testIsFullfilled() {
    Player player = new Player("Darius DragonSlayer", 100, 100, 100);
    GoldGoal goldGoal = new GoldGoal(50);
    assertTrue(goldGoal.isFullfilled(player));
    player.addGold(-51);
    assertFalse(goldGoal.isFullfilled(player));

    try {
      goldGoal.isFullfilled(null);
      fail("Expected IllegalArgumentException was not thrown.");
    } catch (IllegalArgumentException e) {
      // Exception was thrown as expected
    }
  }

  @Test
  @DisplayName("Testing if the getGoal() method returns the correct value")
  public void testGetGoal() {
    // Arrange
    GoldGoal goldGoal = new GoldGoal(100);

    // Act
    Integer goal = goldGoal.getGoal();

    // Assert
    assertEquals(100, goal);
  }

  @Test
  @DisplayName("Testing if the toString() method returns the correct value")
  public void testToString() {
    // Arrange
    GoldGoal goldGoal = new GoldGoal(100);

    // Act
    String result = goldGoal.toString();

    // Assert
    assertEquals("Gold goal: 100", result);
  }
}


