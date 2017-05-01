package ru.stqa.pft.addressbook.model;

public class ContactData {
    private int id;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String mobilePhone;
    private final String workPhone;
    private final String email1;
    private final String email2;
    private final String email3;

    public ContactData(String firstName, String lastName, String address, String mobilePhone, String workPhone, String email1, String email2, String email3) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.mobilePhone = mobilePhone;
        this.workPhone = workPhone;
        this.email1 = email1;
        this.email2 = email2;
        this.email3 = email3;
    }

    public ContactData(int id, String firstName, String lastName, String address, String mobilePhone, String workPhone, String email1, String email2, String email3) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.mobilePhone = mobilePhone;
        this.workPhone = workPhone;
        this.email1 = email1;
        this.email2 = email2;
        this.email3 = email3;
    }

    public ContactData(String firstName, String lastName) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = null;
        this.mobilePhone = null;
        this.workPhone = null;
        this.email1 = null;
        this.email2 = null;
        this.email3 = null;
    }

    public ContactData(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = null;
        this.mobilePhone = null;
        this.workPhone = null;
        this.email1 = null;
        this.email2 = null;
        this.email3 = null;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public String getEmail1() {
        return email1;
    }

    public String getEmail2() {
        return email2;
    }

    public String getEmail3() {
        return email3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (id != that.id) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        return lastName != null ? lastName.equals(that.lastName) : that.lastName == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
