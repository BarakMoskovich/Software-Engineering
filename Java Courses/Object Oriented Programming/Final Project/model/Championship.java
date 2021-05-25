package model;

import exceptions.ErrorToUserException;
import view.View;

import java.util.ArrayList;

public class Championship {
    private Participant[] participants;
    private int activeParticipants;
    private String gameType;
    private GameModel singleGame;

    public Championship(int numberOfParticipants, String gameType) {
        participants = new Participant[numberOfParticipants]; // 8 Participant (From Model)
        this.gameType = gameType;
        activeParticipants = 0;
    }

    public Championship(int numberOfParticipants) {
        this(numberOfParticipants, View.TENNIS_TYPE); // default value
    }

    public Participant[] getParticipants() {
        return participants;
    }

    public int getActiveParticipants() {
        return activeParticipants;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
        singleGame = new GameModel(gameType);
    }

    public String getGameType() { return gameType; }

    public boolean addParticipants(String name) {
        if (activeParticipants == participants.length)
            return false;

        if (activeParticipants < participants.length) {
            for (int i = 0; i < activeParticipants; i++)
                if (participants[i].getName().equals(name))
                    return false;

            participants[activeParticipants] = new Participant(name);
            activeParticipants++;
            return true;
        }
        return false;
    }

    public Participant getParticipantByName(String name) {
        for (int i = 0; i < participants.length; i++) {
            if (participants[i].getName().equals(name))
                return participants[i];
        }
        return null;
    }

    public String playGame(Participant firstParticipant, Participant secondParticipant,
                                ArrayList<Integer> firstParticipantScores,
                                ArrayList<Integer> secondParticipantScores) throws ErrorToUserException {
        singleGame.setScores(firstParticipantScores, secondParticipantScores);
        String temp = singleGame.playGame(firstParticipant, secondParticipant);

        return temp;
    }
}
