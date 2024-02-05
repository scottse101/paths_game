package edu.ntnu.idatt2001.model.actions;

import edu.ntnu.idatt2001.model.Player;

/**
 * Class for inventory removing actions.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class RemoveInventoryAction implements Action {
  private final String item;
  private final String text;

  /**
   * Constructor for inventory action.
   *
   * @param item the item to be removed.
   * @throws IllegalArgumentException if item is null.
   */
  public RemoveInventoryAction(String item) throws IllegalArgumentException {
    if (item == null || item.isEmpty()) {
      throw new IllegalArgumentException("Item must be a non-empty string.");
    }
    this.item = item;
    this.text = "inventory";
  }

  /**
   * Executes the inventory action.
   *
   * @param player the player to execute the action on.
   * @throws IllegalArgumentException if player is null or if error occurs while executing.
   */
  @Override
  public void execute(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Cannot execute InventoryAction with a null player. "
          + "Please provide a valid player object.");
    }
    try {
      player.removeFromInventory(item);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("Item not found in inventory!");
    }
  }

  /**
   * Reverses the inventory action.
   *
   * @param player the player to reverse the action on.
   * @throws IllegalArgumentException if player is null or if error occurs while reversing.
   */
  @Override
  public void reverse(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Cannot execute InventoryAction with a null player. "
          + "Please provide a valid player object.");
    }
    try {
      player.addToInventory(item);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("Error while reversing action!");
    }
  }

  /**
   * Returns the text of the action.
   *
   * @return the text of the action.
   */
  @Override
  public String toString() {
    return text + ": " + item;
  }
}
