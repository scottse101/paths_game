package edu.ntnu.idatt2001.model.designpatterns;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.actions.Action;
import edu.ntnu.idatt2001.model.actions.GoldAction;
import edu.ntnu.idatt2001.model.actions.RemoveGoldAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActionFactoryTest {
  private Player player;

  @BeforeEach
  public void setUp() {
    PlayerBuilder playerBuilder = new PlayerBuilder();
    player = playerBuilder
        .withName("Test Player")
        .withHealth(100)
        .withScore(50)
        .withGold(100)
        .build();
  }

  @Test
  @DisplayName("Test create action with valid type and arguments")
  public void testCreateActionWithValidTypeAndArgs() {
    setUp();
    ActionFactory actionFactory = new ActionFactory();

    // Test with valid type and arguments for GoldAction
    Action action1 = actionFactory.createAction("goldaction", 100);
    assertNotNull(action1);
    assertTrue(action1 instanceof GoldAction);
    action1.execute(player);
    assertEquals(200, player.getGold());

    // Test with valid type and arguments for RemoveGoldAction
    Action action2 = actionFactory.createAction("removegoldaction", 50);
    assertNotNull(action2);
    assertTrue(action2 instanceof RemoveGoldAction);
    action2.execute(player);
    assertEquals(150, player.getGold());

    // Add more test cases for other action types as needed
  }

  @Test
  @DisplayName("Test create action with invalid type")
  public void testCreateActionWithInvalidType() {
    ActionFactory actionFactory = new ActionFactory();

    // Test with invalid type
    assertThrows(IllegalArgumentException.class, () -> {
      actionFactory.createAction("invalidtype");
    });
  }

  @Test
  @DisplayName("Test create action with invalid arguments")
  public void testCreateActionWithInvalidArgs() {
    ActionFactory actionFactory = new ActionFactory();

    // Test with invalid arguments for GoldAction
    assertThrows(IllegalArgumentException.class, () -> {
      actionFactory.createAction("goldaction");
    });

    // Test with invalid arguments for RemoveGoldAction
    assertThrows(IllegalArgumentException.class, () -> {
      actionFactory.createAction("removegoldaction", "invalidarg");
    });

    // Test with invalid arguments for InventoryAction
    assertThrows(IllegalArgumentException.class, () -> {
      actionFactory.createAction("inventoryaction", 123);
    });

    // Test with invalid arguments for RemoveInventoryAction
    assertThrows(IllegalArgumentException.class, () -> {
      actionFactory.createAction("removeinventoryaction");
    });

    // Add more test cases for other action types and arguments as needed
  }
}
