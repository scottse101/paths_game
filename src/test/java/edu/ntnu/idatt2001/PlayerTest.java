package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.model.Player;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

  private Player player;
  private Image testImage;

  @BeforeEach
  public void setUp() throws FileNotFoundException {
    player = new Player("Bob", 100, 100, 100);
    testImage = (new Image(new FileInputStream("src/test/resources/player.png"))); // Create a test image object
  }

  @Nested
  @DisplayName("Tests for get-methods in Player")
  class getTests {
    @Test
    void getName() {
      Player player = new Player("Alice", 100, 0, 10);
      assertEquals("Alice", player.getName());
    }

    @Test
    void getHealth() {
      Player player = new Player("Charlie", 80, 0, 8);
      assertEquals(80, player.getHealth());
    }

    @Test
    void getScore() {
      Player player = new Player("Eve", 90, 30, 9);
      assertEquals(30, player.getScore());
    }

    @Test
    void getGold() {
      Player player = new Player("Grace", 60, 10, 3);
      assertEquals(3, player.getGold());
    }

    @Test
    void getInventory() {
      Player player = new Player("Isabelle", 40, 0, 0);
      assertEquals(Arrays.asList(), player.getInventory());
    }

    @Test
    void getCharactherImage() {
      player.setCharacterImage(testImage);
      Image result = player.getCharacterImage();
      assertEquals(testImage, result);
    }
  }

  @Nested
  @DisplayName("Tests for set-methods in Player")
  class setTests {
    void setName() {
      Player player = new Player("Henry", 50, 10, 10);
      player.setName("Jack");
      assertEquals("Jack", player.getName());
    }

    void setHealth() {
      Player player = new Player("Jack", 50, 10, 20);
      player.setHealth(30);
      assertEquals(30, player.getHealth());
    }

    void setScore() {
      Player player = new Player("Jack", 30, 10, 20);
      player.setScore(20);
      assertEquals(20, player.getScore());
    }

    void setGold() {
      Player player = new Player("Diggle", 30, 10, 20);
      player.setGold(20);
      assertEquals(20, player.getGold());
    }

    void setInventory() {
      Player player = new Player("Red", 30, 10, 20);
      player.setInventory(new ArrayList<String>(Arrays.asList("Sword", "Shield")));
      assertEquals(Arrays.asList("Sword", "Shield"), player.getInventory());
    }
  }

  @Nested
  @DisplayName("Tests for add-methods in Player")
  class addTests {
    @Test
    void addHealth() {
      Player player = new Player("Bob", 50, 0, 5);
      player.addHealth(20);
      assertEquals(70, player.getHealth());
    }

    @Test
    void addScore() {
      Player player = new Player("Dave", 100, 50, 10);
      player.addScore(20);
      assertEquals(70, player.getScore());
    }

    @Test
    void addGold() {
      Player player = new Player("Frank", 70, 20, 5);
      player.addGold(10);
      assertEquals(15, player.getGold());
    }

    @Test
    void addToInventory() {
      Player player = new Player("Henry", 50, 0, 0);
      player.addToInventory("sword");
      player.addToInventory("shield");
      assertEquals(Arrays.asList("sword", "shield"), player.getInventory());
    }

    @Test
    void addItemsToInventory() {
      Player player = new Player("Jack", 30, 0, 0);
      player.addItemsToInventory(new ArrayList<String>(Arrays.asList("sword", "shield")));
      assertEquals(Arrays.asList("sword", "shield"), player.getInventory());
    }

    @Test
    void addingMultipleItemsNoDuplicate() {
      Player player = new Player("Jack", 30, 0, 0);
      player.addToInventory("sword");
      player.addItemsToInventory(new ArrayList<String>(Arrays.asList("sword", "shield")));
      assertEquals(Arrays.asList("sword", "shield"), player.getInventory());
    }

    @Test
    void addingMultipleItemsWithDuplicate() {
      Player player = new Player("Jack", 30, 0, 0, new ArrayList<String>(Arrays.asList("sword",
              "shield", "axe")));
      player.addItemsToInventory(new ArrayList<String>(Arrays.asList("shield", "bone")));
      assertEquals(Arrays.asList("sword", "shield", "axe", "bone"), player.getInventory());
    }

    @Test
    void addingMultipleItemsWithDuplicateAndNull() {
      Player player = new Player("Jack", 30, 0, 0, new ArrayList<String>(Arrays.asList("sword",
              "shield", "axe")));
      assertThrows(IllegalArgumentException.class, () -> {
        player.addItemsToInventory(new ArrayList<String>(Arrays.asList("shield", null)));
      });
    }
  }
}
