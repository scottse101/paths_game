package edu.ntnu.idatt2001.model.actions;

import edu.ntnu.idatt2001.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveScoreActionTest {

    @Test
    @DisplayName("Test executing RemoveScoreAction")
    public void testExecute() {
      // Arrange
      Player player = new Player("John", 100, 300, 100);
      player.addScore(100);
      RemoveScoreAction action = new RemoveScoreAction(50);

      // Act
      action.execute(player);

      // Assert
      assertEquals(350, player.getScore());
    }

    @Test
    @DisplayName("Test executing RemoveScoreAction with null player")
    public void testExecuteWithNullPlayer() {
      // Arrange
      RemoveScoreAction action = new RemoveScoreAction(50);

      // Act & Assert
      assertThrows(IllegalArgumentException.class, () -> action.execute(null));
    }

    @Test
    @DisplayName("Test executing RemoveScoreAction with invalid score value")
    public void testExecuteWithInvalidScoreValue() {
      // Arrange
      Player player = new Player("Lionel", 100, 300, 100);

      // Act & Assert
      assertThrows(IllegalArgumentException.class, () -> new RemoveScoreAction(-50).execute(player));
    }

    @Test
    @DisplayName("Test reversing RemoveScoreAction")
    public void testReverse() {
      // Arrange
      Player player = new Player("Mike", 100, 300, 100);
      player.addScore(50);
      RemoveScoreAction action = new RemoveScoreAction(50);

      // Act
      action.reverse(player);

      // Assert
      assertEquals(400, player.getScore());
    }

    @Test
    @DisplayName("Test reversing RemoveScoreAction with null player")
    public void testReverseWithNullPlayer() {
      // Arrange
      RemoveScoreAction action = new RemoveScoreAction(50);

      // Act & Assert
      assertThrows(IllegalArgumentException.class, () -> action.execute(null));
    }

  @Test
  @DisplayName("Test reversing RemoveScoreAction with invalid score value")
  public void testReverseWithInvalidScoreValue() {
    // Arrange
    Player player = new Player("Don", 100, 300, 100);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> new RemoveScoreAction(-50).execute(player));
  }

    @Test
    @DisplayName("Test toString method")
    public void testToString() {
      // Arrange
      RemoveScoreAction action = new RemoveScoreAction(50);

      // Act
      String result = action.toString();

      // Assert
      assertEquals("score: -50", result);
    }
}