package helper;

import controllers.CalenderController;
import controllers.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * class holds all alerts to be called on throughout use of the program
 * @author Anthony Collins
 */
public abstract class AlertHelper  {
    /**
     * boolean confirmed used throughout program to check if the user confirmed the desired actiong e.g
     * logout, delete, modify, save, cancel.
     */
    public static boolean confirmed = false;

    /**
     * major method uses switch case when called to call correct alert based on title string.
     * @param alert alert passed to the method based on the reason the alerts has been called.
     * @throws SQLException
     */
    public static void callAlert(String alert) throws SQLException {
        ResourceBundle rb = ResourceBundle.getBundle("language_files/rb", Locale.getDefault());

        switch (alert) {
            case ("save") -> {
                Alert save = new Alert(Alert.AlertType.CONFIRMATION);
                save.setTitle(rb.getString("save.title"));
                save.setHeaderText(rb.getString("save.header"));
                save.setContentText(rb.getString("save.content"));
                ButtonType saveButton = new ButtonType(rb.getString("button.save"));
                ButtonType cancelButton = new ButtonType(rb.getString("save.button.cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
                save.getButtonTypes().setAll(saveButton, cancelButton);
                Optional<ButtonType> result = save.showAndWait();
                if (result.get() == saveButton) {
                    confirmed = true;
                }

            }

            case ("cancel") -> {
                Alert cancel = new Alert(Alert.AlertType.CONFIRMATION);
                cancel.setTitle("Cancel?");
                cancel.setHeaderText("Do You Wish To Cancel?");
                cancel.setContentText("are you sure you wish to cancel?");
                ButtonType confirmButton = new ButtonType("Yes cancel");
                ButtonType noCancel = new ButtonType("NO I don't want to cancel");
                cancel.getButtonTypes().setAll(confirmButton, noCancel);
                Optional<ButtonType> result = cancel.showAndWait();
                if (result.get() == confirmButton) {
                    try {
                        confirmed = true;
                        FXMLLoader loader = new FXMLLoader(AlertHelper.class.getResource("/calender.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            case ("invalid end time") -> {
                Alert error = new Alert((Alert.AlertType.ERROR));
                error.setTitle("Invalid end appointment time");
                error.setHeaderText("Invalid end appointment time!");
                error.setContentText("the end time of your appointment must be after the start time" +
                        "please double check the end time of your appointment");
                confirmed = false;
                error.showAndWait();
            }
            case ("invalid login") -> {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle(rb.getString("invalidLogin"));
                error.setHeaderText(rb.getString("invalidLogin.header"));
                error.setContentText(rb.getString("invalidLogin.content"));
                error.showAndWait();
            }
            case ("no selection") -> {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("No Selection");
                error.setHeaderText("No Selection Made");
                error.setContentText("In order to modify / delete a customer or appointment you must first make a selection from the table");
                error.showAndWait();
            }
            case ("invalid customerID") -> {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("INVALID CUSTOMER ID");
                error.setHeaderText("Invalid Customer ID");
                error.setContentText("The appointment could not be made because you entered a invalid customer ID." +
                        " Please enter a valid customer ID and try again.");
                error.showAndWait();
            }
            case ("invalid userID") -> {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("INVALID USER ID");
                error.setHeaderText("Invalid User ID");
                error.setContentText("The appointment could not be made because you entered a invalid user ID" +
                        " Please enter a valid user ID and try again.");
                error.showAndWait();

            }
            case ("outside of business hours") -> {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("INVALID APPOINTMENT TIME");
                error.setHeaderText("Invalid appointment time");
                error.setContentText("the appointment could not be made it is not between 8AM - 10PM EST which is "
                + TimeHelper.convertFromESTToSystemDefault() + " your time.");
                error.showAndWait();

            }
            case("check all fields") -> {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Check Fields");
                error.setHeaderText("Please check all fields");
                error.setContentText("please ensure all fields are filled out correctly");
                error.showAndWait();
            }
            case("overlapping appointments") -> {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("OVERLAPPING APPOINTMENTS");
                error.setHeaderText("Overlapping appointments");
                error.setContentText("the appointment your scheduling at time " + TimeHelper.formatLocalDateTime.apply(TimeHelper.convertFromUTCToSystemDefault(AppointmentsQuery.start)) + " - " + TimeHelper.formatLocalDateTime.apply(TimeHelper.convertFromUTCToSystemDefault(AppointmentsQuery.end)) +
                        " overlap with an appointment " + AppointmentsQuery.appointmentID + " scheduled for " + CustomerQuery.selectCustomerName(AppointmentsQuery.CustomerID) +
                        " at the times of " + TimeHelper.formatLocalDateTime.apply(TimeHelper.convertFromUTCToSystemDefault(AppointmentsQuery.overlapStart)) + " - " + TimeHelper.formatLocalDateTime.apply(TimeHelper.convertFromUTCToSystemDefault(AppointmentsQuery.overlapEnd))+  ".");
                error.showAndWait();

            }
            case("invalid search") -> {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("INVALID SEARCH");
                error.setHeaderText("Invalid Search");
                error.setContentText("the number you have searched does not match any appointment ID or customerID with an appointment on file.");
                error.showAndWait();

            }
            case ("deleteAppointment") -> {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("ARE YOU SURE?");
                confirm.setHeaderText("Are you sure?");
                confirm.setContentText("are you sure you wish to delete  " + CustomerQuery.selectCustomerName(CalenderController.modifyAppointment.getCustomerId()) +
                        "'s appointment for " + CalenderController.modifyAppointment.getType() + " on " + TimeHelper.formatLocalDateTime.apply(CalenderController.modifyAppointment.getStart()) + " ? ");
                ButtonType deleteButton = new ButtonType("DELETE");
                ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                confirm.getButtonTypes().setAll(deleteButton, cancelButton);
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.get() == deleteButton) {
                    confirmed = true;
                } else {
                    confirmed = false;
                }

            }
            case ("appointment deleted") -> {
                Alert notify = new Alert(Alert.AlertType.WARNING);
                notify.setTitle("THE APPOINTMENT HAS BEEN DELETED");
                notify.setHeaderText("The Appointment Has Been Deleted");
                notify.setContentText(CustomerQuery.selectCustomerName(CalenderController.modifyAppointment.getCustomerId()) + "'s appointment for " +
                        CalenderController.modifyAppointment.getType() + " with appointment Id# " + CalenderController.modifyAppointment.getAppointment_ID() + " on " + TimeHelper.formatLocalDateTime.apply(CalenderController.modifyAppointment.getStart()) +
                        " has been deleted.");
                notify.showAndWait();
            }
            case ("delete customer") -> {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("ARE YOU SURE?");
                confirm.setHeaderText("Are you sure?");
                confirm.setContentText("are you sure you wish to delete  " + CustomerQuery.selectCustomerName(CalenderController.modifyCustomer.getCustomerID()) +
                        " as a customer and all of their scheduled appointments?");
                ButtonType deleteButton = new ButtonType("DELETE");
                ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                confirm.getButtonTypes().setAll(deleteButton, cancelButton);
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.get() == deleteButton) {
                    confirmed = true;
                } else {
                    confirmed = false;
                }
            }
            case ("customer deleted") -> {
                Alert notify = new Alert(Alert.AlertType.WARNING);
                notify.setTitle("Customer Deleted");
                notify.setHeaderText("the customer has been deleted");
                notify.setContentText(CalenderController.modifyCustomer.getName() + " and all of their appointments have been deleted from the database.");
                notify.showAndWait();
            }
        case ("appointment within 15") -> {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("APPOINTMENT WARNING");
                warning.setHeaderText("upcoming appointments!");
                warning.setContentText(TimeHelper.appointmentWithin15Min());
                warning.showAndWait();
        }
            case ("logout") -> {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("ARE YOU SURE?");
                confirm.setHeaderText("Are you sure?");
                confirm.setContentText("are you sure you " + LoginController.user + " wish to logout ? ");
                ButtonType deleteButton = new ButtonType("YES, I want to logout");
                ButtonType cancelButton = new ButtonType("no please I don't want to logout!", ButtonBar.ButtonData.CANCEL_CLOSE);
                confirm.getButtonTypes().setAll(deleteButton, cancelButton);
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.get() == deleteButton) {
                    confirmed = true;
                } else {
                    confirmed = false;
                }

            }
        }
    }
}
