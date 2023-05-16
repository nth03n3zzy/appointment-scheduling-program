package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * interface for searching the country and division tables of the database.
 * @author Anthony Collins
 */
public interface CountryAndDivisionQuery {

    /**
     * map made for the use of finding specific first level divisions full names based off the abbreviation that
     * has been selected by the user.
     */
    Map<String, String> divisionPairsAbbKeys = new HashMap<>();


    /**
     * This method takes a divisions full name and returns its abbreviation.
     * @param divisionFullName is the full name of a state/ province/ or territory.
     * @return the abbreviation for the inputted division.
     */
    static String convertToAbbreviation(String divisionFullName){

        Map<String, String> divisionPairsAbbValues = new HashMap<>();
        // states of the US
        divisionPairsAbbValues.put("Alabama", "AL");
        divisionPairsAbbValues.put("Alaska", "AK");
        divisionPairsAbbValues.put("Arizona", "AZ");
        divisionPairsAbbValues.put("Arkansas", "AR");
        divisionPairsAbbValues.put("California", "CA");
        divisionPairsAbbValues.put("Colorado", "CO");
        divisionPairsAbbValues.put("Connecticut", "CT");
        divisionPairsAbbValues.put("Delaware", "DE");
        divisionPairsAbbValues.put("Florida", "FL");
        divisionPairsAbbValues.put("Georgia", "GA");
        divisionPairsAbbValues.put("Hawaii", "HI");
        divisionPairsAbbValues.put("Idaho", "ID");
        divisionPairsAbbValues.put("Illinois", "IL");
        divisionPairsAbbValues.put("Indiana", "IN");
        divisionPairsAbbValues.put("Iowa", "IA");
        divisionPairsAbbValues.put("Kansas", "KS");
        divisionPairsAbbValues.put("Kentucky", "KY");
        divisionPairsAbbValues.put("Louisiana", "LA");
        divisionPairsAbbValues.put("Maine", "ME");
        divisionPairsAbbValues.put("Maryland", "MD");
        divisionPairsAbbValues.put("Massachusetts", "MA");
        divisionPairsAbbValues.put("Michigan", "MI");
        divisionPairsAbbValues.put("Minnesota", "MN");
        divisionPairsAbbValues.put("Mississippi", "MS");
        divisionPairsAbbValues.put("Missouri", "MO");
        divisionPairsAbbValues.put("Montana", "MT");
        divisionPairsAbbValues.put("Nebraska", "NE");
        divisionPairsAbbValues.put("Nevada", "NV");
        divisionPairsAbbValues.put("New Hampshire", "NH");
        divisionPairsAbbValues.put("New Jersey", "NJ");
        divisionPairsAbbValues.put("New Mexico", "NM");
        divisionPairsAbbValues.put("New York", "NY");
        divisionPairsAbbValues.put("North Carolina", "NC");
        divisionPairsAbbValues.put("North Dakota", "ND");
        divisionPairsAbbValues.put("Ohio", "OH");
        divisionPairsAbbValues.put("Oklahoma", "OK");
        divisionPairsAbbValues.put("Oregon", "OR");
        divisionPairsAbbValues.put("Pennsylvania", "PA");
        divisionPairsAbbValues.put("Rhode Island", "RI");
        divisionPairsAbbValues.put("South Carolina", "SC");
        divisionPairsAbbValues.put("South Dakota", "SD");
        divisionPairsAbbValues.put("Tennessee", "TN");
        divisionPairsAbbValues.put("Texas", "TX");
        divisionPairsAbbValues.put("Utah", "UT");
        divisionPairsAbbValues.put("Vermont", "VT");
        divisionPairsAbbValues.put("Virginia", "VA");
        divisionPairsAbbValues.put("Washington", "WA");
        divisionPairsAbbValues.put("West Virginia", "WV");
        divisionPairsAbbValues.put("Wisconsin", "WI");
        divisionPairsAbbValues.put("Wyoming", "WY");
        divisionPairsAbbValues.put("District of Columbia", "DC");


        // Provinces and Territories of Canada
        divisionPairsAbbValues.put("Alberta", "AB");
        divisionPairsAbbValues.put("British Columbia", "BC");
        divisionPairsAbbValues.put("Manitoba", "MB");
        divisionPairsAbbValues.put("New Brunswick", "NB");
        divisionPairsAbbValues.put("Nova Scotia", "NS");
        divisionPairsAbbValues.put("Prince Edward Island", "PE");
        divisionPairsAbbValues.put("Ontario", "ON");
        divisionPairsAbbValues.put("Quebec", "QC");
        divisionPairsAbbValues.put("Saskatchewan", "SK");
        divisionPairsAbbValues.put("Yukon", "YT");
        divisionPairsAbbValues.put("Nunavut", "NU");
        divisionPairsAbbValues.put("Newfoundland and Labrador", "NL");
        divisionPairsAbbValues.put("Northwest Territories", "NT");

        // Countries of the United Kingdom
        divisionPairsAbbValues.put("England", "ENG"); // Country of the United Kingdom
        divisionPairsAbbValues.put("Scotland", "SCT"); // Country of the United Kingdom
        divisionPairsAbbValues.put("Northern Ireland", "NIR"); // Country of the United Kingdom
        divisionPairsAbbValues.put("Wales", "WLS"); // Country of the United Kingdom
        return divisionPairsAbbValues.get(divisionFullName);


    }

