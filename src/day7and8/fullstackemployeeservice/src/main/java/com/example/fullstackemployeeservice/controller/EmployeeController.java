package com.example.fullstackemployeeservice.controller;

import com.example.fullstackemployeeservice.inDTO.EmailInDTO;
import com.example.fullstackemployeeservice.inDTO.EmployeeInDTO;
import com.example.fullstackemployeeservice.outDTO.EmailOutDTO;
import com.example.fullstackemployeeservice.outDTO.EmployeeOutDTO;
import com.example.fullstackemployeeservice.service.EmployeeService;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * REST controller for managing employee operations.
 * This controller handles endpoints related to creating, retrieving,
 * updating, deleting, and uploading employees.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    /**
     * Logger instance for logging controller actions.
     */
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    /**
     * Service layer for employee-related business logic.
     */
    private final EmployeeService employeeService;

    /**
     * Constructs an EmployeeController with the given EmployeeService.
     *
     * @param employeeService the service used to manage employees
     */
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Creates a new employee.
     *
     * @param employeeInDTO the employee data from the request body
     * @return the created employee data
     */
    @PostMapping
    public ResponseEntity<EmployeeOutDTO> createEmployee(@Valid @RequestBody EmployeeInDTO employeeInDTO) {
        logger.info("Received request to create a new employee");
        return new ResponseEntity<>(employeeService.createEmployee(employeeInDTO), HttpStatus.CREATED);
    }

    /**
     * Retrieves all employees.
     *
     * @return a list of all employees
     */
    @GetMapping
    public ResponseEntity<List<EmployeeOutDTO>> getAllEmployees() {
        logger.info("Received request to get all employees");
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    /**
     * Retrieves an employee by ID.
     *
     * @param id the ID of the employee to retrieve
     * @return the employee with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeOutDTO> getEmployeeById(@PathVariable Long id) {
        logger.info("Received request to get employee with ID: {}", id);
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    /**
     * Retrieves an employee by email.
     *
     * @param email the email of the employee to retrieve
     * @return the employee with the specified email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeOutDTO> getEmployeeByEmail(@PathVariable String email) {
        logger.info("Received request to get employee with email: {}", email);
        return new ResponseEntity<>(employeeService.getEmployeeByEmail(email), HttpStatus.OK);
    }

    /**
     * Updates an existing employee by ID.
     *
     * @param id             the ID of the employee to update
     * @param employeeInDTO  the updated employee data
     * @return the updated employee data
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeOutDTO> updateEmployeeById(@PathVariable Long id,
                                                             @Valid @RequestBody EmployeeInDTO employeeInDTO) {
        logger.info("Received request to update employee with ID: {}", id);
        return new ResponseEntity<>(employeeService.updateEmployeeById(id, employeeInDTO), HttpStatus.OK);
    }

    /**
     * Updates an existing employee by email.
     *
     * @param email          the email of the employee to update
     * @param employeeInDTO  the updated employee data
     * @return the updated employee data
     */
    @PutMapping("/email/{email}")
    public ResponseEntity<EmployeeOutDTO> updateEmployeeByEmail(@PathVariable String email,
                                                                @Valid @RequestBody EmployeeInDTO employeeInDTO) {
        logger.info("Received request to update employee with email: {}", email);
        return new ResponseEntity<>(employeeService.updateEmployeeByEmail(email, employeeInDTO), HttpStatus.OK);
    }

    /**
     * Deletes an employee by ID.
     *
     * @param id the ID of the employee to delete
     * @return an empty response with HTTP status 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        logger.info("Received request to delete employee with ID: {}", id);
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves all employees from a specific department.
     *
     * @param department the department name to filter by
     * @return a list of employees in the department
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeOutDTO>> getEmployeesByDepartment(@PathVariable String department) {
        logger.info("Received request to get employees from department: {}", department);
        return new ResponseEntity<>(employeeService.getEmployeesByDepartment(department), HttpStatus.OK);
    }

    /**
     * Retrieves all employees with salary within a specified range.
     *
     * @param minSalary the minimum salary value
     * @param maxSalary the maximum salary value
     * @return a list of employees with salary in the range
     */
    @GetMapping("/salary-range")
    public ResponseEntity<List<EmployeeOutDTO>> getEmployeesBySalaryRange(
            @RequestParam Double minSalary, @RequestParam Double maxSalary) {
        logger.info("Received request to get employees with salary between {} and {}", minSalary, maxSalary);
        return new ResponseEntity<>(
                employeeService.getEmployeesBySalaryRange(minSalary, maxSalary), HttpStatus.OK);
    }


    /**
     * Uploads a CSV file and creates multiple employees from it.
     *
     * @param file the CSV file containing employee data
     * @return a list of created employee data
     */
    @PostMapping("/upload-csv")
    public ResponseEntity<List<EmployeeOutDTO>> uploadEmployeesCsv(@RequestParam("file") MultipartFile file) {
        logger.info("Received request to upload CSV file for bulk employee creation");
        return new ResponseEntity<>(employeeService.addEmployeesFromCsv(file), HttpStatus.CREATED);
    }

    @PostMapping("/notify/feign")
    public ResponseEntity<EmailOutDTO> notifyEmployeeViaFeign(@RequestBody EmailInDTO emailInDTO) {
        EmailOutDTO response = employeeService.emailViaFeignClient(emailInDTO);
        return ResponseEntity.ok(response);
    }
}
