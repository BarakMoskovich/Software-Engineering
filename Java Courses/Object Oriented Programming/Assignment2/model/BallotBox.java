package model;

import model.citizens.Citizen;

import java.util.HashMap;

public class BallotBox<T extends Citizen> {
    /* Defaults:
       address: Afeka
       votePercentage: 0
       citizens.length: 0
       parties.length: 0
   */

    private static int numGen; // Auto generated
    private int id;
    private String address;
    private double votePercentage;
    private HashMap<Party, Integer> parties;
    protected Set<T> citizens;

    private final Class<T> type;

    /************* Constructor *************/
    public BallotBox(String address, Set<T> citizens, HashMap<Party, Integer> parties, Class<T> type){
        setAddress(address);
        setCitizens(citizens);
        setParties(parties);
        this.votePercentage = 0;
        this.id = numGen;
        numGen++;
        this.type = type;
    }

    public BallotBox(String address, Class<T> type) {
        this(address, null, null, type);
    }

    public BallotBox(BallotBox<T> ballotBox) {
        setAddress(ballotBox.address);
        setCitizens(ballotBox.citizens);
        setParties(ballotBox.parties);
        votePercentage = ballotBox.votePercentage;
        numGen = ballotBox.getNumGen();
        id = ballotBox.id;
        type = ballotBox.type;
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

    private boolean setCitizens(Set<T> citizens){
        if (citizens != null && !citizens.isEmpty()){
            this.citizens = citizens;
            return true;
        }
        this.citizens = new Set<T>();
        return false;
    }

    public boolean setParties(HashMap<Party, Integer> parties){
        if (parties != null && !parties.isEmpty()) {
            this.parties = parties;
            return true;
        }
        this.parties = new HashMap<Party, Integer>(0);
        return false;
    }

    public void clearVotes() {
        for(Party party: parties.keySet()) {
            parties.put(party, 0);
        }
    }

    /************ Get Functions ************/
    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public double getVotePercentage() {
        double newPercentage = calculateVotePercentage();
        if (votePercentage != newPercentage)
            votePercentage = newPercentage;
        return votePercentage;
    }

    public Set<T> getCitizens() {
        return citizens;
    }

    public HashMap<Party, Integer> getParties() {
        return parties;
    }

    public int getNumberOfCitizens() {
        return citizens.size();
    }

    public int getNumberOfParties() {
        return parties.size();
    }

    public Class<T> getClassType() {
        return type;
    }

    public String getType() {
        return type.getSimpleName();
    }

    private int getNumGen() {
        return numGen;
    }

    /************** Functions *************/
    public void vote(T citizen, Party party) {
        if (party != null && citizen != null && parties.containsKey(party) &&
                citizens.contains(citizen) && !citizen.getVoted()) {
            citizen.vote();
            parties.put(party, parties.get(party) + 1);
            System.out.println(citizen.getName() + " ID " + citizen.getID() + " voted for " + party.getName()+ "\n");
        } else {
            System.out.println("Vote failed\n");
        }
    }

    private double calculateVotePercentage(){
        int voteSum = 0;
        if (!parties.isEmpty()){
            for (int value: parties.values())
                voteSum += value;
            voteSum = (voteSum / parties.size()) * 100;
        }
        return voteSum;
    }

    public boolean addCitizen(T citizen) {
            if (citizens.add(citizen)) {
                citizen.assignToBallotBox(this);
                return true;
            }
        return false;
    }

    public void addParty(Party party) {
        if (party != null && !parties.containsKey(party))
            this.parties.put(party, 0);
    }

    private void makeStringWithUpperCase() {
        address = address.substring(0, 1).toUpperCase() + address.substring(1).toLowerCase();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof BallotBox)) return false;
        BallotBox<?> ballotBox = (BallotBox<?>) other;
        return id == ballotBox.id;
    }

/*    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        makeStringWithUpperCase();
        sb.append("BallotBox #" + id + "\n");
        sb.append("Address : " + address + "\n");
        sb.append("Type : " + getType() + "\n");
        return sb.toString();
    }*/

    @Override
    public String toString() {
        return address;
    }
}