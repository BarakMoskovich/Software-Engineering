package controller;

import exceptions.ErrorToUserException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Model;
import view.ChampionshipView;
import view.GameView;

import java.util.ArrayList;

public class ChampionshipController {
    private Model model;
    private ChampionshipView championshipView;
    private Stage currentStage;
    private int numberOfParticipants = model.NUMBER_OF_PARTICIPANTS;
    private int numberOfParallelRounds = model.NUMBER_OF_PARALLEL_ROUNDS;
    private ArrayList<boolean[]> playedMatches; // check if you can press the current button

    public ChampionshipController(Model model, ChampionshipView view, Stage stage) {
        this.model = model;
        championshipView = view;
        currentStage = stage;

        playedMatches = new ArrayList<boolean[]>();


        assignPlayedMatches();
        assignAllEvents();

    }

    private void assignPlayedMatches() {
        int numberOfMatches = numberOfParticipants / 2;

        for (int i = 0; i < numberOfParallelRounds; i++) { // all other TextFields are assigned
            boolean[] temp = new boolean[numberOfMatches];

            playedMatches.add(temp);
            numberOfMatches /= 2;
        }
    }

    private boolean isCurrentStagePlayable(int roundNumber) {
        boolean temp[];
        boolean result;

        if (roundNumber == 0) {
            return true;
        } else {
            temp = playedMatches.get(roundNumber - 1);
        }

        result = isAllPlayed(temp);
        return result;
    }

    private boolean isAllPlayed(boolean[] temp) {
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == false)
                return false;
        }
        return true;
    }

    private String[] getParticipants(int roundNumber, int buttonPlace) {
        TextField[] txtFields = championshipView.getParticipants(roundNumber);
        String[] returnArr = new String[2];

        returnArr[0] = txtFields[buttonPlace * 2].getText();
        returnArr[1] = txtFields[(buttonPlace * 2) + 1].getText();

        return returnArr;
    }

    private void game(int roundNumber, int buttonPlace) {
        boolean isPlayed = playedMatches.get(roundNumber)[buttonPlace];
        boolean isCurrentStagePlayable = isCurrentStagePlayable(roundNumber);
        String[] participants = getParticipants(roundNumber, buttonPlace);

        if (isCurrentStagePlayable) {
            if (!isPlayed) {
                // open GameController
                TextField winnerTextField = championshipView.getWinnerTextField(roundNumber, buttonPlace);
                GameView gameView = new GameView(participants[0], participants[1], currentStage, model.getChampionshipType());
                GameController gameController = new GameController(model, gameView, winnerTextField);

                playedMatches.get(roundNumber)[buttonPlace] = true;
            } else {
                new ErrorToUserException("The match has already been played!").show();
            }
        } else {
            new ErrorToUserException("You have to finish previous matches to be able to play this game!").show();
        }
    }

    public void assignAllEvents() {
        for (int i = 0; i < numberOfParallelRounds; i++) {
            for (int j = 0; j < numberOfParticipants / 2; j++) {
                eventForPlayGameButtons(i, j);
            }
            numberOfParticipants/=2;
        }
    }

    private void eventForPlayGameButtons(int roundNumber, int buttonPlace) {
        championshipView.getButtonNumber(roundNumber, buttonPlace).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game(roundNumber, buttonPlace);
            }
        });
    }
}