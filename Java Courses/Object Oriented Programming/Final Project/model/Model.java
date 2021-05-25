package model;

import exceptions.ErrorToUserException;
import java.util.ArrayList;

public class Model {
    private Championship championship;

    public static final int NUMBER_OF_PARTICIPANTS = 8; // current number of participants
                                                        // must be a multiple of 2
    public static final int NUMBER_OF_PARALLEL_ROUNDS = (int)(Math.log(NUMBER_OF_PARTICIPANTS) / Math.log(2));

    public Model() {
        championship = new Championship(NUMBER_OF_PARTICIPANTS);
    }

    public boolean addParticipant(String name) {
        return championship.addParticipants(name);
    }

    public String playGame(Participant firstParticipant, Participant secondParticipant,
                                ArrayList<Integer> firstParticipantScores,
                                ArrayList<Integer> secondParticipantScores) throws ErrorToUserException {

        return championship.playGame(firstParticipant, secondParticipant, firstParticipantScores, secondParticipantScores);
    }

    public int getCurrentNumberOfParticipants() {
        return championship.getActiveParticipants();
    }

    public boolean canAddContestant() {
        return championship.getActiveParticipants() != NUMBER_OF_PARTICIPANTS;
    }

    public Participant[] getParticipants() {
        return championship.getParticipants();
    }

    public Participant getParticipantByName(String firstParticipant) {
        return championship.getParticipantByName(firstParticipant);
    }

    public void setChampionshipGameType(String gameType) {
        championship.setGameType(gameType);
    }

    public String getChampionshipType() {
        return championship.getGameType();
    }

    public boolean legalParticipantName(String name) {
        if (name != null) {
            String[] temp = name.split(" ");
            for (int i = 0; i < temp.length; i++)
                if (!temp[i].matches("^[a-zA-Z. ]*$"))
                    return false;
            return true;
        }
        return false;
    }
}