package model;

import exceptions.ErrorToUserException;
import view.GameView;
import view.View;

import java.util.ArrayList;

public class GameModel {
    private String gameType;
    private ArrayList<Integer> firstParticipantScores, secondParticipantScores;
    private boolean isSoccerGameTie;
    private boolean isSoccerGameFreeKick;
    private boolean isSoccerGameFreeKickTie;
    private int numberOfSets;


    public GameModel(String gameType) {
        this.gameType = gameType;
        isSoccerGameTie = false;
        isSoccerGameFreeKick = false;
        isSoccerGameFreeKickTie = false;
        numberOfSets = 0;
    }

    private void calculateScores(Participant firstParticipant, Participant secondParticipant, int numberOfSets) {
        firstParticipant.clearSetWins();
        secondParticipant.clearSetWins();

        for (int i = 0; i < numberOfSets; i++) {
            int firstScore = firstParticipantScores.get(i);
            int secondScore = secondParticipantScores.get(i);

            if (firstScore > secondScore) {
                firstParticipant.winSet();
            }
            if (firstScore < secondScore) {
                secondParticipant.winSet();
            }
        }
    }

    private String getWinner(Participant firstParticipant, Participant secondParticipant) {

        String winner = null; // tie value

        if (firstParticipant.getSetWins() > secondParticipant.getSetWins()) {
            winner = firstParticipant.getName();
        }
        if (firstParticipant.getSetWins() < secondParticipant.getSetWins()) {
            winner = secondParticipant.getName();
        }

        return winner;
    }

    private String calculateAndGetWinner(Participant firstParticipant, Participant secondParticipant, int numberOfSets) {
        calculateScores(firstParticipant, secondParticipant, numberOfSets);
        return getWinner(firstParticipant, secondParticipant);
    }

    public String tennisGame(Participant firstParticipant, Participant secondParticipant) throws ErrorToUserException {
        // array length are equal
        String winner = null; // tie value

        if (numberOfSets == GameView.TENNIS_MINIMUM_ROUNDS) {
            calculateScores(firstParticipant, secondParticipant, numberOfSets);

            if (firstParticipant.getSetWins() == numberOfSets) {
                winner = firstParticipant.getName();
            } else if (secondParticipant.getSetWins() == numberOfSets) {
                winner = secondParticipant.getName();
            }

        } else if (numberOfSets == GameView.TENNIS_MAXIMUM_ROUNDS) {
            winner = calculateAndGetWinner(firstParticipant, secondParticipant, numberOfSets);
        } else {
            throw new ErrorToUserException("In Tennis there are 3 or 5 sets");
        }

        return winner;
    }

    public String basketballGame(Participant firstParticipant, Participant secondParticipant) throws ErrorToUserException {
        String winner = null; // tie value

        if (numberOfSets == GameView.BASKETBALL_ROUNDS) {
            winner = calculateAndGetWinner(firstParticipant, secondParticipant, numberOfSets);
        } else {
            throw new ErrorToUserException("In BasketBall there are only 4 sets!");
        }

        return winner;
    }

    public String soccerGame(Participant firstParticipant, Participant secondParticipant) throws ErrorToUserException{
        String winner = null; // tie value

        if (!isSoccerGameTie && !isSoccerGameFreeKick && !isSoccerGameFreeKickTie) {
            if (numberOfSets == GameView.SOCCER_ROUNDS) {
                winner = calculateAndGetWinner(firstParticipant, secondParticipant, numberOfSets);
                if (winner == null)
                    isSoccerGameTie = true;
            } else {
                throw new ErrorToUserException("You must assign scores to all sets!");
            }
        } else if (isSoccerGameTie) {
            if (numberOfSets == GameView.SOCCER_TIE_ROUNDS) {
                winner = calculateAndGetWinner(firstParticipant, secondParticipant, numberOfSets);
                if (winner == null) {
                    isSoccerGameFreeKick = true;
                    isSoccerGameTie = false;
                    isSoccerGameFreeKickTie = false;
                } else
                    isSoccerGameTie = false;
            } else {
                throw new ErrorToUserException("You must assign scores to all sets!");
            }
        } else if (isSoccerGameFreeKick) {
            if (numberOfSets == GameView.SOCCER_FREE_KICKS) {
                winner = calculateAndGetWinner(firstParticipant, secondParticipant, numberOfSets);
                if (winner == null) {
                    isSoccerGameFreeKickTie = true;
                    isSoccerGameFreeKick = false;
                    isSoccerGameTie = false;
                } else
                    isSoccerGameFreeKick = false;
            } else {
                throw new ErrorToUserException("You must assign scores to all sets!");
            }
        } else if (isSoccerGameFreeKickTie) {
            if (numberOfSets == GameView.SOCCER_FREE_KICKS_TIE) {
                winner = calculateAndGetWinner(firstParticipant, secondParticipant, numberOfSets);
                if (winner != null) {
                    isSoccerGameFreeKickTie = false;
                    isSoccerGameFreeKick = false;
                    isSoccerGameTie = false;
                }
            } else {
                throw new ErrorToUserException("You must assign scores to all sets!");
            }
        }

        return winner;
    }

    public String playGame(Participant participant1, Participant participant2) throws ErrorToUserException {
        String winner;

        switch (gameType) {
            case View.BASKETBALL_TYPE:
                winner = basketballGame(participant1, participant2);
                break;
            case View.SOCCER_TYPE:
                winner = soccerGame(participant1, participant2);
                break;
            case View.TENNIS_TYPE:
            default:
                winner = tennisGame(participant1, participant2);
                break;
        }

        return winner;
    }

    public void setScores(ArrayList<Integer> firstParticipantScores,
                          ArrayList<Integer> secondParticipantScores) {
        this.firstParticipantScores = firstParticipantScores;
        this.secondParticipantScores = secondParticipantScores;
        numberOfSets = firstParticipantScores.size();
    }
}
