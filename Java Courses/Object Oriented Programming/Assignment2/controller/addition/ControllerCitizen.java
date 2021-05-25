package controller.addition;

import exceptions.ClassAlreadyExists;
import exceptions.StringValueException;
import exceptions.UnderAgeException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Toggle;
import model.ModelGUI;
import model.citizens.Citizen;
import model.citizens.Corona;
import model.citizens.Soldier;
import model.citizens.SoldierCorona;
import view.MainPaneView.AddCitizen;

public class ControllerCitizen {

    private ModelGUI theModel;
    private AddCitizen theView;
    private Runnable checkEnableAddCandidate, checkEnableShowAllCitizens;
    private int year, isolationDays, selectedBallotBox;
    private String name, ID;
    private boolean inIsolation = false;

    public ControllerCitizen(ModelGUI model, AddCitizen view, Runnable checkEnableAddCandidate,
                             Runnable checkEnableShowAllCitizens) {
        theModel = model;
        theView = view;
        this.checkEnableShowAllCitizens = checkEnableShowAllCitizens;
        this.checkEnableAddCandidate = checkEnableAddCandidate;

        eventForSubmitButton();
        changeListenerToYearComboBox();
        changeListenerToToggleGroup();
    }

    private void eventForSubmitButton() {
        EventHandler<ActionEvent> eventForSubmitButton = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = null;
                try {
                    name = theView.getName();
                    ID = theView.getID();
                    year = theView.getYear();
                    inIsolation = theView.getIsolation();
                    isolationDays = -1;

                    if (inIsolation)
                        isolationDays = theView.getIsolationDays();

                    if (!ID.matches("[0-9]+") || ID.length() != 9)
                        throw new NumberFormatException();
                    else if (theModel.getElectionYear() - year < 18)
                        throw new UnderAgeException(alert, "The minimum age is 18!");
                    else if (!name.matches("[a-zA-Z ]+"))
                        throw new StringValueException(alert, "English letters only !");
                    else {
                        boolean soldierAge = theModel.getElection().getYear() - year <= 21, added = true;
                        theView.getSelectedBallotBox();
                        selectedBallotBox = theView.getSelectedBallotBox();

                        if (inIsolation && soldierAge)
                            added = theModel.addCitizen(new SoldierCorona(name, ID, year, true, theModel.getElection().getBallotBoxByNumber(selectedBallotBox), isolationDays));
                        else if (inIsolation)
                            added = theModel.addCitizen(new Corona(name, ID, year, true, theModel.getElection().getBallotBoxByNumber(selectedBallotBox), isolationDays));
                        else if (soldierAge)
                            added = theModel.addCitizen(new Soldier(name, ID, year, theModel.getElection().getBallotBoxByNumber(selectedBallotBox)));
                        else
                            added = theModel.addCitizen(new Citizen(name, ID, year, theModel.getElection().getBallotBoxByNumber(selectedBallotBox)));

                        if (!added) // Duplicate
                            throw new ClassAlreadyExists("Citizen", alert);
                        else {
                            alert = new Alert(Alert.AlertType.NONE);
                            alert.setContentText("The Citizen has been added successfully!");
                            alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                            alert.show();
                        }

                      checkEnableShowAllCitizens.run();
                      checkEnableAddCandidate.run();
                    }
                } catch(UnderAgeException npe) {
                    npe.showErrorMessage();
                } catch(NullPointerException npe) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("1 or more of the fields are empty!");
                    alert.show();
                } catch (StringValueException sve) {
                    sve.showErrorMessage();
                } catch(NumberFormatException nfe) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The ID and Days in Isolation contains only numbers!");
                    alert.show();
                } catch(ClassAlreadyExists cae) {
                    cae.showErrorMessage();
                } catch(Exception e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Can't add citizen!");
                    alert.show();
                }
            }
        };
        theView.eventSubmitButton(eventForSubmitButton);
    }

    private void changeBallotBoxes() {
        inIsolation = theView.getIsolation();
        year = theView.getYear();
        int age = theModel.getElection().getYear() - year;
        String ballotBoxes;
        String[] splitBallotBoxes;

        if (age >= 18 && age <= 21) {
            if (inIsolation)
                ballotBoxes = theModel.getElection().getBallotBoxesByType(4, true); // 4 - SoliderCorona
            else
                ballotBoxes = theModel.getElection().getBallotBoxesByType(3, true); // 3 - Solider
        } else if (inIsolation)
            ballotBoxes = theModel.getElection().getBallotBoxesByType(2, true); // 2 - Corona
        else
            ballotBoxes = theModel.getElection().getBallotBoxesByType(1, true); // 1 - Citizen / Regular

        splitBallotBoxes = ballotBoxes.split(";");
        ComboBox ballotBox = new ComboBox();
        for (String ballot : splitBallotBoxes)
            ballotBox.getItems().add(ballot);

        theView.updateBallotBoxes(ballotBox);
    }

    private void changeListenerToYearComboBox() {
        ChangeListener<Integer> chl = new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                changeBallotBoxes();
            }
        };
        theView.addEventYearComboBox(chl);
    }

    private void changeListenerToToggleGroup() {
        ChangeListener<Toggle> chl = new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                changeBallotBoxes();
                theView.isolationDaysVisible();
            }
        };
        theView.addEventIsoRadio(chl);
    }
}
