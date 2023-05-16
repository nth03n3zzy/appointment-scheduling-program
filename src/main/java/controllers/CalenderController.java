package controllers;

import helper.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Appointment;
import models.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;
/**
 * The CalenderController class is a controller for the calender view.
 * It allows users to view a schedule of all the appointments and sort them by multiple different fields as well
 * as sort them by current week and current month. also allows the user to log out. view all customers. buttons to go to the
 * add customer or appointment scenes, and modify appointment and customer scenes as well as delete customers and appointments,
 * and navigate to the reports scene.
 * @author Anthony Collins
 * @version 1.0
 */

public class CalenderController implements Initializable {

    /**
     * local variable sets stage.
     */
    private Stage stage;
    /**
     * local variable sets scene.
     */
    private Scene scene;
    /**
     * local variable sets root.
     */
    private  Parent root;
    /**
     * local object creates a radioButton for the user to select to view all appointments.
     */
    @FXML    RadioButton viewAll;
    /**
     * local object creates a radioButton for the user to select to view the current month of appointments.
     */
   @FXML RadioButton viewMonth;
    /**
     * local object creates a radioButton for the user to select to view the current week of appointments.
     */
   @FXML RadioButton viewWeek;
    /**
     * local object creates a radioButton for the user to select to view all customers.
     */
   @FXML RadioButton viewCustomer;

    /**
     * local object creates a TableView where customer lists or appointment schedules will be dsiplayed
     * depending on user selection.
     */
   @FXML
    TableView appointmentsAndCustomers;
    /**
     * local object creates a TableColumn for displaying parameters in the tableView.
     */
   @FXML
    TableColumn c1;
    /**
     * local object creates a TableColumn for displaying parameters in the tableView.
     */
    @FXML
    TableColumn c2;
    /**
     * local object creates a TableColumn for displaying parameters in the tableView.
     */
    @FXML
    TableColumn c3;
    /**
     * local object creates a TableColumn for displaying parameters in the tableView.
     */
    @FXML
    TableColumn c4;
    /**
     * local object creates a TableColumn for displaying parameters in the tableView.
     */
    @FXML
    TableColumn c5;
    /**
     * local object creates a TableColumn for displaying parameters in the tableView.
     */
    @FXML
    TableColumn c6;
    /**
     * local object creates a TableColumn for displaying parameters in the tableView.
     */
    @FXML
    TableColumn c7;
    /**
     * local object creates a TableColumn for displaying parameters in the tableView.
     */
    @FXML
    TableColumn c8;
    /**
     * local object creates a TableColumn for displaying parameters in the tableView.
     */
    @FXML
    TableColumn c9;
    /**
     * local object creates a TableColumn for displaying parameters in the tableView.
     */
    @FXML
    TableColumn c10;
    /**
     * local object creates a Label for displaying the users timeZone.
     */
    @FXML
    Label timeZone;
    /**
     * local object creates a TextField for searching appointments and cutomers by appointment ID and customer ID.
     */
    @FXML
    TextField search;

    /**
     * local object creates a Customer for passing a selected customer from the calender scene to the modify customer scene.
     */
    public static Customer modifyCustomer;
    /**
     * local object creates a Customer for passing a selected appointment from the calender scene to the modify appointment scene.
     */
    public static  Appointment modifyAppointment;

    /**
     * local object creates a observable list for all appointments to be used to populate the table view.
     */
    private static ObservableList<Appointment> allAppointmentsList = FXCollections.observableArrayList();
    /**
     * local object creates a observable list for all customers to be used to populate the table view.
     */
    private static  ObservableList<Customer> allCustomersList = FXCollections.observableArrayList();
    /**
     * local object creates a observable list for this months appointments to be used to populate the table view.
     */

    private static ObservableList<Appointment> thisMonthsAppointmentsList = FXCollections.observableArrayList();
    /**
     * local object creates a observable list for this weeks appointments to be used to populate the table view.
     */
    private static ObservableList<Appointment> weekList = FXCollections.observableArrayList();

