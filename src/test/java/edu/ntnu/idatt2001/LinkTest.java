package edu.ntnu.idatt2001;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2001.model.Link;
import edu.ntnu.idatt2001.model.actions.Action;
import edu.ntnu.idatt2001.model.actions.GoldAction;
import edu.ntnu.idatt2001.model.designpatterns.ActionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class LinkTest {

  private Link link;
  private GoldAction goldAction;

  @BeforeEach
  public void setUp() {
    link = new Link("Link text", "Link reference");
    ActionFactory actionFactory = new ActionFactory();
    GoldAction goldAction = (GoldAction) actionFactory.createAction("goldaction", 100);
  }

    @Test
    @DisplayName("Testing link with a valid text")
    void getText () {
      Link link = new Link("Google", "https://www.google.com");
      assertEquals("Google", link.getText());
    }

    @Test
    @DisplayName("Testing link with a valid reference")
    void getReference () {
      Link link = new Link("Google", "https://www.google.com");
      assertEquals("https://www.google.com", link.getReference());
    }

    @Test
    public void testGetActions () {
      List<Action> result = link.getActions();
      assertNotNull(result);
      assertTrue(result.isEmpty());
    }

    @Test
    public void testAddActions () {
      link.addActions(goldAction);
      List<Action> result = link.getActions();
      assertEquals(1, result.size());
      assertEquals(goldAction, result.get(0));
    }

    @Test
    public void testSetActions () {
      List<Action> actions = new ArrayList<>();
      actions.add(goldAction);
      link.setActions(actions);
      List<Action> result = link.getActions();
      assertEquals(1, result.size());
      assertEquals(goldAction, result.get(0));
    }

    @Test
    @DisplayName("Testing link with a text")
    void testToString () {
      Link link = new Link("Google", "https://www.google.com");
      String expectedString = "Link{text='Google', reference='https://www.google.com'}";
      assertEquals(expectedString, link.toString());
    }

    @Test
    @DisplayName("Testing links with a different texts")
    void testEquals () {
      Link link1 = new Link("Google", "https://www.google.com");
      Link link2 = new Link("Google", "https://www.google.com");
      Link link3 = new Link("Yahoo", "https://www.yahoo.com");

      // Reflexive property
      assertEquals(link1, link1);

      // Symmetric property
      assertEquals(link1, link2);
      assertEquals(link2, link1);

      // Transitive property
      assertEquals(link1, link2);
      assertNotEquals(link2, link3);
      assertNotEquals(link1, link3);

      // Consistent property
      assertEquals(link1, link2);
      assertEquals(link1, link2);
      assertEquals(link1, link2);
    }

    @Test
    @DisplayName("Testing links with a different texts")
    void testHashCode () {
      Link link1 = new Link("Google", "https://www.google.com");
      Link link2 = new Link("Google", "https://www.google.com");
      Link link3 = new Link("Yahoo", "https://www.yahoo.com");

      assertEquals(link1.hashCode(), link2.hashCode());
      assertNotEquals(link1.hashCode(), link3.hashCode());
    }
  }


