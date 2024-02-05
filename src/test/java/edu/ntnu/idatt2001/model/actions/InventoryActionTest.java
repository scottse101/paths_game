package edu.ntnu.idatt2001.model.actions;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.actions.InventoryAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryActionTest {
  private InventoryAction inventoryAction;
  private Player player;

  @BeforeEach
  public void setUp() {
    inventoryAction = new InventoryAction("sword");
    player = new Player("Zahid", 1000, 5000, 2000, new ArrayList<String>(Arrays.asList("axe", "hammer")));
  }

    @Test
    @DisplayName("Check if an item is added to the player inventory")
    public void addItemToInventory() {
      inventoryAction.execute(player);
      List<String> expected = Arrays.asList("axe", "hammer", "sword");
      assertEquals(expected, player.getInventory());
    }

    @Test
    @DisplayName("Check if an item equal to null throws an IllegalArgumentException")
    public void testExecuteWithNullItem() {
      assertThrows(IllegalArgumentException.class, () -> {
        new InventoryAction(null);
      });
    }

//  @Test
//  @DisplayName("Check if adding item already in inventory does not duplicate it")
//  public void addItemAlreadyInInventory() {
//    player.addToInventory("sword");
//    inventoryAction.execute(player);
//    List<String> expected = Arrays.asList("axe", "hammer");
//    assertEquals(expected, player.getInventory());
//  }

  @Test
  @DisplayName("Check if adding multiple items to inventory works")
  public void addMultipleItemsToInventory() {
    player.addItemsToInventory(new ArrayList<String>(Arrays.asList("sword", "shield")));
    List<String> expected = Arrays.asList("axe", "hammer", "sword", "shield");
    assertEquals(expected, player.getInventory());
  }
}