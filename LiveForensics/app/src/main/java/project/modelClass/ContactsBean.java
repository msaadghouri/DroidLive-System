package project.modelClass;

import java.io.Serializable;

/**
 * Created by Mohammad-Ghouri on 3/8/17.
 */

public class ContactsBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int contactId;
    private String phoneName;
    private String phoneNumber;

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ContactsBean(int contactId, String phoneName, String phoneNumber) {
        this.contactId = contactId;
        this.phoneName = phoneName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "{\"contactId\":" + "\"" + contactId
                + "\"" + ", \"phoneName\":" + "\"" + phoneName
                + "\"" + ", \"phoneNumber\":" + "\"" + phoneNumber
                + "\"" + "}";
    }
}
