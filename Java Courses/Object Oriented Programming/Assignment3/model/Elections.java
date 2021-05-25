package model;

import model.citizens.*;
import java.util.*;

public class Elections {
    /* Defaults:
   month: 1
   year: 2021
    */
    private ArrayList<BallotBox<Citizen>> citizen;
    private ArrayList<BallotBox<Soldier>> soldier;
    private ArrayList<BallotBox<SoldierCorona>> soldierCorona;
    private ArrayList<BallotBox<Corona>> corona;

    private HashMap<Party, Integer> parties;

    private int month;
    private int year;

    /************* Constructor *************/
    public Elections(ArrayList<BallotBox<Citizen>> citizen, ArrayList<BallotBox<Soldier>> soldier, ArrayList<BallotBox<SoldierCorona>> soldierCorona,
                     ArrayList<BallotBox<Corona>> corona, HashMap<Party, Integer> parties, int month, int year) {
        setCitizen(citizen);
        setSoldier(soldier);
        setSoldierCorona(soldierCorona);
        setCorona(corona);
        setParties(parties);
        setMonth(month);
        setYear(year);
    }

    public Elections(int month, int year) {
        this (null, null, null, null, null, month, year);
    }

    public Elections() {
        this (null, null, null, null, null, 1, 2021); // Default
    }

    /************ Set Functions ************/
    private boolean setParties(HashMap<Party, Integer> parties){
        if (parties != null && !parties.isEmpty()) {
            this.parties = parties;
            return true;
        }
        this.parties = new HashMap<Party, Integer>(0);
        return false;
    }

    private boolean setCitizen(ArrayList<BallotBox<Citizen>> ballotBoxes) {
        try {
            if (ballotBoxes != null)
                this.citizen = new ArrayList<>(ballotBoxes);
            else
                this.citizen = new ArrayList<>();
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong");
            return false;
        }
    }

    private boolean setCorona(ArrayList<BallotBox<Corona>> ballotBoxes) {
        try {
            if (ballotBoxes != null)
                this.corona = new ArrayList<>(ballotBoxes);
            else
                this.corona = new ArrayList<>();
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong");
            return false;
        }
    }

    private boolean setSoldier(ArrayList<BallotBox<Soldier>> ballotBoxes) {
        try {
            if (ballotBoxes != null)
                this.soldier = new ArrayList<>(ballotBoxes);
            else
                this.soldier = new ArrayList<>();
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong");
            return false;
        }
    }

    private boolean setSoldierCorona(ArrayList<BallotBox<SoldierCorona>> ballotBoxes) {
        try {
            if (ballotBoxes != null)
                this.soldierCorona = new ArrayList<>(ballotBoxes);
            else
                this.soldierCorona = new ArrayList<>();
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong");
            return false;
        }
    }

    private boolean setYear(int year) {
        if (year >= 2020) {
            this.year = year;
            return true;
        } else {
            this.year = 2020;
            return false;
        }
    }

    private boolean setMonth(int month) {
        if (month >= 1 && month <= 12) {
            this.month = month;
            return true;
        } else {
            this.month = 1;
            return false;
        }
    }

    /************ Get Functions ************/
    public ArrayList<BallotBox<Citizen>> getRegular() {
        return citizen;
    }

    public ArrayList<BallotBox<Soldier>> getSoldier() {
        return soldier;
    }

    public ArrayList<BallotBox<SoldierCorona>> getSoldierCorona() {
        return soldierCorona;
    }

    public ArrayList<BallotBox<Corona>> getCorona() {
        return corona;
    }

    public HashMap<Party, Integer> getParties() {
        return parties;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getAllParties() {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<Party, Integer> party: parties.entrySet()) {
            sb.append(party.getKey().getName() + "(" + party.getKey().getSection() + ") ");
        }
        return sb.toString();
    }

