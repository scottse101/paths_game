package edu.ntnu.idatt2001.model.designpatterns;

import edu.ntnu.idatt2001.model.Player;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

/**
 * Class for building players.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class PlayerBuilder {
  private String name;
  private int health;
  private int score;
  private int gold;
  private List<String> inventory;
  private Image characterImage;

  /**
   * Constructor for player builder.
   */
  public PlayerBuilder() {
    this.inventory = new ArrayList<>();
  }

  /**
   * Adds the name to the player.
   *
   * @return the player builder.
   */
  public PlayerBuilder withName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Adds the health to the player.
   *
   * @return the player builder.
   */
  public PlayerBuilder withHealth(int health) {
    this.health = health;
    return this;
  }

  /**
   * Adds the score to the player.
   *
   * @return the player builder.
   */
  public PlayerBuilder withScore(int score) {
    this.score = score;
    return this;
  }

  /**
   * Adds the gold to the player.
   *
   * @return the player builder.
   */
  public PlayerBuilder withGold(int gold) {
    this.gold = gold;
    return this;
  }

  /**
   * Adds the item to the player's inventory.
   *
   * @param item the item to be added.
   * @return the player builder.
   *
   * @throws IllegalArgumentException if item is null.
   * @throws IllegalArgumentException if item already exists in inventory.
   *
   */
  public PlayerBuilder addToInventory(String item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null");
    }
    boolean itemExists = false;
    for (String itemInInventory : inventory) {
      if (itemInInventory.equals(item)) {
        itemExists = true;
        break;
      }
    }
    if (!itemExists) {
      inventory.add(item);
    } else {
      throw new IllegalArgumentException("Item already exists in inventory");
    }
    return this;
  }

  /**
   * Adds the items to the player's inventory.
   *
   * @param items the items to be added.
   * @return the player builder.
   *
   * @throws IllegalArgumentException if item is null.
   * @throws IllegalArgumentException if item already exists in inventory.
   *
   */
  public PlayerBuilder addItemsToInventory(ArrayList<String> items) {
    for (String item : items) {
      if (item == null) {
        throw new IllegalArgumentException("Item cannot be null");
      }
      boolean itemExists = false;
      for (String itemInInventory : inventory) {
        if (itemInInventory.equals(item)) {
          itemExists = true;
          break;
        }
      }
      if (!itemExists) {
        inventory.add(item);
      } else {
        throw new IllegalArgumentException("Item already exists in inventory");
      }
    }
    return this;
  }

  /**
   * Adds the character image to the player.
   *
   * @return the player builder.
   */
  public PlayerBuilder withCharacterImage(Image characterImage) {
    this.characterImage = characterImage;
    return this;
  }

  /**
   * Validates the player data.
   *
   * @return a list of errors.
   */
  public List<String> validate() {
    List<String> errors = new ArrayList<>();
    if (name == null || name.isBlank()) {
      errors.add("Name cannot be null");
    }
    if (health <= 0) {
      errors.add("Health must be greater than 0");
    }
    if (score < 0) {
      errors.add("Score cannot be negative");
    }
    if (gold < 0) {
      errors.add("Gold cannot be negative");
    }
    return errors;
  }

  /**
   * Builds the player.
   *
   * @return the player.
   *
   * @throws IllegalStateException if player data is invalid.
   */
  public Player build() {
    List<String> errors = validate();
    if (!errors.isEmpty()) {
      throw new IllegalStateException("Invalid player data: " + String.join(", ", errors));
    }
    return new Player(name, health, score, gold, inventory, characterImage);
  }
}
