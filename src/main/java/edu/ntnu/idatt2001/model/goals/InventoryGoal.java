package edu.ntnu.idatt2001.model.goals;

import edu.ntnu.idatt2001.model.Player;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for inventory goals.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class InventoryGoal implements Goal {
  private final List<String> mandatoryItems;

  /**
   * Constructor for inventory goal.
   *
   * @param mandatoryItems the mandatory items to fulfill the goal.
   */
  public InventoryGoal(List<String> mandatoryItems) {
    this.mandatoryItems = mandatoryItems;
    if (mandatoryItems == null) {
      throw new IllegalArgumentException("Mandatory items cannot be null.");
    }
    if (mandatoryItems.isEmpty()) {
      throw new IllegalArgumentException("Mandatory items cannot be empty.");
    }
  }

  /**
   * Checks if the goal is fulfilled.
   *
   * @param player the player to check the goal on.
   * @return true if the goal is fulfilled, false otherwise.
   * @throws IllegalArgumentException if the player is null.
   */
  @Override
  public boolean isFullfilled(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Cannot execute action: player is null.");
    }
    List<String> inventory = player.getInventory();
    return new HashSet<>(inventory).containsAll(mandatoryItems);
  }

  /**
   * Returns the goal.
   *
   * @param <T> the type of the goal.
   * @return the goal.
   */
  @Override
  public <T> T getGoal() {
    return (T) mandatoryItems;
  }

  /**
   * Returns the goal as a string.
   *
   * @return the goal as a string.
   */
  @Override
  public String toString() {
    return "Inventory goal: " + mandatoryItems.stream()
        .map(Object::toString)
        .collect(Collectors.joining(", "));
  }
}
