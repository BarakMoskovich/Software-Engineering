

import java.util.Scanner;

public class ServicesManager {
    public static Elections election = new Elections();
    public static VoterRegister vr = new VoterRegister();

    public static void hardCodeToTest() {
        // Ballot Boxes
        BallotBox[] ballotBoxes = new BallotBox[3];
        ballotBoxes[0] = new Regular("Balfour");
        ballotBoxes[1] = new Army("Jenin");
        ballotBoxes[2] = new Corona("Tel Aviv");

        // Parties
        Party[] parties = new Party[3];
        parties[0] = new Party("Likud", "right", 1973, 1, 1);
        parties[1] = new Party("Kahol Lavan", "center", 2019, 1, 1);
        parties[2] = new Party("Meretz", "left", 1992, 1, 1);

        addBallotBox(ballotBoxes[0]);
        addBallotBox(ballotBoxes[1]);
        addBallotBox(ballotBoxes[2]);

        BallotBox[] refBallotBoxes = election.getBallotBoxes();

        // Citizens
        Citizen[] citizens = new Citizen[6];
        citizens[0] = new Citizen("Barak", "000000001", 1996, false, refBallotBoxes[0], null, false); // Regular
        citizens[1] = new Citizen("Denis", "000000002", 1990, false, refBallotBoxes[0], null, false); // Regular
        citizens[2] = new Citizen("Dana", "000000003", 2002, false, refBallotBoxes[1], null, false); // Army
        citizens[3] = new Citizen("Alon", "000000004", 2001, false, refBallotBoxes[1], null, false); // Army
        citizens[4] = new Citizen("Tal", "000000005", 1986, true, refBallotBoxes[2], null, false); // Corona
        citizens[5] = new Citizen("Bar", "000000006", 1994, true, refBallotBoxes[2], null, false); // Corona

        addCitizen(citizens[0]);
        addCitizen(citizens[1]);
        addCitizen(citizens[2]);
        addCitizen(citizens[3]);
        addCitizen(citizens[4]);
        addCitizen(citizens[5]);

        Citizen[] refCitizen = vr.getCitizens();

        addParty(parties[0]);
        addParty(parties[1]);
        addParty(parties[2]);

        addCandidate(refCitizen[0].getID(), parties[0].getName(), 1);
        addCandidate(refCitizen[1].getID(), parties[0].getName(), 2);
        addCandidate(refCitizen[2].getID(), parties[1].getName(), 2);
        addCandidate(refCitizen[3].getID(), parties[1].getName(), 1);
        addCandidate(refCitizen[4].getID(), parties[2].getName(), 5);
        addCandidate(refCitizen[5].getID(), parties[2].getName(), 1);

//        System.out.println(election.toString());
//        System.out.println(vr.toString());
    }

