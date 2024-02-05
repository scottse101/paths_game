package edu.ntnu.idatt2001.controller;

import edu.ntnu.idatt2001.model.Link;
import edu.ntnu.idatt2001.model.Passage;
import edu.ntnu.idatt2001.model.Story;
import edu.ntnu.idatt2001.model.actions.Action;
import edu.ntnu.idatt2001.model.designpatterns.ActionFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used to save and load stories to and from files.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class FileHandling {
  private static FileHandling instance;

  private FileHandling() {
  }

  /**
   * Returns the instance of the FileHandling class.
   *
   * @return the instance of the FileHandling class.
   */
  public static synchronized FileHandling getInstance() {
    if (instance == null) {
      instance = new FileHandling();
    }
    return instance;
  }

  /**
   * Saves a story to a file.
   *
   * @param filePath the path to the file.
   * @param story    the story to save.
   * @throws IOException if an I/O error occurs.
   */
  public void saveStoryToFile(String filePath, Story story) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      // Write the story title
      writer.write(story.getTitle());
      writer.newLine();
      writer.newLine();

      // Write the opening passage
      Passage openingPassage = story.getOpeningPassage();
      writer.write("::" + openingPassage.getTitle());
      writer.newLine();
      writer.write(openingPassage.getContent());
      writer.newLine();

      // Write the opening passage links
      for (Link link : openingPassage.getLinks()) {
        if (link.getText() != null && !(link.getReference().equals(openingPassage.getTitle()))) {
          writer.write(String.format("[%s](%s)", link.getText(), link.getReference()));
          writer.newLine();
        }
      }

      // Write the other passages
      for (Passage passage : story.getPassages()) {
        if (passage == openingPassage) {
          continue;
        }

        // Write the passage title
        writer.newLine();
        writer.write("::" + passage.getTitle());
        writer.newLine();

        // Write the passage content
        writer.write(passage.getContent());
        writer.newLine();

        // Write the passage links
        for (Link link : passage.getLinks()) {
          if (link.getText() != null && !(link.getReference().equals(passage.getTitle()))) {
            writer.write(String.format("[%s](%s)", link.getText(), link.getReference()));
            writer.newLine();
          }
          if (link.getActions() != null && !link.getActions().isEmpty()) {
            for (Action action : link.getActions()) {
              writer.write("*" + action.toString() + "*");
              writer.newLine();
            }
          }
        }

        // Write a blank line to separate passages
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Loads a story from a file.
   *
   * @param file the file to load the story from.
   * @return the story.
   * @throws IOException if an I/O error occurs.
   */
  public Story loadStoryFromFile(File file) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String title = reader.readLine();
      if (title == null || title.trim().isEmpty() || title.startsWith("::")) {
        throw new IOException("Invalid file format: missing story title.");
      }
      title = title.trim();
      Story story = new Story(title, new Passage(title, ""));
      Passage openingPassage = null;

      String line;
      Passage currentPassage = null;
      Link currentLink = null;

      // Create an instance of ActionFactory
      ActionFactory actionFactory = new ActionFactory();

      int lineNumber = 1;

      while ((line = reader.readLine()) != null) {
        int totalPassages = story.getPassages().size();
        lineNumber++;
        int totalLines = (lineNumber + totalPassages - 1);
        line = line.trim();

        if (line.isEmpty()) {
          // Empty line, indicates end of passage
          currentPassage = null;
          currentLink = null;
          continue;
        }

        if (!line.isEmpty() && currentPassage == null && !line.startsWith("::")) {
          // Non-empty line, indicates start of passage
          throw new IOException("Invalid file format: missing passage title (line " + totalLines
              + ").");
        }

        if (line.startsWith("::")) {
          // Passage title line
          String passageTitle = line.substring(2).trim();
          String passageContent = reader.readLine();
          if (passageContent == null || passageContent.trim().isEmpty()
              || passageContent.startsWith("[")) {
            throw new IOException("Invalid file format: missing content for passage "
                + passageTitle + " (line " + totalLines + ").");
          }
          passageContent = passageContent.trim();
          Passage passage = new Passage(passageTitle, passageContent);

          if (openingPassage == null) {
            openingPassage = passage;
            story.setOpeningPassage(openingPassage);
            story.addPassage(openingPassage);
          } else {
            story.addPassage(passage);
          }

          currentPassage = passage;
          currentLink = null;
        } else if (currentPassage != null) {
          if (line.startsWith("[")) {
            // Link line
            int linkTextStartIndex = line.indexOf('[') + 1;
            int linkTextEndIndex = line.indexOf(']');
            int linkReferenceStartIndex = line.indexOf('(') + 1;
            int linkReferenceEndIndex = line.indexOf(')');

            if (linkTextStartIndex < 0 || linkTextEndIndex < 0 || linkReferenceStartIndex < 0
                || linkReferenceEndIndex < 0) {
              throw new IOException("Invalid file format: invalid link format in passage "
                  + currentPassage.getTitle() + " (line " + totalLines + ").");
            }

            String linkText = line.substring(linkTextStartIndex, linkTextEndIndex);
            String linkReference = line.substring(linkReferenceStartIndex, linkReferenceEndIndex);

            currentLink = new Link(linkText, linkReference);
            currentPassage.addLink(currentLink);
          } else if (!line.trim().isEmpty() && !line.startsWith("*")) {
            throw new IOException("Invalid file format: unexpected text in passage or invalid "
                + "link format in passage  " + currentPassage.getTitle() + " (line " + totalLines
                + ").");
          } else if (line.startsWith("*")) {
            // Action line
            if (currentLink == null) {
              throw new IOException("Invalid file format: action line without a link in passage "
                  + currentPassage.getTitle() + " (line " + totalLines + ").");
            }

            String actionText = line.substring(1, line.length() - 1).trim();
            String[] actionParts = actionText.split("\\s+", 2);
            String actionType = actionParts[0].replace(":", "");
            String actionValue = (actionParts.length > 1) ? actionParts[1].replace("+",
                "") : "";
            int intValue = 0;

            if (actionType.equals("gold") || actionType.equals("score")
                || actionType.equals("health")) {
              // Check if actionValue is an integer
              try {
                intValue = Integer.parseInt(actionValue);
                // If intValue is negative, modify actionType
                if (intValue < 0) {
                  actionType = "remove" + actionType + "action";
                } else if (intValue > 0) {
                  actionType += "action";
                }
              } catch (NumberFormatException e) {
                // If actionValue is not a valid integer, handle the exception as appropriate
                e.printStackTrace();
                throw new IOException("Invalid action value for action type " + actionType + " in"
                    + " line: " + totalLines);
              }
            } else if (actionType.equals("inventory")) {
              actionType += "action";
              // Check if actionValue has a negative sign
              if (actionValue.startsWith("-")) {
                actionType = "remove" + actionType + "action";
                actionValue = actionValue.substring(1);
              }
            } else {
              throw new IOException("Invalid action type in line: " + totalLines);
            }

            Action action = null;

            if (actionType.startsWith("gold") || actionType.startsWith("score") || actionType.startsWith("health")) {
              action = actionFactory.createAction(actionType, intValue);
            } else if (actionType.startsWith("remove")) {
              if (actionType.equals("removeinventoryaction")) {
                action = actionFactory.createAction(actionType, actionValue);
              } else {
                action = actionFactory.createAction(actionType, -intValue);
              }
            } else {
              action = actionFactory.createAction(actionType, actionValue);
            }

            if (action == null) {
              throw new IOException("Invalid action type in line: " + totalLines);
            }

            currentLink.addActions(action);
          }
        } else {
          throw new IOException("Invalid file format in line: " + totalLines);
        }
      }

      return story;
    } catch (IOException e) {
      throw new IOException(e.getMessage(), e);
    }
  }
}
