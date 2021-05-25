package Exceptions;

import javafx.scene.control.Alert;

public class AlertUserException extends Exception {

    private final String name;

    public AlertUserException(String name) {
        this.name = name;
    }

    public void showErrorMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(name);
        alert.show();
    }
}