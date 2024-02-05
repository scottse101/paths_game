package edu.ntnu.idatt2001.model.designpatterns;

import edu.ntnu.idatt2001.model.goals.Goal;
import edu.ntnu.idatt2001.model.goals.GoldGoal;
import edu.ntnu.idatt2001.model.goals.HealthGoal;
import edu.ntnu.idatt2001.model.goals.InventoryGoal;
import edu.ntnu.idatt2001.model.goals.ScoreGoal;
import java.util.List;

/**
 * Factory class for creating goals.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class GoalFactory {
  private static GoalFactory instance;

  /**
   * Private constructor for the GoalFactory, used to create a singleton pattern.
   */
  private GoalFactory() {
  }

  /**
   * Returns the instance of the GoalFactory. If the instance is null, it creates a new instance.
   *
   * @return the instance of the GoalFactory.
   */
  public static synchronized GoalFactory getInstance() {
    if (instance == null) {
      instance = new GoalFactory();
    }
    return instance;
  }

  /**
   * Creates a goal based on the goal type and parameter.
   *
   * @param goalType the type of goal to create.
   * @param parameter the parameter for the goal.
   * @param <T> the type of the parameter.
   * @return the created goal.
   */
  public <T> Goal createGoal(String goalType, T parameter) {
    switch (goalType.toLowerCase()) {
      case "gold" -> {
        if (parameter instanceof Integer) {
          return new GoldGoal((int) parameter);
        }
        throw new IllegalArgumentException("Invalid parameter type for 'gold' goal: "
            + parameter.getClass().getSimpleName());
      }
      case "health" -> {
        if (parameter instanceof Integer) {
          return new HealthGoal((int) parameter);
        }
        throw new IllegalArgumentException("Invalid parameter type for 'health' goal: "
            + parameter.getClass().getSimpleName());
      }
      case "score" -> {
        if (parameter instanceof Integer) {
          return new ScoreGoal((int) parameter);
        }
        throw new IllegalArgumentException("Invalid parameter type for 'score' goal: "
            + parameter.getClass().getSimpleName());
      }
      case "inventory" -> {
        if (parameter instanceof List) {
          return new InventoryGoal((List<String>) parameter);
        }
        throw new IllegalArgumentException("Invalid parameter type for 'inventory' goal: "
            + parameter.getClass().getSimpleName());
      }
      // Add more cases for other types of goals as needed
      default -> throw new IllegalArgumentException("Unknown goal type: " + goalType);
    }
  }
}
