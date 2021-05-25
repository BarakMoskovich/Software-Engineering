package model.citizens;

import model.*;

public class Citizen {
    /* Defaults:
      name: citizen
      id: 123456789
      birthYear: 1990
      isolation: false
      ballotBox: null
      party: null
      voted: false
  */
    protected String name;
    protected String id;
    protected int birthYear;
    protected BallotBox ballotBox;
    protected Party party;
    protected boolean voted;
    protected boolean isInParty;
    protected boolean isInBallotBox;

    /************ Constructor *************/
    public Citizen(String name, String id, int birthYear, BallotBox ballotBox, Party party, boolean voted) {
        setName(name);
        setId(id);
        setBirthYear(birthYear);
        setBallotBox(ballotBox);
        setInParty(party);
        setVoted(voted);
    }

    public Citizen(Citizen citizen) {
        name = citizen.name;
        id = citizen.id;
        birthYear = citizen.birthYear;
        ballotBox = citizen.ballotBox;
        party = citizen.party;
        voted = citizen.voted;
        this.isInParty = citizen.isInParty;
        this.isInBallotBox = citizen.isInBallotBox;
    }

    public Citizen(String name, String id, int birthYear, BallotBox ballotBox) {
        this (name, id, birthYear, ballotBox, null, false);
    }

    public Citizen(String name, String id, int birthYear) {
        this(name, id, birthYear, null, null, false);
    }

    /************ Get Functions ************/
    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public BallotBox getBallotBox() {
        return ballotBox;
    }

    public Party getParty() {
        return party;
    }

    public boolean getVoted() {
        return voted;
    }

    public boolean isInParty(){
        return isInParty;
    }

    /************ Set Functions ************/
    private boolean setName(String name){
        boolean done = false;
        String result = "";

        if (name != null) {
            String[] temp = name.split(" ");
            for (int i = 0; i < temp.length && !done; i++) {
                if (!temp[i].matches("^[a-zA-Z]*$")) {
                    done = true;
                } else {
                    result += temp[i] +" ";
                }
            }
        }

        if (done)
            this.name = "citizen";
        else
            this.name = result.substring(0, result.length() - 1);

        return done;
    }

    private boolean setId(String id) {
        if (id != null) {
            this.id = id;
            return true;
        }
        this.id = "-1";
        return false;
    }

    private boolean setBirthYear(int birthYear) {
        if (birthYear >= 0){
            this.birthYear = birthYear;
            return true;
        } else
            this.birthYear = 1990;

        return false;
    }

    private boolean setBallotBox(BallotBox ballotBox) {
        this.ballotBox = ballotBox;
        return true;
    }

    private boolean setInParty(Party party) {
        if (party != null) {
            this.party = party;
            isInParty = true;
            return true;
        } else
            this.party = null;
        return false;
    }

    private boolean setVoted(boolean voted) {
        this.voted = voted;
        return voted;
    }

    /************** Functions **************/
    public boolean canVote() {
        if (voted == false)
            return true;
        return false;
    }

    public void assignToParty(Party party) {
        if (!isInParty) {
            setInParty(party);
            isInParty = true;
        }
    }

    public void assignToBallotBox(BallotBox ballotBox) {
        if (!isInBallotBox) {
            setBallotBox(ballotBox);
            isInBallotBox = true;
        }
    }

    public void vote(){
        if(canVote())
            setVoted(true);
    }

    @Override
    public boolean equals(Object obj) {
        if(Citizen.class.equals(obj.getClass()) || Corona.class.equals(obj.getClass()) ||
            SoldierCorona.class.equals(obj.getClass()) || Soldier.class.equals(obj.getClass())) {
            return id.equals(Citizen.class.cast(obj).getID());
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("----------------\n");
        sb.append("Name : " + name + ", ID : " + id + ", Birth Year :" + birthYear + "\n");

        if (voted)
            sb.append("Voted in " + ballotBox.getAddress() + "\n");
        else
            sb.append("Can vote in " + ballotBox.getAddress() + "\n");

        if (party != null)
            sb.append("Party : " + party.getName() + "\n");
        return sb.toString();
    }
}