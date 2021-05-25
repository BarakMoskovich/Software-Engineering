package exceptions;

import javafx.scene.control.Alert;

public class UserNotificationException extends Exception{

    private String message;
    private Alert alert;

    public UserNotificationException(String message) {
        this.message = message;
        assignInformationMessage();
    }

    private void assignInformationMessage() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
    }

    public void show() {
        alert.show();
    }
}
