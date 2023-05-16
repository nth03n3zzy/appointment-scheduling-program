package models;

import java.time.LocalDateTime;

/**
 * class for Appointment objects used to represent and act as appointments from the database.
 * @author  Anthony Collins.
 */
public class Appointment {
    /**
     * appointment ID for appointments assigned in the database.
     */
    private int appointment_ID; // tables primary key set by DB
    /**
     * declares String to be used as title for appointments.
     */
    private String title;
    /**
     * declares String to be used as description for appointments.
     */
    private String description;
    /**
     * declares String to be used as location for appointments.
     */
    private String location;
    /**
     * declares String to be used as type for appointments.
     */
    private String type;
    /**
     * declares LocaleDateTime to be used as start time for appointments.
     */
    private LocalDateTime start;
    /**
     * declares LocaleDateTime to be used as end time for appointments.
     */
    private LocalDateTime end;
    /**
     * declares LocaleDateTime to be used as created time for appointments.
     */
    private LocalDateTime createDate;
    /**
     * declares String to be used as created by for appointments.
     */
    private String createdBy;
    /**
     * declares LocaleDateTime to be used as last updated time for appointments.
     */
    private LocalDateTime lastUpdate;
    /**
     * declares String to be used as last updated by for appointments.
     */
    private String lastUpdatedBy;
    /**
     * declares a int to be used as the customer ID for appointments
     */
    private int customerId;
    /**
     * declares a int to be used as the user ID for appointments
     */
    private int userId;
    /**
     * declares a int to be used as the contact ID for appointments
     */
    private int contactId;

    /**
     * constructor for adding appointments in app.
     * @param title title of the appointment
     * @param description description of appointment
     * @param location location of appointment
     * @param type type of appointment
     * @param start start time and date of appointment
     * @param end end time and date of the appointment
     * @param createdBy user who created the appointment.
     * @param customerId customer ID of who the appointment is for.
     * @param userId user ID of who created the appointment
     * @param contactId contact ID of the contact for the appointment.
     */
    public Appointment(String title, String description, String location,
                       String type, LocalDateTime start, LocalDateTime end,
                       String createdBy, int customerId, int userId, int contactId) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createdBy = createdBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * cunstructor for pulling appointments from the database.
     * @param appointment_ID appointment ID of the appointment
     * @param description description of appointment
     * @param location location of appointment
     * @param type type of appointment
     * @param start start time and date of appointment
     * @param end end time and date of the appointment
     * @param createdBy user who created the appointment.
     * @param customerId customer ID of who the appointment is for.
     * @param userId user ID of who created the appointment
     * @param contactId contact ID of the contact for the appointment.
     * @param createDate date and time the appointment was created.
     * @param lastUpdate  user who last updated the appointment.
     * @param title title of the appointment.
     * @param lastUpdatedBy who the appointment was last updated by.
     */
    public Appointment(int appointment_ID, String title, String description,
                       String location, String type, LocalDateTime start, LocalDateTime end,
                       LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                       int customerId, int userId, int contactId) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * setter for the appointment title
     * @param title title of the appointment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * setter for the description of the appointment.
     * @param description a description of the appointment.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * setter for the location of the appointment
     * @param location location of the appointment
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * setter for the start time of the appointment.
     * @param start start time and date for the appointment.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * setter for the end time and date of the appointment
     * @param end end time and date of the appointment
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * getter for the appointment ID.
     * @return the appointment ID.
     */
    public int getAppointment_ID() {
        return appointment_ID;
    }

    /**
     * getter for the title of the class.
     * @return the title of the class.
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter for the description of the appointment.
     * @return the description of the appointment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * getter of the location of the appointment.
     * @return the location of the appointment.
     */
    public String getLocation() {
        return location;
    }

    /**
     * getter of the type of the appointment
     * @return the type of the appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * getter of the start time and date of the appointment.
     * @return the start time iand date of the appointment.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * getter of the end time and date of the appointment.
     * @return the end time and date of the appointment.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * getter of the customer ID
     * @return the customer ID.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * getter of the user ID for the appointment.
     * @return the user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * getter for the contact ID
     * @return the contact ID.
     */
    public int getContactId() {
        return contactId;
    }
}
