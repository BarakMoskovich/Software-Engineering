

public abstract class BallotBox {
    /* Defaults:
       address: Afeka
       votePercentage: 0
       citizens.length: 0
       parties.length: 0
       votesForParty.length: 0
   */
    private static int numGen; // auto generated
    private int id;
    private String address;
    private int votePercentage; // total of legal citizens that can vote to a specific ballot box
    private Citizen[] citizens; // list of all citizens that can vote to this specific ballotBox
    private Party[] parties;
    private int[] votesForParty;
    protected int citizenCounter;
    protected int partiesCounter;

    /************ Constructor ************/
    public BallotBox(String address, int votePercentage, Citizen[] citizens, Party[] parties, int[] votesForParty) {
        setAddress(address);
        setVotePercentage(votePercentage);
        setCitizens(citizens);
        setParties(parties);
        setVotesForParty(votesForParty);
        this.id = numGen;
        numGen++;
    }

    public BallotBox(String address) {
        this(address, 0, new Citizen[0], new Party[0],new int[0]);
    }

    public BallotBox(BallotBox ballotBox){
        setAddress(ballotBox.address);
        setVotePercentage(ballotBox.votePercentage);
        setCitizens(ballotBox.citizens);
        setParties(ballotBox.parties);
        setVotesForParty(ballotBox.votesForParty);
        this.id = ballotBox.id;
    }

    /************ Set Functions ************/
    private boolean setAddress(String address) {
        boolean isSet = false;
        if(address != null){
            this.address = address;
            isSet = true;
        }
        else
            this.address = "Afeka";
        return isSet;
    }

    private boolean setVotePercentage(int votePercentage) {
        boolean isSet = false;
        if(votePercentage >= 0) {
            this.votePercentage = votePercentage;
            isSet = true;
        }else
            this.votePercentage = -1;
        return isSet;
    }

    private boolean setCitizens(Citizen[] citizens) {
        if (citizens != null) {
            this.citizens = citizens;
            citizenCounter = this.citizens.length;
            return true;
        }
        this.citizens = new Citizen[0];
        return false;
    }

    private boolean setParties(Party[] parties) {
        if (parties != null) {
            this.parties = parties;
            partiesCounter = this.parties.length;
            return true;
        }
        this.parties = new Party[0];
        return false;
    }

    private boolean setVotesForParty(int[] votesForParty) {
        boolean isSet = true;
        if(votesForParty == null || parties.length == 0 || votesForParty.length == 0) {
            this.votesForParty = new int[0];
            isSet = false;
        }else{
            this.votesForParty = new int[parties.length];
            for (int i = 0; i < parties.length; i++) {
                this.votesForParty[i] = votesForParty[i];
            }
        }
        return isSet;
    }

    /************ Get Functions ************/
    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public int getVotePercentage() {
        calculateVotePercentage();
        return votePercentage;
    }

    public Citizen[] getCitizens() {
        return citizens;
    }

    public Party[] getParties() {
        return parties;
    }

    public int[] getVotesForParty() {
        return votesForParty;
    }

    public int getNumberOfCitizens() {
        return citizenCounter;
    }

    public int getNumberOfParties() {
        return partiesCounter;
    }

    /************** Functions **************/
    public void vote(Citizen citizen, Party party) {
        if(party != null && isPartyExists(party) && canVote(citizen)) {
            citizen.vote();
            addVote(party);
        }
    }

    private void calculateVotePercentage() {
        int votesSum = 0;
        for(int i = 0; i < votesForParty.length; i++) {
            votesSum += votesForParty[i];
        }
        if (citizens.length > 0)
            votePercentage = (votesSum / citizens.length) * 100;
    }

    public void addCitizens(Citizen... newCitizens) {
        int k = newCitizens.length + this.citizens.length;
        Citizen[] tempBallotBoxes = new Citizen[k * 2];

        int newCitizensCounter = 0;
        for (int i = 0; i < k; i++) {
            if (this.citizens.length > i) {
                if (this.citizens[i] != null) {
                    tempBallotBoxes[i] = this.citizens[i];
                } else {
                    if (newCitizensCounter < newCitizens.length) {
                        tempBallotBoxes[i] = newCitizens[newCitizensCounter];
                        citizenCounter++;
                        newCitizensCounter++;
                    }
                }
            } else {
                if (newCitizensCounter < newCitizens.length) {
                    tempBallotBoxes[i] = newCitizens[newCitizensCounter];
                    citizenCounter++;
                    newCitizensCounter++;
                }
            }
        }

        this.citizens = tempBallotBoxes;
    }

    public void addParties(Party... newParties) {
        int k = newParties.length + this.parties.length;
        Party[] tempParty = new Party[k * 2];
        int[] tempVoteForParty = new int[k * 2];

        int newPartiesCounter = 0;
        for (int i = 0; i < k; i++) {
            if (this.parties.length > i) {
                if (this.parties[i] != null) {
                    tempParty[i] = this.parties[i];
                    tempVoteForParty[i] = this.votesForParty[i];
                } else {
                    if (newPartiesCounter < newParties.length) {
                        tempParty[i] = newParties[newPartiesCounter];
                        partiesCounter++;
                        newPartiesCounter++;
                    }
                }
            } else {
                if (newPartiesCounter < newParties.length) {
                    tempParty[i] = newParties[newPartiesCounter];
                    partiesCounter++;
                    newPartiesCounter++;
                }
            }
        }

        this.parties = tempParty;
        this.votesForParty = tempVoteForParty;
    }

    protected boolean isCitizenExists(Citizen newCitizen) {
        boolean exists = false;
        for (Citizen citizen: citizens) {
            if (citizen != null && newCitizen != null)
                if (citizen.equals(newCitizen))
                    exists = true;
        }
        return exists;
    }

    private boolean isPartyExists(Party newParty) {
        for (Party party: parties) {
            if (party != null && newParty != null)
                if (party.equals(newParty))
                    return true;
        }
        return false;
    }

    protected void addVote(Party party) {
        for (int i = 0; i < parties.length; i++) {
            if (party.equals(parties[i])) {
                votesForParty[i]++;
                System.out.println("Thanks you for voted!\n");
//                System.out.println("Vote has been added!\n#added to party number:" + i + "\n#added to votesForParty number " + i ); // To test
            }
        }
    }

    abstract public boolean canVote(Citizen citizen);

    public boolean equals(BallotBox ballotBox) {
        if (ballotBox == null && this == null)
            return true;
        else if (ballotBox == null || this == null)
            return false;
        return address.equals(ballotBox.address); // enough to check only address
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("BallotBox #" + id + "\n");
        sb.append("Address : " + address + "\n");
        return sb.toString();
    }

}