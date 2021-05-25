package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class BackButtonView {
    protected Button backToMainButton;
    protected VBox mainView, backToMainVBox;

    public BackButtonView() {
        mainView = new VBox();
        backToMainVBox = new VBox();
        assignAll();
    }

    private void assignAll() {
        assignBackToMainButton();
    }

    private void assignBackToMainButton() {
        backToMainButton = new Button("");
        backToMainButton.getStyleClass().add("button-back");
        backToMainVBox.getChildren().add(backToMainButton);
    }

    public void eventBackToMainButton(EventHandler<ActionEvent> event) {
        backToMainButton.setOnAction(event);
    }

    public VBox update() {
        mainView.getChildren().clear();

        return mainView;
    }

}
