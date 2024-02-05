package edu.ntnu.idatt2001.model.actions;

import edu.ntnu.idatt2001.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveHealthActionTest {

  @Test
  @DisplayName("Test executing RemoveHealthAction")
  public void testExecute() {
    // Arrange
    Player player = new Player("Zaza", 100, 300, 100);
    player.addHealth(100);
    RemoveHealthAction action = new RemoveHealthAction(50);

    // Act
    action.execute(player);

    // Assert
    assertEquals(150, player.getHealth());
  }

  @Test
  @DisplayName("Test executing RemoveHealthAction with null player")
  public void testExecuteWithNullPlayer() {
    // Arrange
    RemoveHealthAction action = new RemoveHealthAction(50);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> action.execute(null));
  }

  @Test
  @DisplayName("Test executing RemoveHealthAction with invalid health value")
  public void testExecuteWithInvalidHealthValue() {
    // Arrange
    Player player = new Player("Biggie", 100, 300, 100);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> new RemoveHealthAction(-50).execute(player));
  }

  @Test
  @DisplayName("Test reversing RemoveHealthAction")
  public void testReverse() {
    // Arrange
    Player player = new Player("Ronny", 100, 100, 50);
    player.addHealth(50);
    RemoveHealthAction action = new RemoveHealthAction(50);

    // Act
    action.execute(player);

    // Assert
    assertEquals(100, player.getHealth());
  }

  @Test
  @DisplayName("Test reversing RemoveHealthAction with null player")
  public void testReverseWithNullPlayer() {
    // Arrange
    RemoveHealthAction action = new RemoveHealthAction(50);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> action.execute(null));
  }

  @Test
  @DisplayName("Test reversing RemoveHealthAction with invalid health value")
  public void testReverseWithInvalidHealthValue() {
    // Arrange
    Player player = new Player("Batman", 100, 300, 100);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> new RemoveHealthAction(-50).execute(player));
  }

  @Test
  @DisplayName("Test toString method")
  public void testToString() {
    // Arrange
    RemoveHealthAction action = new RemoveHealthAction(50);

    // Act
    String result = action.toString();

    // Assert
    assertEquals("health: -50", result);
  }
}
