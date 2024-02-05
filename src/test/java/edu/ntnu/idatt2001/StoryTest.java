package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.controller.FileHandling;
import edu.ntnu.idatt2001.model.Link;
import edu.ntnu.idatt2001.model.Passage;
import edu.ntnu.idatt2001.model.Story;
import edu.ntnu.idatt2001.model.actions.Action;
import edu.ntnu.idatt2001.model.actions.GoldAction;
import edu.ntnu.idatt2001.model.actions.InventoryAction;
import org.junit.Test;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoryTest {
  private Story story;
  private Passage passage1;
  private Passage passage2;

  @Before
  public void setUp() {
    passage1 = new Passage("Passage 1", "This is the first passage.");
    passage2 = new Passage("Passage 2", "This is the second passage.");
    story = new Story("My Story", passage1);
  }

  @Nested
  @DisplayName("Testing the stories with passage- titles and content")
  class StoryTests {

    @Test
    @DisplayName("Test if the title is acquired correctly")
    public void testGetTitle() {
      assertEquals("My Story", story.getTitle());
    }

    @Test
    @DisplayName("Test if the opening passage is acquired correctly")
    public void testGetOpeningPassage() {
      assertEquals(passage1, story.getOpeningPassage());
    }

    @Test
    @DisplayName("Test if the passages are added correctly")
    public void testAddPassage() {
      story.addPassage(passage2);
      Collection<Passage> passages = story.getPassages();
      assertEquals(2, passages.size());
      assertTrue(passages.contains(passage1));
      assertTrue(passages.contains(passage2));
    }

    @Test
    @DisplayName("Test if the passages are linked correctly")
    public void testGetPassage() {
      Link link = new Link("Passage 1", "Passage 1");
      assertEquals(passage1, story.getPassage(link));
    }

    @Test
    @DisplayName("Test if passages with links in the story are returned correctly")
    public void testRemovePassageWithLink() {
      Story story = new Story("Haunted House", new Passage("Beginnings", "You are in a dark room."));
      Link firstLink = new Link("Go to the first room", "First Room");
      story.getOpeningPassage().addLink(firstLink);
      Passage firstPassage = new Passage("First Room", "You are in the first room.");
      story.addPassage(firstPassage);
      story.removePassage(firstLink);
      assertEquals(2, story.getPassages().size());
    }

    @Test
    @DisplayName("Test if passages with no links in the story are returned correctly")
    public void testRemovePassageWithNoLinks() {
      Story story = new Story("Haunted House", new Passage("Beginnings", "You are in a dark room."));
      Link firstLink = new Link("Go to the first room", "First Room");
      story.getOpeningPassage().addLink(firstLink);
      Passage firstPassage = new Passage("First Room", "You are in the first room.");
      story.addPassage(firstPassage);
      story.addPassage(new Passage("Second room", "You are in the second room."));
      story.removePassage(new Link("Second room", "Second room"));
      assertEquals(2, story.getPassages().size());
    }

    @Test
    @DisplayName("Test if the passages with broken links are added correctly")
    public void testGetBrokenLinks() {
      Story story = new Story("My Story", new Passage("Beginning", "You wake up in a dark room."));
      Passage passage1 = new Passage("Passage 1", "You enter a room filled with treasure.");
      story.addPassage(passage1);
      Passage passage2 = new Passage("Passage 2", "You enter a room filled with monsters.");
      story.addPassage(passage2);
      Passage passage3 = new Passage("Passage 3", "You enter a room filled with puzzles.");
      story.addPassage(passage3);

      // Add some links, including some that are dead
      story.getOpeningPassage().addLink(new Link("Go to Passage 1", "Passage 1"));
      story.getOpeningPassage().addLink(new Link("Go to Passage 2", "Passage 2"));
      story.getOpeningPassage().addLink(new Link("Go to Passage 4", "Passage 4")); // Dead link
      passage1.addLink(new Link("Go to Passage 3", "Passage 3"));
      passage1.addLink(new Link("Go to Passage 4", "Passage 4")); // Dead link
      passage2.addLink(new Link("Go to Passage 3", "Passage 3"));

      List<Link> brokenLinks = story.getBrokenLinks();

      // Check that the method returns the correct dead links
      assertEquals(2, brokenLinks.size());
      assertTrue(brokenLinks.contains(new Link("Go to Passage 4", "Passage 4")));
    }
  }

  @Nested
  @DisplayName("Tests including file handling")
  public class FileHandlingTests {
    @Test
    @DisplayName("Test if the storywriting throws an exception when trying to add a different passages")
    public void testWritingStoryToFile() throws IOException {
      Story story = new Story("My Story", new Passage("Beginning", "You wake up in a dark room."));
      Passage passage1 = new Passage("Passage 1", "You enter a room filled with treasure.");
      story.addPassage(passage1);
      Passage passage2 = new Passage("Passage 2", "You enter a room filled with monsters.");
      story.addPassage(passage2);
      Passage passage3 = new Passage("Passage 3", "You enter a room filled with puzzles.");
      story.addPassage(passage3);

      // Add some links, including some that are dead
      story.getOpeningPassage().addLink(new Link("Go to Passage 1", "Passage 1"));
      story.getOpeningPassage().addLink(new Link("Go to Passage 2", "Passage 2"));
      story.getOpeningPassage().addLink(new Link("Go to Passage 4", "Passage 4")); // Dead link
      passage1.addLink(new Link("Go to Passage 3", "Passage 3"));
      passage1.addLink(new Link("Go to Passage 4", "Passage 4")); // Dead link
      passage2.addLink(new Link("Go to Passage 3", "Passage 3"));

      FileHandling fileHandling = FileHandling.getInstance();
      fileHandling.saveStoryToFile("src/test/resources/test.paths", story);
    }

    @Test
    @DisplayName("Test if the storywriting throws an exception when trying to add a title and content to the story")
    public void testWritingStoryN2() {
      Story myStory = new Story("Haunted House", new Passage("Beginnings", "You are in a small, dimly lit room. There is a door in front of you."));
      FileHandling fileHandling = FileHandling.getInstance();

      try {
        fileHandling.saveStoryToFile("src/test/resources/myStory.paths", myStory);
      } catch (IOException e) {
        System.err.println("Failed to save story to file: " + e.getMessage());
      }
    }

    @Test
    @DisplayName("Test if the storywriting throws an exception when trying to load a story from a file")
    public void testLoadStoryFromFile() throws IOException {
      String filePath = "src/test/resources/test.paths";
      FileHandling fileHandling = FileHandling.getInstance();

      Story story = fileHandling.loadStoryFromFile(new File(filePath));

      // Verify the title of the story
      assertEquals("My Story", story.getTitle());

      // Verify the opening passage
      Passage openingPassage = story.getOpeningPassage();
      assertEquals("Beginning", openingPassage.getTitle());
      assertEquals("You wake up in a dark room.", openingPassage.getContent());

      // Verify the links in the opening passage
      List<Link> openingPassageLinks = openingPassage.getLinks();
      assertEquals(4, openingPassageLinks.size());
      assertEquals("Beginning", openingPassageLinks.get(0).getReference());
      assertEquals("Beginning", openingPassageLinks.get(0).getText());
      assertEquals("Passage 1", openingPassageLinks.get(1).getReference());
      assertEquals("Go to Passage 1", openingPassageLinks.get(1).getText());
      assertEquals("Passage 2", openingPassageLinks.get(2).getReference());
      assertEquals("Go to Passage 2", openingPassageLinks.get(2).getText());
      assertEquals("Passage 4", openingPassageLinks.get(3).getReference());
      assertEquals("Go to Passage 4", openingPassageLinks.get(3).getText());

      // Verify Passage 1
      Link link1 = new Link("Go to Passage 1", "Passage 1");
      Passage passage1 = story.getPassage(link1);
      assertEquals("You enter a room filled with treasure.", passage1.getContent());

      // Verify the links in Passage 1
      List<Link> passage1Links = passage1.getLinks();
      assertEquals(3, passage1Links.size());
      assertEquals("Passage 1", passage1Links.get(0).getReference());
      assertEquals("Passage 1", passage1Links.get(0).getText());
      assertEquals("Passage 3", passage1Links.get(1).getReference());
      assertEquals("Go to Passage 3", passage1Links.get(1).getText());
      assertEquals("Passage 4", passage1Links.get(2).getReference());
      assertEquals("Go to Passage 4", passage1Links.get(2).getText());

      // Verify Passage 2
      Link link2 = new Link("Go to Passage 2", "Passage 2");
      Passage passage2 = story.getPassage(link2);
      assertEquals("You enter a room filled with monsters.", passage2.getContent());

      // Verify the links in Passage 2
      List<Link> passage2Links = passage2.getLinks();
      assertEquals(2, passage2Links.size());
      assertEquals("Passage 2", passage2Links.get(0).getReference());
      assertEquals("Passage 2", passage2Links.get(0).getText());
      assertEquals("Passage 3", passage2Links.get(1).getReference());
      assertEquals("Go to Passage 3", passage2Links.get(1).getText());

      // Verify Passage 3
      Link link3 = new Link("Go to Passage 3", "Passage 3");
      Passage passage3 = story.getPassage(link3);
      assertEquals("You enter a room filled with puzzles.", passage3.getContent());

      // Verify the links in Passage 3
      List<Link> passage3Links = passage3.getLinks();
      assertEquals(1, passage3Links.size());
      assertEquals("Passage 3", passage3Links.get(0).getReference());
      assertEquals("Passage 3", passage3Links.get(0).getText());
    }

    @Test
    @DisplayName("Test if the storywriting throws an exception when trying to add actions to the story")
    public void AddingActionsTest() throws IOException {
      Story story = new Story("My Story", new Passage("Beginning", "You wake up in a dark room."));
      Passage passage1 = new Passage("Passage 1", "You enter a room filled with treasure.");
      story.addPassage(passage1);
      Passage passage2 = new Passage("Passage 2", "You enter a room filled with monsters.");
      story.addPassage(passage2);
      Passage passage3 = new Passage("Passage 3", "You enter a room filled with puzzles.");
      story.addPassage(passage3);

      // Add some links, including some that are dead
      story.getOpeningPassage().addLink(new Link("Go to Passage 1", "Passage 1"));
      story.getOpeningPassage().addLink(new Link("Go to Passage 2", "Passage 2"));
      story.getOpeningPassage().addLink(new Link("Go to Passage 4", "Passage 4")); // Dead link
      Link linkToPassage4 = new Link("Go to Passage 4", "Passage 4");
      InventoryAction foundSword = new InventoryAction("Sword");
      linkToPassage4.addActions(foundSword);
      Link linkToPassage3 = new Link("Go to Passage 3", "Passage 3");
      GoldAction found10Gold = new GoldAction(10);
      linkToPassage3.addActions(found10Gold);
      GoldAction lose20Gold = new GoldAction(20);
      linkToPassage3.addActions(lose20Gold);
      passage1.addLink(linkToPassage3);
      passage1.addLink(linkToPassage4); // Dead link
      passage2.addLink(new Link("Go to Passage 3", "Passage 3"));

      FileHandling fileHandling = FileHandling.getInstance();

      fileHandling.saveStoryToFile("src/test/resources/actionsTest.paths", story);
    }

    @Test
    @DisplayName("Test if the storywriting throws an exception with already added actions to the story")
    public void testReadingFromStoryWithActions() throws IOException {
      String filePath = "src/test/resources/actionsTest.paths";

      FileHandling fileHandling = FileHandling.getInstance();

      Story story = fileHandling.loadStoryFromFile(new File(filePath));

      assertNotNull(story);
      assertEquals("My Story", story.getTitle());
      assertNotNull(story.getOpeningPassage());

      Passage openingPassage = story.getOpeningPassage();
      assertEquals("Beginning", openingPassage.getTitle());
      assertEquals("You wake up in a dark room.", openingPassage.getContent());
      assertEquals(4, openingPassage.getLinks().size());

      Passage passage3 = story.getPassage(new Link("Passage 3", "Passage 3"));
      assertNotNull(passage3);
      assertEquals("You enter a room filled with puzzles.", passage3.getContent());
      assertEquals(1, passage3.getLinks().size());

      Passage passage2 = story.getPassage(new Link("Passage 2", "Passage 2"));
      assertNotNull(passage2);
      assertEquals("You enter a room filled with monsters.", passage2.getContent());
      assertEquals(2, passage2.getLinks().size());

      Link passage2Link = passage2.getLinks().get(1);
      assertEquals("Go to Passage 3", passage2Link.getText());
      assertEquals("Passage 3", passage2Link.getReference());
      assertEquals(0, passage2Link.getActions().size());

      Passage passage1 = story.getPassage(new Link("Passage 1", "Passage 1"));
      assertNotNull(passage1);
      assertEquals("You enter a room filled with treasure.", passage1.getContent());
      assertEquals(3, passage1.getLinks().size());

      Link passage1Link1 = passage1.getLinks().get(1);
      assertEquals("Go to Passage 3", passage1Link1.getText());
      assertEquals("Passage 3", passage1Link1.getReference());
      assertEquals(2, passage1Link1.getActions().size());

      Link passage1Link2 = passage1.getLinks().get(2);
      assertEquals("Go to Passage 4", passage1Link2.getText());
      assertEquals("Passage 4", passage1Link2.getReference());
      assertEquals(1, passage1Link2.getActions().size());

      Action action1 = passage1Link2.getActions().get(0);
      assertTrue(action1 instanceof InventoryAction);
      //assertEquals(10, ((GoldAction) action1).getValue());

      Action action2 = passage1Link1.getActions().get(1);
      assertTrue(action2 instanceof GoldAction);
      //assertEquals(-20, ((GoldAction) action2).getValue());

      Link passage1Link3 = passage1.getLinks().get(2);
      //assertEquals("Sword", passage1Link3.getActions().get(0).getValue());
      assertEquals(1, passage1Link3.getActions().size());
    }

    @Test
    @DisplayName("Test if reading a custom story throws an exception")
    public void testReadingCustomFile() throws IOException {
      String filePath = "src/test/resources/anotherTest.paths";

      FileHandling fileHandling = FileHandling.getInstance();

      Story story = fileHandling.loadStoryFromFile(new File(filePath));

      Passage passage4 = story.getPassage(new Link("Passage 4", "Passage 4"));
      assertNotNull(passage4);

      System.out.println(story.getBrokenLinks().size());

    }

    @Test
    @DisplayName("Test if the error messages in the paths.file throws an exception")
    public void testingErrorMessages() throws IOException {
      String filePath = "src/main/resources/stories/simpleStory.paths";

      FileHandling fileHandling = FileHandling.getInstance();

      Story story = fileHandling.loadStoryFromFile(new File(filePath));
    }
  }
}