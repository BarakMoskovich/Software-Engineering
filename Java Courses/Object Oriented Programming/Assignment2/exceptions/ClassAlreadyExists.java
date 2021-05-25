package exceptions;

import javafx.scene.control.Alert;

public class ClassAlreadyExists extends Exception{

    private String name;
    private Alert alert;

    public ClassAlreadyExists(String name, Alert alert) {
        this.name = name;
        this.alert = alert;
    }

    public void showErrorMessage() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(name + " already exists!");
        alert.show();
    }
}
