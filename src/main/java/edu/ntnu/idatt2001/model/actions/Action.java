package edu.ntnu.idatt2001.model.actions;

import edu.ntnu.idatt2001.model.Player;


/**
 * Interface class for actions.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public interface Action {
  /**
   * Executes the action.
   *
   * @param player the player to execute the action on.
   */
  public void execute(Player player);

  /**
   * Reverses the action.
   *
   * @param player the player to reverse the action on.
   */
  public void reverse(Player player);

  /**
   * Returns the action as a string.
   *
   * @return the action as a string.
   */
  public String toString();
}
