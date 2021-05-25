package view.MainPaneView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.ViewGUI;

public class WelcomeMenu extends MainPane {
    private TextField tfYear, tfMonth;
    private HBox hbYear, hbMonth;

    public WelcomeMenu() {
        super();
        setHeadline("Welcome to Election App");
        setYear();
        setMonth();
    }

    private void setMonth() {
        hbMonth = new HBox();
        tfMonth = new TextField();
        tfMonth.setPromptText("Enter Current Month Number");
        tfMonth.setFont(buttonsFont);
        tfMonth.setMinWidth(ViewGUI.TEXT_INPUT_WIDTH_VALUE);
        tfMonth.setFocusTraversable(false);

        hbMonth.getChildren().add(tfMonth);
    }

    private void setYear() {
        hbYear = new HBox();
        tfYear = new TextField();
        tfYear.setPromptText("Enter Current Year Number");
        tfYear.setFont(buttonsFont);
        tfYear.setMinWidth(ViewGUI.TEXT_INPUT_WIDTH_VALUE);
        tfYear.setFocusTraversable(false);

        hbYear.getChildren().add(tfYear);
    }

    public Button getSubmitButton() {
        return submit;
    }

    public String getMonthText() {
        return tfMonth.getText();
    }

    public String getYearText() {
        return tfYear.getText();
    }

    public VBox update() {
        super.update();
        mainView.getChildren().addAll(hbMonth, hbYear, hbSubmit);
        mainView.setMargin(hbMonth, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(hbYear, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(hbSubmit, ViewGUI.DEFAULT_INSETS);

        return mainView;
    }

    public void addEventSubmitButton(EventHandler<ActionEvent> event) {
        submit.setOnAction(event);
    }
}
