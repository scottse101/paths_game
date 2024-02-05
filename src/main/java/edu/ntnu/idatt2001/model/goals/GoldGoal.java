package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;

/**
 * Class for gold goals.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class GoldGoal implements Goal {
  private final int minimumGold;

  /**
   * Constructor for gold goal.
   *
   * @param minimumGold the minimum gold to fulfill the goal.
   */
  public GoldGoal(int minimumGold) {
    this.minimumGold = minimumGold;
    if (minimumGold < 0) {
      throw new IllegalArgumentException("Gold goal can not be negative.");
    }
    if (minimumGold == 0) {
      throw new IllegalArgumentException("Doesn't make sense to have gold goal as 0.");
    }
  }

  /**
   * Checks if the goal is fulfilled.
   *
   * @param player the player to check the goal on.
   * @return true if the goal is fulfilled, false otherwise.
   */
  @Override
  public boolean isFullfilled(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Cannot execute action: player is null.");
    }
    return player.getGold() >= minimumGold;
  }

  /**
   * Returns the goal.
   *
   * @param <T> the type of the goal.
   * @return the goal.
   */
  @Override
  public <T> T getGoal() {
    return (T) (Integer) minimumGold;
  }

  /**
   * Returns the goal as a string.
   *
   * @return the goal as a string.
   */
  @Override
  public String toString() {
    return "Gold goal: " + minimumGold;
  }
}
