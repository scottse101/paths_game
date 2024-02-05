package edu.ntnu.idatt2001.model.actions;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.actions.HealthAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthActionTest {

  @Test
  @DisplayName("Testing player health action with a positive health value")
  void testPlayerWithPositiveHealth() {
    HealthAction healthAction = new HealthAction(10);
    Player player = new Player("Zaza", 100, 300, 0);
    healthAction.execute(player);
    assertEquals(110, player.getHealth());
  }

//  @Test
//  @DisplayName("Testing player health action with a negative health value")
//  void testPlayerWithNegativeHealth() {
//    HealthAction healthAction = new HealthAction(-10);
//    Player player = new Player("Zaza", 100, 300, 0);
//    healthAction.execute(player);
//    assertEquals(90, player.getHealth());
//  }

  @Test
  @DisplayName("Testing null player")
  void testPlayerWithNullPlayer() {
    HealthAction healthAction = new HealthAction(10);
      Assertions.assertThrows(IllegalArgumentException.class, () -> {
        healthAction.execute(null);
      });
  }

}
