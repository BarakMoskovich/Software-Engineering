package controller;

import exceptions.ErrorToUserException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.*;
import view.*;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        assignAllEvents();
    }

    private void assignAllEvents() {
        eventForAddParticipantButton();
        eventForStartChampionshipButton();
    }

    private void addParticipant(String participantName) {
        boolean isAdded;
        isAdded = model.addParticipant(participantName);

        if (isAdded) {
            view.updateParticipantsNames(participantName, model.getCurrentNumberOfParticipants() - 1);
        } else {
            new ErrorToUserException("The participant is already in the championship.").show();
        }
    }

    private void eventForAddParticipantButton() {
        EventHandler<ActionEvent> eventForAddParticipantButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0) {
                String participantName = view.getParticipantName(); // Set name

                int participantNameLength = participantName.length();
                if (participantNameLength < 2 || participantNameLength > 20)
                    new ErrorToUserException("Please enter participant name, Which is contained from 2-20 characters.").show();
                else if (!model.legalParticipantName(participantName))
                    new ErrorToUserException("Invalid name, try again.").show();
                else if (model.canAddContestant()) {
                    addParticipant(participantName);
                    if (!model.canAddContestant())
                        view.disableAddParticipantButton(); // Disable button
                } else {
                    new ErrorToUserException("You've reached the maximum amount of participants.").show();
                }
            }
        };
        view.addEventHandlerToAddParticipantButton(eventForAddParticipantButton);
    }

    private void eventForStartChampionshipButton() {
        EventHandler<ActionEvent> eventForStartChampionshipButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0) {
                // open the window of the championship
                if (!model.canAddContestant()){
                    model.setChampionshipGameType(view.getChampionshipType()); // update GameModel type as well
                    ChampionshipView championshipView = new ChampionshipView(view.getParticipantTextFields(), view.getTextFieldHBox());
                    view.updateToGameView();
                    ChampionshipController championshipController = new ChampionshipController(model, championshipView, view.getStage());
                } else {
                    new ErrorToUserException("Can't begin Championship, more players are required!").show();
                }
            }
        };
        view.addEventHandlerToStartChampionshipButton(eventForStartChampionshipButton);
    }
}
