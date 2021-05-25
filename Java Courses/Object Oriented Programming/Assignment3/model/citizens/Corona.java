package model.citizens;

import model.*;

public class Corona extends Citizen {
    private boolean isolation;
    private int daysInIsolation;

    /************ Constructor *************/
    public Corona(String name, String id, int birthYear, boolean isolation, BallotBox ballotBox, Party party, boolean voted, int daysInIsolation) {
        super(name, id, birthYear, ballotBox, party, voted);
        setIsolation(isolation);
        setDaysInIsolation(daysInIsolation);
    }

    public Corona(Corona corona) {
        super(corona);
        this.isolation = corona.isolation;
        this.daysInIsolation = corona.daysInIsolation;
    }

    public Corona(String name, String id, int birthYear, boolean isolation, BallotBox ballotBox, int daysInIsolation) {
        super(name, id, birthYear, ballotBox);
        this.isolation = isolation;
        this.daysInIsolation = daysInIsolation;
    }

    public Corona(String name, String id, int birthYear) {
        super(name, id, birthYear);
        isolation = true;
        daysInIsolation = 0;
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

    /************ Get Functions ***********/
    public int getDaysInIsolation() {
        return daysInIsolation;
    }

    public boolean isIsolation() {
        return isolation;
    }

    /************** Functions *************/
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

    @Override
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