package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;

/**
 * Class for health goals.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class HealthGoal implements Goal {
  private final int minimumHealth;

  /**
   * Constructor for health goal.
   *
   * @param minimumHealth the minimum health to fulfill the goal.
   */
  public HealthGoal(int minimumHealth) {
    this.minimumHealth = minimumHealth;
    if (minimumHealth < 0) {
      throw new IllegalArgumentException("Health goal can not be negative.");
    }
    if (minimumHealth == 0) {
      throw new IllegalArgumentException("Doesn't make sense to have health goal as 0.");
    }
  }

  @Override
  public boolean isFullfilled(Player player) throws IllegalArgumentException{
    if (player == null) {
      throw new IllegalArgumentException("Cannot execute action: player is null.");
    }
    return player.getHealth() >= minimumHealth;
  }

  @Override
  public <T> T getGoal() {
    return (T) (Integer) minimumHealth;
  }

  @Override
  public String toString() {
    return "Health goal: " + minimumHealth;
  }
}
