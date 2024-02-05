package edu.ntnu.idatt2001.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class for the passages in the story.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class Passage {
  private String title;
  private String content;
  private List<Link> links;

  /**
   * Constructor for the passage.
   *
   * @param title the title of the passage.
   * @param content the content of the passage.
   */
  public Passage(String title, String content) {
    this.title = title;
    this.content = content;
    links = new ArrayList<Link>();
  }

  /**
   * Returns the title of the passage.
   *
   * @return the title of the passage.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the content of the passage.
   *
   * @return the content of the passage.
   */
  public String getContent() {
    return content;
  }

  /**
   * Adds a link to the passage.
   *
   * @param link the link to add.
   * @return true if the link was added, false otherwise.
   */
  public boolean addLink(Link link) {
    return links.add(link);
  }

  /**
   * Removes a link from the passage.
   *
   * @param link the link to remove.
   * @return true if the link was removed, false otherwise.
   */
  public boolean removeLink(Link link) {
    return links.remove(link);
  }

  /**
   * Returns the links of the passage.
   *
   * @return the links of the passage.
   */
  public List<Link> getLinks() {
    return links;
  }

  /**
   * Returns true if the passage has links, false otherwise.
   *
   * @return true if the passage has links, false otherwise.
   */
  public boolean hasLinks() {
    return !links.isEmpty();
  }

  /**
   * Returns a string representation of the passage.
   *
   * @return a string representation of the passage.
   */
  @Override
  public String toString() {
    return "Passage{" + "title='" + title + '\'' + ", content='" + content
        + '\'' + ", links=" + links + '}';
  }

  /**
   * Returns true if the passage has the given link, false otherwise.
   *
   * @param o the link to check.
   * @return true if the passage has the given link, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Passage passage = (Passage) o;
    return Objects.equals(title, passage.title) && Objects.equals(content, passage.content)
        && Objects.equals(links, passage.links);
  }

  /**
   * Returns the hashcode of the passage.
   *
   * @return the hashcode of the passage.
   */
  @Override
  public int hashCode() {
    return Objects.hash(title, content, links);
  }
}
