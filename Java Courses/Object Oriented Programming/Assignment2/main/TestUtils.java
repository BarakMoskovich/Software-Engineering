package main;

import controller.ControllerCLI;
import model.BallotBox;
import model.Party;
import model.Set;
import model.citizens.Citizen;
import model.citizens.Corona;
import model.citizens.Soldier;
import model.citizens.SoldierCorona;

import java.util.ArrayList;

public class TestUtils {
    public static void hardCodeToTest(ControllerCLI c) {
        // Ballot Boxes
        BallotBox<Citizen>       b1 = new BallotBox<Citizen>("Balfour", Citizen.class);
        BallotBox<Soldier>       b2 = new BallotBox<Soldier>("Jenin", Soldier.class);
        BallotBox<Corona>        b3 = new BallotBox<Corona>("Tel Aviv", Corona.class);
        BallotBox<SoldierCorona> b4 = new BallotBox<SoldierCorona>("Bet-el", SoldierCorona.class);

        // Parties
        Party[] parties = new Party[3];
        parties[0] = new Party("Likud", "right", 1973, 1, 1);
        parties[1] = new Party("Kahol Lavan", "center", 2019, 1, 1);
        parties[2] = new Party("Meretz", "left", 1992, 1, 1);

        // Add Ballot Boxes to Election
        c.addBallotBox(b1);
        c.addBallotBox(b2);
        c.addBallotBox(b3);
        c.addBallotBox(b4);

        // Ref Ballot Boxes
        ArrayList<BallotBox<Citizen>> refCitizenBallotBoxes     = (ArrayList<BallotBox<Citizen>>) ControllerCLI.election.getBallotBoxes(1);
        ArrayList<BallotBox<Corona>> refCoronaBallotBoxes       = (ArrayList<BallotBox<Corona>>) ControllerCLI.election.getBallotBoxes(2);
        ArrayList<BallotBox<Soldier>> refSoldierBallotBoxes     = (ArrayList<BallotBox<Soldier>>) ControllerCLI.election.getBallotBoxes(3);
        ArrayList<BallotBox<SoldierCorona>> refSoldierCoronaBallotBoxes = (ArrayList<BallotBox<SoldierCorona>>) ControllerCLI.election.getBallotBoxes(4);

        // Citizens
        Citizen[] citizens = new Citizen[8];
        citizens[0] = new Citizen("Barak", "000000001", 1996, refCitizenBallotBoxes.get(0), null, false); // Regular
        citizens[1] = new Citizen("Denis", "000000002", 1990, refCitizenBallotBoxes.get(0), null, false); // Regular
        citizens[2] = new Corona("Dana", "000000003", 1994, true, refCoronaBallotBoxes.get(0), 5); // Corona
        citizens[3] = new Corona("Alon", "000000004", 1998, true, refCoronaBallotBoxes.get(0), 15); // Corona
        citizens[4] = new Soldier("Tal", "000000005", 2002, refSoldierBallotBoxes.get(0), null, false); // Soldier
        citizens[5] = new Soldier("Bar", "000000006", 2001, refSoldierBallotBoxes.get(0), null, false); // Soldier
        citizens[6] = new SoldierCorona("Tom", "000000007", 2000, true, refSoldierCoronaBallotBoxes.get(0), 3); // SoldierCorona
        citizens[7] = new SoldierCorona("Shani", "000000008", 2001, true, refSoldierCoronaBallotBoxes.get(0), 21); // SoldierCorona

        // Add Citizen to VoterRegister
        c.addCitizen(citizens[0]);
        c.addCitizen(citizens[1]);
        c.addCitizen(citizens[2]);
        c.addCitizen(citizens[3]);
        c.addCitizen(citizens[4]);
        c.addCitizen(citizens[5]);
        c.addCitizen(citizens[6]);
        c.addCitizen(citizens[7]);

        // Ref of Citizens
        Set<Citizen> refCitizen = ControllerCLI.vr.getCitizens();

        // Add Parties to Election
        c.addParty(parties[0]);
        c.addParty(parties[1]);
        c.addParty(parties[2]);

        // Add Candidate to Parties
        c.addCandidate(refCitizen.get(0).getID(), parties[0].getName(), 1);
        c.addCandidate(refCitizen.get(1).getID(), parties[0].getName(), 2);
        c.addCandidate(refCitizen.get(2).getID(), parties[1].getName(), 2);
        c.addCandidate(refCitizen.get(3).getID(), parties[1].getName(), 1);
        c.addCandidate(refCitizen.get(4).getID(), parties[2].getName(), 5);
        c.addCandidate(refCitizen.get(5).getID(), parties[2].getName(), 1);
    }
}
