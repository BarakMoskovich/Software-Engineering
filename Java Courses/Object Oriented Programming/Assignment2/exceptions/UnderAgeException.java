package exceptions;

import javafx.scene.control.Alert;

public class UnderAgeException extends Exception{
    private String message;
    private Alert alert;

    public UnderAgeException(String message) {
        this.message = message;
    }

    public UnderAgeException(Alert alert, String message) {
        this(message);
        this.alert = alert;
    }

    public String getMessage() {
        return message;
    }

    public void showErrorMessage() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}