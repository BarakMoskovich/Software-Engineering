

public class Citizen {
    /* Defaults:
      name: citizen
      id: 123456789
      birthYear: 1990
      isolation: false
      ballotBox: null       maybe empty object of ballotBox?
      party: null           maybe empty object of party?
      voted: false
  */
    private String name;
    private String id;
    private int birthYear;
    private boolean isolation; // bidud
    private BallotBox ballotBox; // kalpi eleha hu meshuyah
    private Party party;
    private boolean voted;

    /************ Constructor ************/
    public Citizen(String name, String id, int birthYear, boolean isolation, BallotBox ballotBox, Party party, boolean voted) {
        setName(name);
        setId(id);
        setBirthYear(birthYear);
        setIsolation(isolation);
        setBallotBox(ballotBox);
        setInParty(party);
        setVoted(voted);
    }

    public Citizen(Citizen citizen) {
        this(citizen.name, citizen.id, citizen.birthYear, citizen.isolation, citizen.ballotBox, citizen.party, citizen.voted);
    }

    public Citizen(String name, String id, int birthYear, boolean isolation, BallotBox ballotBox) {
        this (name, id, birthYear, isolation, ballotBox, null, false);
    }

    public Citizen(String name, String id, int birthYear) {
        this(name, id, birthYear, false, null, null, false);
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

    public boolean isIsolation() {
        return isolation;
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

    /************ Set Functions ************/
    private boolean setName(String name){
        // checks if the citizen got multiple names
        // checks if there is a character that is not alphabetic in his name

        boolean done = false;
        String result = "";

        if(name != null){
            String[] temp = name.split(" ");
            for(int i = 0; i < temp.length && !done; i++){
                if(!temp[i].matches("^[a-zA-Z]*$")){
                    done = true;
                }else{
                    result += temp[i] +" ";
                }
            }
        }

        if(done)
            this.name = "citizen";
        else
            this.name = result.substring(0, result.length() - 1);

        return done;
    }

    private boolean setId(String id){
        boolean legalIdLength = id.length() >= 8 && id.length() <= 9;

        if (id != null && legalIdLength){
            this.id = id;
            return true;
        } else
            this.id = "-1";

        return false;
    }

    private boolean setBirthYear(int birthYear){
        if(birthYear >= 0){
            this.birthYear = birthYear;
            return true;
        }else
            this.birthYear = 1990;

        return false;
    }

    private boolean setIsolation(boolean isolation) {
        this.isolation = isolation;
        return isolation;
    }

    public boolean setBallotBox(BallotBox ballotBox) {
        if (ballotBox != null)
            this.ballotBox = ballotBox;
        else {
            this.ballotBox = null;
            return false;
        }
        return true;
    }

    public boolean setInParty(Party party) {
        if(party != null){
            this.party = party;
            return true;
        }else
            this.party = null;

        return false;
    }

    private boolean setVoted(boolean voted) {
        this.voted = voted;
        return voted;
    }

    /************** Functions **************/
    public void vote(){
        if(!voted)
            setVoted(true);
    }


    public boolean equals(Citizen citizen) {
        if(citizen == null && this == null)
            return true;
        else if (citizen == null || this == null)
            return false;
        return id.equals(citizen.id); // enough to check only id
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("----------------\n");
        sb.append("Name : " + name + ", ID : " + id + ", Birth Year :" + birthYear + "\n");
        if (isolation)
            sb.append("In isolation \n");
        else
            sb.append("Not in isolation\n");

        if (voted)
            sb.append("Voted in " + ballotBox + "\n");
        else
            sb.append("Can vote in " + ballotBox + "\n");

        if (party != null)
            sb.append("Party : " + party.getName() + "\n");

        sb.append("----------------\n");

        return sb.toString();
    }
}
