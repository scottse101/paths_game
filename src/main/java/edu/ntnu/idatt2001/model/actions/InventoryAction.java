package edu.ntnu.idatt2001.model.actions;

import edu.ntnu.idatt2001.model.Player;

/**
 * Class for inventory actions.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class InventoryAction implements Action {
  private final String item;
  private final String text;

  /**
   * Constructor for inventory action.
   *
   * @param item the item to be added.
   * @throws IllegalArgumentException if item is null.
   */
  public InventoryAction(String item) throws IllegalArgumentException {
    if (item == null || item.isBlank()) {
      throw new IllegalArgumentException("Item cannot be null.");
    }
    this.item = item;
    this.text = "inventory";
  }

  /**
   * Executes the inventory action.
   *
   * @param player the player to execute the action on.
   * @throws IllegalArgumentException if player is null or if duplicate items are added.
   */
  @Override
  public void execute(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Cannot execute InventoryAction with a null player. "
          + "Please provide a valid player object.");
    }
    try {
      player.addToInventory(item);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Can't have duplicate items in inventory!");
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
      player.removeFromInventory(item);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("Error while reversing InventoryAction.");
    }
  }

  /**
   * Returns the string representation of the inventory action.
   *
   * @return the string representation of the inventory action.
   */
  @Override
  public String toString() {
    return text + ": +" + item;
  }
}
