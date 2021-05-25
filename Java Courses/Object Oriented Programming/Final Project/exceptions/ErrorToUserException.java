package exceptions;

import javafx.scene.control.Alert;

public class ErrorToUserException extends Exception{

    private String message;
    private Alert alert;

    public ErrorToUserException(String message) {
        this.message = message;
        assignAlertToUser();
    }

    private void assignAlertToUser() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
    }

    public void show() {
        alert.show();
    }
}
