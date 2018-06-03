package evv.chessportal.model.userservice;

public class PersonDetails {

    private String firstName;
    private String surName;
    private String email;
    private String phoneNumber;

    public PersonDetails(String firstName, String surName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
