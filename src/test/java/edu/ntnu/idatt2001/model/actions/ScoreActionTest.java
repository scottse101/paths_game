package edu.ntnu.idatt2001.model.actions;

import edu.ntnu.idatt2001.model.Player;
import edu.ntnu.idatt2001.model.actions.ScoreAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreActionTest {

  @Test
  @DisplayName("Test player with a positive score")
  void testPlayerWithPositiveScore() {
    Player player = new Player("John", 100, 300, 0);
    ScoreAction scoreAction = new ScoreAction(10);
    scoreAction.execute(player);
    assertEquals(310, player.getScore());
  }

  @Test
  @DisplayName("Test null player")
    void testPlayerWithNullPlayer() {
        ScoreAction scoreAction = new ScoreAction(10);
        assertThrows(IllegalArgumentException.class, () -> {
            scoreAction.execute(null);
        });
    }
}