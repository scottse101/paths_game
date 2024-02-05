package edu.ntnu.idatt2001.model;

import edu.ntnu.idatt2001.model.goals.Goal;
import java.util.List;

/**
 * Class for the game itself.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class Game {
  private Player player;
  private Story story;
  private List<Goal> goals;

  /**
   * Constructor for the game.
   *
   * @param player the player who plays the game.
   * @param story the story of the game.
   * @param goals the goals of the game.
   */
  public Game(Player player, Story story, List<Goal> goals) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }

    if (story == null) {
      throw new IllegalArgumentException("Story cannot be null");
    }

    if (goals == null) {
      throw new IllegalArgumentException("Goals cannot be null");
    }

    for (Goal goal : goals) {
      if (goal == null) {
        throw new IllegalArgumentException("Goals cannot contain null elements");
      }
    }

    this.player = player;
    this.story = story;
    this.goals = goals;
  }

  /**
   * Returns the player.
   *
   * @return the player.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Returns the story.
   *
   * @return the story.
   */
  public Story getStory() {
    return story;
  }

  /**
   * Returns the goals.
   *
   * @return the goals.
   */
  public List<Goal> getGoals() {
    return goals;
  }

  /**
   * Returns the current passage.
   *
   * @return the current passage.
   */
  public Passage begin() {
    return getStory().getOpeningPassage();
  }

  /**
   * Returns the next passage.
   *
   * @param link the link to the next passage.
   * @return the next passage.
   */
  public Passage go(Link link) {
    return getStory().getPassage(link);
  }
}
