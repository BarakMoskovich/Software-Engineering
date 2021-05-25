package model.citizens;

import model.*;

public class SoldierCorona extends Citizen implements Soldierable {
    private boolean isolation;
    private int daysInIsolation;

    /************ Constructor *************/
    public SoldierCorona(String name, String id, int birthYear, boolean isolation, BallotBox ballotBox, Party party, boolean voted, int daysInIsolation) {
        super(name, id, birthYear, ballotBox, party, voted);
        setIsolation(isolation);
        setDaysInIsolation(daysInIsolation);
    }

    public SoldierCorona(SoldierCorona soldierCorona) {
        this(soldierCorona.name, soldierCorona.id, soldierCorona.birthYear,
                soldierCorona.isolation, soldierCorona.ballotBox, soldierCorona.party,
                soldierCorona.voted, soldierCorona.daysInIsolation);
    }

    public SoldierCorona(String name, String id, int birthYear, boolean isolation, BallotBox ballotBox, int daysInIsolation) {
        super(name, id, birthYear, ballotBox);
        this.isolation = isolation;
        this.daysInIsolation = daysInIsolation;
    }

    public SoldierCorona(String name, String id, int birthYear) {
        super(name, id, birthYear);
        isolation = false;
        daysInIsolation = 0;
    }

    /************ Get Functions ***********/
    public int getDaysInIsolation() {
        return daysInIsolation;
    }

    public boolean isIsolation() {
        return isolation;
    }

    /************ Set Functions ***********/
    private boolean setIsolation(boolean isolation) {
        this.isolation = isolation;
        return isolation;
    }

    private boolean setDaysInIsolation(int days){
        if (days >= 0){
            this.daysInIsolation = days;
            return true;
        }
        return false;
    }

    /************** Functions *************/
    @Override
    public void carryWeapon() {
        System.out.println("Achievement unlocked: Rifleman");
    }

    @Override
    public boolean canVote() {
        if(super.canVote() && isolation)
            return true;
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());

        if (isolation)
            sb.append("In isolation for " + daysInIsolation + " days\n");
        else
            sb.append("Not in isolation\n");

        return sb.toString();
    }
}