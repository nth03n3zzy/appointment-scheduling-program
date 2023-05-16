package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * class for performing searches and returns on the contacts table of the database.
 * @author Anthony Collins
 */
public interface ContactQuery {

    /**
     * observable list of a list of contact names to be used for various logic throughtout the application.
     */
     ObservableList<String> listOfContactsNames = FXCollections.observableArrayList();

    /**
     *when called on updates the list of all contact names with all contact names.
     */
     static void selectContactName() throws SQLException {
         listOfContactsNames.clear();
        String sql ="SELECT * FROM contacts ";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            listOfContactsNames.add(contactName);

        }
    }

    /**
     * method for finding the contact ID when the contact name is passed in.
     * @param name name of the contacts
     * @returns the contact ID.
     */
    static int selectContactID(String name) throws SQLException {
         int contactID = 0;
         String sql = "SELECT Contact_ID FROM Contacts WHERE Contact_Name = ?";
         PreparedStatement ps = JDBC.connection.prepareStatement(sql);
         ps.setString(1, name);
         ResultSet rs = ps.executeQuery();
         while(rs.next()){
             contactID = rs.getInt("Contact_ID");
         }
         return contactID;
    }

    /**
     * method for returning the contact name when a contact ID is passed in.
     * @param contactID ID of the contact being searched.
     * @returns the name of the contact whos ID was provided.
     * @throws SQLException
     */
    static String selectContactName(int contactID) throws SQLException{
         String contactName = "";
         String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";
         PreparedStatement ps = JDBC.connection.prepareStatement(sql);
         ps.setInt(1,contactID);
         ResultSet rs = ps.executeQuery();
         while(rs.next()){
             contactName = rs.getString("Contact_Name");
         }
         return contactName;
    }
}
