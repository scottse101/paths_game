package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;

/**
 * Interface class for goals.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public interface Goal {

  /**
   * Checks if the goal is fulfilled.
   *
   * @param player the player to check the goal on.
   * @return true if the goal is fulfilled, false otherwise.
   */
  public boolean isFullfilled(Player player);

  /**
   * Returns the goal.
   *
   * @param <T> the type of the goal.
   * @return the goal.
   */
  public <T> T getGoal();

  /**
   * Returns the goal as a string.
   *
   * @return the goal as a string.
   */
  public String toString();
}
