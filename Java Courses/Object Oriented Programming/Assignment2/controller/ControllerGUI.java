package controller;

import controller.addition.ControllerBallotBox;
import controller.addition.ControllerCandidate;
import controller.addition.ControllerCitizen;
import controller.addition.ControllerParty;
import exceptions.MissingItemException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.TestUtils;
import model.ModelGUI;
import view.MainPaneView.AddBallotBox;
import view.MainPaneView.AddCitizen;
import view.MainPaneView.AddParty;
import view.MainPaneView.AssignCandidate;
import view.MenuButtons;
import view.ViewGUI;
import view.showMenu.*;

import java.util.*;

public class ControllerGUI {
    private ModelGUI theModel;
    private ViewGUI theView;
    private Stage dialog;
    private VBox dialogVBox;
    private int currentYear;
    private Runnable checkEnableAddCandidate, checkEnableShowAllBallotBoxes, checkEnableShowAllParties,
            checkEnableShowAllCitizens, checkEnableBeginElections, checkEnableShowResults;
    // enable buttons for user
    private boolean isAddButtonsAdded, isShowAssignCandidatesButton, isShowAllBallotBox,
            isShowAllParties, isShowAllCitizens,
            isBeginElections, isShowResults;

    private LinkedHashMap<String, Button> allMenuButtons;
    private MenuButtons menuButtons;

    public ControllerGUI(ModelGUI model, ViewGUI view) {
        theModel = model;
        theView = view;
        allMenuButtons = theView.getMenuButtons().getAllButtons();
        menuButtons = theView.getMenuButtons();

        eventForWelcomeMenuSubmitButton();
        checkEnableAddCandidate = this::showAssignCandidate;
        checkEnableShowAllBallotBoxes = this::enableShowBallotBoxButton;
        checkEnableShowAllParties = this::enableShowAllPartiesButton;
        checkEnableShowAllCitizens = this::enableShowAllCitizensButton;
        checkEnableBeginElections = this::enableBeginElections;
        checkEnableShowResults = this::enableShowResults;
        dialog();

        eventCloseButton();
        eventHelpButton();
        eventAboutButton();
    }

    private void enableShowAllPartiesButton() {
        if (!isShowAllParties) {
            menuButtons.getShowAllParties().setDisable(false);
            isShowAllParties = true;
        }
    }

    private void enableShowAllCitizensButton() {
        if (!isShowAllCitizens) {
            menuButtons.getShowAllCitizens().setDisable(false);
            isShowAllCitizens = true;
        }
    }