    public List<? extends BallotBox<? extends Citizen>> getBallotBoxes(int type) {
        /** 1 - Citizen / Regular
         ** 2 - Corona
         ** 3 - Solider
         ** 4 - SoliderCorona **/
        try {
            switch (type) {
                case 1:
                    return citizen;
                case 2:
                    return corona;
                case 3:
                    return soldier;
                case 4:
                    return soldierCorona;
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return null;
    }

    public String getAllBallotBoxes() {
        /** 1 - Citizen / Regular
         ** 2 - Corona
         ** 3 - Solider
         ** 4 - SoliderCorona **/
        StringBuffer sb = new StringBuffer();
        for (BallotBox<Citizen> ballotBox : citizen) {
            sb.append(ballotBox.toString() + "\n");
            sb.append("----------------\n");
        }
        for (BallotBox<Corona> ballotBox : corona)
            sb.append(ballotBox.toString() + "\n");
        for (BallotBox<Soldier> ballotBox : soldier)
            sb.append(ballotBox.toString() + "\n");
        for (BallotBox<SoldierCorona> ballotBox : soldierCorona)
            sb.append(ballotBox.toString() + "\n");
        return sb.toString();
    }

    public String getBallotBoxesByType(int type, boolean toCheck) {
        try {
            if (toCheck) {
                String toReturn = "";
                switch (type) {
                    case 1:
                        for (BallotBox<Citizen> c : citizen)
                            toReturn += c.getId() + ";";
                        return toReturn;
                    case 2:
                        for (BallotBox<Corona> c : corona)
                            toReturn += c.getId() + ";";
                        return toReturn;
                    case 3:
                        for (BallotBox<Soldier> c : soldier)
                            toReturn += c.getId() + ";";
                        return toReturn;
                    case 4:
                        for (BallotBox<SoldierCorona> c : soldierCorona)
                            toReturn += c.getId() + ";";
                        return toReturn;
                }
            } else {
                switch (type) {
                    case 1:
                        return citizen.toString();
                    case 2:
                        return corona.toString();
                    case 3:
                        return soldier.toString();
                    case 4:
                        return soldierCorona.toString();
                }
            }
        } catch(Exception e){
            System.out.println("Something went wrong");
        }
        return null;
    }

    /************** Functions **************/
    public boolean addBallotBoxes(ArrayList<BallotBox<? extends Citizen>> ballotBoxes) {
        try {
            for(BallotBox<? extends Citizen> ballotBox: ballotBoxes) {
                addBallotBox(ballotBox);
                updateBallotBoxes();
            }
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong");
            return false;
        }
    }

    public boolean addBallotBox(BallotBox<? extends Citizen> ballotBox) {
        try {
            Class<? extends Citizen> classType = ballotBox.getClassType();

            if (Citizen.class.equals(classType))
                citizen.add(new BallotBox<>((BallotBox<Citizen>) ballotBox));
            else if (Corona.class.equals(classType))
                corona.add(new BallotBox<>((BallotBox<Corona>) ballotBox));
            else if (Soldier.class.equals(classType))
                soldier.add(new BallotBox<>((BallotBox<Soldier>) ballotBox));
            else if (SoldierCorona.class.equals(classType))
                soldierCorona.add(new BallotBox<>((BallotBox<SoldierCorona>) ballotBox));
            updateBallotBoxes();
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong");
            return false;
        }
    }

    public void addCitizenToBallotBox(Citizen citizen, BallotBox<? extends Citizen> ballotBox) {
        try {
            if (citizen instanceof Corona) {
                if (corona.contains(ballotBox))
                    corona.get(corona.indexOf(ballotBox)).addCitizen((Corona)citizen);
            } else if (citizen instanceof Soldier) {
                if (soldier.contains(ballotBox))
                    soldier.get(soldier.indexOf(ballotBox)).addCitizen((Soldier)citizen);
            } else if (citizen instanceof SoldierCorona) {
                if (soldierCorona.contains(ballotBox))
                    soldierCorona.get(soldierCorona.indexOf(ballotBox)).addCitizen((SoldierCorona)citizen);
            } else{
                if (this.citizen.contains(ballotBox))
                    this.citizen.get(this.citizen.indexOf(ballotBox)).addCitizen(citizen);
            }
        } catch (Exception e){
            System.out.println(e.toString());
            System.out.println("Something went wrong");
        }

    }

    public BallotBox<? extends Citizen> getBallotBoxByNumber(int number) {

        for (BallotBox<Citizen> regular: this.citizen) {
            if (regular.getId() == number)
                return regular;
        }

        for (BallotBox<Corona> corona: this.corona) {
            if (corona.getId() == number)
                return corona;
        }

        for (BallotBox<Soldier> soldier: this.soldier) {
            if (soldier.getId() == number)
                return soldier;
        }

        for (BallotBox<SoldierCorona> soldierCorona: this.soldierCorona) {
            if (soldierCorona.getId() == number)
                return soldierCorona;
        }

        return null;
    }

    public Party getPartiesByName(String partyName) {
        for (Party party: parties.keySet()) {
            if (party.getName().toUpperCase().equals(partyName.toUpperCase()))
                return party;
        }
        return null;
    }

    public boolean addParty(Party party) {
        if (party != null && !parties.containsKey(party)) {
            this.parties.put(party, 0);
            updateBallotBoxes();
            return true;
        }
        return false;
    }

    private void updateBallotBoxes() {
        for (BallotBox<Citizen> ballotBox : citizen)
            ballotBox.setParties(parties);
        for (BallotBox<Corona> ballotBox : corona)
            ballotBox.setParties(parties);
        for (BallotBox<Soldier> ballotBox : soldier)
            ballotBox.setParties(parties);
        for (BallotBox<SoldierCorona> ballotBox : soldierCorona)
            ballotBox.setParties(parties);
    }

    public boolean existBallotBox(BallotBox<? extends Citizen> ballotBox) {
        String b = ballotBox.getType();
        switch (b) {
            case "Citizen":
                return citizen.contains(ballotBox);
            case "Corona":
                return corona.contains(ballotBox);
            case "Soldier":
                return soldier.contains(ballotBox);
            case "SoldierCorona":
                return soldierCorona.contains(ballotBox);
            default:
                return false;
        }
    }

    public boolean existParty(Party party) {
        if (parties.containsKey(party))
            return true;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Elections elections = (Elections) o;
        return month == elections.month &&
                year == elections.year &&
                Objects.equals(citizen, elections.citizen) &&
                Objects.equals(soldier, elections.soldier) &&
                Objects.equals(soldierCorona, elections.soldierCorona) &&
                Objects.equals(corona, elections.corona) &&
                Objects.equals(parties, elections.parties);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getAllBallotBoxes());
        return sb.toString();
    }
}