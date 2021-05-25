

import java.util.Arrays;

public class VoterRegister {
    /* Defaults:
       citizens.length: 0
       citizensCounter: 0
   */
    private Citizen[] citizens;
    private int citizensCounter;

    /************ Constructor ************/
    public VoterRegister(Citizen[] citizens) {
        setCitizens(citizens);
    }

    public VoterRegister() {
        this(new Citizen[0]);
    }

    /************ Set Functions ************/
    private boolean setCitizens(Citizen[] citizens){
        if(citizens != null && citizens.length != 0){
            for(int i = 0; i < citizens.length; i++){
                this.citizens[i] = new Citizen(citizens[i]);
            }
            citizensCounter = citizens.length;
            return true;
        }
        this.citizens = new Citizen[0];
        citizensCounter = 0;
        return false;
    }

    /************ Get Functions ************/
    public Citizen[] getCitizens() {
        return citizens;
    }

/*    public int getIndexByCitizen(Citizen citizen) {
        for (int i = 0; i < citizens.length; i++) {
            if(citizen != null) {
                if (citizens[i].equals(citizen)) {
                    return i;
                }
            }
        }
        return -1;
    }*/

    public Citizen getCitizenById(String id) {
        for (int i = 0; i < citizens.length; i++) {
            if (citizens[i] != null && id != null) {
                if (citizens[i].getID().equals(id))
                    return citizens[i];
            }
        }
        return null;
    }

    /************** Functions **************/
    private void expandCitizens() {
        Citizen[] temp = new Citizen[citizens.length * 2];
        for (int i = 0; i < citizens.length; i++) {
            temp[i] = citizens[i];
        }
        this.citizens = temp;
    }

    public boolean addCitizen(Citizen citizen) {
        if (citizens.length == 0) {
            this.citizens = new Citizen[1];
        }

        if (existCitizen(citizen)) {
            return false;
        } else if (citizensCounter >= citizens.length) {
            expandCitizens();
            addCitizen(citizen);
        } else {
            citizens[citizensCounter] = new Citizen(citizen);
            citizensCounter++;
            return true;
        }
        return false;
    }

    public void addCitizens(Citizen... newCitizens){
        int k = newCitizens.length + this.citizens.length;
        Citizen[] temp = new Citizen[k * 2];

        for(int i = 0; i < k; i++){
            if(this.citizens.length > i)
                temp[i] = this.citizens[i];
            else{
                if(newCitizens[i - this.citizens.length] != null && !existCitizen(newCitizens[i - this.citizens.length])){
                    temp[i] = new Citizen(newCitizens[i - this.citizens.length]);
                    citizensCounter++;
                }
            }
        }
        this.citizens = temp;
    }

    private boolean existCitizen(Citizen citizen) {
        for (int i = 0; i < citizens.length; i++) {
            if (citizens[i] != null && citizen != null) {
                if (citizens[i].equals(citizen)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean equals(VoterRegister voterRegister){
        if (voterRegister == null && this == null)
            return true;
        else if  (voterRegister == null || this == null)
            return false;
        return citizensCounter == voterRegister.citizensCounter &&
                Arrays.equals(citizens, voterRegister.citizens);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < citizens.length; i++)
            if (citizens[i] != null)
                sb.append(citizens[i] + "\n");
        return sb.toString();
    }
}