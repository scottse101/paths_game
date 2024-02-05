package edu.ntnu.idatt2001.view.utility;

import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
  * This class is used to create a typewriter animation for a text node.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class TypewriterAnimation {
  private final Text text;
  private final String content;
  private final double duration;
  private final double delay;
  private final Timeline timeline;

  /**
   * Constructor for the typewriter animation.
   *
   * @param text the text node to animate.
   * @param content the content to animate.
   * @param duration the duration of the animation.
   * @param delay the delay of the animation.
   */
  public TypewriterAnimation(Text text, String content, double duration, double delay) {
    this.text = text;
    this.content = content;
    this.duration = duration;
    this.delay = delay;

    timeline = new Timeline();
    for (int i = 0; i < content.length(); i++) {
      final int index = i;
      KeyValue kv = new KeyValue(text.textProperty(), content.substring(0, index + 1));
      KeyFrame kf = new KeyFrame(Duration.seconds(duration * i + delay), kv);
      timeline.getKeyFrames().add(kf);
    }

    timeline.setOnFinished(event -> {
      // play the next animation if available
      if (text.getParent() != null) {
        List<Node> children = text.getParent().getChildrenUnmodifiable();
        int index = children.indexOf(text);
        if (index < children.size() - 1) {
          Node nextChild = children.get(index + 1);
          if (nextChild != null) {
            TypewriterAnimation nextAnimation = (TypewriterAnimation) nextChild.getUserData();
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
   * Creates a typewriter animation for a text field.
   *
   * @param textField the text field to animate.
   * @param content the content to animate.
   * @param duration the duration of the animation.
   */
  public void animate(TextField textField, String content, double duration) {
    Text text = new Text();
    text.setFont(textField.getFont());
    text.getStyleClass().addAll(textField.getStyleClass());
    text.setText("");

    Timeline showText = new Timeline();
    for (int i = 0; i < content.length(); i++) {
      final int index = i;
      KeyValue kv = new KeyValue(text.textProperty(), content.substring(0, index + 1));
      KeyFrame kf = new KeyFrame(Duration.seconds(duration * i), kv);
      showText.getKeyFrames().add(kf);
    }

    // Replace the text field with the animated text node
    textField.getParent().getChildrenUnmodifiable().set(textField.getParent()
        .getChildrenUnmodifiable().indexOf(textField), text);
    text.setUserData(this);
    showText.play();

    showText.setOnFinished(e -> {
      // Replace the animated text node with the original text field
      textField.getParent().getChildrenUnmodifiable().set(textField.getParent()
          .getChildrenUnmodifiable().indexOf(text), textField);
      textField.setText(content);
      textField.setUserData(null);
    });
  }
}
