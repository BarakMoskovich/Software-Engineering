package controller.addition;

import exceptions.ClassAlreadyExists;
import exceptions.MissingItemException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.ModelGUI;
import model.VoterRegister;
import view.MainPaneView.AssignCandidate;

public class ControllerCandidate {

    private AssignCandidate candidate;
    private ModelGUI theModel;
    private VoterRegister vr;

    private Runnable checkEnableBeginElections;

    public ControllerCandidate(AssignCandidate assignCandidate,
                               ModelGUI model,
                               Runnable r) {
        candidate = assignCandidate;
        theModel = model;
        checkEnableBeginElections = r;
        eventForSubmitButton();
    }

    private void eventForSubmitButton() {
        EventHandler<ActionEvent> eventForSubmitButton = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = null;
                try {
                    String citizenID = candidate.getCitizenID();
                    String[] partyName = candidate.getPartyName().split(" - ");
                    String strPlace = candidate.getPlace();
                    int place = Integer.parseInt(strPlace);

                    boolean isEmpty = citizenID == null || partyName == null || strPlace == null;

                    if (isEmpty)
                        throw new MissingItemException(alert);

                    if (!strPlace.matches("[0-9]+"))
                        throw new NumberFormatException();


                    String pName = partyName[0];
                    String id = citizenID.substring(0,9);

                    if (theModel.assignCandidate(id, pName, place)) {
                        alert = new Alert(Alert.AlertType.NONE);
                        alert.setContentText("The Candidate has been added successfully!");
                        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                        alert.show();

                        checkEnableBeginElections.run();
                    } else {
                        throw new ClassAlreadyExists("Candidate", alert);
                    }
                } catch(ClassAlreadyExists cae) {
                    cae.showErrorMessage();
                } catch(NumberFormatException nfe) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The Place contains only numbers!");
                    alert.show();
                } catch(MissingItemException mie) {
                    mie.showErrorMessage();
                } catch(Exception e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Something went wrong");
                    alert.showAndWait();
                }
            }
        };
        candidate.eventSubmitButton(eventForSubmitButton);
    }
}
