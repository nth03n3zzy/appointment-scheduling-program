package models;

import java.time.LocalDateTime;

/**
 * Class for customer objects used to represent customers from the database.
 * @author Anthony Collins.
 */
public class Customer {
    /**
     * int representing the customers ID.
     */
    private int customerID;
    /**
     * String for the customers name.
     */
    private String name;
    /**
     * String for the customers address.
     */
    private String address;
    /**
     * String for te customers postal code.
     */
    private String postalCode;
    /**
     * String for the customers phone number.
     */
    private String phoneNumber;
    /**
     * localDateTime for the date and time the customer was created.
     */
    private LocalDateTime createDate;
    /**
     * String for who the customer was created by.
     */
    private String createdBy;
    /**
     * LocalDateTime for the date and time the customer was last updated.
     */
    private LocalDateTime lastUpdate;
    /**
     * String for who last updated the customer.
     */
    private String lastUpdatedBy;
    /**
     * int for the division ID.
     */
    private int divisionId;


    /**
     * constructor for the customer.
     * @param customerID ID number of the customer.
     * @param name name of the customer.
     * @param address address of the customer.
     * @param postalCode postal code of the customer.
     * @param phoneNumber phone number of the customer.
     * @param createdBy the user who created the customer.
     * @param lastUpdate when the customer was last updated.
     * @param lastUpdatedBy who last updated the customer.
     * @param divisionId division ID of the customer.
     */
    public Customer(int customerID, String name, String address, String postalCode,
                    String phoneNumber, String createdBy, LocalDateTime lastUpdate,
                    String lastUpdatedBy, int divisionId) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    /**
     * constructor for pulling customer from the database.
     * @param customerID Id of the customer.
     * @param name name of the customer.
     * @param address address of the customer.
     * @param postalCode postal code of the customer.
     * @param phoneNumber phone number of the customer.
     * @param createDate the date the customer was created.
     * @param createdBy who created the customer.
     * @param lastUpdate the last time the customer was updated.
     * @param lastUpdatedBy who was the last person to update the customer.
     * @param divisionId the division ID of the customer.
     */
    public Customer(int customerID, String name, String address, String postalCode,
                    String phoneNumber, LocalDateTime createDate, String createdBy,
                    LocalDateTime lastUpdate,
                    String lastUpdatedBy, int divisionId) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    /**
     * setter for the customer name
     * @param name name of the customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for the customer ID
     * @return the customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     *  getter for the customers name
     * @return the customers name.
     */
    public String getName() {
        return name;
    }

    /**
     * getter for the customers address
     * @return the customers address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * getter for the customers postal code
     * @return the customers postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * getter for the customers phone number.
     * @return the customers phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * getter for the create date
     * @return the create date.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * getter for the user who created the customer
     * @return the user who created the customer.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * getter for the last date and time the customer was updated.
     * @return the last date and time the customer was updated.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * getter for customers division ID
     * @return customers division ID,
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * gettter for who last updated the customer
     * @return last updated by
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
}
