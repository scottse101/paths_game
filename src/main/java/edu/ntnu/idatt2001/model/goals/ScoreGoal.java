package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;

/**
 * Class for score goals.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class ScoreGoal implements Goal {
  private final int minimumPoints;

  /**
   * Constructor for score goal.
   *
   * @param minimumPoints the minimum points to fulfill the goal.
   */
  public ScoreGoal(int minimumPoints) {
    this.minimumPoints = minimumPoints;
    if (minimumPoints < 0) {
      throw new IllegalArgumentException("Score goal can not be negative.");
    }
    if (minimumPoints == 0) {
      throw new IllegalArgumentException("Doesn't make sense to have score goal as 0.");
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
    return player.getScore() >= minimumPoints;
  }


  /**
   * Returns the goal.
   *
   * @param <T> the type of the goal.
   * @return the goal.
   */
  @Override
  public <T> T getGoal() {
    return (T) (Integer) minimumPoints;
  }

  /**
   * Returns the goal as a string.
   *
   * @return the goal as a string.
   */
  @Override
  public String toString() {
    return "Score goal: " + minimumPoints;
  }
}
