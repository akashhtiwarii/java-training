package com.example.fullstackemployeeservice.inDTO;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object used for receiving employee input data in create or update operations.
 * <p>
 * Contains validation annotations to ensure the integrity of user-provided data.
 */
public class EmployeeInDTO {

    /**
     * The employee's email address.
     * Must be a non-blank valid Gmail address.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Only Gmail addresses are accepted")
    private String email;

    /**
     * The employee's first name.
     * Must be a non-blank string containing 2 to 30 alphabetic characters, spaces, hyphens, or apostrophes.
     */
    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Za-z\\s-']{2,30}$", message = "First name should be valid")
    private String firstName;

    /**
     * The employee's last name.
     * Must be a non-blank string containing 2 to 30 alphabetic characters, spaces, hyphens, or apostrophes.
     */
    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[A-Za-z\\s-']{2,30}$", message = "Last name should be valid")
    private String lastName;

    /**
     * The employee's phone number.
     * Should be 10 to 15 digits and may start with a plus sign.
     */
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number should be valid")
    private String phoneNumber;

    /**
     * The employee's role in the organization.
     * Must be one of: ADMIN, EMPLOYEE, or HR.
     */
    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(ADMIN|EMPLOYEE|HR)$",
            message = "Role must be one of: ADMIN, EMPLOYEE, HR")
    private String role;

    /**
     * The employee's department.
     * Must be a non-blank string.
     */
    @NotBlank(message = "Department is required")
    @Pattern(regexp = "^[A-Za-z\\s-&]{2,50}$", message = "Department should be valid")
    private String department;

    /**
     * The employee's salary.
     * Must be a positive number.
     */
    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than zero")
    private Double salary;

    /**
     * Default constructor.
     */
    public EmployeeInDTO() {
    }

    /**
     * Constructs an EmployeeInDTO with the specified details.
     *
     * @param email       the email of the employee
     * @param firstName   the first name of the employee
     * @param lastName    the last name of the employee
     * @param phoneNumber the phone number of the employee
     * @param role        the role of the employee
     * @param department  the department of the employee
     * @param salary      the salary of the employee
     */
    public EmployeeInDTO(String email, String firstName, String lastName, String phoneNumber,
                         String role, String department, Double salary) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.department = department;
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /**
     * Returns a string representation of the EmployeeInDTO object.
     *
     * @return string with employee input data
     */
    @Override
    public String toString() {
        return "EmployeeInDTO{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}
