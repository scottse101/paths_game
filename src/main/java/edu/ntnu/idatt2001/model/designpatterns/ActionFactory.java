package edu.ntnu.idatt2001.model.designpatterns;

import edu.ntnu.idatt2001.model.actions.Action;
import edu.ntnu.idatt2001.model.actions.GoldAction;
import edu.ntnu.idatt2001.model.actions.HealthAction;
import edu.ntnu.idatt2001.model.actions.InventoryAction;
import edu.ntnu.idatt2001.model.actions.RemoveGoldAction;
import edu.ntnu.idatt2001.model.actions.RemoveHealthAction;
import edu.ntnu.idatt2001.model.actions.RemoveInventoryAction;
import edu.ntnu.idatt2001.model.actions.RemoveScoreAction;
import edu.ntnu.idatt2001.model.actions.ScoreAction;

/**
 * Factory class for creating actions.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class ActionFactory {

  /**
   * Creates an action.
   *
   * @param actionType the type of action to be created.
   * @param args       the arguments for the action.
   * @param <T>        the type of arguments. Either an Integer or a String.
   * @return the action.
   * @throws IllegalArgumentException if actionType is null or empty.
   */
  public <T> Action createAction(String actionType, T... args) throws IllegalArgumentException {
    if (actionType == null || actionType.isEmpty()) {
      throw new IllegalArgumentException("Action type cannot be null or empty.");
    }

    switch (actionType.toLowerCase()) {
      case "goldaction" -> {
        if (args.length == 1 && args[0] instanceof Integer) {
          return new GoldAction((int) args[0]);
        } else {
          throw new IllegalArgumentException("Invalid arguments for GoldAction. Expected: int");
        }
      }
      case "removegoldaction" -> {
        if (args.length == 1 && args[0] instanceof Integer) {
          int intValue = (int) args[0];
          return new RemoveGoldAction(intValue);
        } else {
          throw new IllegalArgumentException("Invalid arguments for RemoveGoldAction. "
              + "Expected: int");
        }
      }
      case "scoreaction" -> {
        if (args.length == 1 && args[0] instanceof Integer) {
          return new ScoreAction((int) args[0]);
        } else {
          throw new IllegalArgumentException("Invalid arguments for ScoreAction. Expected: int");
        }
      }
      case "removescoreaction" -> {
        if (args.length == 1 && args[0] instanceof Integer) {
          int intValue = (int) args[0];
          return new RemoveScoreAction(intValue);
        } else {
          throw new IllegalArgumentException("Invalid arguments for RemoveScoreAction. "
              + "Expected: int");
        }
      }
      case "healthaction" -> {
        if (args.length == 1 && args[0] instanceof Integer) {
          return new HealthAction((int) args[0]);
        } else {
          throw new IllegalArgumentException("Invalid arguments for HealthAction. Expected: int");
        }
      }
      case "removehealthaction" -> {
        if (args.length == 1 && args[0] instanceof Integer) {
          int intValue = (int) args[0];
          return new RemoveHealthAction(intValue);
        } else {
          throw new IllegalArgumentException("Invalid arguments for RemoveHealthAction."
              + " Expected: int");
        }
      }
      case "inventoryaction" -> {
        if (args.length == 1 && args[0] instanceof String) {
          return new InventoryAction((String) args[0]);
        } else {
          throw new IllegalArgumentException("Invalid arguments for InventoryAction. "
              + "Expected: String");
        }
      }
      case "removeinventoryaction" -> {
        if (args.length == 1 && args[0] instanceof String) {
          return new RemoveInventoryAction((String) args[0]);
        } else {
          throw new IllegalArgumentException("Invalid arguments for RemoveInventoryAction. "
              + "Expected: String");
        }
      }
      default -> throw new IllegalArgumentException("Unsupported action type: " + actionType);
    }
  }
}
