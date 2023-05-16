package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * interface for all query's to the database that involve the customers table.
 * @author Anthony Collins
 */
public interface CustomerQuery {

    /**
     * observable list for holding customer IDs for various functions throughout the program.
     */
     ObservableList<Integer> listOfCustomerID = FXCollections.observableArrayList();

    /**
     * method to delete customers from the database, all appointments associated with the customer are deleted first and
     * then the customer is deleted due to foreign key constraints.
     * @param customerID ID number of the customer to be deleted.
     * @throws SQLException
     */
     static void deleteCustomer(int customerID) throws SQLException {
         //deletes all appointments associated with the customer.
         String sql ="DELETE FROM appointments WHERE Customer_ID = ?";
         PreparedStatement psAppointments = JDBC.connection.prepareStatement(sql);
         psAppointments.setInt(1, customerID);
         psAppointments.execute();

         //the customer itself is then deleted.
         sql = "DELETE FROM customers WHERE Customer_ID = ?";
         PreparedStatement ps = JDBC.connection.prepareStatement(sql);
         ps.setInt(1, customerID);
         ps.execute();
     }

    /**
     * all customer IDs are selected and fill the customer ID list to be used throughout the peogram.
     * @throws SQLException
     */
    static void selectCustomerID() throws SQLException {
        String sql ="SELECT * FROM customers ";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerID = rs.getInt("Customer_ID");
            String contactName = rs.getString("Customer_Name");
            listOfCustomerID.add(customerID);
        }
    }

    /**
     * method to return a customer name when a customer ID is passed in.
     * @param customerID the ID number for the customer whos name we want returned.
     * @return customer name associated with the customer ID number.
     * @throws SQLException
     */
    static String selectCustomerName(int customerID) throws SQLException {
        String sql = "SELECT Customer_Name FROM customers WHERE Customer_ID = ? ";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,customerID);
        ResultSet rs = ps.executeQuery();
        String customerName = null;
        if (rs.next()) {
            customerName = rs.getString("Customer_Name");

        }
        return customerName;
    }

    /**
     * method for inserting a new customer into the database.
     * @param customerName name of the customer.
     * @param address address of the customer not including state/province/territory or country.
     * @param postalCode customers postal code.
     * @param phone customers phone number.
     * @param createDate the date the customer has been created.
     * @param createdBy who the customer has been created by.
     * @param lastUpdate when the customer was last updated.
     * @param lastUpdatedBy who the customer was last updated by.
     * @param divisionID division ID of the customer.
     * @throws SQLException
     */
    static void insert(String customerName, String address, String postalCode, String phone,
                              LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                              int divisionID) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2,address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        Timestamp createTimeStamp = Timestamp.valueOf(createDate);
        ps.setTimestamp(5,createTimeStamp);
        ps.setString(6,createdBy);
        Timestamp lastUpdateTimeStamp = Timestamp.valueOf(lastUpdate);
        ps.setTimestamp(7,lastUpdateTimeStamp);
        ps.setString(8,lastUpdatedBy);
        ps.setInt(9, divisionID);
        ps.executeUpdate();

    }

    /**
     * method for updating a existing customer within the database.
     * @param customerName name of the customer.
     * @param address address of the customer not including state/province/territory or country.
     * @param postalCode customers postal code.
     * @param phone customer phone number.
     * @param createDate date the customer was created.
     * @param createdBy who the customer was created by.
     * @param lastUpdate when the customer was last updated.
     * @param lastUpdatedBy who the customer was last updated by.
     * @param divisionID division ID of the customer.
     * @param customerID Customer ID of the customer non-editable parameter.
     * @throws SQLException
     */
     static void update(String customerName, String address, String postalCode, String phone,
                             LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                             int divisionID, int customerID) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?," +
                " Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?," +
                " Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2,address);
        ps.setString(3,postalCode);
        ps.setString(4,phone);
        Timestamp createTimeStamp = Timestamp.valueOf(createDate);
        ps.setTimestamp(5,createTimeStamp);
        ps.setString(6,createdBy);
        Timestamp lastUpdateTimeStamp = Timestamp.valueOf(lastUpdate);
        ps.setTimestamp(7,lastUpdateTimeStamp);
        ps.setString(8,lastUpdatedBy);
        ps.setInt(9, divisionID);
        ps.setInt(10, customerID);

        ps.executeUpdate();


    }
}
