package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.model.Link;
import edu.ntnu.idatt2001.model.Passage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PassageTest {

  @Test
  @DisplayName("Test if the passage gets the title")
  void getTitle() {
    Passage p = new Passage("Title", "Content");
    assertEquals("Title", p.getTitle());
  }

  @Test
  @DisplayName("Test if the passage gets the content")
  void getContent() {
    Passage p = new Passage("Title", "Content");
    assertEquals("Content", p.getContent());
  }

  @Test
  @DisplayName("Test if the passage adds the link correctly")
  void addLink() {
    Passage p = new Passage("Title", "Content");
    Link l = new Link("https://www.example.com", "Example Website");
    assertTrue(p.addLink(l));
    assertEquals(Arrays.asList(l), p.getLinks());
  }

  @Test
  @DisplayName("Test if the passage adds the links correctly")
  void getLinks() {
    Passage p = new Passage("Title", "Content");
    Link l1 = new Link("https://www.example.com", "Example Website");
    Link l2 = new Link("https://www.google.com", "Google");
    p.addLink(l1);
    p.addLink(l2);
    assertEquals(Arrays.asList(l1, l2), p.getLinks());
  }

  @Test
  @DisplayName("Test if the passage has the links created")
  void hasLinks() {
    Passage p1 = new Passage("Title", "Content");
    assertFalse(p1.hasLinks());
    Passage p2 = new Passage("Title", "Content");
    Link l = new Link("https://www.example.com", "Example Website");
    p2.addLink(l);
    assertTrue(p2.hasLinks());
  }

  @Test
  @DisplayName("Test if the passages links are equal")
  void testEquals() {
    Passage p1 = new Passage("Title", "Content");
    Passage p2 = new Passage("Title", "Content");
    assertEquals(p1, p2);
    Link l = new Link("https://www.example.com", "Example Website");
    p1.addLink(l);
    assertNotEquals(p1, p2);
    p2.addLink(l);
    assertEquals(p1, p2);
  }

  @Test
  @DisplayName("Test if the passages links are equal")
  void testHashCode() {
    Passage p1 = new Passage("Title", "Content");
    Passage p2 = new Passage("Title", "Content");
    assertEquals(p1.hashCode(), p2.hashCode());
    Link l = new Link("https://www.example.com", "Example Website");
    p1.addLink(l);
    assertNotEquals(p1.hashCode(), p2.hashCode());
    p2.addLink(l);
    assertEquals(p1.hashCode(), p2.hashCode());
  }
}