package edu.ntnu.idatt2001.view.utility;

/**
 * This class is used to mute the sound effects.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class MuteEvent {
  private static boolean muted = false;

  /**
   * Returns the muted property. It's fine to use static method here.
   *
   * @return the muted property.
   */
  public static boolean isMuted() {
    return muted;
  }

  /**
   * Sets the muted property. It's fine to use static method here.
   *
   * @param muted the muted property.
   */
  public static void setMuted(boolean muted) {
    MuteEvent.muted = muted;
  }
}
