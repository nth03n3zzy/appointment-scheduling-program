package helper;

import controllers.CalenderController;
import controllers.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

/**
 * method used for query's to the database that have to do with the appointments table.
 * @author Anthony Collins
 */
public abstract class AppointmentsQuery {
    /**
     * LocalDateTime used as the start time for appointments
     */
    public static LocalDateTime start;
    /**
     * localDateTime used as the end time for appointments
     */
    public static LocalDateTime end;
    /**
     * int used to  hold the customerID for appointment query's.
     */
    public static int CustomerID;
    /**
     * localDateTime used in the logic of checking for overlapping appointments as the start time.
     */
    public static LocalDateTime overlapStart;
    /**
     * localDateTime used in the logic of checking for overlapping appointments as the end time.
     */
    public static LocalDateTime overlapEnd;
    /**
     * int used as a holder for appointment ID in appointment query's.
     */
    public static int appointmentID;

    /**
     * method used to insert appointments into the appointments table.
     * @param title title of the appointment.
     * @param description description of the appointment.
     * @param location location of the appointment.
     * @param start start time of the appointment.
     * @param end end time of tha appointment.
     * @param createDate time and date of the appointment's creation.
     * @param createdBy who created teh appointment.
     * @param lastUpdate time and date the appointment was last updated.
     * @param lastUpdatedBy who the appointment was last updated by.
     * @param customerID customer ID of whom the appointment is scheduled for.
     * @param userID user ID of who is scheduling the appointment.
     * @param contactID contactID of the contact for the appointment.
     * @param type the type of appointment.
     */
    public static void insert(String title,String description, String location, LocalDateTime start, LocalDateTime end,
                             LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                             int customerID, int userID, int contactID, String type) throws SQLException {


        //sql Statement for insertion of appointment
            String sql = "INSERT INTO appointments (Title, Description, Location, Start, End, Create_Date, Created_By, " +
                    "Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID, Type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            //connection established to the database.
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            //prepares statement values are passed in from parameters entered in the method
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            Timestamp startTimeStamp = Timestamp.valueOf(start);
            ps.setTimestamp(4, startTimeStamp);
            Timestamp endTimeStamp = Timestamp.valueOf(end);
            ps.setTimestamp(5, endTimeStamp);
            Timestamp createTimeStamp = Timestamp.valueOf(createDate);
            ps.setTimestamp(6, createTimeStamp);
            ps.setString(7, createdBy);
            Timestamp lastUpdateTimeStamp = Timestamp.valueOf(lastUpdate);
            ps.setTimestamp(8, lastUpdateTimeStamp);
            ps.setString(9, lastUpdatedBy);
            ps.setInt(10, customerID);
            ps.setInt(11, userID);
            ps.setInt(12, contactID);
            ps.setString(13, type);
            //prepared statement is executed.
            ps.executeUpdate();
    }