    /**
     * This method pulls appointments data from the database and populates the
     * allAppointmentsList ArrayList.
     * @throws SQLException if there is an issue with executing the SQL query
     */
    private void pullAppointmentsFromDB() throws SQLException {
        String sql = "SELECT * FROM appointments";
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
            //start time is converted from UTC to system default time.
            start = TimeHelper.convertFromUTCToSystemDefault(start);
            Timestamp timestampEnd = rs.getTimestamp("End");
            LocalDateTime end = timestampEnd.toLocalDateTime();
            //end time is converted from UTC to system default time.
            end = TimeHelper.convertFromUTCToSystemDefault(end);
            Timestamp timestampCreateDate = rs.getTimestamp("Create_Date");
            LocalDateTime createDate = timestampCreateDate.toLocalDateTime();
            //create time is converted from UTC to system default time.
            createDate = TimeHelper.convertFromUTCToSystemDefault(createDate);
            String createdBy = rs.getString("Created_By");
            Timestamp timestampLastUpdate = rs.getTimestamp("Last_Update");
            LocalDateTime lastUpdate = timestampLastUpdate.toLocalDateTime();
            //last updated time converted from UTC to system default time.
            lastUpdate = TimeHelper.convertFromUTCToSystemDefault(lastUpdate);
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");

            Appointment appointment = new Appointment(Appointment_ID, title, description, location,
                    type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

            //each appointment is added to the all appointment list.
            allAppointmentsList.add(appointment);

        }
    }
    /**
     * This method pulls customers data from the database and populates the
     * allCustomersList ArrayList.
     * @throws SQLException if there is an issue with executing the SQL query
     */
    private void pullCustomerFromDB() throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customer_ID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            Timestamp timestampCreateDate = rs.getTimestamp("Create_Date");
            LocalDateTime createDate = timestampCreateDate.toLocalDateTime();
            //create time is converted from UTC to systemdefault time.
            createDate = TimeHelper.convertFromUTCToSystemDefault(createDate);
            Timestamp timestampLastUpdate = rs.getTimestamp("Last_Update");
            LocalDateTime lastUpdate = timestampLastUpdate.toLocalDateTime();
            //last updated time is converted from UTC to system default time.
            lastUpdate = TimeHelper.convertFromUTCToSystemDefault(lastUpdate);
            String createdBy = rs.getString("Created_By");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");

            Customer customer = new Customer(customer_ID, customerName, address, postalCode, phone, createDate,
                    createdBy, lastUpdate, lastUpdatedBy, divisionId);

