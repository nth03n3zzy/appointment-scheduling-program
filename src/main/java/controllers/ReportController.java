package controllers;

import helper.AppointmentsQuery;
import helper.ContactQuery;
import helper.TimeHelper;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * report controller provides logic and opertations for the reports scene. scene contains 3 reports
 * one informing the user of the quantity of appointments of a certain type within a selected month.
 * another showing the user a schedule of appointments for a given contact. and one showing the user
 * a display of scheduled appointments for a searched customer.
 */
public class ReportController implements Initializable {

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
     * local object ComboBox for selecting a month..
     */
    @FXML
    ComboBox<String> monthComboBox;
    /**
     * local object ComboBox for selecting a appointment type..
     */
    @FXML
    ComboBox<String> typeComboBox;
    /**
     * local object Label for displaying the quantity of appointments.
     */
    @FXML
    Label quantity;
    /**
     * local object Button used to execute logic of counting the number of a certain type of appointments for a selected month.
     */
    @FXML
    Button compute;
    /**
     * local object ComboBox for selecting a contact.
     */
    @FXML
    ComboBox<String> contactsComboBox;

    /**
     * local object table column for displaying data in a table view.
     */
    @FXML TableColumn c1;
    /**
     * local object table column for displaying data in a table view.
     */
    @FXML TableColumn c2;
    /**
     * local object table column for displaying data in a table view.
     */
    @FXML TableColumn c3;
    /**
     * local object table column for displaying data in a table view.
     */
    @FXML TableColumn c4;
    /**
     * local object table column for displaying data in a table view.
     */
    @FXML TableColumn c5;
    /**
     * local object table column for displaying data in a table view.
     */
    @FXML TableColumn c6;
    /**
     * local object table column for displaying data in a table view.
     */
    @FXML TableColumn c7;
    /**
     * local object table column for displaying data in a table view.
     */
    @FXML TableColumn c8;
    /**
     * local object table column for displaying data in a table view.
     */
    @FXML TableColumn c9;
    /**
     * local object table column for displaying data in a table view.
     */
    @FXML TableColumn c10;
    /**
     * local object table column for displaying data in a table view.
     */
    @FXML TableColumn c11;
    /**
     * local object table column for displaying data in a table view.
     */
    @FXML TableColumn c12;
    /**
     * local object table view  for displaying data of selected contacts scheduled appointments.
     */
    @FXML
    TableView<Appointment> contactScheduleTableView;
    /**
     * local object of a textField for the user to enter a customer ID to pull up a list of
     * that customers appoiuntments
     */
    @FXML
    TextField customerIDTextField;
    /**
     * local object button used to execute logic of searching the entered customers scheduled appointments and displaying them in
     * the customer schedule tableview.
     */
    @FXML Button searchButton;
    /**
     * local object table for displaying a customers schedule.
     */
    @FXML TableView<Appointment> customerScheduleTableView;
    /**
     * list of abbreviated months for the month selection combo box.
     */
    @FXML
    ObservableList<String> listOfMonths = FXCollections.observableArrayList(
            "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
            "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");

    /**
     * list of appointment types for the type comboBox.
     */
    @FXML ObservableList<String> listOfTypes = FXCollections.observableArrayList("Dental",  "General",
            "Coffee Break", "Business Meeting");
    /**
     * list of contacts appointments populated with data when a contact is selected.
     */
    @FXML ObservableList<Appointment> listOfContactsAppointments = FXCollections.observableArrayList();
    /**
     * list of customer appointments populated with data when a customer is searched.
     */
    @FXML ObservableList<Appointment> listOfCustomersAppointments = FXCollections.observableArrayList();


    /**
     * This method sets up the ComboBoxes and populates the contactsComboBox with the list of contacts retrieved
     * from the database.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle used to localize the root object, or null if the root object was not localized.
     * @throws RuntimeException if there is an error retrieving the list of contacts from the database.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monthComboBox.setItems(listOfMonths);
        typeComboBox.setItems(listOfTypes);

        try {
            ContactQuery.selectContactName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        contactsComboBox.setItems(ContactQuery.listOfContactsNames);
    }

    /**
     * method sets the quantity label to display the quantity of appointments for the selected month and type.
     * @param actionEvent copute button is clicked
     * @throws SQLException
     */
   @FXML public void computeButtonClicked(ActionEvent actionEvent) throws SQLException {
       quantity.setText(String.valueOf(AppointmentsQuery.reportQuantityQuery(monthComboBox.getValue(), typeComboBox.getValue())));
    }

    /**
     * method populates the list of contact appointments by searching the dataBase for all the appointments scheduled for the
     * selected contact. it then sets the contact schedule tableview to display the list.
     * @param actionEvent a contact is selected
     * @throws SQLException
     */
    @FXML public void contactSelected(ActionEvent actionEvent) throws SQLException {
        int contactID = ContactQuery.selectContactID(contactsComboBox.getSelectionModel().getSelectedItem());
       listOfContactsAppointments = AppointmentsQuery.getAppointmentsForContact(contactID);
       contactScheduleTableView.setItems(listOfContactsAppointments);

        c1.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        c2.setCellValueFactory(new PropertyValueFactory<>("title"));
        c3.setCellValueFactory(new PropertyValueFactory<>("type"));
        c4.setCellValueFactory(new PropertyValueFactory<>("description"));
        /*columns are updated with strings of formatted times instead of localDateTime objects of the start time from the
       appointment object so that the displayed time is more readable to the user.
        */
        c5.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> p) {
                return new SimpleStringProperty(TimeHelper.formatLocalDateTime.apply(p.getValue().getStart()));
            }
        });

        /*columns are updated with strings of formatted times instead of localDateTime objects of the end time from the
       appointment object so that the displayed time is more readable to the user.
        */
        c6.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> p) {
                return new SimpleStringProperty(TimeHelper.formatLocalDateTime.apply(p.getValue().getEnd()));
            }
        });
        c7.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }

    /**
     * method populates the list of customer appointments based on the customer ID entered in the TextField.
     * the list is then displayed on the customer schedule table view
     * @param actionEvent search button is clicked.
     * @throws SQLException
     */
   @FXML public void customerSearched(ActionEvent actionEvent) throws SQLException {
        listOfCustomersAppointments.clear();

        int customerID = Integer.parseInt(customerIDTextField.getText());

       listOfCustomersAppointments = AppointmentsQuery.getAppointmentsForCustomer(customerID);
       customerScheduleTableView.setItems(listOfCustomersAppointments);

       c8.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
       c9.setCellValueFactory(new PropertyValueFactory<>("title"));
       c10.setCellValueFactory(new PropertyValueFactory<>("type"));
        /*columns are updated with strings of formatted times instead of localDateTime objects of the start time from the
       appointment object so that the displayed time is more readable to the user.
        */
       c11.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> p) {
               return new SimpleStringProperty(TimeHelper.formatLocalDateTime.apply(p.getValue().getStart()));
           }
       });

        /*columns are updated with strings of formatted times instead of localDateTime objects of the end time from the
       appointment object so that the displayed time is more readable to the user.
        */
       c12.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> p) {
               return new SimpleStringProperty(TimeHelper.formatLocalDateTime.apply(p.getValue().getEnd()));
           }
       });



   }

    /**
     * method to exit back to the calender scene.
     * @param actionEvent exit button clicked
     * @throws IOException
     */
   @FXML public  void exitButtonClicked(ActionEvent actionEvent) throws IOException {

       root = FXMLLoader.load(getClass().getResource("/calender.fxml"));
       stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
       scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
   }
}
