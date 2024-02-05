
package edu.ntnu.idatt2001.model.actions;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.actions.GoldAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

  class GoldActionTest {

    @Test
    @DisplayName("Testing player gold action with a positive gold value")
    void testExecutePositiveGold() {
    GoldAction goldAction = new GoldAction(10);
    Player player = new Player("Zaza", 100, 300, 0);
    goldAction.execute(player);
    assertEquals(10, player.getGold());
  }

//    @Test
//    @DisplayName("Testing player gold action with zero as gold value")
//    void testExecuteZeroGold() {
//    GoldAction goldAction = new GoldAction(0);
//    Player player = new Player("Zaza", 100, 200, 0);
//    goldAction.execute(player);
//    assertEquals(0, player.getGold());
//    }

    @Test
    @DisplayName("Testing gold action with a negative gold value")
    void testExecuteNegativeGold() {
      assertThrows(IllegalArgumentException.class, () -> {new GoldAction(-10);
      });
    }

    @Test
    @DisplayName("Test execute with null player")
    public void testExecuteWithNullPlayer() {
      GoldAction goldAction = new GoldAction(10);
      assertThrows(IllegalArgumentException.class, () -> {
        goldAction.execute(null);
      });
    }

//    @Test
//    @DisplayName("Test execute with negative gold value")
//    public void testExecuteWithNegativeGoldValue() {
//      Player player = new Player("Zazz", 100, 500, 0);
//      GoldAction goldAction = new GoldAction(-10);
//      assertThrows(IllegalArgumentException.class, () -> {
//        goldAction.execute(player);
//      });
//      assertEquals(100, player.getGold());
//    }

}