            //each customer from DB is added to the all customer list.
            allCustomersList.add(customer);

        }
    }

    /**
     * This method is called when the "Search" button is clicked. It retrieves the user's search input from the search TextField,
     * and searches for customers or appointments. If view all, View month, or view week are selected then appointments can be searched
     * by customerID or appointmentID. if an invalid search is entered an alert will apear. if the view customer RadioButtion is selected
     * then customers can only be searched by customerID or CustomerName if the search is not valid an alert will appear.
     * @param actionEvent the event that triggered the method call
     * @throws SQLException if an error occurs when querying the database
     */
   @FXML void searchButtonClicked(ActionEvent actionEvent) throws SQLException {
       // creates list of appointments matching search criteria to be displayed in the table.
    ObservableList<Appointment> searchedAppointments = FXCollections.observableArrayList();

    // creates a list of customers matching the search to be displayed in the table.
    ObservableList<Customer> searchedCustomer = FXCollections.observableArrayList();

    //instantiates string from the search text field.
    String searchedAppointment = search.getText();

    //if view all button is selected the all appointments list will be searched. searches can be by eitherappointmentID
       //or customerID. partial matches are accepted.
    if(viewAll.isSelected()){
        for (Appointment appointment : allAppointmentsList) {
            if (String.valueOf(appointment.getAppointment_ID()).contains(searchedAppointment)
                    || String.valueOf(appointment.getCustomerId()).contains(searchedAppointment)) {
                //if there is a match it is added to the searched appointments list
                searchedAppointments.add(appointment);
            }
        }

        //table view is set to display the list of appointments matching the search
        appointmentsAndCustomers.setItems(searchedAppointments);
        //if searched appointment list is empty calls a alert for invalid search and then displays the all appointment list.
        if(searchedAppointment.isEmpty()) {
            AlertHelper.callAlert("invalid search");
            appointmentsAndCustomers.setItems(allAppointmentsList);
        }
        }
       //if view month button is selected the current months appointments list will be searched. searches can be by either appointmentID
       //or customerID. partial matches are accepted.
    if(viewMonth.isSelected()){
        for (Appointment appointment : thisMonthsAppointmentsList) {
            if (String.valueOf(appointment.getAppointment_ID()).contains(searchedAppointment)
                    || String.valueOf(appointment.getCustomerId()).contains(searchedAppointment)) {

                //if there is a match it is added to the searched appointments list
                searchedAppointments.add(appointment);
            }
        }

        //table view is set to display the list of appointments matching the search
        appointmentsAndCustomers.setItems(searchedAppointments);
        if(searchedAppointment.isEmpty()) {

            //if searched appointment list is empty calls a alert for invalid search and then displays the all appointment list.
            AlertHelper.callAlert("invalid search");
            appointmentsAndCustomers.setItems(thisMonthsAppointmentsList);
        }
    }
       //if view week button is selected the current weeks appointments list will be searched. searches can be by either appointmentID
       //or customerID. partial matches are accepted
    if(viewWeek.isSelected()){
        for (Appointment appointment : weekList) {
            if (String.valueOf(appointment.getAppointment_ID()).contains(searchedAppointment)
                    || String.valueOf(appointment.getCustomerId()).contains(searchedAppointment)) {

                //if there is a match it is added to the searched appointments list
                searchedAppointments.add(appointment);
            }
        }

        //table view is set to display the list of appointments matching the search
        appointmentsAndCustomers.setItems(searchedAppointments);
        if(searchedAppointment.isEmpty()) {

            //if searched appointment list is empty calls a alert for invalid search and then displays the all appointment list.
            AlertHelper.callAlert("invalid search");
            appointmentsAndCustomers.setItems(weekList);
        }
    }
       //if view customers button is selected the customers list will be searched. searches can be by either customer name
       //or customerID. partial matches are accepted
    if(viewCustomer.isSelected()){
        for (Customer customer : allCustomersList) {
            if (String.valueOf(customer.getCustomerID()).contains(searchedAppointment)
            || customer.getName().contains(searchedAppointment)) {

                //if there is a match it is added to the searched customers list
                searchedCustomer.add(customer);
            }
        }

        //table view is set to display the list of customers matching the search
        appointmentsAndCustomers.setItems(searchedCustomer);
        if(searchedCustomer.isEmpty()) {

            //if searched customers list is empty calls a alert for invalid search and then displays the all customers list.
            AlertHelper.callAlert("invalid search");
            appointmentsAndCustomers.setItems(allCustomersList);
        }
    }
    }

    /**
     * Displays all appointments in the system and clears any search filters.
     * This method clears the list of all appointments, sets the viewAll radio button to selected,
     * and unselects all other view options. It then populates the table view with all appointments
     * from the database, and sets the cell value factories for each column to the corresponding
     * appointment data field. This method also clears the list of all customers to prevent duplicates
     * when a view of the customer list is pulled up.
     * @param actionEvent the view all radio button is selected.
     * @throws IOException if there is an error loading the FXML file for the appointment/customer list
     * @throws SQLException if there is an error querying the database for the appointments
     */
   @FXML
    void displayAll(ActionEvent actionEvent) throws IOException, SQLException {

       //resets search prompt
       search.setPromptText("Enter Appt ID/ CustID");
       //all appointments list is cleared to get rid of any duplicates if the button is being selected multiple times.
       allAppointmentsList.clear();
       //clears the other radioButtons
       if (viewAll.isSelected()) {
           viewAll.setSelected(true);
           viewMonth.setSelected(false);
           viewCustomer.setSelected(false);
           viewWeek.setSelected(false);
       }
       //sets the titles of the columns
       c1.setText("Appointment_ID");
       c2.setText("Title");
       c3.setText("Description");
       c4.setText("Location");
       c5.setText("contact");
       c6.setText("type");
       c7.setText("Start Date and Time");
       c8.setText("End Date And Time");
       c9.setText("Customer_ID");
       c10.setText("User_ID");

       //pulls all the appointments from the DB
       pullAppointmentsFromDB();

       // added this so when a view of the customer list is pulled up the list of customers is not duplicated.
       allCustomersList.clear();

       //sets the table to display the list of all customers.
       appointmentsAndCustomers.setItems(allAppointmentsList);

       //sets the columns to display the appropriate data
       c1.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
       c2.setCellValueFactory(new PropertyValueFactory<>("title"));
       c3.setCellValueFactory(new PropertyValueFactory<>("description"));
       c4.setCellValueFactory(new PropertyValueFactory<>("location"));
       c5.setCellValueFactory(new PropertyValueFactory<>("contactId"));
       c6.setCellValueFactory(new PropertyValueFactory<>("type"));

       /*columns are updated with strings of formatted times instead of localDateTime objects of the start time from the
       appointment object so that the displayed time is more readable to the user.
        */
       c7.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> p) {
               return new SimpleStringProperty(TimeHelper.formatLocalDateTime.apply(p.getValue().getStart()));
           }
       });

       /*columns are updated with strings of formatted times instead of localDateTime objects of the end time from the
       appointment object so that the displayed time is more readable to the user.
        */
       c8.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> p) {
               return new SimpleStringProperty(TimeHelper.formatLocalDateTime.apply(p.getValue().getEnd()));
           }
       });


       c9.setCellValueFactory(new PropertyValueFactory<>("customerId"));
       c10.setCellValueFactory(new PropertyValueFactory<>("userId"));
   }
    /**
     * Displays the current months appointments in the system and clears any search filters.
     * This method clears the months appointments list, sets the viewMonth radio button to selected,
     * and unselects all other view options. It then populates the table view with this months appointments
     * from the database, and sets the cell value factories for each column to the corresponding
     * appointment data field. This method also clears the list of all customers to prevent duplicates
     * when a view of the customer list is pulled up.
     * @param actionEvent the view month radio button is selected.
     * @throws IOException if there is an error loading the FXML file for the appointment/customer list
     * @throws SQLException if there is an error querying the database for the appointments
     */
   @FXML void displayMonth(ActionEvent actionEvent) throws IOException, SQLException {

       //resets search prompt
       search.setPromptText("Enter Appt ID/ CustID");
       //months appointments list is cleared to get rid of any duplicates if the button is being selected multiple times.
        thisMonthsAppointmentsList.clear();
        allCustomersList.clear();
       if (viewMonth.isSelected()) {
           viewMonth.setSelected(true);
           viewAll.setSelected(false);
           viewWeek.setSelected(false);
           viewCustomer.setSelected(false);
       }

       //sets the titles of the columns
       c1.setText("Appointment_ID");
       c2.setText("Title");
       c3.setText("Description");
       c4.setText("Location");
       c5.setText("contact");
       c6.setText("type");
       c7.setText("Start Date and Time");
       c8.setText("End Date And Time");
       c9.setText("Customer_ID");
       c10.setText("User_ID");

       //pulls current months appointments from the Datta base.
       AppointmentsQuery.selectMonth(thisMonthsAppointmentsList);

       //sets the list of the current months appointments as the display in the table.
       appointmentsAndCustomers.setItems(thisMonthsAppointmentsList);

       c1.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
       c2.setCellValueFactory(new PropertyValueFactory<>("title"));
       c3.setCellValueFactory(new PropertyValueFactory<>("description"));
       c4.setCellValueFactory(new PropertyValueFactory<>("location"));
       c5.setCellValueFactory(new PropertyValueFactory<>("contactId"));
       c6.setCellValueFactory(new PropertyValueFactory<>("type"));

        /*columns are updated with strings of formatted times instead of localDateTime objects of the start time from the
       appointment object so that the displayed time is more readable to the user.
        */
       c7.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> p) {
               return new SimpleStringProperty(TimeHelper.formatLocalDateTime.apply(p.getValue().getStart()));
           }
       });

       /*columns are updated with strings of formatted times instead of localDateTime objects of the end time from the
       appointment object so that the displayed time is more readable to the user.
        */
       c8.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> p) {
               return new SimpleStringProperty(TimeHelper.formatLocalDateTime.apply(p.getValue().getEnd()));
           }
       });

       c9.setCellValueFactory(new PropertyValueFactory<>("customerId"));
       c10.setCellValueFactory(new PropertyValueFactory<>("userId"));


   }

    /**
     * Displays the current weeks appointments in the system and clears any search filters.
     * This method clears the weeks appointments list, sets the viewWeek radio button to selected,
     * and unselects all other view options. It then populates the table view with this weeks appointments
     * from the database, and sets the cell value factories for each column to the corresponding
     * appointment data field. This method also clears the list of all customers to prevent duplicates
     * when a view of the customer list is pulled up.
     * @param actionEvent the view week radio button is selected.
     * @throws IOException if there is an error loading the FXML file for the appointment/customer list
     * @throws SQLException if there is an error querying the database for the appointments
     */
   @FXML void displayWeek(ActionEvent actionEvent) throws IOException, SQLException {
       //resets search prompt
       search.setPromptText("Enter Appt ID/ CustID");

       //weeks appointments list is cleared to get rid of any duplicates if the button is being selected multiple times.
       weekList.clear();
       if (viewWeek.isSelected()) {
           viewWeek.setSelected(true);
           viewAll.setSelected(false);
           viewMonth.setSelected(false);
           viewCustomer.setSelected(false);
       }

       //sets column titles.
       c1.setText("Appointment_ID");
       c2.setText("Title");
       c3.setText("Description");
       c4.setText("Location");
       c5.setText("contact");
       c6.setText("type");
       c7.setText("Start Date and Time");
       c8.setText("End Date And Time");
       c9.setText("Customer_ID");
       c10.setText("User_ID");

       //pulls list of current weeks appointments from the database.
       AppointmentsQuery.selectWeek(weekList);

       //sets the table view to the current weeks list of appointments.
       appointmentsAndCustomers.setItems(weekList);


       c1.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
       c2.setCellValueFactory(new PropertyValueFactory<>("title"));
       c3.setCellValueFactory(new PropertyValueFactory<>("description"));
       c4.setCellValueFactory(new PropertyValueFactory<>("location"));
       c5.setCellValueFactory(new PropertyValueFactory<>("contactId"));
       c6.setCellValueFactory(new PropertyValueFactory<>("type"));

        /*columns are updated with strings of formatted times instead of localDateTime objects of the start time from the
       appointment object so that the displayed time is more readable to the user.
        */
       c7.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> p) {
               return new SimpleStringProperty(TimeHelper.formatLocalDateTime.apply(p.getValue().getStart()));
           }
       });

       /*columns are updated with strings of formatted times instead of localDateTime objects of the end time from the
       appointment object so that the displayed time is more readable to the user.
        */
       c8.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> p) {
               return new SimpleStringProperty(TimeHelper.formatLocalDateTime.apply(p.getValue().getEnd()));
           }
       });

       c9.setCellValueFactory(new PropertyValueFactory<>("customerId"));
       c10.setCellValueFactory(new PropertyValueFactory<>("userId"));

   }
    /**
     * Displays a table of all customers in the database.
     * Clears the allCustomersList, sets the viewCustomer radio button to selected, and clears other radio button selections.
     * Populates the table with customer data from the database and sets the cell value factories for each column.
     * @param actionEvent The event triggered by clicking the Display Customers RadioButton.
     * @throws IOException If an error occurs while loading the FXML file.
     * @throws SQLException If an error occurs while interacting with the database.
     */
   @FXML void displayCustomer(ActionEvent actionEvent) throws IOException, SQLException {


       //changes search prompt for customers.
       search.setPromptText("Cust name or ID");
       //clears list of all customers to avoid duplicates
       allCustomersList.clear();
        if (viewCustomer.isSelected()){
            viewCustomer.setSelected(true);
            viewAll.setSelected(false);
            viewMonth.setSelected(false);
            viewWeek.setSelected(false);
        }
        //sets column titles.
       c1.setText("Customer_ID");
       c2.setText("Name");
       c3.setText("Address");
       c4.setText("Postal Code");
       c5.setText("Phone #");
       c6.setText("Date Created");
       c7.setText("Created By");
       c8.setText("Last Updated");
       c9.setText("Last Updated By");
       c10.setText("Division ID");

       //pulls customers from the database.
       pullCustomerFromDB();

       //list of customers is set to be displayed in the table.
       appointmentsAndCustomers.setItems(allCustomersList);


       c1.setCellValueFactory(new PropertyValueFactory<>("customerID"));
       c2.setCellValueFactory(new PropertyValueFactory<>("name"));
       c3.setCellValueFactory(new PropertyValueFactory<>("address"));
       c4.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
       c5.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));


        /*columns are updated with strings of formatted times instead of localDateTime objects of the create date from the
       customer object so that the displayed time is more readable to the user.
        */
       c6.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Customer, String> p) {
               return new SimpleStringProperty(TimeHelper.formatLocalDateTime.apply(p.getValue().getCreateDate()));
           }
       });
       c7.setCellValueFactory(new PropertyValueFactory<>("createdBy"));

       /*columns are updated with strings of formatted times instead of localDateTime objects of the last updated date from the
       customer object so that the displayed time is more readable to the user.
        */
       c8.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Customer, String> p) {
               return new SimpleStringProperty(TimeHelper.formatLocalDateTime.apply(p.getValue().getLastUpdate()));
           }
       });

       c9.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
       c10.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

    }

    /**
     * Method loads the add appointment or add customer scene depending on which radioButton is selected.
     * @param event add button is clicked.
     */
    @FXML
    private void addButtonClicked(ActionEvent event){
        //if view customer is selected the add customer scene will load.
        try {
            if(viewCustomer.isSelected()) {

                root = FXMLLoader.load(getClass().getResource("/addCustomer.fxml"));
            }else {
                //if any appointment view is selected the add appointment scene will load.
                root = FXMLLoader.load(getClass().getResource("/addAppointment.fxml"));
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for loading the edit appointment or edit customer scene. if the the view customer display is up it will load
     * the edit customer scene otherwise it will load the edit appointment scene.
     * @param actionEvent modify button is clicked.
     * @throws SQLException
     */
    @FXML void modifyButtonCLicked(ActionEvent actionEvent) throws SQLException {
        try {
            /*
            if view customer radioButton is selected checks if a customer has been selected in the table
            if not an alert is displayed telling the user to select a customer. Otherwise a Customer object is
            created copying the selected customer to pass to the modify customer scene to fill the
            fields in that scene.
             */

            if(viewCustomer.isSelected() && !appointmentsAndCustomers.getSelectionModel().isEmpty()) {

                modifyCustomer = (Customer) appointmentsAndCustomers.getSelectionModel().getSelectedItem();

                root = FXMLLoader.load(getClass().getResource("/editCustomer.fxml"));

               /*
                    if any appointment view radioButton is selected. it checks if a appointment has been selected in the table,
                    if not an alert is displayed telling the user to select a appointment. Otherwise a Appointment object is
                    created copying the selected appointment to pass to the modify appointment scene to fill the
                    fields in that scene.
                */
            }else if((viewAll.isSelected() || viewMonth.isSelected() || viewWeek.isSelected()) && !appointmentsAndCustomers.getSelectionModel().isEmpty()){

                modifyAppointment = (Appointment) appointmentsAndCustomers.getSelectionModel().getSelectedItem();
                System.out.println("calender appointment selection set");

                root = FXMLLoader.load(getClass().getResource("/editAppointment.fxml"));
            }
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        }
        catch(Exception e) {
            //calls the no selection alert.
            AlertHelper.callAlert("no selection");
        }
    }

    /**
     * handles logic for determining if a customer is to be deleted or appointment is to be deleted based on
     * which list is being displayed in the main table. as well as exception handling if no appointment or customer has
     * been selected.displays alerts asking if the user if they are sure they wish to delete the object
     * and an alert informing the user that the selected object has successfully been deleted.
     * @throws SQLException when there is an error interacting with the database
     */
    @FXML void deleteButtonClicked(ActionEvent actionEvent) throws SQLException {
        try{
            /*
            if the view customer radio button is selected the program checks if a selection has been made on the table.
            if there is no selection an alert informing the user is called.
             */
            if (viewCustomer.isSelected() && !appointmentsAndCustomers.getSelectionModel().isEmpty()) {

                //Customer object modify customer created copying the selected customer.
                modifyCustomer = (Customer) appointmentsAndCustomers.getSelectionModel().getSelectedItem();

                //alert called to verify the user wishes to delete the customer.
                AlertHelper.callAlert("delete customer");

                //if the user confirms they wish to delete the appointment.
                if(AlertHelper.confirmed){

                    //order sent to database to delete the customer that was selected.
                    CustomerQuery.deleteCustomer(modifyCustomer.getCustomerID());

                    //clears the all customer list
                    allCustomersList.clear();

                    //and reloads the list to display the list without the now removed customer.
                    pullCustomerFromDB();

                    //table is set to display the new customer list.
                    appointmentsAndCustomers.setItems(allCustomersList);

                    //alert called informing the user the selected customer has been deleted.
                    AlertHelper.callAlert("customer deleted");

                    //resetting confirmed to false for future logic operations.
                    AlertHelper.confirmed = false;

                }

                //checks if any of the appointment views are selected and a appointment has been selected. if a appointment
                //is not selected an alert is called informing the user to select an appointment.
            } else if ((viewAll.isSelected() || viewMonth.isSelected() || viewWeek.isSelected()) && !appointmentsAndCustomers.getSelectionModel().isEmpty()) {


                //appointment object created copying the selected appointment.
                modifyAppointment = (Appointment) appointmentsAndCustomers.getSelectionModel().getSelectedItem();

                //alert called verifyingthe suer wishes to delete the appointment.
                AlertHelper.callAlert("deleteAppointment");

                //if delete action is confirmed
                if(AlertHelper.confirmed) {
                    //query sent to database to delete the appointment.
                    AppointmentsQuery.delete(modifyAppointment.getAppointment_ID());

                    //the appointment list is cleared to get rid of the old appointment that has ben deleted.
                    allAppointmentsList.clear();

                    //appointment list is pulled from the database to get the accurate list of current shceduled appointments.
                    pullAppointmentsFromDB();

                    //table is set to display the updated list of appointments.
                    appointmentsAndCustomers.setItems(allAppointmentsList);

                    //alert called informing the appointment has been deleted.
                    AlertHelper.callAlert("appointment deleted");

                    //confirmed reset to false for future logic operations.
                    AlertHelper.confirmed = false;
                }
            }


        } catch (SQLException e) {
            //alert called when no selection has been made.
            AlertHelper.callAlert("no selection");
        }
    }

    /**
     *This method is called when the report button is clicked.
     *It loads the reports  file.
     *@param actionEvent report button is clicked.
     *@throws IOException if the reports file cannot be loaded
     */
    @FXML void reportButtonClicked(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/reports.fxml"));
         stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
         scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is called when the logout button is clicked.
     * It displays an alert prompting the user to confirm the logout action.
     * If the user confirms, it loads the loginScreen file
     * @param event logout button is clicked.
     * @throws SQLException if there is an error with the database connection
     */
    public void logoutButtonClicked(ActionEvent event) throws SQLException {
        //calls alert asking user if they are sure they wish to logout.
        AlertHelper.callAlert("logout");
            if(AlertHelper.confirmed) {


                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/loginScreen.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    //resets confirmed to false for future logic operations.
                    AlertHelper.confirmed = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }
    /**
     * This method is called when the Calendar is initialized.
     * It sets the text of the timeZone label to the system's default time zone.
     * It clears the allAppointmentsList, modifyAppointment, and modifyCustomer variables.
     * It sets the viewAll radio button as selected and the others as not selected.
     * It sets the column names of the appointmentsAndCustomers TableView.
     * It pulls the appointments from the database and sets them to the appointmentsAndCustomers TableView.
     * It also sets the cell value factories for the appointment and customer data displayed in the TableView.
     * @param url the URL of the FXML file to be loaded
     * @param resourceBundle the ResourceBundle used to localize the FXML file
     * @throws RuntimeException if there is an error with the SQL query
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //sets time label to users default time zone.
        timeZone.setText("Current Time Zone: " + ZoneId.systemDefault().getId());

        //clears all apointment list
        allAppointmentsList.clear();
        //resets modify appointment to null
        modifyAppointment = null;
        //resets modify customer to null
        modifyCustomer = null;

        //sets view all radioButton to selected
        viewAll.setSelected(true);
        viewMonth.setSelected(false);
        viewCustomer.setSelected(false);
        viewWeek.setSelected(false);

        c1.setText("Appointment_ID");
        c2.setText("Title");
        c3.setText("Description");
        c4.setText("Location");
        c5.setText("contact");
        c6.setText("type");
        c7.setText("Start Date and Time");
        c8.setText("End Date And Time");
        c9.setText("Customer_ID");
        c10.setText("User_ID");

        try {
            pullAppointmentsFromDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // added this so when a view of the customer list is pulled up the list of customers is not duplicated.
        allCustomersList.clear();

        appointmentsAndCustomers.setItems(allAppointmentsList);

        c1.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        c2.setCellValueFactory(new PropertyValueFactory<>("title"));
        c3.setCellValueFactory(new PropertyValueFactory<>("description"));
        c4.setCellValueFactory(new PropertyValueFactory<>("location"));
        c5.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        c6.setCellValueFactory(new PropertyValueFactory<>("type"));

        /*columns are updated with strings of formatted times instead of localDateTime objects of the start time from the
       appointment object so that the displayed time is more readable to the user.
        */
        c7.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> p) {
                return new SimpleStringProperty(TimeHelper.formatLocalDateTime.apply(p.getValue().getStart()));
            }
        });

       /*columns are updated with strings of formatted times instead of localDateTime objects of the end time from the
       appointment object so that the displayed time is more readable to the user.
        */
        c8.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> p) {
                return new SimpleStringProperty(TimeHelper.formatLocalDateTime.apply(p.getValue().getEnd()));
            }
        });

        c9.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        c10.setCellValueFactory(new PropertyValueFactory<>("userId"));


    }
}
