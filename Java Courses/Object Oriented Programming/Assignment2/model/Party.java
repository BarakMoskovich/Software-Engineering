package model;

import model.citizens.*;

import java.util.*;

public class Party {
        /* Defaults:
        name: Default
        section: RIGHT_SECTION
        day: 1
        month: 1
        year: 2020
        candidates.length: 0
    */
    public static final String LEFT_SECTION = "left";
    public static final String RIGHT_SECTION = "right";
    public static final String CENTER_SECTION = "center";
    private String name;
    private String section;
    private int year;
    private int month;
    private int day;
    private Hashtable<Citizen, Integer> candidates;

    /************* Constructor *************/
    public Party(String name, String section, int year, int month, int day, Hashtable<Citizen, Integer> candidates) {
        setName(name);
        setSection(section);
        setCreationDate(year, month, day);
        setCandidates(candidates);
    }

    public Party(String name, String section, int year, int month, int day) {
        this(name, section, year, month, day, null);
    }

    public Party(Party party){
        this(party.name, party.section, party.year, party.month, party.day, party.candidates);
    }

    /************ Get Functions ************/
    public String getName() {
        return name;
    }

    public String getSection() {
        return section;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    /************ Set Functions ************/
    private boolean setCandidates(Hashtable<Citizen, Integer> candidates) {
        if (candidates != null && !candidates.isEmpty()){
            this.candidates = candidates;
            return true;
        }
        this.candidates = new Hashtable<Citizen, Integer>(0);
        return false;
    }

    public boolean setName(String name) {
        if (name != null) {
            this.name = name;
            return true;
        } else {
            this.name = "Default";
            return false;
        }
    }

    private boolean setSection(String section) {
        if (section.contains(RIGHT_SECTION) || section.contains(LEFT_SECTION) || section.contains(CENTER_SECTION)){
            this.section = section.toLowerCase();
            return true;
        } else {
            this.section = RIGHT_SECTION;
        }
        return false;
    }

    private boolean setCreationDate(int year, int month, int day) {
        if (month <= 12 && month >= 1 && year > 0) {
            Calendar cal = new GregorianCalendar(year, month, 1);
            int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            if (day <= daysInMonth) {
                this.day = day;
                this.month = month;
                this.year = year;
                return true;
            }
        } else {
            this.day = 1;
            this.month = 1;
            this.year = 2020;
            return false;
        }
        return false;
    }

    /************** Functions **************/
    public boolean addCandidate(Citizen candidate, int place) {
        if (candidate != null && !candidate.isInParty() && place > 0 && !candidates.containsValue(place)) {
            candidates.put(candidate, place);
            candidate.assignToParty(this);
            return true;
        }
        return false;
    }
/*
    @Override
    public boolean equals(Party party) {
        if (party == null || this == null)
            return false;
        return name.toUpperCase().equals(party.name.toUpperCase()); // enough to check only name
    }*/

    private void makeStringWithUpperCase() {
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        section = section.substring(0, 1).toUpperCase() + section.substring(1).toLowerCase();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Party)) return false;
        Party party = (Party) o;
        return name.equals(party.name);
    }

/*    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        makeStringWithUpperCase();
        sb.append("Party name : " + name + "\n");
        sb.append("Section : " + section + "\n");
        sb.append("Creation Date : " + day + "/" + month + "/" + year + "\n");
        sb.append("Candidates : " + candidates.size() + "\n");
        return sb.toString();
    }*/

    @Override
    public String toString() {
        return name;
    }
}