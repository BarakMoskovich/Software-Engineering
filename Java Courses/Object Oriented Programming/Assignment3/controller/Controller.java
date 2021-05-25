package controller;

import model.*;
import model.citizens.*;
import java.util.HashMap;
import java.util.Scanner;

public class Controller {
    public static Elections election;
    public static VoterRegister vr;

    public Controller() {
        election = new Elections();
        vr = new VoterRegister();
    }

    public Elections getElection() {
        return election;
    }

    public VoterRegister getVoterRegister() {
        return vr;
    }

    public String getCLIMenu() {
        String sb = "1 - Add Ballot Box \n" +
                "2 - Add Citizen \n" +
                "3 - Add Party \n" +
                "4 - Add Candidate \n" +
                "5 - Show All Ballot Box \n" +
                "6 - Show All Citizens \n" +
                "7 - Show All Parties \n" +
                "8 - Begin Elections \n" +
                "9 - Show Results \n" +
                "10 - Exit \n";
        return sb;
    }

    public boolean addBallotBox(BallotBox<? extends Citizen> ballotBox) {
        if (ballotBox.getAddress().length() != 0 && !election.existBallotBox((BallotBox<Citizen>) ballotBox)) {
            election.addBallotBox(ballotBox);
            return true;
        } else {
            System.out.println("This BallotBox has already been created!!!\n");
            return false;
        }
    }

    public boolean addParty(Party party) {
        if (party.getName().length() != 0 && !election.existParty(party)) {
            election.addParty(party);
            return true;
        } else {
            System.out.println("You are not allowed to add this party");
            return false;
        }
    }

    public String getAllCitizens() {
        return vr.toString();
    }

    public String getAllBallotBox() {
        return election.getAllBallotBoxes();
    }

    public String getAllParties() {
        String str = "";
        for (Party party : election.getParties().keySet())
            str += party.toString() + "\n";
        return str;
    }

    public boolean addCitizen(Citizen citizen) {
        if (vr.addCitizen(citizen)) {
            election.addCitizenToBallotBox(citizen, citizen.getBallotBox());
            return true;
        }
        System.out.println("The citizen shouldn't be added!");
        return false;
    }

    public void addCandidate(String citizenID, String partyName, int place) {
        if (citizenID.length() != 0 && citizenID != null && partyName != null && partyName.length() != 0) {
            Citizen citizen = vr.getCitizenById(citizenID);
            Party party = election.getPartiesByName(partyName);

            if (citizen != null && party != null) {
                party.addCandidate(citizen, place);
            } else {
                System.out.println("An error has occurred\n");
            }
        } else
            System.out.println("An error has occurred\n");
    }

    public String getResults() {
        HashMap<Party, Integer> refParties = election.getParties();
        StringBuffer sb = new StringBuffer();
        sb.append("--------------------------------------------------\n");
        for (Party party : refParties.keySet()) {
            sb.append(party.getName() + " : " + refParties.get(party) + "\n");
        }
        sb.append("--------------------------------------------------\n");
        return sb.toString();
    }

    private String getVoteParty(Scanner s, String citizenName) {
        System.out.print(citizenName + ", You want to vote ? [Y/N] ");
        char ansChar = s.next().toCharArray()[0];
        if (ansChar == 'y' || ansChar == 'Y') {
            s.nextLine();
            System.out.print("You want to vote to : ");
            return s.nextLine();
        } else if (ansChar == 'n' || ansChar == 'N') {
            return "false";
        } else
            return " ";
    }

    private boolean CoronaQuiz(Scanner s) {
        System.out.print("Are you wearing a protective suit ?  [Y/N] ");
        char ansChar = s.next().toCharArray()[0];
        if (ansChar == 'y' || ansChar == 'Y')
            return true;
        return false;
    }

    public void startVoteCLI(Scanner s) {
        Set<Citizen> citizens = vr.getCitizens(); // Reference
        Party partyVote;
        String getVote;
        System.out.println(election.getAllParties());

        for (Citizen c : citizens.getSet()) {
            if (c != null) {
                if (!c.getVoted()) {
                    getVote = getVoteParty(s, c.getName());
                    if (getVote.equals("false")) {
                        System.out.println("Don't worry, See you in three months\n");
                    } else {
                        partyVote = election.getPartiesByName(getVote);
                        if (partyVote != null) {
                            if (c.getBallotBox().getType().equals("Corona")
                                    || c.getBallotBox().getType().equals("SoldierCorona")) {
                                if (CoronaQuiz(s))
                                    c.getBallotBox().vote(c, partyVote);
                                else
                                    System.out.println("You have been violated Ministers of health rules against Corona pandemic !!! You have to pay a 15,000 Shekels fee !!!\n");
                            } else
                                c.getBallotBox().vote(c, partyVote);
                        } else {
                            System.out.println("Ooops... Something went wrong with your vote...\n");
                        }
                    }
                }
            }
        }
    }

    public static String getLegalBallotBoxes(int birthYear, boolean isolation, boolean toCheck) {
        int age = election.getYear() - birthYear;
        if (age >= 18 && age <= 21) {
            if (isolation)
                return election.getBallotBoxesByType(4, toCheck); // 4 - SoliderCorona
            else
                return election.getBallotBoxesByType(3, toCheck); // 3 - Solider
        } else if (isolation)
            return election.getBallotBoxesByType(2, toCheck); // 2 - Corona
        else
            return election.getBallotBoxesByType(1, toCheck); // 1 - Citizen / Regular

    }
}
