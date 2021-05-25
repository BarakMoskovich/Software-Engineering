

public class Party {
    /* Defaults:
        name: Default
        section: RIGHT_SECTION
        day: 1
        month: 1
        year: 2020
        candidates: new Citizen[0]
        candidatesPlaces.length: 0
        counterCandidates: 0
    */

    public static final String LEFT_SECTION = "left";
    public static final String RIGHT_SECTION = "right";
    public static final String CENTER_SECTION = "center";
    private String name;
    private String section;
    private int year;
    private int month;
    private int day;
    private Citizen[] candidates;
    private int[] candidatesPlaces;
    private int counterCandidates;

    public Party(String name, String section, int year, int month, int day, Citizen[] candidates, int[] candidatesPlaces) {
        setName(name);
        setSection(section);
        setCreationDate(year, month, day);
        setCandidates(candidates);
        setCandidatesPlaces(candidatesPlaces);
//        this.counterCandidates = counterCandidates;
    }

    public Party(String name, String section, int year, int month, int day) {
//        this(name, section, year, month, day, new Citizen[0], 0);
        this(name, section, year, month, day, new Citizen[0], new int[0]);
    }

    public Party(Party party){
//        this(party.name, party.section, party.year, party.month, party.day, party.candidates, 0);
        this(party.name, party.section, party.year, party.month, party.day, party.candidates, party.candidatesPlaces);
        this.counterCandidates = party.counterCandidates;
        this.candidatesPlaces = new int[0];
    }

    /************ Get Functions ************/
    public String getName() {
        return name;
    }

    public String getSection() {
        return section;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay(){
        return this.day;
    }

    public Citizen[] getCandidates() {
        return candidates;
    }

    public int getCandidatesCounter() {
        return candidates.length;
    }

    /************ Set Functions ************/
    private void setCandidatesPlaces(int[] candidatesPlaces) {
        if (candidates.length != candidatesPlaces.length) {
            int[] tempPlaces = candidatesPlaces;
            this.candidatesPlaces = new int[candidates.length];
            this.candidatesPlaces = tempPlaces;
        } else {
            this.candidatesPlaces = candidatesPlaces;
        }
    }

    private boolean setName(String name) {
        if(name != null) {
            this.name = name;
            return true;
        } else {
            this.name = "Default";
            return false;
        }
    }

    private boolean setSection(String section) {
        if(section.contains(RIGHT_SECTION) || section.contains(LEFT_SECTION) || section.contains(CENTER_SECTION)){
            this.section = section.toLowerCase();
            return true;
        } else {
            this.section = RIGHT_SECTION;
        }
        return false;
    }

    private boolean setCreationDate(int year, int month, int day) {
        boolean done = false;
        boolean legalYear = year >= 0;
        int resultDay;
        int resultMonth;
        int resultYear;

        if(legalYear && day >= 0) {
            resultYear = year;
            resultMonth = month;
            switch(resultMonth) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if(day <= 31){
                        resultDay = day;
                        done = true;
                    }else{
                        resultDay = 1;
                        done = false;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if(day <= 30){
                        resultDay = day;
                        done = true;
                    }else{
                        resultDay = 1;
                        done = false;
                    }
                    break;
                case 2:
                    if(day <= 28){
                        resultDay = day;
                        done = true;
                    }else{
                        resultDay = 1;
                        done = false;
                    }
                    break;
                default:
                    resultDay = 1;
                    resultMonth = 1;
                    break;
            }
        } else {
            resultDay = 1;
            resultMonth = 1;
            resultYear = 2020;
            done = false;
        }
        this.year = resultYear;
        this.month = resultMonth;
        this.day = resultDay;
        return done;
    }

    private boolean setCandidates(Citizen[] candidates) {
        if(candidates != null) {
            this.candidates = new Citizen[candidates.length];
            for(int i = 0; i < candidates.length; i++){
                this.candidates[i] = candidates[i];
            }
            counterCandidates = candidates.length;
            this.candidatesPlaces = new int[candidates.length];
            return true;
        }
        this.candidates = new Citizen[0];
        counterCandidates = 0;
        this.candidatesPlaces = new int[0];
        return false;
    }
/*
    private boolean setCandidatesPlaces() {
        this.candidatesPlaces = new int[candidates.length];
        return true;
    }
 */

    /************** Functions **************/
    public boolean addCandidate(Citizen candidate, int place) {
        if (candidates == null || candidates.length == 0) {
            this.candidates = new Citizen[1];
            this.candidatesPlaces = new int[1];
        }

        if (counterCandidates >= candidates.length) {
            if (place > (candidates.length * 2))
                expandCandidatesByPlace(place);
            else
                expandCandidates();

            addCandidate(candidate, place);
        } else {
            candidates[counterCandidates] = candidate;
            candidatesPlaces[counterCandidates] = place;
            counterCandidates++;
            candidate.setInParty(this);
            candidatesSort();
            return true;
        }
        return false;
    }

    private void candidatesSort() {
        for (int i = 0; i < candidatesPlaces.length; i++) {
            int min = candidatesPlaces[i];
            Citizen citizenMin = candidates[i];
            int minIndex = i;
            for (int j = i+1; j < candidatesPlaces.length; j++) {
                if (candidatesPlaces[j] < min && candidatesPlaces[j] > 0) {
                    min = candidatesPlaces[j];
                    citizenMin = candidates[j];
                    minIndex = j;
                }
            }

            int temp = candidatesPlaces[i];
            candidatesPlaces[i] = min;
            candidatesPlaces[minIndex] = temp;

            Citizen citizenTemp = candidates[i];
            candidates[i] = citizenMin;
            candidates[minIndex] = citizenTemp;
        }
    }

    private void expandCandidatesByPlace(int place) {
        Citizen[] tempCitizen = new Citizen[place + 1];
        int[] tempPlaces = new int[place + 1];
        for (int i = 0; i < candidates.length; i++) {
            tempCitizen[i] = candidates[i];
            tempPlaces[i] = candidatesPlaces[i];
        }
        this.candidates = tempCitizen;
        this.candidatesPlaces = tempPlaces;
    }

    private void expandCandidates() {
        Citizen[] tempCitizen = new Citizen[candidates.length * 2];
        int[] tempPlaces = new int[candidates.length * 2];
        for (int i = 0; i < candidates.length; i++) {
            tempCitizen[i] = candidates[i];
            tempPlaces[i] = candidatesPlaces[i];
        }
        this.candidates = tempCitizen;
        this.candidatesPlaces = tempPlaces;
    }


    public boolean equals(Party party) {
        if (this == null && party == null)
            return true;
        else if (party == null || this == null)
            return false;
        return name.toUpperCase().equals(party.name.toUpperCase()); // enough to check only name
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Party name : " + name + "\n");
        sb.append("Section : " + section + "\n");
        sb.append("Creation Date : " + day + "/" + month + "/" + year + "\n");
        sb.append("Candidates : " + candidates.length + "\n");
        return sb.toString();
    }
}