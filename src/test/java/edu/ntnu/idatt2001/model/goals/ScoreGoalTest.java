package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.goals.ScoreGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreGoalTest {
  private ScoreGoal scoreGoal;

  @BeforeEach
    void setUp() {
        scoreGoal = new ScoreGoal(10000);
    }

    @Test
    @DisplayName("Should check if we can create a player with 10000 score")
    void playerCanHave10000Score() {
        Player player = new Player("Player", 100, 10000, 100);
        assertEquals(10000, player.getScore());
    }

    @Test
    @DisplayName("Should check if we can create a player 2 with 10000 score")
    void player2CanHave20000Score() {
        Player player2 = new Player("Player2", 200, 10000, 200);
        assertEquals(10000, player2.getScore());
    }

    @Test
    @DisplayName("Should check if the minimum score goal can be zero, which will always fail")
    void theMinimumScoreGoalCanNotBeZero() {
        try {
            ScoreGoal scoreGoal = new ScoreGoal(0);
            fail("Expected an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Doesn't make sense to have score goal as 0.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Should check if the minimum score goal can be negative, which will always fail")
    void scoreGoalCanNotBeANegativeValue() {
        try {
            ScoreGoal scoreGoal = new ScoreGoal(-123);
            fail("Expected an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Score goal can not be negative.", e.getMessage());
        }
    }

  @Test
  public void testIsFullfilled() {
    // Create a new player with minimum score
    Player player = new Player("Legolas", 100, 50, 100);

    ScoreGoal scoreGoal = new ScoreGoal(40);
    assertTrue(scoreGoal.isFullfilled(player));
    player.addScore(-41);
    assertFalse(scoreGoal.isFullfilled(player));

    try {
      scoreGoal.isFullfilled(null);
      fail("Expected IllegalArgumentException was not thrown.");
    } catch (IllegalArgumentException e) {
      // Exception was thrown as expected
    }
  }

  @Test
  @DisplayName("Testing if the score goal with minimum points is fulfilled")
  public void testGetGoal() {
    // Arrange
    int minimumPoints = 100;
    ScoreGoal scoreGoal = new ScoreGoal(minimumPoints);

    // Act
    int goal = scoreGoal.getGoal();

    // Assert
    assertEquals(minimumPoints, goal);
  }

  @Test
  @DisplayName("Testing if the toString() method with minimum points is returning the correct string and result")
  public void testToString() {
    // Arrange
    int minimumPoints = 100;
    ScoreGoal scoreGoal = new ScoreGoal(minimumPoints);

    // Act
    String result = scoreGoal.toString();

    // Assert
    assertEquals("Score goal: 100", result);
  }

}

