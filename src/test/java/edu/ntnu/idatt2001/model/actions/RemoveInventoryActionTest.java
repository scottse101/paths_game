package edu.ntnu.idatt2001.model.actions;

import edu.ntnu.idatt2001.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveInventoryActionTest {

  @Test
  @DisplayName("Test executing RemoveInventoryAction")
  public void testExecute() {
    // Arrange
    Player player = new Player("Legolas", 100, 1000, 200);
    player.addToInventory("Sword");
    player.addToInventory("Shield");
    RemoveInventoryAction action = new RemoveInventoryAction("Sword");

    // Act
    action.execute(player);

    // Assert
    assertFalse(player.getInventory().contains("Sword"));
    assertTrue(player.getInventory().contains("Shield"));
  }

  @Test
  @DisplayName("Test executing RemoveInventoryAction with null player")
  public void testExecuteWithNullPlayer() {
    // Arrange
    RemoveInventoryAction action = new RemoveInventoryAction("Sword");

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> action.execute(null));
  }

  @Test
  @DisplayName("Test executing RemoveInventoryAction with invalid item")
  public void testExecuteWithInvalidItem() {
    // Arrange
    Player player = new Player("King", 50, 100, 30);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> new RemoveInventoryAction("Shield").execute(player));
  }

  @Test
  @DisplayName("Test reversing RemoveInventoryAction")
  public void testReverse() {
    // Arrange
    Player player = new Player("McGyver", 100, 1000, 200);
    player.addToInventory("Arrow");
    RemoveInventoryAction action = new RemoveInventoryAction("Arrow");

    // Act
    action.execute(player);
    action.reverse(player);

    // Assert
    assertTrue(player.getInventory().contains("Arrow"));
  }

  @Test
  @DisplayName("Test reversing RemoveInventoryAction with null player")
  public void testReverseWithNullPlayer() {
    // Arrange
    RemoveInventoryAction action = new RemoveInventoryAction("Shield");

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> action.reverse(null));
  }

  @Test
  @DisplayName("Test reversing RemoveInventoryAction with invalid item")
  public void testReverseWithInvalidItem() {
    // Arrange
    Player player = new Player("Jack", 50, 100, 30);
    RemoveInventoryAction action = new RemoveInventoryAction("Dagger");

    // Act & Assert
    assertDoesNotThrow(() -> action.reverse(player));
  }

  @Test
  @DisplayName("Test toString method")
  public void testToString() {
    // Arrange
    RemoveInventoryAction action = new RemoveInventoryAction("Armor");

    // Act
    String result = action.toString();

    // Assert
    assertEquals("inventory: Armor", result);
  }
}