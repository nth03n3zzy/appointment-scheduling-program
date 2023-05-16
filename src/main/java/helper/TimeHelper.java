package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * class used to help with logic and conversions dealing with time zones and appointment times, and time formatting
 * @author Anthony Collins
 */
public abstract class TimeHelper {
    /**
     * date time formatter for formatting time throughout the application
     */
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");

    /**
     * method checking if an appointment is within fifteen minutes if when it is called on,
     * @return returns a list of appointments within fifteen minutes including the customer name,
     * appointment ID start time of the appointment and how many minutes that is in
     * which is passed into an alert. or returns an alert informing
     * the user there are no appointments within the next fifteen minutes.
     * @throws SQLException
     */
    public static String appointmentWithin15Min() throws SQLException {
        String s = "";

        //local date time of now established.
        LocalDateTime now = LocalDateTime.now();
        //now is converted to UTC because all times in the database are in UTC.
        now = convertFromSystemDefaultToUTC(now);

        //creating a localDateTime of fifteen minutes in the future.
        LocalDateTime fifteenMinFromNow = now.plusMinutes(15);

        //database is checked to see if any appointments are between now and fifteen minutes from now.
        String sql = "SELECT Customer_ID, Start, End FROM appointments WHERE Start >= ? AND Start < ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(now));
        ps.setTimestamp(2, Timestamp.valueOf(fifteenMinFromNow));
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            //if an appintment is found the start time is found.
           Timestamp appointmentTimeStamp =  rs.getTimestamp("Start");
           LocalDateTime appointmentTime = appointmentTimeStamp.toLocalDateTime();
           // and how many minuets till that appointment is determined.
           int appointmentMinuets = appointmentTime.getMinute();
            if(appointmentMinuets < now.getMinute()){
                appointmentMinuets += 60;
            }

            int timeTillAppt = appointmentMinuets - now.getMinute();
            System.out.println("time till appt = " + appointmentMinuets + " - " + now.getMinute() + " = " + (appointmentMinuets - now.getMinute()));

            // a string is returned with the customer name, appointment ID start time of the appointment and how many minuets that is in
            //which is passed into an alert.
            return s = CustomerQuery.selectCustomerName(rs.getInt("Customer_ID")) + " has an appointment with the appointment ID#  " +
                    AppointmentsQuery.returnAppointmentID(appointmentTime, rs.getInt("Customer_ID")) + " which starts at " + TimeHelper.formatLocalDateTime.apply(appointmentTime) +
                    " which is in " + timeTillAppt + " minuets!";
        }
        return "no appointments within the next 15 Minuets.";
    }


    /**
     * this lambda expression uses  A Predicate functional interface that checks if a given LocalDateTime
     * falls within business hours in Eastern Standard Time (EST) time zone.The predicate converts the input
     * LocalDateTime to EST time zone using the system default time zone and checks if it falls within the
     * range of 8 AM to 10 PM EST.
     */
    public static Predicate<LocalDateTime> withinBusinessHoursCheck =
            dateTime -> {
                ZoneId systemZone = ZoneId.systemDefault();
                ZoneId estZone = ZoneId.of("America/New_York");
                ZonedDateTime zonedDateTime = dateTime.atZone(systemZone).withZoneSameInstant(estZone);
                int hour = zonedDateTime.getHour();
                return (hour >= 8 && hour <= 22);
            };



    /**
     * method to convert from UTC to the systemDefault timeZone.
     * @param input a LocalDateTime that is EST.
     * @return a LocaleDateTime that is in system default time.
     */
    public static LocalDateTime convertFromUTCToSystemDefault(LocalDateTime input) {

        ZonedDateTime inputZDT = input.atZone(ZoneId.of("UTC"));
        ZonedDateTime UTCtoLocal = inputZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime output = UTCtoLocal.toLocalDateTime();

        return output;
    }

    /**
     * method to convert from EST to system default time specifically used to check if an appointment is between
     * 8AM - 10PM in EST and if it is not convert 8AM - 10PM EST to the system default time zone.
     * @return a String of the system default timezone equivalent of 8AM-10PM EST.
     */
    public static String convertFromESTToSystemDefault() {

        ZonedDateTime start = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        ZonedDateTime startToUser = start.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime output = startToUser.toLocalDateTime();

        ZonedDateTime end = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));
        ZonedDateTime endToUser = end.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime endOutput = endToUser.toLocalDateTime();

        String startStr = output.format(DateTimeFormatter.ofPattern("h:mm a"));
        String endStr = endOutput.format(DateTimeFormatter.ofPattern("h:mm a"));

        return startStr + " - " + endStr;



    }

    /**
     * method converts from system default time to UTC.
     * @param input LocalDate time in the system default time zone.
     * @return a LocaleDateTime in UTC.
     */
    public static LocalDateTime convertFromSystemDefaultToUTC(LocalDateTime input) {


        ZonedDateTime inputZDT = input.atZone(ZoneId.systemDefault());
        ZonedDateTime UTCZDT = inputZDT.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime UTC = UTCZDT.toLocalDateTime();

        System.out.println(UTC);

        return UTC;
    }

    /**
     * A lambda expression that formats a given LocalDateTime object as a string using a specified format.
     * The function takes a LocalDateTime input and formats it as a string using a DateTimeFormatter object
     * specified by the "formatter" variable. The formatted string is returned as the output.
     */
    public static Function<LocalDateTime, String> formatLocalDateTime =
            input -> input.format(formatter);


}
