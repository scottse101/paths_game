package edu.ntnu.idatt2001.view.utility;

import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * This class is used to create a typewriter animation for a label node.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class LabelAnimation {
  private final Label label;
  private final String content;
  private final double duration;
  private final double delay;
  private final Timeline timeline;

  /**
   * Constructor for the typewriter animation.
   *
   * @param label the label node to animate.
   * @param content the content to animate.
   * @param duration the duration of the animation.
   * @param delay the delay of the animation.
   */
  public LabelAnimation(Label label, String content, double duration, double delay) {
    this.label = label;
    this.content = content;
    this.duration = duration;
    this.delay = delay;

    timeline = new Timeline();
    for (int i = 0; i < content.length(); i++) {
      final int index = i;
      KeyValue kv = new KeyValue(label.textProperty(), content.substring(0, index + 1));
      KeyFrame kf = new KeyFrame(Duration.seconds(duration * i + delay), kv);
      timeline.getKeyFrames().add(kf);
    }

    timeline.setOnFinished(event -> {
      // Play the next animation if available
      if (label.getParent() != null) {
        List<Node> children = label.getParent().getChildrenUnmodifiable();
        int index = children.indexOf(label);
        if (index < children.size() - 1) {
          Node nextChild = children.get(index + 1);
          if (nextChild != null) {
            LabelAnimation nextAnimation = (LabelAnimation) nextChild.getUserData();
            if (nextAnimation != null) {
              nextAnimation.play();
            }
          }
        }
      }
    });
  }

  /**
   * Plays the animation.
   */
  public void play() {
    timeline.play();
  }

  /**
   * Sets the on finished event handler.
   *
   * @param handler the event handler.
   */
  public void setOnFinished(EventHandler<ActionEvent> handler) {
    timeline.setOnFinished(handler);
  }

  /**
   * Animates a label.
   *
   * @param label the label to animate
   * @param content the content to animate
   * @param duration the duration of the animation
   */
  public void animate(Label label, String content, double duration) {
    Label animatedLabel = new Label();
    animatedLabel.setFont(label.getFont());
    animatedLabel.getStyleClass().addAll(label.getStyleClass());
    animatedLabel.setText("");

    Timeline showText = new Timeline();
    for (int i = 0; i < content.length(); i++) {
      final int index = i;
      KeyValue kv = new KeyValue(animatedLabel.textProperty(), content.substring(0, index + 1));
      KeyFrame kf = new KeyFrame(Duration.seconds(duration * i), kv);
      showText.getKeyFrames().add(kf);
    }

    // Replace the label with the animated label
    label.getParent().getChildrenUnmodifiable().set(label.getParent().getChildrenUnmodifiable()
        .indexOf(label), animatedLabel);
    animatedLabel.setUserData(this);
    showText.play();

    showText.setOnFinished(e -> {
      // Replace the animated label with the original label
      label.getParent().getChildrenUnmodifiable().set(label.getParent()
           .getChildrenUnmodifiable().indexOf(animatedLabel), label);
      label.setText(content);
      animatedLabel.setUserData(null);
    });
  }
}
