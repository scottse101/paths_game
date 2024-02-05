package edu.ntnu.idatt2001.model.actions;

import edu.ntnu.idatt2001.model.Player;

/**
 * Class for score removing actions.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class RemoveScoreAction implements Action {
  private final int score;
  private final String text;

  /**
   * Constructor for score action.
   *
   * @param score the amount of score to be removed.
   * @throws IllegalArgumentException if score is less than or equal to 0.
   */
  public RemoveScoreAction(int score) throws IllegalArgumentException {
    if (score <= 0) {
      throw new IllegalArgumentException("Score must be a positive integer.");
    }
    this.score = (score * -1);
    this.text = "score";
  }

  /**
   * Executes the score action.
   *
   * @param player the player to execute the action on.
   * @throws IllegalArgumentException if player is null or if error occurs while executing.
   */
  @Override
  public void execute(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Cannot execute ScoreAction with a null player. Please "
          + "provide a valid player object.");
    }
    try {
      player.addScore(score);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("Error while executing ScoreAction.");
    }
  }

  /**
   * Reverses the score action.
   *
   * @param player the player to reverse the action on.
   * @throws IllegalArgumentException if player is null or if error occurs while reversing.
   */
  @Override
  public void reverse(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("Cannot execute ScoreAction with a null player. Please "
          + "provide a valid player object.");
    }
    try {
      player.addScore(-score);
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("Error while reversing ScoreAction.");
    }
  }

  /**
   * Returns the score action as a string.
   *
   * @return the score action as a string.
   */
  @Override
  public String toString() {
    return text + ": " + score;
  }
}
