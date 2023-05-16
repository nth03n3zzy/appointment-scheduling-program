package models;

/**
 * class for divison objects. not used in current version of program retained for future updates.
 * @author Anthony Collins
 */
public class Division {
    public int divisionID;
    public String name;
    public Countries country;

    public Division(int divisionID, String name, Countries country) {
        this.divisionID = divisionID;
        this.name = name;
        this.country = country;
    }

    public int getDivisionID() {
        return divisionID;
    }
}