    /**
     * this method returns a divisions full name based off an abbreviation that is passed in.
     * @param division the abbreviation for a state/ territory/ or province.
     * @return full name for the passed in abbreviation.
     */
    static String getDivisionName(String division) {

//       US States

        divisionPairsAbbKeys.put("AL", "Alabama");
        divisionPairsAbbKeys.put("AK", "Alaska");
        divisionPairsAbbKeys.put("AZ", "Arizona");
        divisionPairsAbbKeys.put("AR", "Arkansas");
        divisionPairsAbbKeys.put("CA", "California");
        divisionPairsAbbKeys.put("CO", "Colorado");
        divisionPairsAbbKeys.put("CT", "Connecticut");
        divisionPairsAbbKeys.put("DE", "Delaware");
        divisionPairsAbbKeys.put("FL", "Florida");
        divisionPairsAbbKeys.put("GA", "Georgia");
        divisionPairsAbbKeys.put("HI", "Hawaii");
        divisionPairsAbbKeys.put("ID", "Idaho");
        divisionPairsAbbKeys.put("IL", "Illinois");
        divisionPairsAbbKeys.put("IN", "Indiana");
        divisionPairsAbbKeys.put("IA", "Iowa");
        divisionPairsAbbKeys.put("KS", "Kansas");
        divisionPairsAbbKeys.put("KY", "Kentucky");
        divisionPairsAbbKeys.put("LA", "Louisiana");
        divisionPairsAbbKeys.put("ME", "Maine");
        divisionPairsAbbKeys.put("MD", "Maryland");
        divisionPairsAbbKeys.put("MA", "Massachusetts");
        divisionPairsAbbKeys.put("MI", "Michigan");
        divisionPairsAbbKeys.put("MN", "Minnesota");
        divisionPairsAbbKeys.put("MS", "Mississippi");
        divisionPairsAbbKeys.put("MO", "Missouri");
        divisionPairsAbbKeys.put("MT", "Montana");
        divisionPairsAbbKeys.put("NE", "Nebraska");
        divisionPairsAbbKeys.put("NV", "Nevada");
        divisionPairsAbbKeys.put("NH", "New Hampshire");
        divisionPairsAbbKeys.put("NJ", "New Jersey");
        divisionPairsAbbKeys.put("NM", "New Mexico");
        divisionPairsAbbKeys.put("NY", "New York");
        divisionPairsAbbKeys.put("NC", "North Carolina");
        divisionPairsAbbKeys.put("ND", "North Dakota");
        divisionPairsAbbKeys.put("OH", "Ohio");
        divisionPairsAbbKeys.put("OK", "Oklahoma");
        divisionPairsAbbKeys.put("OR", "Oregon");
        divisionPairsAbbKeys.put("PA", "Pennsylvania");
        divisionPairsAbbKeys.put("RI", "Rhode Island");
        divisionPairsAbbKeys.put("SC", "South Carolina");
        divisionPairsAbbKeys.put("SD", "South Dakota");
        divisionPairsAbbKeys.put("TN", "Tennessee");
        divisionPairsAbbKeys.put("TX", "Texas");
        divisionPairsAbbKeys.put("UT", "Utah");
        divisionPairsAbbKeys.put("VT", "Vermont");
        divisionPairsAbbKeys.put("VA", "Virginia");
        divisionPairsAbbKeys.put("WA", "Washington");
        divisionPairsAbbKeys.put("WV", "West Virginia");
        divisionPairsAbbKeys.put("WI", "Wisconsin");
        divisionPairsAbbKeys.put("WY", "Wyoming");
        divisionPairsAbbKeys.put("DC", "District of Columbia"); // Federal District of the US

//       provinces and territories of can

        divisionPairsAbbKeys.put("AB", "Alberta");
        divisionPairsAbbKeys.put("BC", "British Columbia");
        divisionPairsAbbKeys.put("MB", "Manitoba");
        divisionPairsAbbKeys.put("NB", "New Brunswick");
        divisionPairsAbbKeys.put("NS", "Nova Scotia");
        divisionPairsAbbKeys.put("PE", "Prince Edward Island");
        divisionPairsAbbKeys.put("ON", "Ontario");
        divisionPairsAbbKeys.put("QC", "Quebec");
        divisionPairsAbbKeys.put("SK", "Saskatchewan");
        divisionPairsAbbKeys.put("YT", "Yukon");
        divisionPairsAbbKeys.put("NU", "Nunavut");
        divisionPairsAbbKeys.put("NL", "Newfoundland and Labrador");
        divisionPairsAbbKeys.put("NT", "Northwest Territories");

        // Countries of the United Kingdom

        divisionPairsAbbKeys.put("ENG", "England"); // Country of the United Kingdom
        divisionPairsAbbKeys.put("SCT", "Scotland"); // Country of the United Kingdom
        divisionPairsAbbKeys.put("NIR", "Northern Ireland"); // Country of the United Kingdom
        divisionPairsAbbKeys.put("WLS", "Wales"); // Country of the United Kingdom

        return divisionPairsAbbKeys.get(division);


    }

