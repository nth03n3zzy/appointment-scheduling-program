package controllers;

import helper.AlertHelper;
import helper.LoginQuery;
import helper.TimeHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * login controller controls logic of the login screen
 * @author Anthony Collins
 */
public class LoginController implements Initializable {
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
     * local object for the time to be displayed on the login screen.
     */

    @FXML public Label time;
    /**
     * local TextField for entering the username.
     */
    @FXML
     private TextField username;
    /**
     * local TextField for entering the password.
     */
    @FXML private TextField password;
    /**
     * local Label for displaying "username" in french or english depending on user preference.
     */
    @FXML Label userNameLabel;
    /**
     * local Label for displaying "password" in french or english depending on user preference.
     */
    @FXML Label passwordLabel;
    /**
     * local Button for displaying "login" in french or english depending on user preference.
     */
    @FXML
    Button loginButton;
    /**
     * local Text for displaying "Wlecome" in french or english depending on user preference.
     */
    @FXML Text welcomeLabel;
    /**
     * local Text for displaying title in french or english depending on user preference.
     */
    @FXML
    Text titleLabel;
    /**
     * local Text for displaying "please login" in french or english depending on user preference.
     */
    @FXML Text pleaseLoginLabel;

    /**
     * local String for holding the logged in users info for saving which user is performing actions through out the application.
     */
    public static String user;

    /**
     * Method provide logic for checking username and password against acceptable combos from the database
     * if they are correct the user is directed to the calender scene otherwise an alert is displayed in french or english
     * depending on system preference settings, informing the user the credentials they have entered are incorrect.
     * @param event login button clicked.
     */
    @FXML
    private void loginButtonClicked(ActionEvent event){
        try {

            //if statement checking the username and password provided are correct.
            if(LoginQuery.checkLogin(username.getText(), password.getText())) {

                //user is set to the user that is logging in.
                user = username.getText();

                //calender scene is loaded.
                root = FXMLLoader.load(getClass().getResource("/calender.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                try {
                    //alert called informing the user of appointments coming up within the next fifteen minuets.
                    AlertHelper.callAlert("appointment within 15");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the LoginController by setting the user interface elements with the appropriate texts and formats the current date and time.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle containing the localized strings for the user interface elements.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle rb = ResourceBundle.getBundle("language_files/rb");

        if(Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")){
            userNameLabel.setText(rb.getString("User.Name"));
            passwordLabel.setText(rb.getString("Password"));
            loginButton.setText(rb.getString("login"));
            password.setPromptText("password");
            username.setPromptText("User.Name");
            welcomeLabel.setText(rb.getString("Welcome"));
            titleLabel.setText(rb.getString("Appointment.Scheduler"));
            pleaseLoginLabel.setText(rb.getString("Please.Login"));
        }
        // Get the current date and time in UTC.
        Instant instant = Instant.now();

        // Convert the UTC instant to a LocalDateTime object using the system default time zone.
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(instant, zoneId);
        //converts the currentDateTime to a formatted string from the abstract class TimeHelper.
        time.setText(TimeHelper.formatLocalDateTime.apply(currentDateTime));
    }

    }

