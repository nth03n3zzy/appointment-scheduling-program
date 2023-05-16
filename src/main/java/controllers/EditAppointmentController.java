package controllers;

import helper.*;
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
import javafx.stage.Stage;
import models.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;
/**
 * The EditAppointmentController class implements the logic for editing appointments in the system.
 * It handles the user's input and updates the database accordingly.
 * @author Anthony Collins
 */
public class EditAppointmentController implements Initializable {

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
    private Parent root;

    /**
     * local object creates a textField for the user to input the title of the appointment.
     */
    @FXML
    private TextField title;
    /**
     * local object creates a TextArea for the user to input the description of the appointment.
     */
    @FXML private TextArea description;
    /**
     * local object creates a textField for the user to input the location of the appointment.
     */
    @FXML private TextField location;
    /**
     * local object creates a textField for the user to input the customer ID for the appointment.
     */
    @FXML private TextField customerID;

    /**
     * local object creates a textField for  the appointmentID of the appointment.
     */
    @FXML private TextField appointmentID;
    /**
     * local object creates a ComboBox for the user to select the type of  appointment.
     */
    @FXML
    private ComboBox type;
    /**
     * local object creates a textField for the user to input the user ID of the appointment.
     */
    @FXML private TextField userID;
    /**
     * local object creates a Spinner for the user to select the starting hour for the appointment.
     */
    @FXML
    private Spinner<Integer> startHour;
    /**
     * local object creates a Spinner for the user to select the starting minute for the appointment.
     */
    @FXML
    private Spinner<Integer> startMinuet;
    /**
     * local object creates a Spinner for the user to select the end hour for the appointment.
     */
    @FXML
    private Spinner<Integer> endHour;
    /**
     * local object creates a Spinner for the user to select the end minute for the appointment.
     */
    @FXML
    private Spinner<Integer> endMinuet;
    /**
     * local object creates a DatePicker for the user to select the starting date for the appointment.
     */
    @FXML private DatePicker startDate;
    /**
     * local object creates a Appointment to be created based of the appointment that was selected to modify out of the
     * calender scene.
     */
    @FXML private Appointment appointmentToModify;
    /**
     * local object creates a ComboBox for the user to select the contact  for the appointment.
     */
    @FXML public ComboBox<String> contactNameComboBox;

    /**
     * Initializes the appointment form with all fields pre populated with information from tthe appointment that was selected
     * to modify.
     * @param url the URL to initialize the appointment form
     * @param resourceBundle the resource bundle used to initialize the appointment form
     * @throws RuntimeException if there is a SQL exception when attempting to select contact names, customer IDs, or user IDs
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //appointment object is a copied from the appointment that was selected to modify.
        appointmentToModify = CalenderController.modifyAppointment;

        try {
            //calls method to populate a list of contact names with all contact names from the dataBase.
            ContactQuery.selectContactName();
            /*calls method to populate a list of customerIDs with all customer IDs from dataBase so that when a incorrect customer
            id is entered it is checked against the list and can be found without running into a SQL exception */
            CustomerQuery.selectCustomerID();
            /*calls method to populate a list of UserIDs with all user IDs from dataBase so that when a incorrect user
            ID is entered it is checked against the list and can be found without running into a SQL exception */
            UserQuery.selectUserID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //contact name combo box is populated with all the contact names for the database.
        contactNameComboBox.setItems(ContactQuery.listOfContactsNames);

        //list of appointment types created to add to the type comboBox.
        ObservableList<String> listOfAppointmentType = FXCollections.observableArrayList();
        listOfAppointmentType.add("Dental");
        listOfAppointmentType.add("General");
        listOfAppointmentType.add("Coffee Break");
        listOfAppointmentType.add("Business Meeting");
        type.setItems(listOfAppointmentType);