    public static void showMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("1 - Add Ballot Box \n");
        sb.append("2 - Add Citizen \n");
        sb.append("3 - Add Party \n");
        sb.append("4 - Add Candidate \n");
        sb.append("5 - Show All Ballot Box \n");
        sb.append("6 - Show All Citizens \n");
        sb.append("7 - Show All Parties \n");
        sb.append("8 - Begin Elections \n");
        sb.append("9 - Show Results \n");
        sb.append("10 - Exit \n");
        System.out.println(sb.toString());
    }

    public static void addBallotBox(BallotBox ballotBox) {
        if (ballotBox.getAddress().length() != 0 && !election.existBallotBox(ballotBox))
            election.addBallotBoxes(ballotBox);
        else
            System.out.println("This BallotBox has already been created!!!\n");

    }

    public static void addCitizen(Citizen citizen) {
        if (!citizen.getID().equals("-1") && citizen.getName().length() != 0) {
            if (citizen.isIsolation() && !(citizen.getBallotBox() instanceof Corona) ||
                    (election.getYear() - citizen.getBirthYear()) <= 21 && !(citizen.getBallotBox() instanceof Army) && !citizen.isIsolation())
                System.out.println("You are prohibited to enter as a citizen to this ballot box!");
            else {
                vr.addCitizen(citizen);
                Citizen refToCitizen = vr.getCitizenById(citizen.getID());
                election.addCitizenToBallotBoxes(refToCitizen);
            }
        } else
            System.out.println("You are prohibited to enter as a citizen!");
    }

    public static BallotBox getBallotBoxByNumber(int number) {
        return election.getBallotBoxByNumber(number);
    }

    public static void addParty(Party party) {
        if (party.getName().length() != 0 && !election.existParty(party))
            election.addParty(party);
        else
            System.out.println("You are not allowed to add this party");
    }

    public static void addCandidate(String citizenID, String partyName, int place) {
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

    public static String showAllCitizens() {
        return vr.toString();
    }

    public static String showAllBallotBox() {
        return election.getAllBallotBoxes();
    }

    public static String showAllParties() {
        return election.getAllParties();
    }

    public static void beginElections(Scanner s) {
        Citizen[] citizens = vr.getCitizens(); // Reference
        Party partyVote;
        String getVote;
        System.out.println(election.getAllPartiesLine());
        for (int i = 0; i < citizens.length; i++) {
            if (citizens[i] != null) {
                if (!citizens[i].getVoted()) {
                    getVote = Program.getVoteParty(s, citizens[i].getName());
                    if (getVote.equals("false")) {
                        System.out.println("Don't worry, See you in three months\n");
                    } else {
                        partyVote = election.getPartiesByName(getVote);
                        if (partyVote != null) {
                            if (citizens[i].getBallotBox() instanceof Corona) {
                                if (Program.CoronaQuiz(s))
                                    citizens[i].getBallotBox().vote(citizens[i], partyVote);
                                else
                                    System.out.println("You have been violated Ministers of health rules against Corona pandemic !!! You have to pay a 15,000 Shekels fee !!!\n");
                            } else
                                citizens[i].getBallotBox().vote(citizens[i], partyVote);
                        } else {
                            System.out.println("Ooops... Something went wrong with your vote...\n");
                        }
                    }
                }
            }
        }
    }

    public static void showResults() {
        int[] results = new int[election.getAllParties().length()];
        BallotBox[] ballotBoxes = election.getBallotBoxes();
        int[] voteForParty;
        Party[] parties;

        System.out.println("--------------------------------------------------\n");
        for (int i = 0; i < ballotBoxes.length; i++) {
            if (ballotBoxes[i] != null) {
                System.out.print(ballotBoxes[i].toString());
                voteForParty = ballotBoxes[i].getVotesForParty();
                parties = ballotBoxes[i].getParties();
                for (int j = 0; j < parties.length; j++) {
                    if (parties[j] != null) {
                        results[j] += voteForParty[j];
                        System.out.println(parties[j].getName() + " : " + voteForParty[j]);
                    }
                }

                System.out.println("");
            }
        }
        System.out.println("--------------------------------------------------\n");

        parties = election.getParties();
        System.out.println("Final Results : ");
        for (int i = 0; i < parties.length; i++) {
            if (parties[i] != null) {
                System.out.println(parties[i].getName() + " : " + results[i]);
            }
        }
    }

    public static String getLegalBallotBoxes(int birthYear, boolean isolation) {
        String str = "";
        BallotBox[] refBallotBoxes = election.getBallotBoxes();
        for (int i = 0; i <refBallotBoxes.length; i++) {
            if (isolation) {
                if (refBallotBoxes[i] instanceof Corona)
                    str += refBallotBoxes[i].getId() + "-" + refBallotBoxes[i].getAddress() + " ";

            } else if (election.getYear() - birthYear <= 21) { // 18+
                if (refBallotBoxes[i] instanceof Army)
                    str += refBallotBoxes[i].getId() + "-" + refBallotBoxes[i].getAddress() + " ";
            } else {
                if (refBallotBoxes[i] instanceof Regular)
                    str += refBallotBoxes[i].getId() + "-" + refBallotBoxes[i].getAddress() + " ";
            }
        }
        return str;
    }
}