    private void eventForWelcomeMenuSubmitButton() {
        EventHandler<ActionEvent> eventForWelcomeMenuSubmitButton = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = null;

                try {

                    String monthText = theView.getMonthText();
                    String yearText = theView.getYearText();

                    if (monthText.isEmpty() || yearText.isEmpty())
                        throw new MissingItemException(alert);

                    int month = Integer.parseInt(monthText);
                    int year = Integer.parseInt(yearText);

                    theModel.setElection(month, year);

                    if (year == theModel.getElection().getYear() &&
                            month == theModel.getElection().getMonth()) {
                        alert = new Alert(Alert.AlertType.NONE);
                        alert.setContentText("Election Program has been created successfully!\n" +
                                "Please continue inserting the data");
                        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                        alert.show();

                        currentYear = year;

                        theView.clear();
                        enableAddButtons(); // enables the add Buttons

                        assignAllEvents(); // assign all other menu button events
                    } else {
                        throw new Exception();
                    }

                } catch (NumberFormatException nfe) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("1 or more of fields are not a number!");
                    alert.showAndWait();
                } catch (MissingItemException mie) {
                    mie.showErrorMessage();
                } catch (Exception e){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error in Year number or Month number!");
                    alert.setContentText("Minimum Year is: " + theModel.getElection().DEFAULT_YEAR + "" +
                            "\nMonths are between: (1-12) inclusive");
                    alert.showAndWait();
                }

            }
        };
        theView.getWelcomeMenu().addEventSubmitButton(eventForWelcomeMenuSubmitButton);
    }

    private void assignAllEvents() {
        eventForAddBallotBoxButton();
        eventForAddCandidateButton();
        eventForAddCitizenButton();
        eventForAddPartyButton();
        eventForBeginElectionButton();
        eventForShowBallotBoxesButton();
        eventForShowCitizensButton();
        eventForShowPartiesButton();
        eventForShowResultsButton();
        eventImportButton();
    }

    private void eventForAddBallotBoxButton() {
        EventHandler<ActionEvent> eventForAddBallotBoxButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0) {
                AddBallotBox addBallotBox = new AddBallotBox();
                theView.update(addBallotBox);
                ControllerBallotBox cbb = new ControllerBallotBox(theModel, addBallotBox,
                        checkEnableShowAllBallotBoxes);

            }
        };
        menuButtons.addEventHandlerToAddBallotBoxButton(eventForAddBallotBoxButton);
    }

    private void eventForAddCandidateButton() {
        EventHandler<ActionEvent> eventForAddCandidateButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0) {
                AssignCandidate assignCandidate = new AssignCandidate(theModel.getVoterRegister(),
                        theModel.getParties());
                theView.update(assignCandidate);
                ControllerCandidate cc = new ControllerCandidate(assignCandidate, theModel, checkEnableBeginElections);
            }
        };
        menuButtons.addEventHandlerToAssignCandidateButton(eventForAddCandidateButton);
    }

    private void eventForAddCitizenButton() {
        EventHandler<ActionEvent> eventForAddCitizenButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0) {
                AddCitizen addCitizen = new AddCitizen(currentYear);
                theView.update(addCitizen);
                ControllerCitizen cc = new ControllerCitizen(theModel, addCitizen, checkEnableAddCandidate,
                        checkEnableShowAllCitizens);
            }
        };
        menuButtons.addEventHandlerToAddCitizenButton(eventForAddCitizenButton);
    }

    private void eventForAddPartyButton() {
        EventHandler<ActionEvent> eventForAddPartyButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0) {
                AddParty addParty = new AddParty(currentYear);
                theView.update(addParty);
                ControllerParty cp = new ControllerParty(theModel, addParty, menuButtons.getShowAllParties(),
                        checkEnableShowAllParties, checkEnableAddCandidate);
            }
        };
        menuButtons.addEventHandlerToAddPartyButton(eventForAddPartyButton);
    }

    private void eventForBeginElectionButton() {
        EventHandler<ActionEvent> eventForBeginElectionButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0) {
                theView.beginElectionButton(theModel);
                checkEnableShowResults.run();
                theView.update(new ShowResults(theModel.getElection().getParties())); // Show Results
            }
        };
        menuButtons.addEventHandlerToBeginElectionsButton(eventForBeginElectionButton);
    }

    private void eventForShowResultsButton() {
        EventHandler<ActionEvent> eventForShowResultsButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0) {
                theView.update(new ShowResults(theModel.getElection().getParties()));
            }
        };
        menuButtons.addEventHandlerToShowResultsButton(eventForShowResultsButton);
    }

    private void eventForShowBallotBoxesButton() {
        EventHandler<ActionEvent> eventForShowBallotBoxesButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0) {
                ShowBallotBoxes showBallotBoxes = new ShowBallotBoxes(theModel.getRegularBallotBoxList(), theModel.getSoldierBallotBoxList(),
                        theModel.getSoldierCoronaBallotBoxList(),
                        theModel.getCoronaBallotBoxList());
                theView.update(showBallotBoxes);
            }
        };
        menuButtons.addEventHandlerToShowAllBallotBoxesButton(eventForShowBallotBoxesButton);
    }

    private void eventForShowCitizensButton() {
        EventHandler<ActionEvent> eventForShowCitizensButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0) {
                ShowCitizens showCitizens = new ShowCitizens(theModel.getVoterRegister());
                theView.update(showCitizens);
            }
        };
        menuButtons.addEventHandlerToShowAllCitizensButton(eventForShowCitizensButton);
    }

    private void eventForShowPartiesButton() {
        EventHandler<ActionEvent> eventForShowPartiesButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0) {
                theView.update(new ShowParties(theModel.getElection().getParties()));
            }
        };
        menuButtons.addEventHandlerToShowAllPartiesButton(eventForShowPartiesButton);
    }

    private void eventCloseButton() {
        EventHandler<ActionEvent> eventCloseButton = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
                System.exit(0);
            }
        };
        theView.addEventCloseButton(eventCloseButton);
    }

    private void eventImportButton() {
        EventHandler<ActionEvent> eventCloseButton = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Hard Code
                ControllerCLI c = new ControllerCLI(theModel.getElection(), theModel.getVoterRegister());
                TestUtils.hardCodeToTest(c);

                checkEnableAddCandidate.run();
                checkEnableShowAllBallotBoxes.run();
                checkEnableShowAllCitizens.run();
                checkEnableBeginElections.run();
                checkEnableShowAllParties.run();
            }
        };
        theView.addEventImportButton(eventCloseButton);
    }

    private void eventHelpButton() {
        EventHandler<ActionEvent> eventHelpButton = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                helpDialog();
            }
        };
        theView.addEventHelpButton(eventHelpButton);
    }

    private void eventAboutButton() {
        EventHandler<ActionEvent> eventAboutButton = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                aboutDialog();
            }
        };
        theView.addEventAboutButton(eventAboutButton);
    }

    private void dialog() {
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialogVBox = new VBox(20);
        Scene dialogScene = new Scene(dialogVBox, 400, 150);
        dialog.setScene(dialogScene);
    }

    private void aboutDialog() {
        dialogVBox.getChildren().clear();
        Text headline = new Text("Election Interface");
        Text body = new Text("Created by :\n" +
                " - Barak Moskovich\n" +
                " - Denis Karabitski");
        headline.setFont(Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 16));
        body.setFont(Font.font("Tahoma", FontWeight.LIGHT, FontPosture.REGULAR, 14));
        dialogVBox.getChildren().addAll(headline, body);
        dialogVBox.setMargin(headline, new Insets(10, 0, 0, 20));
        dialogVBox.setMargin(body, new Insets(20, 0, 0, 20));
        dialog.show();
    }

    private void helpDialog() {
        dialogVBox.getChildren().clear();
        Text headline = new Text("Help");
        Text body = new Text("To begin vote you MUST insert in at least one citizen,\n" +
                "one ballot box, one party, and assign one candidate.\n\n" +
                "You can insert fixed data at File > Import Fixed Data");

        headline.setFont(Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 16));
        body.setFont(Font.font("Tahoma", FontWeight.LIGHT, FontPosture.REGULAR, 14));
        dialogVBox.getChildren().addAll(headline, body);
        dialogVBox.setMargin(headline, new Insets(10, 0, 0, 20));
        dialogVBox.setMargin(body, new Insets(10, 0, 0, 20));
        dialog.show();
    }

    private void enableAddButtons() {
        if (!isAddButtonsAdded) {
            for(String buttonText: allMenuButtons.keySet()){
                if (buttonText.toUpperCase().contains("ADD"))
                    allMenuButtons.get(buttonText).setDisable(false);
            }
            isAddButtonsAdded = true;
        }
    }

    private void showAssignCandidate() {
        if (!isShowAssignCandidatesButton) {
            int numberOfCitizens = theModel.getNumberOfCitizens();
            int numberOfParties = theModel.getNumberOfParties();

            boolean canAssign = numberOfCitizens != 0 && numberOfParties != 0;
            if (canAssign) {
                isShowAssignCandidatesButton = true;
                menuButtons.getAssignCandidateButton().setDisable(false);
            }
        }
    }

    private void enableShowBallotBoxButton() {
        if (!isShowAllBallotBox) {
            menuButtons.getShowAllBallotBoxButton().setDisable(false);
            isShowAllBallotBox = true;
        }
    }

    private void enableBeginElections() {
        if (!isBeginElections) {
            menuButtons.getBeginElectionButton().setDisable(false);
            isBeginElections = true;
        }
    }

    private void enableShowResults() {
        if (!isShowResults) {
            menuButtons.getShowResults().setDisable(false);
            isShowResults = true;
        }
    }
}
