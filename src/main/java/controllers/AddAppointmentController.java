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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

/**
 * The AddAppointmentController class is a controller for the Add Appointment view.
 * It allows users to add a new appointment to the database, including the appointment's title,
 * description, location, start and end times, customer ID, user ID, contact name, and type.
 * it implements the Initializable interface.
 * @author Anthony Collins
 * @version 1.0
 */

public class AddAppointmentController implements Initializable {
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
    @FXML private TextField title;
    /**
     * local object creates a textArea for the user to input the description of the appointment.
     */
    @FXML private TextArea description;
    /**
     * local object creates a textField for the user to input the location of the appointment.
     */
    @FXML private TextField location;
    /**
     * local object creates a textField for the user to input the customer's ID for the appointment.
     */
    @FXML private TextField customerID;
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
     * local object creates a ComboBox for the user to select the contact  for the appointment.
     */

    @FXML public ComboBox<String> contactNameComboBox;

    /**
     * Initializes the appointment form with default values for the start and end times, appointment type, and contact names.
     * @param url the URL to initialize the appointment form
     * @param resourceBundle the resource bundle used to initialize the appointment form
     * @throws RuntimeException if there is a SQL exception when attempting to select contact names, customer IDs, or user IDs
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        //start hour spinner initial value of 8 and the range is implemented.
        startHourValueFactory.setValue(8);
        startHour.setValueFactory(startHourValueFactory);

        //start minute spinner set with a range of 0 - 59.
        SpinnerValueFactory<Integer> startMinuetValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59);

        //start minute spinner initial value of 0 and the range is implemented.

        startMinuetValueFactory.setValue(0);
        startMinuet.setValueFactory(startMinuetValueFactory);

        //end hour spinner set with a range of 0 - 23.

        SpinnerValueFactory<Integer> endHourValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23);

        //end hour spinner initial value of 8 and the range is implemented.

        endHourValueFactory.setValue(8);
        endHour.setValueFactory(endHourValueFactory);

        //end minute spinner set with a range of 0 - 59.

        SpinnerValueFactory<Integer> endMinuetValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59);

        //end minute spinner initial value of 0 and the range is implemented.

        endMinuetValueFactory.setValue(0);
        endMinuet.setValueFactory(endMinuetValueFactory);

        //start date set to the current date.
        startDate.setValue(LocalDate.now());




    }


    /**
     * This method is called when the save button is clicked in the Add Appointment form.
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

            //If statement checking if all fields are filled out. if not an alert is called.
            if (!title.getText().isEmpty() && !description.getText().isEmpty() && !location.getText().isEmpty() &&
                    !customerID.getText().isEmpty() && !userID.getText().isEmpty() && !(contactNameComboBox.getSelectionModel().getSelectedItem() == null) &&
                    !(type.getSelectionModel().getSelectedItem() == null)) {

                //if statement checking if user ID entered is a valid user ID. if not an alert is called.
                try {
                    if (UserQuery.listOfUserId.contains(Integer.parseInt(userID.getText()))) {
                }
                }catch (NumberFormatException e) {
                        AlertHelper.callAlert("invalid userID");
                    }


                if (UserQuery.listOfUserId.contains(Integer.parseInt(userID.getText()))) {

                    try {
                        if ((CustomerQuery.listOfCustomerID.contains(Integer.parseInt(customerID.getText())))) {
                        }
                    }catch (NumberFormatException e) {
                        AlertHelper.callAlert("invalid customerID");
                    }

                    //if statement checking if customerId entered is valid. if not an alert is called.
                    if ((CustomerQuery.listOfCustomerID.contains(Integer.parseInt(customerID.getText())))) {

                        /*grabbing the entered start time from the start hour and start minute spinners.
                        and turning it into a local date time.
                         */
                        LocalTime startTime = LocalTime.of(startHour.getValue(), startMinuet.getValue());
                        LocalDate startDate = this.startDate.getValue();
                        LocalDateTime startTimeDate = LocalDateTime.of(startDate, startTime);

                         /*grabbing the entered end time from the end hour and end minute spinners.
                        and turning it into a local date time.
                         */
                        LocalTime endTime = LocalTime.of(endHour.getValue(), endMinuet.getValue());
                        LocalDate endDate = startDate;
                        LocalDateTime endTimeDate = LocalDateTime.of(endDate, endTime);

                        //if statement checking if the end time is after the start time if not an alert is called.
                        if (startTimeDate.isBefore(endTimeDate)) {

                            //if statement checking if start time and end time are within business hours of 8AM - 10PM EST. If not an alert is called.
                            if (TimeHelper.withinBusinessHoursCheck.test(startTimeDate) && TimeHelper.withinBusinessHoursCheck.test(endTimeDate)) {

                                //calls method to convert start time to UTC.
                            startTimeDate = TimeHelper.convertFromSystemDefaultToUTC(startTimeDate);

                                //calls method to convert end time to UTC.
                                endTimeDate = TimeHelper.convertFromSystemDefaultToUTC(endTimeDate);

                                //if statement calling method to check if the customer the appointment is being scheduled for has an existing appointment
                                //within the new appointment time.
                                if (AppointmentsQuery.checkForOverlappingAddAppointments(Integer.parseInt(customerID.getText()), startTimeDate, endTimeDate)) {

                                    //calls alert to get confirmation from the user if they wish to save the appointment.
                                    AlertHelper.callAlert("save");

                                    //if statement executing if the user confirms the save.
                                    if (AlertHelper.confirmed) {

                                        //a LocalDateTime for the current instant is created to be used as the create time for the appointment.
                                        LocalDateTime createTime = TimeHelper.convertFromSystemDefaultToUTC(LocalDateTime.now());

                                        //appoinment object is created with the data in the form.
                                        AppointmentsQuery.insert(title.getText(),
                                                description.getText(), location.getText(), startTimeDate, endTimeDate, createTime, LoginController.user, createTime, LoginController.user,
                                                Integer.parseInt(customerID.getText()),
                                                Integer.parseInt(userID.getText()), ContactQuery.selectContactID(contactNameComboBox.getSelectionModel().getSelectedItem()), type.getValue().toString());

                                        //if statement executed if the user confirmed they wish to save the appointment.
                                        if (AlertHelper.confirmed == true) {

                                            //confirmed boolean reset to false for future logic operations.
                                            AlertHelper.confirmed = false;

                                            //calender scene is reloaded.
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


                        } else {
                            AlertHelper.callAlert("invalid customerID");
                        }

                    } else {
                        AlertHelper.callAlert("invalid userID");
                    }


            }else AlertHelper.callAlert("check all fields");
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



