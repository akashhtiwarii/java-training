package com.example.fullstackemployeeservice.service;

import com.example.fullstackemployeeservice.inDTO.EmployeeInDTO;
import com.example.fullstackemployeeservice.outDTO.EmployeeOutDTO;
import com.example.fullstackemployeeservice.exception.ResourceAlreadyExistsException;
import com.example.fullstackemployeeservice.exception.ResourceInvalidException;
import com.example.fullstackemployeeservice.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service interface for managing employee operations.
 * <p>
 * This interface defines the contract for handling CRUD operations related to employees,
 * as well as specialized operations such as retrieving employees by specific criteria
 * and bulk importing from CSV files.
 * </p>
 *
 */
public interface EmployeeService {

    /**
     * Creates a new employee in the system.
     *
     * @param employeeInDTO the DTO containing employee information to be created
     * @return EmployeeOutDTO representing the newly created employee
     * @throws ResourceAlreadyExistsException if an employee with the same email already exists
     */
    EmployeeOutDTO createEmployee(EmployeeInDTO employeeInDTO);

    /**
     * Retrieves all employees from the system.
     *
     * @return List of all employees as DTOs
     * @throws ResourceNotFoundException if no employees are found in the system
     */
    List<EmployeeOutDTO> getAllEmployees();

    /**
     * Retrieves an employee by their ID.
     *
     * @param id the unique identifier of the employee
     * @return EmployeeOutDTO representing the found employee
     * @throws ResourceNotFoundException if no employee with the given ID exists
     */
    EmployeeOutDTO getEmployeeById(Long id);

    /**
     * Retrieves an employee by their email address.
     *
     * @param email the email address of the employee to find
     * @return EmployeeOutDTO representing the found employee
     * @throws ResourceNotFoundException if no employee with the given email exists
     */
    EmployeeOutDTO getEmployeeByEmail(String email);

    /**
     * Updates an existing employee identified by their ID.
     *
     * @param id the unique identifier of the employee to update
     * @param employeeInDTO the DTO containing updated employee information
     * @return EmployeeOutDTO representing the updated employee
     * @throws ResourceNotFoundException if no employee with the given ID exists
     * @throws ResourceAlreadyExistsException if the updated email is already in use by another employee
     */
    EmployeeOutDTO updateEmployeeById(Long id, EmployeeInDTO employeeInDTO);

    /**
     * Updates an existing employee identified by their email address.
     *
     * @param email the email address of the employee to update
     * @param employeeInDTO the DTO containing updated employee information
     * @return EmployeeOutDTO representing the updated employee
     * @throws ResourceNotFoundException if no employee with the given email exists
     * @throws ResourceAlreadyExistsException if the updated email is already in use by another employee
     */
    EmployeeOutDTO updateEmployeeByEmail(String email, EmployeeInDTO employeeInDTO);

    /**
     * Deletes an employee from the system by their ID.
     *
     * @param id the unique identifier of the employee to delete
     * @throws ResourceNotFoundException if no employee with the given ID exists
     */
    void deleteEmployee(Long id);

    /**
     * Bulk imports employees from a CSV file.
     * <p>
     * The CSV file must have headers and include at minimum the following columns:
     * email, firstName, lastName, and role. The phoneNumber column is optional.
     * </p>
     *
     * @param file the MultipartFile containing CSV data of employees to import
     * @return List of EmployeeOutDTO representing all successfully imported employees
     * @throws ResourceInvalidException if the file is empty, contains validation errors,
     *         contains duplicate emails, or has processing errors
     * @throws ResourceAlreadyExistsException if any employee in the CSV has an email that already exists in the system
     */
    List<EmployeeOutDTO> addEmployeesFromCsv(MultipartFile file);

    /**
     * Retrieves all employees from a specific department.
     *
     * @param department the department name to filter by
     * @return List of employees in the specified department
     * @throws ResourceNotFoundException if no employees are found in the department
     */
    List<EmployeeOutDTO> getEmployeesByDepartment(String department);

    /**
     * Retrieves all employees with salary within a specified range.
     *
     * @param minSalary the minimum salary value (inclusive)
     * @param maxSalary the maximum salary value (inclusive)
     * @return List of employees with salary in the specified range
     * @throws ResourceNotFoundException if no employees are found within the salary range
     * @throws ResourceInvalidException if minSalary is greater than maxSalary
     */
    List<EmployeeOutDTO> getEmployeesBySalaryRange(Double minSalary, Double maxSalary);
}