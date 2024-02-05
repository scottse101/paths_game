package edu.ntnu.idatt2001.view.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

/**
 * This class is used to create alerts.
 *
 * @Author Emil Johnsen, Scott Emonanekkul
 * @version 1.0
 */
public class AlertHelper {
  /**
   * Constructor for the AlertHelper class.
   */
  public void uploadFileError(Exception e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("While reading your file, this error was encountered: \n" + e.getMessage()
        + "\n\nPlease check your file and try again. \nIf you want to see how the file should be "
        + "formatted, \npress the help button in the bottom left corner of the screen.");

    // Apply the CSS to the alert
    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.getStylesheets().add(getClass().getResource("/Styling.CSS").toExternalForm());

    alert.showAndWait();
  }

  /**
   * Shows an alert.
   *
   * @param message the message to be shown.
   */
  public void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Shows an alert.
   *
   * @param alertType the type of alert.
   * @param title the title of the alert.
   * @param contentText the content of the alert.
   *
   * @return the alert.
   */
  private Alert createAlert(Alert.AlertType alertType, String title, String contentText) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(contentText);


    // Apply the CSS to the alert
    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.getStylesheets().add(getClass().getResource("/Styling.css").toExternalForm());
    dialogPane.getStyleClass().add("popup-message2");

    return alert;
  }
}
