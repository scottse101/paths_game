package edu.ntnu.idatt2001.view.utility;

import javafx.scene.text.Font;

/**
 * This class is used to load a custom font.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class LoadCustomFont {
  private static LoadCustomFont instance;

  /**
   * Constructor for the LoadCustomFont class.
   */
  private LoadCustomFont() {
    // private constructor to prevent external instantiation
  }

  /**
   * Returns the instance of the LoadCustomFont class.
   *
   * @return the instance of the LoadCustomFont class.
   */
  public static LoadCustomFont getInstance() {
    if (instance == null) {
      instance = new LoadCustomFont();
    }
    return instance;
  }

  /**
   * Loads the Gielenor font.
   *
   * @param fontSize the size of the font.
   * @return the font.
   */
  public Font loadGielenorFont(int fontSize) {
    return Font.loadFont(getClass().getResourceAsStream("/gielenor_font.ttf"),
        fontSize);
  }
}
