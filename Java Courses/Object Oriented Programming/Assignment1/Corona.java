

public class Corona extends BallotBox {
    /* Defaults:
       extends from BallotBox
     */

    public Corona(String address, int legalCitizens, Citizen[] citizens, Party[] parties, int[] votesForParty) {
        super(address, legalCitizens, citizens, parties, votesForParty);
    }

    public Corona(String address) {
        this(address, 0, new Citizen[0], new Party[0],new int[0]);
    }

    public Corona(Corona corona){
        super(corona);
    }

    @Override
    public boolean canVote(Citizen citizen) {
        boolean canVote = false;
        boolean isCitizenExists = isCitizenExists(citizen);
        if(citizen.isIsolation() && !citizen.getVoted() && isCitizenExists)
            canVote = true;

        return canVote;
    }

    public boolean equals(Corona corona){
        return super.equals(corona);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append("Type : Corona\n");
        return sb.toString();
    }
}