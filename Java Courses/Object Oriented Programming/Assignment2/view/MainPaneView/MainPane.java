package view.MainPaneView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import view.ViewGUI;
import view.showMenu.Show;

public class MainPane extends Show {

    protected Button submit;
    protected HBox hbSubmit;
    protected Font buttonsFont = Font.font("Tahoma", FontWeight.LIGHT, FontPosture.REGULAR, 14);

    public MainPane()  {
        super();
        createSubmitButton();
    }
    private void createSubmitButton() {
        hbSubmit = new HBox();
        submit = new Button("Submit");
        submit.setMinWidth(ViewGUI.MIN_BUTTON_WIDTH_VALUE);
        submit.setFont(buttonsFont);
        hbSubmit.getChildren().add(submit);
    }

    public void eventSubmitButton(EventHandler<ActionEvent> event) {
        submit.setOnAction(event);
    }
}
