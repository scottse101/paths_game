package edu.ntnu.idatt2001.model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

/**
 * Class for the player in the story.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class Player {
  private String name;
  private int health;
  private int score;
  private int gold;
  private List<String> inventory;
  private Image characterImage;

  /**
   * Constructor for the player.
   *
   * @param name the name of the player.
   * @param health the health of the player.
   * @param score the score of the player.
   * @param gold the gold of the player.
   */
  public Player(String name, int health, int score, int gold) { //constructor for no items in
    // inventory
    this.name = name;
    this.health = health;
    this.score = score;
    this.gold = gold;
    inventory = new ArrayList<String>();
  }

  /**
   * Constructor for a player with an inventory.
   *
   * @param name the name of the player.
   * @param health the health of the player.
   * @param score the score of the player.
   * @param gold the gold of the player.
   * @param inventory the inventory of the player.
   */
  public Player(String name, int health, int score, int gold, List<String> inventory) {
    //constructor for adding items to inventory
    this.name = name;
    this.health = health;
    this.score = score;
    this.gold = gold;
    this.inventory = inventory;
  }

  /**
   * Constructor for a player with an inventory and an image.
   *
   * @param name the name of the player.
   * @param health the health of the player.
   * @param score the score of the player.
   * @param gold the gold of the player.
   * @param inventory the inventory of the player.
   * @param characterImage the image of the player.
   */
  public Player(String name, int health, int score, int gold, List<String> inventory,
                  Image characterImage) { //constructor for adding items and image
    this.name = name;
    this.health = health;
    this.score = score;
    this.gold = gold;
    this.inventory = inventory;
    this.characterImage = characterImage;
  }

  /**
   * Returns the character image of the player.
   *
   * @return the character image of the player.
   */
  public Image getCharacterImage() {
    return characterImage;
  }

  /**
   * Sets the character image of the player.
   *
   * @param characterImage the character image of the player.
   */
  public void setCharacterImage(Image characterImage) {
    this.characterImage = characterImage;
  }

  /**
   * Returns the name of the player.
   *
   * @return the name of the player.
   */
  public String getName() {
    return name;
  }

  /**
   * Adds health to the player.
   *
   * @param health the health to add to the player.
   */
  public void addHealth(int health) {
    this.health += health;
  }

  /**
   * Returns the health of the player.
   *
   * @return the health of the player.
   */
  public int getHealth() {
    return health;
  }

  /**
   * Adds score to the player.
   *
   * @param score the score to add to the player.
   */
  public void addScore(int score) {
    this.score += score;
  }

  /**
   * Returns the score of the player.
   *
   * @return the score of the player.
   */
  public int getScore() {
    return score;
  }

  /**
   * Adds gold to the player.
   *
   * @param gold the gold to add to the player.
   */
  public void addGold(int gold) {
    this.gold += gold;
  }

  /**
   * Returns the gold of the player.
   *
   * @return the gold of the player.
   */
  public int getGold() {
    return gold;
  }

  /**
   * Adds an item to the inventory of the player.
   *
   * @param item the item to add to the inventory of the player.
   * @throws IllegalArgumentException if the item is null or empty.
   */
  public void addToInventory(String item) throws IllegalArgumentException {
    if (item == null || item.isBlank()) {
      throw new IllegalArgumentException("Item cannot be null or empty");
    }

    if (inventory.contains(item)) {
      throw new IllegalArgumentException("Item already exists in inventory");
    }

    inventory.add(item);
  }

  /**
   * Removes an item from the inventory of the player.
   *
   * @param item the item to remove from the inventory of the player.
   * @throws IllegalArgumentException if the item is null or empty.
   */
  public void removeFromInventory(String item) throws IllegalArgumentException {
    if (item == null || item.isBlank()) {
      throw new IllegalArgumentException("Item cannot be null or empty");
    }
    boolean itemExists = false;
    for (String itemInInventory : inventory) {
      if (itemInInventory.equals(item)) {
        itemExists = true;
        inventory.remove(itemInInventory);
        break;
      }
    }
    if (!itemExists) {
      throw new IllegalArgumentException("Item does not exist in inventory");
    }
  }

  /**
   * Returns the inventory of the player.
   *
   * @return the inventory of the player.
   */
  public List<String> getInventory() {
    return inventory;
  }

  /**
   * Adds items to the inventory of the player.
   *
   * @param items the items to add to the inventory of the player.
   * @throws IllegalArgumentException if the item is null or empty.
   */
  public void addItemsToInventory(ArrayList<String> items) {
    boolean itemExists = false;
    for (String item : items) {
      if (item == null) {
        throw new IllegalArgumentException("Item cannot be null");
      }
      itemExists = false;
      for (String itemInInventory : inventory) {
        if (itemInInventory.equals(item)) {
          itemExists = true;
          break;
        }
      }
      if (!itemExists) {
        inventory.add(item);
      }
    }
  }

  /**
   * Sets the name of the player.
   *
   * @param name the name of the player.
   */
  public void setName(String name) {  //added setters even though this wasn't in the
    this.name = name; // requirements, as it makes sense to be able to change these properties.
  }

  /**
   * Sets the health of the player.
   *
   * @param health the health of the player.
   */
  public void setHealth(int health) {
    this.health = health;
  }

  /**
   * Sets the score of the player.
   *
   * @param score the score of the player.
   */
  public void setScore(int score) {
    this.score = score;
  }

  /**
   * Sets the gold of the player.
   *
   * @param gold the gold of the player.
   */
  public void setGold(int gold) {
    this.gold = gold;
  }

  /**
   * Sets the inventory of the player.
   *
   * @param inventory the inventory of the player.
   */
  public void setInventory(List<String> inventory) {
    this.inventory = inventory;
  }

  /**
   * Returns the player.
   *
   * @return the player.
   */
  public Player getPlayer() {
    return this;
  }
}
