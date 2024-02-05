package edu.ntnu.idatt2001.model.actions;

import edu.ntnu.idatt2001.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveGoldActionTest {

  @Test
  @DisplayName("Test executing RemoveGoldAction")
  public void testExecute() {
    // Arrange
    Player player = new Player( "Zaza", 100, 300, 100);
    player.addGold(100);
    RemoveGoldAction action = new RemoveGoldAction(50);

    // Act
    action.execute(player);

    // Assert
    assertEquals(150, player.getGold());
  }

  @Test
  @DisplayName("Test executing RemoveGoldAction with null player")
  public void testExecuteWithNullPlayer() {
    // Arrange
    RemoveGoldAction action = new RemoveGoldAction(50);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> action.execute(null));
  }

  @Test
  @DisplayName("Test executing RemoveGoldAction with invalid gold value")
  public void testExecuteWithInvalidGoldValue() {
    // Arrange
    Player player = new Player("Biggie", 100, 300, 100);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> new RemoveGoldAction(-50).execute(player));
  }

  @Test
  @DisplayName("Test reversing RemoveGoldAction")
  public void testReverse() {
    // Arrange
    Player player = new Player("Ronny", 100, 100, 50);
    player.addGold(50);
    RemoveGoldAction action = new RemoveGoldAction(50);

    // Act
    action.execute(player);

    // Assert
    assertEquals(50, player.getGold());
  }

  @Test
  @DisplayName("Test reversing RemoveGoldAction with null player")
  public void testReverseWithNullPlayer() {
    // Arrange
    RemoveGoldAction action = new RemoveGoldAction(50);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> action.execute(null));
  }

  @Test
  @DisplayName("Test reversing RemoveGoldAction with invalid gold value")
  public void testReverseWithInvalidGoldValue() {
    // Arrange
    Player player = new Player("Daniel", 200, 500, 1000);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> new RemoveGoldAction(-50).execute(player));
  }

  @Test
  @DisplayName("Test toString method")
  public void testToString() {
    // Arrange
    RemoveGoldAction action = new RemoveGoldAction(50);

    // Act
    String result = action.toString();

    // Assert
    assertEquals("gold: -50", result);
  }
}