package edu.ntnu.idatt2001.model.designpatterns;

import edu.ntnu.idatt2001.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerInfoBuilderTest {
  private PlayerBuilder playerBuilder;

  @BeforeEach
  public void setUp() {
    playerBuilder = new PlayerBuilder();
  }

  @Test
  @DisplayName("Test build player with all attributes")
  public void testBuildPlayerWithAllAttributes() {
    Player player = playerBuilder
        .withName("Lionel")
        .withHealth(100)
        .withScore(0)
        .withGold(10)
        .addToInventory("Bronze sword")
        .addToInventory("Wooden shield")
        .build();

    assertEquals("Lionel", player.getName());
    assertEquals(100, player.getHealth());
    assertEquals(0, player.getScore());
    assertEquals(10, player.getGold());
    List<String> expectedInventory = new ArrayList<>();
    expectedInventory.add("Bronze sword");
    expectedInventory.add("Wooden shield");
    assertEquals(expectedInventory, player.getInventory());
  }

  @Test
  @DisplayName("Test build player with minimum attributes")
  public void testBuildPlayerWithMinimumAttributes() {
    Player player = playerBuilder
        .withName("Cristiano")
        .withHealth(50)
        .withScore(0)
        .withGold(0)
        .build();

    assertEquals("Cristiano", player.getName());
    assertEquals(50, player.getHealth());
    assertEquals(0, player.getScore());
    assertEquals(0, player.getGold());
    assertTrue(player.getInventory().isEmpty());
  }

  @Test
  @DisplayName("Test build player with invalid attributes")
  public void testBuildPlayerWithInvalidAttributes() {
    assertThrows(IllegalStateException.class, () -> {
      playerBuilder
          .withHealth(-10)
          .build();
    });

    assertThrows(IllegalStateException.class, () -> {
      playerBuilder
          .withScore(-5)
          .build();
    });

    assertThrows(IllegalStateException.class, () -> {
      playerBuilder
          .withGold(-20)
          .build();
    });
  }

  @Test
  @DisplayName("Test build player with null name")
  public void testBuildPlayerWithNullName() {
    assertThrows(IllegalStateException.class, () -> {
      playerBuilder
          .withHealth(100)
          .build();
    });
  }

  @Test
  @DisplayName("Test build player with null inventory")
  public void testBuildPlayerWithNullItemInInventory() {
    assertThrows(IllegalArgumentException.class, () -> {
      playerBuilder
          .addToInventory(null)
          .build();
    });
  }

  @Test
  @DisplayName("Test build player with duplicate items in inventory")
  public void testBuildPlayerWithDuplicateItemInInventory() {
    assertThrows(IllegalArgumentException.class, () -> {
      playerBuilder
          .addToInventory("Sword")
          .addToInventory("Shield")
          .addToInventory("Sword")
          .build();
    });
  }

  @Test
  @DisplayName("Test build player with multiple items in inventory")
  public void testBuildPlayerWithMultipleItemsInInventory(){
    ArrayList<String> itemsToAdd = new ArrayList<>();
    itemsToAdd.add("Sword");
    itemsToAdd.add("Shield");
    itemsToAdd.add("Potion");
    Player player =
    playerBuilder
        .withName("Messi")
        .withGold(0)
        .withScore(0)
        .withHealth(99)
        .addItemsToInventory(itemsToAdd)
        .build();

    List<String> expectedInventory = new ArrayList<>();
    expectedInventory.add("Sword");
    expectedInventory.add("Shield");
    expectedInventory.add("Potion");
    assertEquals(expectedInventory, player.getInventory());
  }

  @Test
  @DisplayName("Test build player with null items in inventory")
  public void testBuildPlayerWithDuplicateItemsInInventory(){
    ArrayList<String> itemsToAdd = new ArrayList<>();
    itemsToAdd.add("Sword");
    itemsToAdd.add("Shield");
    itemsToAdd.add("Sword");
    assertThrows(IllegalArgumentException.class, () -> {
      playerBuilder
          .addItemsToInventory(itemsToAdd)
          .build();
    });
  }
}

