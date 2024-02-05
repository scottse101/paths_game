package edu.ntnu.idatt2001.view.utility;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.scene.control.Button;

/**
 * This class is used to mute the sound effects.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class MuteButton extends Button {
  private final SoundPlayer soundPlayer;
  private final ReadOnlyBooleanWrapper muteDisabledProperty;

  /**
   * Constructor for the MuteButton class.
   *
   * @param soundPlayer the sound player.
   */
  public MuteButton(SoundPlayer soundPlayer) {
    this.soundPlayer = soundPlayer;
    this.muteDisabledProperty = new ReadOnlyBooleanWrapper();

    setPrefSize(45, 45);
    getStyleClass().add("audio-button");
    updateStyle();

    setOnAction(e -> {
      boolean isMuted = !MuteEvent.isMuted();
      MuteEvent.setMuted(isMuted);
      updateStyle();
      soundPlayer.getMediaPlayer().setVolume(isMuted ? 0.0 : 1.0);
    });

    soundPlayer.muteProperty().addListener((observable, oldValue, newValue) -> {
      muteDisabledProperty.set(newValue);
      updateStyle();
      soundPlayer.getMediaPlayer().setVolume(newValue ? 0.0 : 1.0);
    });

    muteDisabledProperty.set(soundPlayer.isMute());
  }

  /**
   * Updates the style of the mute button.
   */
  private void updateStyle() {
    if (MuteEvent.isMuted()) {
      // Apply the style for muted state
      getStyleClass().remove("audio-button");
      getStyleClass().add("mute-button");
    } else {
      // Apply the style for unmuted state
      getStyleClass().remove("mute-button");
      getStyleClass().add("audio-button");
    }
  }

  /**
   * Returns the mute disabled property.
   *
   * @return the mute disabled property.
   */
  public ReadOnlyBooleanProperty muteDisabledProperty() {
    return muteDisabledProperty.getReadOnlyProperty();
  }
}
