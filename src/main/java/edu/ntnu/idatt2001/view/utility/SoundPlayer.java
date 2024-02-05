package edu.ntnu.idatt2001.view.utility;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * This class is used to play sound effects.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class SoundPlayer {
  private static SoundPlayer instance;
  private MediaPlayer mediaPlayer;
  private final BooleanProperty muteProperty;

  /**
   * Constructor for the SoundPlayer class.
   */
  private SoundPlayer() {
    this.muteProperty = new SimpleBooleanProperty(false);
  }

  /**
   * Returns the instance of the SoundPlayer class.
   *
   * @return the instance of the SoundPlayer class.
   */
  public static synchronized SoundPlayer getInstance() {
    if (instance == null) {
      instance = new SoundPlayer();
    }
    return instance;
  }

  /**
   * Plays a sound file.
   *
   * @param soundFile the sound file to be played.
   */
  public synchronized void play(String soundFile) {
    stop();

    try {
      Media media = new Media(getClass().getResource(soundFile).toExternalForm());
      this.mediaPlayer = new MediaPlayer(media);

      if (MuteEvent.isMuted()) {
        mediaPlayer.setVolume(0.0);
      }

      mediaPlayer.play();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Stops the sound file that is currently playing.
   */
  public synchronized void stop() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
      mediaPlayer.dispose();
      mediaPlayer = null;
    }
  }

  /**
   * Sets the mute property.
   *
   * @param mute the mute property.
   */
  public synchronized void setMute(boolean mute) {
    muteProperty.set(mute);
    if (mediaPlayer != null) {
      mediaPlayer.setMute(mute);
    }
  }

  /**
   * Returns the mute property.
   *
   * @return the mute property.
   */
  public synchronized boolean isMute() {
    return muteProperty.get();
  }

  /**
   * Returns the mute property.
   *
   * @return the mute property.
   */
  public BooleanProperty muteProperty() {
    return muteProperty;
  }

  /**
   * Returns the media player.
   *
   * @return the media player.
   */
  public MediaPlayer getMediaPlayer() {
    return mediaPlayer;
  }
}