        //start hour spinner set with a range of 0 - 23.
        SpinnerValueFactory<Integer> startHourValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23);

        //takes the appointment to modifys start time and sets the hour spinner to the start times hour
        LocalTime startTimeInitial = appointmentToModify.getStart().toLocalTime();
        startHourValueFactory.setValue(startTimeInitial.getHour());
        startHour.setValueFactory(startHourValueFactory);

        //start minuet spinner set with range of 0-59.
        SpinnerValueFactory<Integer> startMinuetValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59);

        //start minuet spinner set o initial value of the start time minuet.
        startMinuetValueFactory.setValue(startTimeInitial.getMinute());
        startMinuet.setValueFactory(startMinuetValueFactory);

        //sets the end time spinner to a range of 0-23
        SpinnerValueFactory<Integer> endHourValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23);

        //sets the end time hour spinner initial value to the end time hour.
        LocalTime endTimeInitial = appointmentToModify.getEnd().toLocalTime();
        endHourValueFactory.setValue(endTimeInitial.getHour());
        endHour.setValueFactory(endHourValueFactory);

        //sets end time minute spinner range to 0-59.
        SpinnerValueFactory<Integer> endMinuetValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59);


        //sets the then end time minute spinner to an initial value of the end time minuet.
        endMinuetValueFactory.setValue(endTimeInitial.getMinute());
        endMinuet.setValueFactory(endMinuetValueFactory);


        //sets the other fields to the selected appointments values.
        appointmentID.setText(String.valueOf(appointmentToModify.getAppointment_ID()));
        title.setText(appointmentToModify.getTitle());
        description.setText(appointmentToModify.getDescription());
        location.setText(appointmentToModify.getLocation());


        try {
            contactNameComboBox.setValue(ContactQuery.selectContactName(appointmentToModify.getContactId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        type.setValue(appointmentToModify.getType());
        //sets the appointments date to the selected appointments date.
       LocalDate startDate = appointmentToModify.getStart().toLocalDate();
       this.startDate.setValue(startDate);


        customerID.setText(String.valueOf(appointmentToModify.getCustomerId()));
        userID.setText(String.valueOf(appointmentToModify.getUserId()));


    }

    /**
     * This method is called when the save button is clicked in the edit Appointment form.
     * It first checks if all required fields are filled and then proceeds to check if the provided
     * customer ID and user ID are valid. If valid, it checks if the start and end times are within
     * business hours, and then proceeds to convert them to UTC time zone. If they are within business hours
     * and do not overlap with existing appointments, it prompts the user with a confirmation alert before
     * inserting the appointment into the database. After successful insertion, it returns the user to the
     * main calendar view.
     * @param actionEvent  the save button being clicked.
     * @throws SQLException if there is an error with the SQL query execution
     * @throws IOException if there is an error loading the FXML file for the main calendar view
     */

    public void saveButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {

        //if statement checking if all appointment fields are filled out.
        if (!title.getText().isEmpty() && !description.getText().isEmpty() && !location.getText().isEmpty() &&
                !customerID.getText().isEmpty() && !userID.getText().isEmpty() && !(contactNameComboBox.getSelectionModel().getSelectedItem() == null) &&
                !(type.getSelectionModel().getSelectedItem() == null)) {

            try {
                if (UserQuery.listOfUserId.contains(Integer.parseInt(userID.getText()))) {
                }
            }catch (NumberFormatException e) {
                AlertHelper.callAlert("invalid userID");
            }

            //if statement checking if user id is valid
            if (UserQuery.listOfUserId.contains(Integer.parseInt(userID.getText()))) {

                try {
                    if ((CustomerQuery.listOfCustomerID.contains(Integer.parseInt(customerID.getText())))) {
                    }
                }catch (NumberFormatException e) {
                    AlertHelper.callAlert("invalid customerID");
                }

                //if statement checking if customer id is a valid customer ID.
                if ((CustomerQuery.listOfCustomerID.contains(Integer.parseInt(customerID.getText())))) {

                    //created a localDateTime for the start time of the appointment.
                    LocalTime startTime = LocalTime.of(startHour.getValue(), startMinuet.getValue());
                    LocalDate startDate = this.startDate.getValue();
                    LocalDateTime startTimeDate = LocalDateTime.of(startDate, startTime);

                    //creates alocalDateTime for the end time of the appointment
                    LocalTime endTime = LocalTime.of(endHour.getValue(), endMinuet.getValue());
                    LocalDate endDate = startDate;
                    LocalDateTime endTimeDate = LocalDateTime.of(endDate, endTime);

                    //checks if the end time is after the start time.
                    if (startTimeDate.isBefore(endTimeDate)) {

                        //checks if the appointment being scheduled is between 8AM- 10PM EST.
                        if (TimeHelper.withinBusinessHoursCheck.test(startTimeDate) && TimeHelper.withinBusinessHoursCheck.test(endTimeDate)) {

                            //appointment times are checked agains database to see if they overlap with any appointments currently scheduled for the customer.
                            if (AppointmentsQuery.checkForOverlappingEditAppointments(Integer.parseInt(customerID.getText()), startTimeDate, endTimeDate)) {

                                //alert called to get user confirmation that they wish to save the appointment.
                                AlertHelper.callAlert("save");

                                //if save is confirmed.
                                if (AlertHelper.confirmed) {


                                    //appointment is updated in data base.
                                    AppointmentsQuery.update(appointmentToModify.getAppointment_ID(), title.getText(),
                                            description.getText(), location.getText(), startTimeDate, endTimeDate, LoginController.user,
                                            Integer.parseInt(customerID.getText()),
                                            Integer.parseInt(userID.getText()), ContactQuery.selectContactID(contactNameComboBox.getSelectionModel().getSelectedItem()), type.getValue().toString());

                                    //if the appointment is confirmed to save loads the calender scene is loaded.
                                    if (AlertHelper.confirmed == true) {
                                        //confirmed reset to false for future logic operations
                                        AlertHelper.confirmed = false;

                                        root = FXMLLoader.load(getClass().getResource("/calender.fxml"));
                                        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                        scene = new Scene(root);
                                        stage.setScene(scene);
                                        stage.show();
                                    }
                                }
                            }
                        } else {
                            AlertHelper.callAlert("outside of business hours");
                        }
                    } else {
                        AlertHelper.callAlert("invalid end time");
                    }
                } else
                    AlertHelper.callAlert("invalid customerID");
            } else {
                AlertHelper.callAlert("invalid userID");
            }

        } else {
            AlertHelper.callAlert("check all fields");
        }
    }

    /**
     * This method is called when the cancel button is clicked. It shows a confirmation dialog to the user to make sure
     * they really want to cancel the current action. If confirmed, it closes the current window.
     * @param actionEvent cancel button being clicked.
     * @throws SQLException If an error occurs while performing the database operation.
     */
    @FXML public void cancelButtonClicked(ActionEvent actionEvent) throws SQLException {
        AlertHelper.callAlert("cancel");
        if (AlertHelper.confirmed) {
            AlertHelper.confirmed = false;
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }

    }
}
