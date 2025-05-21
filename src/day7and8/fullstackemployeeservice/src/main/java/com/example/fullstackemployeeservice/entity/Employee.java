package com.example.fullstackemployeeservice.entity;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entity representing an employee record in the system.
 * Mapped to the "employees" table in the database.
 */
@Entity
@Table(name = "employees")
public class Employee {

    /**
     * Primary key of the employee.
     * Auto-generated using the identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique and non-null email address of the employee.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * First name of the employee. Cannot be null.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Last name of the employee. Cannot be null.
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * Optional phone number of the employee.
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Role of the employee in the organization. Cannot be null.
     */
    @Column(nullable = false)
    private String role;

    /**
     * Department of the employee. Cannot be null.
     */
    @Column(nullable = false)
    private String department;

    /**
     * Salary of the employee. Cannot be null.
     */
    @Column(nullable = false)
    private Double salary;

    /**
     * Default no-argument constructor.
     */
    public Employee() {
    }

    /**
     * Parameterized constructor to create an Employee with all fields.
     *
     * @param id          the employee's ID
     * @param email       the employee's email
     * @param firstName   the employee's first name
     * @param lastName    the employee's last name
     * @param phoneNumber the employee's phone number
     * @param role        the employee's role
     * @param department  the employee's department
     * @param salary      the employee's salary
     */
    public Employee(Long id, String email, String firstName, String lastName, String phoneNumber,
                    String role, String department, Double salary) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.department = department;
        this.salary = salary;
    }


    /**
     * Gets the employee's ID.
     *
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the employee's ID.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the employee's email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the employee's email.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the employee's first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the employee's first name.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the employee's last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the employee's last name.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the employee's phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the employee's phone number.
     *
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the employee's role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the employee's role.
     *
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the employee's department.
     *
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the employee's department.
     *
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Gets the employee's salary.
     *
     * @return the salary
     */
    public Double getSalary() {
        return salary;
    }

    /**
     * Sets the employee's salary.
     *
     * @param salary the salary to set
     */
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /**
     * Checks equality based on employee ID and email.
     *
     * @param o the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(email, employee.email) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(phoneNumber, employee.phoneNumber) && Objects.equals(role, employee.role) && Objects.equals(department, employee.department) && Objects.equals(salary, employee.salary);
    }

    /**
     * Generates hash code based on employee ID and email.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, phoneNumber, role, department, salary);
    }

    /**
     * Returns a string representation of the employee.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}