    /**
     * method used to insert appointments into the appointments table.
     * @param appointmentID appointment ID for the appointment being edited. the appointment ID is not editable.
     * @param title title of the appointment.
     * @param description description of the appointment.
     * @param location location of the appointment.
     * @param start start time of the appointment.
     * @param end end time of tha appointment.
     * @param lastUpdatedBy who the appointment was last updated by.
     * @param customerID customer ID of whom the appointment is scheduled for.
     * @param userID user ID of who is scheduling the appointment.
     * @param contactID contactID of the contact for the appointment.
     * @param type the type of appointment.
     */
    public static void update(int appointmentID, String title, String description, String location, LocalDateTime start,
                LocalDateTime end, String lastUpdatedBy, int customerID,
        int userID, int contactID, String type) throws SQLException {
        //prepared statement sent to the dataBase
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Start = ?, End = ?, " +
                    "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?, Type = ? " +
                    "WHERE Appointment_ID = ?";
            //connection established with the database
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            //parameters entered in the method are passed to the prepared statement.
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3,location);
            start = TimeHelper.convertFromSystemDefaultToUTC(start);
            Timestamp startTimeStamp = Timestamp.valueOf(start);
            ps.setTimestamp(4, startTimeStamp);
            end = TimeHelper.convertFromSystemDefaultToUTC(end);
            Timestamp endTimeStamp = Timestamp.valueOf(end);
            ps.setTimestamp(5, endTimeStamp);
            LocalDateTime lastUpdate = LocalDateTime.now();
            lastUpdate = TimeHelper.convertFromSystemDefaultToUTC(lastUpdate);
            Timestamp lastUpdateTimeStamp = Timestamp.valueOf(lastUpdate);
            ps.setTimestamp(6,lastUpdateTimeStamp);
            ps.setString(7,lastUpdatedBy);
            ps.setInt(8,customerID);
            ps.setInt(9,userID);
            ps.setInt(10,contactID);
            ps.setString(11,type);
            ps.setInt(12, appointmentID);
            //statement is executed.
            ps.executeUpdate();
        }

    /**
     * method to return an appointment ID.
     * @param start start date and time of appointment.
     * @param customerID customer ID of the customer the appointment is scheduled for.
     * @return returns an int of the appointment ID if found. If no appointment is found "-1" is returned.
     */
        public static int returnAppointmentID(LocalDateTime start, int customerID) throws SQLException {
        String sql = "SELECT Appointment_ID FROM appointments WHERE Start = ? AND Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(start));
        ps.setInt(2, customerID);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            return rs.getInt("Appointment_ID");
        }

        return -1;

        }

    /**
     * method checks for overlapping appointments for a given customer when appointment is scheduled.
     * @param customerID ID of customer appointment is being scheduled for
     * @param startScheduled scheduled start time of the appointment attempting to be scheduled.
     * @param endScheduled scheduled end time of the appointment attempting to be scheduled.
     * @return returns a boolean if true. if no overlapping appointments and false if one is found.
     */
    public static boolean checkForOverlappingAddAppointments(int customerID, LocalDateTime startScheduled, LocalDateTime endScheduled) throws SQLException {
        // resets variables that will be used for the search.
        overlapEnd = null;
        overlapStart = null;
        start = null;
        end = null;
        appointmentID = 0;
        CustomerID = customerID;
        //SQL statement for database search.
        String sql = "SELECT Start, End FROM appointments WHERE Customer_ID = ? AND Start <= ? AND End >= ?";
        //Prepared statement passed into the database.
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //parameters for the prepared statement.
        ps.setInt(1, customerID);
        ps.setTimestamp(2, Timestamp.valueOf(endScheduled));
        ps.setTimestamp(3, Timestamp.valueOf(startScheduled));
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            //if an overlap is found start is set to the scheduled start time
            start = startScheduled;
            //end is set to scheduled end time
            end = endScheduled;
            //overlap end is set to the end time of the overlapping appointment
            overlapEnd = rs.getTimestamp("End").toLocalDateTime();

            //overlap start is set to the start time of the overlapping appointment
            overlapStart = rs.getTimestamp("Start").toLocalDateTime();

            //appointment ID for the overlapping appointment is grabbed
            appointmentID = returnAppointmentID(overlapStart, customerID);

            //parameters are passed into the overlapping appointments alert to create a custom alert with the
            //attempted start and end times and the overlapping start and end times all displayed in system default
            //time making it simple for the user to schedule the appointment to another time that will not overlap
            //with any appointments scheduled for the user.
            AlertHelper.callAlert("overlapping appointments");
            //false is returned to stop the attempted appointment save in the add appointment controller.
            return false; // overlapping appointments found
        }
        // No overlapping appointments found
        return true;
    }

    /**
     * method checks for overlapping appointments for a given customer when appointment is updated.
     * @param customerID ID of customer appointment is being scheduled for
     * @param startScheduled scheduled start time of the appointment attempting to be scheduled.
     * @param endScheduled scheduled end time of the appointment attempting to be scheduled.
     * @return returns a boolean of value true if no overlapping appointments and false if one is found.
     */
    public static boolean checkForOverlappingEditAppointments(int customerID, LocalDateTime startScheduled, LocalDateTime endScheduled) throws SQLException {
        // resets variables that will be used for the search.
        overlapEnd = null;
        overlapStart = null;
        start = null;
        end = null;
        appointmentID = 0;
        CustomerID = customerID;
        //SQL statement for database search.
        String sql = "SELECT Start, End FROM appointments WHERE Customer_ID = ? AND Start <= ? AND End >= ? AND Appointment_ID <> ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //Prepared statement passed into the database.
        ps.setInt(1, customerID);
        ps.setTimestamp(2, Timestamp.valueOf(endScheduled));
        ps.setTimestamp(3, Timestamp.valueOf(startScheduled));
        ps.setInt(4,returnAppointmentID(startScheduled, customerID));
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            //if an overlap is found start is set to the scheduled start time
            start = startScheduled;
            //end is set to scheduled end time
            end = endScheduled;
            //overlap end is set to the end time of the overlapping appointment
            overlapEnd = rs.getTimestamp("End").toLocalDateTime();
            //overlap start is set to the start time of the overlapping appointment
            overlapStart = rs.getTimestamp("Start").toLocalDateTime();
            //appointment ID for the overlapping appointment is grabbed
            appointmentID = returnAppointmentID(overlapStart, customerID);

            //logic checking to ensure that the overlapping appointment is not the appointment we are currently editing.
            if (appointmentID != CalenderController.modifyAppointment.getAppointment_ID()) {

                //parameters are passed into the overlapping appointments alert to create a custom alert with the
                //attempted start and end times and the overlapping start and end times all displayed in system default
                //time making it simple for the user to schedule the appointment to another time that will not overlap
                //with any appointments scheduled for the user.
                AlertHelper.callAlert("overlapping appointments");
                //false is returned to stop the attempted appointment save in the edit appointment controller.
                return false; // overlapping appointments found
            }
        }
        // No overlapping appointments found
        return true;
    }


    /**
     * deleted appointment based off passed in appointment ID.
     * @param appointmentID ID of appointment that was selected for being deleted.
     */
    public static void delete(int appointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,appointmentID);
        ps.execute();
    }

    /**
     * method takes in a passed in observable list and adds appointments for the current week to it.
     * @param appointmentsList list passed into method to have appointments from the current week added to it.
     */
    public static void selectWeek(ObservableList<Appointment> appointmentsList) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE YEARWEEK(Start, 1) = YEARWEEK(CURDATE(), 1)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int Appointment_ID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            int contactId = rs.getInt("Contact_ID");
            Timestamp timestampStart = rs.getTimestamp("Start");
            LocalDateTime start = timestampStart.toLocalDateTime();
            Timestamp timestampEnd = rs.getTimestamp("End");
            LocalDateTime end = timestampEnd.toLocalDateTime();
            Timestamp timestampCreateDate = rs.getTimestamp("Create_Date");
            LocalDateTime createDate = timestampCreateDate.toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            Timestamp timestampLastUpdate = rs.getTimestamp("Last_Update");
            LocalDateTime lastUpdate = timestampLastUpdate.toLocalDateTime();
            String lastUpdatedBy = LoginController.user;
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");

            Appointment appointment = new Appointment(Appointment_ID, title, description, location,
                    type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

            appointmentsList.add(appointment);
        }
    }

    /**
     * method takes in a passed in observable list and populates it with all appointments in the current month.
     * @param monthsList list of appointments for the current month.
     */
    public static void selectMonth(ObservableList<Appointment> monthsList) throws SQLException {
            String sql = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(CURDATE())";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                int contactId = rs.getInt("Contact_ID");
                Timestamp timestampStart = rs.getTimestamp("Start");
                LocalDateTime start =  timestampStart.toLocalDateTime();
                Timestamp timestampEnd = rs.getTimestamp("End");
                LocalDateTime end = timestampEnd.toLocalDateTime();
                Timestamp timestampCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDate = timestampCreateDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp timestampLastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime lastUpdate = timestampLastUpdate.toLocalDateTime();
                String lastUpdatedBy = LoginController.user;
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Appointment appointment = new Appointment(Appointment_ID, title, description, location,
                        type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

                monthsList.add(appointment);
            }
        }

    /**
     * method is used to report a quantity of appointments of a certain type within a given month.
     * @param month selected month for which appointments are being counted.
     * @param type selected type for which appointments are being counted.
     * @return int of quantity of appointments of a certain type within a certain month.
     */
    public static int reportQuantityQuery(String month, String type) throws SQLException {

        /*hash map made because the ComboBox for the types uses three letter abbreviations for the month
        but the database uses the full name for the month so the keys are three letter abbreviations and the
        values are the full name.
         */
        Map<String, String> monthMap = new HashMap<>();
        monthMap.put("JAN", "JANUARY");
        monthMap.put("FEB", "FEBRUARY");
        monthMap.put("MAR", "MARCH");
        monthMap.put("APR", "APRIL");
        monthMap.put("MAY", "MAY");
        monthMap.put("JUN", "JUNE");
        monthMap.put("JUL", "JULY");
        monthMap.put("AUG", "AUGUST");
        monthMap.put("SEP", "SEPTEMBER");
        monthMap.put("OCT", "OCTOBER");
        monthMap.put("NOV", "NOVEMBER");
        monthMap.put("DEC", "DECEMBER");

        //full name is pulled based off the passed in key value.
        String fullMonth = monthMap.get(month);

        int count = 0;
        int monthInt = Month.valueOf(fullMonth.toUpperCase()).getValue();
        String sql = "SELECT * FROM appointments WHERE MONTH(Start) = ? AND Type = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, monthInt);
        ps.setString(2, type);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            //for every appointment found count is incremented
            count++;
        }

        //count is returned
        return count;
    }


    /**
     * method used to return a list of appointments scheduled for a contact.
     * @param contactId ID of contact being searched.
     * @return list of months for the contact.
     */
    public static ObservableList<Appointment> getAppointmentsForContact(int contactId) throws SQLException {
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        //appointments table is searched for the contact.
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();

        //appointments found are added to the list and returned.
        while (rs.next()) {
            int Appointment_ID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp timestampStart = rs.getTimestamp("Start");
            LocalDateTime start = timestampStart.toLocalDateTime();
            start = TimeHelper.convertFromUTCToSystemDefault(start);
            Timestamp timestampEnd = rs.getTimestamp("End");
            LocalDateTime end = timestampEnd.toLocalDateTime();
            end = TimeHelper.convertFromUTCToSystemDefault(end);
            Timestamp timestampCreateDate = rs.getTimestamp("Create_Date");
            LocalDateTime createDate = timestampCreateDate.toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            Timestamp timestampLastUpdate = rs.getTimestamp("Last_Update");
            LocalDateTime lastUpdate = timestampLastUpdate.toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");

            Appointment appointment = new Appointment(Appointment_ID, title, description, location, type,
                    start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

            appointmentsList.add(appointment);
        }

        return appointmentsList;
    }

    /**
     * method returns an observable list of appointments for a passed in customer
     * @param customerID customer ID of the customer being searched
     * @return list of appointments for the given customer.
     */
    public static ObservableList<Appointment> getAppointmentsForCustomer(int customerID) throws SQLException {
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();


            String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp timestampStart = rs.getTimestamp("Start");
                LocalDateTime start = timestampStart.toLocalDateTime();
                start = TimeHelper.convertFromUTCToSystemDefault(start);
                Timestamp timestampEnd = rs.getTimestamp("End");
                LocalDateTime end = timestampEnd.toLocalDateTime();
                end = TimeHelper.convertFromUTCToSystemDefault(end);
                Timestamp timestampCreateDate = rs.getTimestamp("Create_Date");
                LocalDateTime createDate = timestampCreateDate.toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp timestampLastUpdate = rs.getTimestamp("Last_Update");
                LocalDateTime lastUpdate = timestampLastUpdate.toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int contactId = rs.getInt("Contact_ID");
                int userId = rs.getInt("User_ID");

                Appointment appointment = new Appointment(Appointment_ID, title, description, location, type,
                        start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userId, contactId);

                appointmentsList.add(appointment);
            }
            return appointmentsList;
    }


}
