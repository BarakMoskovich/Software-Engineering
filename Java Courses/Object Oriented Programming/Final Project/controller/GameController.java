package controller;

import exceptions.ErrorToUserException;
import exceptions.UserNotificationException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Model;
import model.Participant;
import view.GameView;
import view.View;

import java.util.ArrayList;

public class GameController {
    private Model model;
    private GameView gameView;
    private TextField winnerField;
    private int soccerTieMatches;

    private ArrayList<Integer> firstParticipantScores, secondParticipantScores;

public GameController(Model model, GameView gameView, TextField winnerField) {
    this.model = model;
    this.gameView = gameView;
    this.winnerField = winnerField;

    firstParticipantScores = new ArrayList<>();
    secondParticipantScores = new ArrayList<>();

    soccerTieMatches = 0;

    eventForDoneButton();
}

    private void getScores(TextField[] textFields) {
        // get all scores for each participant to its array
        int middle = (textFields.length / 2) - 1;

        getScoresByPlace(0, middle, textFields, firstParticipantScores);
        getScoresByPlace(middle + 1, textFields.length - 1, textFields, secondParticipantScores);
    }

    private void getScoresByPlace(int start, int end, TextField[] textFields, ArrayList<Integer> participantScores) {
        participantScores.clear();
        for (int i = start; i <= end; i++) {
            String temp = textFields[i].getText();
            if (!temp.equals("") && temp != null) {
                participantScores.add(Integer.parseInt(temp));
            }
        }
    }

    private void getFreeKickScores(Button[] freeKickButtons) {
        int middle = (freeKickButtons.length / 2) - 1;

        getFreeKickScoresByPlace(0, middle, freeKickButtons, firstParticipantScores);
        getFreeKickScoresByPlace(middle + 1, freeKickButtons.length - 1, freeKickButtons, secondParticipantScores);
    }

    private void getFreeKickScoresByPlace(int start, int end, Button[] freeKickButtons, ArrayList<Integer> participantScores) {
        participantScores.clear();
        for (int i = start; i <= end; i++) {
            String temp = freeKickButtons[i].getText();
            if (!temp.equals("Kick!") && temp != null) {
                if (temp.equals("O"))
                    participantScores.add(1);
                else
                    participantScores.add(0);
            }
        }
    }

    private void tennisRestrictions() throws ErrorToUserException {
        if (firstParticipantScores.size() == GameView.TENNIS_MINIMUM_ROUNDS) {
            // for tennis error to user
            throw new ErrorToUserException("In 3 sets of Tennis, one of the participants must have 3 consecutive wins!");
        } else {
            throw new ErrorToUserException("There can't be a tie!\nPut different numbers!");
        }
    }

    private void soccerRestrictions() throws UserNotificationException {
        soccerTieMatches++;
        if (soccerTieMatches == 1) {
            gameView.updateToTieRounds();
            throw new UserNotificationException("The matched ended as a tie!\nMoving to the next phase!");
        } else if (soccerTieMatches == 2) {
            gameView.updateFreeKicks(false);
            assignFreeKicksEvents();
            throw new UserNotificationException("The matched ended as a tie!\nMoving to free kicks!\n1 Click to Miss, 2 Clicks to Goal");
        } else if (soccerTieMatches > 2) {
            gameView.updateFreeKicks(true);
            assignFreeKicksEvents();
            throw new UserNotificationException("The matched ended as a tie!\nMoving to other free kick!\n1 Click to Miss, 2 Clicks to Goal");
        }
    }

    private void eventForDoneButton() {
        EventHandler<ActionEvent> eventForDoneButton = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0) {
                try {
                    Participant firstParticipant = model.getParticipantByName(gameView.getFirstParticipant());
                    Participant secondParticipant = model.getParticipantByName(gameView.getSecondParticipant());

                    if (soccerTieMatches < 2)
                        getScores(gameView.getResultFields());
                    else
                        getFreeKickScores(gameView.getButtonsFreeKicks());

                    if (firstParticipantScores.size() == secondParticipantScores.size()) {
                        String winner = model.playGame(firstParticipant, secondParticipant,
                                                       firstParticipantScores, secondParticipantScores);

                        if (model.getChampionshipType().equals(View.SOCCER_TYPE) && winner == null) {
                            soccerRestrictions();
                        }

                        if (winner == null) {
                            tennisRestrictions();
                        } else {
                            winnerField.setText(winner);
                            gameView.closeWindow();
                        }
                    } else {
                        throw new ErrorToUserException("One of the players is missing his score!");
                    }

                } catch(NumberFormatException nfe) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("One or more of inputs is not a number!");
                    alert.show();
                } catch(ErrorToUserException etue) {
                    etue.show();
                } catch(UserNotificationException une) {
                    une.show();
                }
            }
        };
        gameView.addEventHandlerToDoneButton(eventForDoneButton);
    }

    public void assignFreeKicksEvents() {
        for (int i = 0; i < gameView.getRounds() * 2; i++) {
            eventForPlayGameButtons(i);
        }
    }

    private void eventForPlayGameButtons(int buttonNumber) {
        gameView.getButtonByNumber(buttonNumber).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameView.changeKickButtonValue(buttonNumber);
                if ((buttonNumber + 1) < gameView.getRounds() * 2) {
                    if (buttonNumber < gameView.getRounds())
                        gameView.enableKickButton(buttonNumber + gameView.getRounds());
                    else
                        gameView.enableKickButton(buttonNumber - gameView.getRounds() + 1);
                }
            }
        });
    }
}
