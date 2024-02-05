package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.goals.InventoryGoal;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryGoalTest {
  private InventoryGoal inventoryGoal;
  private Player playerWithItems;
  private Player playerWithoutItems;

  @Before
  public void setUp() {
    ArrayList<String> mandatoryItems = new ArrayList<String>((Arrays.asList("sword", "shield")));
    inventoryGoal = new InventoryGoal(mandatoryItems);
    playerWithItems = new Player("Svein", 100, 500, 30, new ArrayList<String>(Arrays.asList("sword", "shield")));
    playerWithoutItems = new Player("Svein", 100, 500, 30);
  }

  @Test
  @DisplayName("Check if the player has the mandatory items")
  public void playerWithMandatoryItems() {
    setUp();
    assertTrue(inventoryGoal.isFullfilled(playerWithItems));
  }

  @Test
  @DisplayName("Check if the player is missing the mandatory items")
  public void playerWithoutMandatoryItems() {
    setUp();
    assertFalse(inventoryGoal.isFullfilled(playerWithoutItems));
  }

  @Test
  @DisplayName("Check if the player has partial of the mandatory items")
  public void playerwithPartialMandatoryItems() {
    setUp();
    Player playerWithPartialItems = new Player("Svein", 34, 500,100, new ArrayList<String>(Arrays.asList("sword")));
    assertFalse(inventoryGoal.isFullfilled(playerWithPartialItems));
  }

  @Test
  @DisplayName("Testing if the InventoryGoal() method is returning the correct goalslist with the items")
  public void testGetGoal() {
    // Arrange
    List<String> items = Arrays.asList("Sword", "Shield", "Spear");
    InventoryGoal inventoryGoal = new InventoryGoal(items);

    // Act
    List<String> goal = inventoryGoal.getGoal();

    // Assert
    assertEquals(items, goal);
  }

  @Test
  @DisplayName("Testing if the InventoryGoal() method is returning the correct string of the items")
  public void testToString() {
    // Arrange
    List<String> items = Arrays.asList("Axe", "Bow", "Arrow");
    InventoryGoal inventoryGoal = new InventoryGoal(items);

    // Act
    String result = inventoryGoal.toString();

    // Assert
    assertEquals("Inventory goal: Axe, Bow, Arrow", result);
  }
}

