package models;

/**
 * class for the contact objects. class not used in project currently. retained for possible use in future updates.
 * @author Anthony Collins.
 */
public class Contacts {
    private int contactId; //
    private String contactName;
    private String contactEmail;

    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }
}
