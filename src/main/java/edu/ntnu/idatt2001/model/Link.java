package edu.ntnu.idatt2001.model;

import edu.ntnu.idatt2001.model.actions.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class for the links in the story.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class Link {
  private String text;
  private String reference;
  private List<Action> actions;

  /**
   * Constructor for the link.
   *
   * @param text the text of the link.
   * @param reference the reference of the link.
   */
  public Link(String text, String reference) {
    this.text = text;
    this.reference = reference;
    this.actions = new ArrayList<>();
  }

  /**
   * Returns the text of the link.
   *
   * @return the text of the link.
   */
  public String getText() {
    return text;
  }

  /**
   * Returns the reference of the link.
   *
   * @return the reference of the link.
   */
  public String getReference() {
    return reference;
  }

  /**
   * Returns the actions of the link.
   *
   * @return the actions of the link.
   */
  public List<Action> getActions() {
    return actions;
  }

  /**
   * Adds an action to the link.
   *
   * @param action the action to be added.
   */
  public void addActions(Action action) {
    actions.add(action);
  }

  /**
   * Sets the actions of the link.
   *
   * @param actions the actions to be set.
   */
  public void setActions(List<Action> actions) {
    this.actions = actions;
  }

  /**
   * Returns the string representation of the link.
   *
   * @return the string representation of the link.
   */
  @Override
  public String toString() {
    return "Link{"
        + "text='" + text + '\'' + ", reference='" + reference + '\'' + '}';
  }

  /**
   * Returns true if the link is equal to the object.
   *
   * @param o the object to be compared.
   * @return true if the link is equal to the object.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Link link = (Link) o;
    return Objects.equals(reference, link.reference);
  }

  /**
   * Returns the hashcode of the link.
   *
   * @return the hashcode of the link.
   */
  @Override
  public int hashCode() {
    return Objects.hash(reference);
  }
}