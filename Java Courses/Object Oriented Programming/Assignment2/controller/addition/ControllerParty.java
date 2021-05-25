package controller.addition;

import exceptions.ClassAlreadyExists;
import exceptions.MissingItemException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import model.ModelGUI;
import view.MainPaneView.AddParty;

public class ControllerParty{

    private ModelGUI theModel;
    private int currentYear;
    private int currentMonth;
    private AddParty addParty;
    private Button showAllParties;
    private Runnable showAllPartiesAction, showAllCandidatesAction;

    public ControllerParty(ModelGUI model, AddParty addParty, Button showAllParties,
                           Runnable showAllPartiesAction, Runnable showAllCandidatesAction) {
        theModel = model;
        currentYear = theModel.getElectionYear();
        currentMonth = theModel.getElectionMonth();
        this.addParty = addParty;
        this.showAllParties = showAllParties;
        this.showAllPartiesAction = showAllPartiesAction;
        this.showAllCandidatesAction = showAllCandidatesAction;

        eventForSubmitButton();
    }

    private void eventForSubmitButton() {

        EventHandler<ActionEvent> eventForSubmitButton = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = null;
                try {

                    String partyName = addParty.getPartyName();
                    String stringDay = addParty.getDay();
                    String stringMonth = addParty.getMonth();
                    String stringYear = addParty.getYear();
                    String section = addParty.getSection();

                    boolean isEmpty = section == null || partyName.isEmpty() || stringDay.isEmpty() ||
                            stringMonth.isEmpty() || stringYear.isEmpty();
                    if (isEmpty) {
                        throw new MissingItemException(alert);
                    }

                    int day = Integer.parseInt(stringDay);
                    int month = Integer.parseInt(stringMonth);
                    int year = Integer.parseInt(stringYear);

                    boolean legalDate = legalDate(month, year);

                    if (legalDate) {
                        if(theModel.addParty(partyName, section, year, month, day)) {
                            alert = new Alert(Alert.AlertType.NONE);
                            alert.setContentText("The party has been added successfully!");
                            alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

                            showAllPartiesAction.run();
                            showAllCandidatesAction.run();

                            alert.showAndWait();
                        } else {
                            throw new ClassAlreadyExists("Party", alert);
                        }
                    } else
                        throw new Exception();

                } catch(MissingItemException mie) {
                    mie.showErrorMessage();
                } catch(NumberFormatException nfe) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The party name contains numbers!");
                    alert.show();
                } catch(ClassAlreadyExists re) {
                    re.showErrorMessage();
                } catch(Exception e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Can't add Future party!");
                    alert.show();
                }
            }
        };

        addParty.eventSubmitButton(eventForSubmitButton);

    }

    private boolean legalDate(int month, int year) {
        boolean isLegalYear = false;

        if (currentYear > year || (currentYear == year && currentMonth >= month))
            isLegalYear = true;

        return isLegalYear;
    }
}
