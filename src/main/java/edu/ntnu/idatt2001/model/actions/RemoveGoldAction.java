package edu.ntnu.idatt2001.model.actions;

import edu.ntnu.idatt2001.model.Player;

/**
 * Class for gold removing actions.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class RemoveGoldAction implements Action {
  private final int gold;
  private final String text;

  /**
   * Constructor for gold action.
   *
   * @param gold the amount of gold to be removed.
   * @throws IllegalArgumentException if gold is less than or equal to 0.
   */
  public RemoveGoldAction(int gold) throws IllegalArgumentException {
    if (gold <= 0) {
      throw new IllegalArgumentException("Gold must be a positive integer.");
    }
    this.gold = (gold * -1);
    this.text = "gold";
  }

  /**
   * Executes the gold action.
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
      player.addGold(gold);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("Error while executing GoldAction.");
    }
  }

  /**
   * Reverses the gold action.
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
      player.addGold(-gold);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("Error while reversing GoldAction.");
    }
  }

  /**
   * Returns the gold action as a string.
   *
   * @return the gold action as a string.
   */
  @Override
  public String toString() {
    return text + ": " + gold;
  }
}
