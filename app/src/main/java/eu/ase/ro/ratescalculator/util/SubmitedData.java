package eu.ase.ro.ratescalculator.util;


public class SubmitedData {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String dateToBeContacted;
    private Object receivedObject;

    public SubmitedData(String firstName, String lastName, String phoneNumber,
                        String email, String dateToBeContacted, Object receivedObject) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateToBeContacted = dateToBeContacted;
        this.receivedObject = receivedObject;
    }

    @Override
    public String toString() {
        return "SubmitedData{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", dateToBeContacted='" + dateToBeContacted + '\'' +
                ", receivedObject=" + receivedObject +
                '}';
    }
}
