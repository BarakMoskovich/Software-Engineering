package model;

public class Participant {
    private final String name;
    private int setWins;

    public Participant(String name) {
        this.name = name;
        setWins = 0;
    }

    public String getName() {
        return name;
    }

    public int getSetWins() {
        return setWins;
    }

    public void clearSetWins() {
        setWins = 0;
    }

    public void winSet() { // Add win
        setWins++;
    }

}