package controllers;

import helper.AlertHelper;
import helper.CountryAndDivisionQuery;
import helper.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * The EditCustomerController class implements the logic for editing Customers in the system.
 * It handles the user's input and updates the database accordingly.
 * @author Anthony Collins
 */
public class EditCustomerController implements Initializable {

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
     * local object Customer used to load the customer that was selected in the calender scene.
     */
    private static Customer customerToModify;
    /**
     * local object creates a textField to display the ID of the customer.
     */
    @FXML
    TextField customerID;
    /**
     * local object creates a textField for the user to input the name of the customer.
     */

    @FXML TextField customerName;
    /**
     * local object creates a textField for the user to input the address of the customer.
     */
    @FXML TextField customerAddress;
    /**
     * local object creates a textField for the user to input the phone number of the customer.
     */
    @FXML
    TextField customerPhoneNumber;
    /**
     * local object creates a textField for the user to input the postal code of the customer.
     */
    @FXML TextField customerPostalCode;
    /**
     * local object creates a ComboBox for the user to select the country of the customer.
     */
    @FXML
    ComboBox country;
    /**
     * local object creates a ComboBox for the user to select the province of the customer.
     */
    @FXML ComboBox stateProvince;

    /**
     * Initializes a int to be used as the division ID for input of the customer in the dataBase.
     */
    private int divisionID;

    /**
     * Initializes a observable list of US states for the stateProvince ComboBox.
     */
    @FXML
    ObservableList<String> listOFUSStates = FXCollections.observableArrayList();
    /**
     * Initializes a observable list of US states for the stateProvince ComboBox.
     */
    @FXML ObservableList<String> listOfCANProvinces = FXCollections.observableArrayList();
    /**
     * Initializes a observable list of UK countires for the stateProvince ComboBox.
     */
    @FXML ObservableList<String> listOfUKProvinces = FXCollections.observableArrayList();
    /**
     * Initializes a observable list of  countries for the country ComboBox.
     */
    @FXML ObservableList<String> listOfCountries = FXCollections.observableArrayList();

    /**
     * The initialize method sets up the country and state/province selection boxes in the UI.
     * as well as filling out all the fields with the data of the customer that was selected.
     * @param url The URL of the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The ResourceBundle that contains localizable strings.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //adds the pertinant countries to the list of countries.
        listOfCountries.addAll("US", "UK", "CAN");

        //sets the country combo box with the lis of countries.
        country.setItems(listOfCountries);

        //popoulates list of US states.
        listOFUSStates.addAll("AL", "AK","AZ","AR","CA","CO","CT", "DC", "DE","FL","GA","HI","ID","IL",
                "IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY",
                "NC", "ND","OH","OK","OR","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY");

        //populates list of canadian provinces and territories.
        listOfCANProvinces.addAll("AB","BC","MB","NB","NL","NT","NS","NU","ON","PE","QC","SK","YT");

        //populates list of UK countries.
        listOfUKProvinces.addAll("ENG","SCT","NIR","WLS");

        //provides the logic for a country having to be selected before the list of states or provinces will be added to the state province comboBox
        country.valueProperty().addListener((observable, oldValue, newValue) ->{
            if( newValue.equals("US")){
                stateProvince.setItems(listOFUSStates);
                country.setValue("US");
            }
            if( newValue.equals("CAN")){
                stateProvince.setItems(listOfCANProvinces);
                country.setValue("CAN");
            }
            if( newValue.equals("UK")){
                stateProvince.setItems(listOfUKProvinces);
                country.setValue("UK");
            }
        });

        //sets the customer to modify to the selected customer
        customerToModify = CalenderController.modifyCustomer;

        //sets fields to the values of the customer that was selected.
        customerID.setText(String.valueOf(customerToModify.getCustomerID()));
        customerName.setText(customerToModify.getName());
        customerAddress.setText(customerToModify.getAddress());
        customerPhoneNumber.setText(customerToModify.getPhoneNumber());
        customerPostalCode.setText(customerToModify.getPostalCode());
        try {
            country.setValue(CountryAndDivisionQuery.pullCountry(customerToModify.getDivisionId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            String divisionFullName = (CountryAndDivisionQuery.pullDivision(customerToModify.getDivisionId()));
            stateProvince.setValue(CountryAndDivisionQuery.convertToAbbreviation(divisionFullName));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is called when the save button is clicked. It checks if all the required fields are filled out, and if so,
     * it retrieves the division ID based on the selected state/province, prompts the user for confirmation, and inserts the
     * customer data into the database. It then loads the calendar scene and sets the scene to display it.
     * @param actionEvent clicking the save button.
     * @throws SQLException if there is an error executing the SQL query
     * @throws IOException if there is an error loading the calendar.fxml file
     */
    public void saveButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {

        //checking all fields have data in them
        if (!customerName.getText().isEmpty() && !customerAddress.getText().isEmpty() && !customerPostalCode.getText().isEmpty() &&
                !customerPhoneNumber.getText().isEmpty() && !(stateProvince.getSelectionModel().getSelectedItem() == null)) {

            //creates a string of the full name of the state or province created from the three letter selection
            //in the stateProvince ComboBox.
            String divisionIDSearch = CountryAndDivisionQuery.getDivisionName(stateProvince.getSelectionModel().getSelectedItem().toString());

            //sets the int divisonID to the corresponfing divisionID from the state name pulled from the database.
            divisionID = CountryAndDivisionQuery.pullDivisonID(divisionIDSearch);

            //alert confirming the user wishes to save the canges to the customer.
            AlertHelper.callAlert("save");

            //if confirmed
            if (AlertHelper.confirmed == true) {

                //confirmed logic reset to false for future logic operations.
                AlertHelper.confirmed = false;

                //update time set to the instant.
                LocalDateTime updateTime = LocalDateTime.now();

                //customer updated in Database.
                CustomerQuery.update(customerName.getText(), customerAddress.getText(), customerPostalCode.getText(),
                        customerPhoneNumber.getText(), customerToModify.getCreateDate(), customerToModify.getCreatedBy(),
                        updateTime, LoginController.user, divisionID, customerToModify.getCustomerID());

                //calender scene is reloaded
                root = FXMLLoader.load(getClass().getResource("/calender.fxml"));
                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } else {
            //alert telling user to check all fields called if a blank field was detected.
            AlertHelper.callAlert("check all fields");
        }
    }

    /**
     * This method is called when the cancel button is clicked. It shows a confirmation dialog to the user to make sure
     * they really want to cancel the current action. If confirmed, it closes the current window.
     * @param actionEvent cancel button being clicked.
     * throws SQLException If an error occurs while performing the database operation.
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
