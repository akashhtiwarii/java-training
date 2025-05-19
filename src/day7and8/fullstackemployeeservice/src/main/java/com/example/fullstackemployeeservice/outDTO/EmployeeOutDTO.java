package com.example.fullstackemployeeservice.outDTO;

/**
 * Data Transfer Object (DTO) used to send employee information to the client.
 * <p>
 * This class represents the structure of employee data in API responses.
 * It does not include any validation or persistence logic.
 */
public class EmployeeOutDTO {

    /**
     * Email address of the employee.
     */
    private String email;

    /**
     * First name of the employee.
     */
    private String firstName;

    /**
     * Last name of the employee.
     */
    private String lastName;

    /**
     * Phone number of the employee.
     */
    private String phoneNumber;

    /**
     * Role of the employee (e.g., ADMIN, EMPLOYEE, HR).
     */
    private String role;

    /**
     * Default constructor.
     */
    public EmployeeOutDTO() {
    }

    /**
     * Parameterized constructor to initialize all fields.
     *
     * @param email       the employee email
     * @param firstName   the employee's first name
     * @param lastName    the employee's last name
     * @param phoneNumber the employee's phone number
     * @param role        the employee's role
     */
    public EmployeeOutDTO(String email, String firstName, String lastName, String phoneNumber, String role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    /**
     * Returns the employee's email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the employee's email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the employee's first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the employee's first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the employee's last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the employee's last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the employee's phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the employee's phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the employee's role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the employee's role.
     *
     * @param role the role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return string representation of the employee
     */
    @Override
    public String toString() {
        return "EmployeeOutDTO{" +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
