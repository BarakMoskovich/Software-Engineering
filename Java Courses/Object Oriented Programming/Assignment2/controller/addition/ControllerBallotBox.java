package controller.addition;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import model.ModelGUI;
import view.MainPaneView.AddBallotBox;

public class ControllerBallotBox{
    private AddBallotBox mainView;
    private ModelGUI theModel;
    private Button showAllBallotBoxes;
    private Runnable submitAction;

    public ControllerBallotBox(ModelGUI model, AddBallotBox newMainView,
                               Runnable submitAction) {
        theModel = model;
        mainView = newMainView;
//        showAllBallotBoxes = showAllBallotBoxButton;
        this.submitAction = submitAction;
        eventForSubmitButton();
    }


    private void eventForSubmitButton() {

        EventHandler<ActionEvent> eventForSubmitButton = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert;
                try {
                    String address = mainView.getAddress();
                    String ballotBoxType = mainView.getType();

                    if (!address.isEmpty() && !ballotBoxType.isEmpty()) {
                        alert = new Alert(Alert.AlertType.NONE);
                        alert.setContentText("The BallotBox has been added successfully!");
                        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                        alert.show();

                        theModel.addBallotBox(address, ballotBoxType);

                        submitAction.run();
                    } else
                        throw new NullPointerException();
                } catch(NullPointerException npe) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("1 or more of fields are not selected");
                    alert.showAndWait();
                } catch(Exception e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Something went wrong");
                    alert.showAndWait();
                }
            }
        };
        mainView.eventSubmitButton(eventForSubmitButton);
    }
}