package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * class used for query's of the user table in the database.
 * @author Anthony Collins.
 */
public interface UserQuery {

    /**
     * observable list of userIDs used throughout the program.
     */
    ObservableList<Integer> listOfUserId = FXCollections.observableArrayList();

    /**
     * method that adds all the user IDs in the database to the list of user IDs when called on.
     * @throws SQLException
     */
    static void selectUserID() throws SQLException {
        String sql ="SELECT * FROM users ";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int userID = rs.getInt("User_ID");
            listOfUserId.add(userID);
        }
    }
}