    /**
     * method searched for a first level divisions ID based on its name.
     * @param divisionIDSearch the name of a first level division.
     * @return the ID number for that first level division, if no division ID is found -1 is returned.
     * @throws SQLException
     */
    static int pullDivisonID(String divisionIDSearch) throws SQLException {

        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, divisionIDSearch);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("Division_ID");
        } else
            return -1;

    }

    /**
     * method returns full name for a division when a division ID is passed in.
     * @param divisionID the ID number for a division.
     * @return the name of the division whose ID was entered.
     * @throws SQLException
     */
    static String pullDivision(int divisionID) throws SQLException {
        String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println(rs.getString("Division"));
            return rs.getString("Division");

        } else
            return sql;
    }

    /**
     * method returns a countrys name when a division ID is entered.
     * @param divisionID the Id number for a division.
     * @return the name of the country to which the division belongs.
     * @throws SQLException
     */
    static String pullCountry(int divisionID) throws SQLException {
        String countryName = "";
        String sql = "SELECT Country_ID FROM first_Level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            System.out.println("country ID = " + countryID);


            String selectCountrySql = "SELECT Country FROM countries WHERE Country_ID = ?";
            PreparedStatement psCountry = JDBC.connection.prepareStatement(selectCountrySql);
            psCountry.setInt(1, countryID);
            ResultSet rsCountry = psCountry.executeQuery();

            if (rsCountry.next()){
                countryName = rsCountry.getString("Country");
                System.out.println("country name = " + countryName);


                //logic changing the abbreviations selectable in the program to the names of the countries that
                //are stored in the database.
            if (Objects.equals(countryName, "U.S")) {
                countryName = "US";
                return countryName;
            } else if (Objects.equals(countryName, "Canada")) {
                countryName = "CAN";
                return countryName;
            } else if (Objects.equals(countryName, "UK")) {
                countryName = "UK";
                return countryName;

            }
            }
         }
        System.out.println(countryName);
         return countryName;
    }
}


