package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * class for tracking login attempts of the program and appending them to the login_activity.txt.
 * @author Anthony Collins.
 */
public class LoginTracker {
    /**
     * establishing the name of the file.
     */
        private static final String FILENAME = "login_activity.txt";

    /**
     * method to record login attempts in the program.
     * has logic to record who attempted to login, was it succesful and format it in a string with the
     * date and time of the attempt.
     * @param username inputted username by user.
     * @param success boolean reporting if the login attempt was successful or not.
     */
    public static void trackLoginAttempt(String username, boolean success) {
            LocalDateTime now = LocalDateTime.now();
            String timestamp = now.format(DateTimeFormatter.ISO_DATE_TIME);
            String status = success ? "SUCCESS" : "FAILED";
            String logEntry = String.format("%s - User %s attempted to login with status %s %s%n", timestamp, username, status, ZoneId.systemDefault());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
                writer.write(logEntry);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
