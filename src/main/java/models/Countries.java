package models;

/**
 * class for country objects.
 * @author ANthony Collins
 */
public class Countries {
    /**
     * int used as the country ID
     */
    public int countryId;
    /**
     * string for the countries name.
     */
    public String countryName;

    /**
     * constructor for a country
     * @param countryId ID of the country.
     * @param countryName name of the country.
     */
    public Countries(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }
}
