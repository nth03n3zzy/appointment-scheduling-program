package com.example.c195project;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 The Main class is the entry point for the application and contains the start and main method.
 It extends the JavaFX Application class and overrides the start method to load the loginScreen.fxml file
 and display it in a JavaFX stage.
 The main method initializes a JDBC connection, launches the JavaFX application, and closes the JDBC connection
 when the application is closed.
 @author Anthony Collins.
 @version 1.0
 */
public class Main extends Application {
        @Override
        /**
         Overrides the start method of the JavaFX Application class to load the loginScreen.fxml file and display it in a JavaFX stage.
         The stage is then displayed to the user.
         @param stage The primary stage for this application.
         @throws IOException if the loginScreen.fxml file cannot be loaded.
         */
        public void start(Stage stage) throws IOException {

            try {

                Parent root = FXMLLoader.load(getClass().getResource("/loginScreen.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("Super Appointment Scheduler");
                stage.setScene(scene);
                stage.show();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    /**
     The main method initializes a JDBC connection, launches the JavaFX application, and closes the JDBC connection
     when the application is closed.
     @param args command line arguments.
     @throws SQLException if a database access error occurs.
     */
        public static void main(String[] args) throws SQLException {

            JDBC.openConnection();
            launch();

            JDBC.closeConnection();
        }
    }

