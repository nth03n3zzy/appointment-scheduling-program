package helper;

import models.LoginTracker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * purpose of this class is to provide methods to check if the username and password entered at login are correct.
 * @author Anthony Collins
 */
public abstract class LoginQuery {

    /**
     * boolean will be flipped to true if username and password entered are correct.
     */
    private static boolean accessGranted = true;


    /**
     * method to check if login is valid.
     * @param username username of the user attempting to login.
     * @param password password of the user attempting to login.
     * @return returns true or false depending if access is granted. if access is not granted then a alert is
     * displayed to the user notifying them of the invalid login. the login tracker also updates and enters an entry
     * in the login_activity.TXT file.
     */
    public static boolean checkLogin(String username, String password){
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LoginTracker.trackLoginAttempt(username, true);
               return accessGranted = true;
            } else {
                AlertHelper.callAlert("invalid login");
                LoginTracker.trackLoginAttempt(username, false);
                return accessGranted = false;
            }
        } catch (SQLException e) {
            System.out.println("SQL error occurred: " + e.getMessage());
        }
        return false;
    }


}
