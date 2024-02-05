package edu.ntnu.idatt2001.model.actions;

import edu.ntnu.idatt2001.model.Player;

/**
 * Class for health removing actions.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class RemoveHealthAction implements Action {
  private final int health;
  private final String text;

  /**
   * Constructor for health action.
   *
   * @param health the amount of health to be added.
   * @throws IllegalArgumentException if health is less than or equal to 0.
   */
  public RemoveHealthAction(int health) throws IllegalArgumentException {
    if (health <= 0) {
      throw new IllegalArgumentException("Health must be a positive integer.");
    }
    this.health = (health * -1);
    this.text = "health";
  }

  /**
   * Executes the health removing actions.
   *
   * @param player the player to execute the action on.
   * @throws IllegalArgumentException if player is null or if error occurs while executing.
   */
  @Override
  public void execute(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Cannot execute GoldAction with a null player. Please "
          + "provide a valid player object.");
    }
    try {
      player.addHealth(health);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("Error while executing GoldAction.");
    }
  }

  /**
   * Reverses the health action.
   *
   * @param player the player to reverse the action on.
   * @throws IllegalArgumentException if player is null or if error occurs while reversing.
   */
  @Override
  public void reverse(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Cannot execute GoldAction with a null player. Please "
          + "provide a valid player object.");
    }
    try {
      player.addHealth(-health);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("Error while reversing GoldAction.");
    }
  }

  /**
   * Returns the health action as a string.
   *
   * @return the health action as a string.
   */
  @Override
  public String toString() {
    return text + ": " + health;
  }
}
