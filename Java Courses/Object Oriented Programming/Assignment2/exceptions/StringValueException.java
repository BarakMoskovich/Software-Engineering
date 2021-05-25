package exceptions;

import javafx.scene.control.Alert;

public class StringValueException extends Exception{

    private String message;
    private Alert alert;

    public StringValueException(Alert alert, String msg) {
        this.alert = alert;
        message = msg;
    }

    public void showErrorMessage() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
