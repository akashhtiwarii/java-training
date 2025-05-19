package com.example.fullstackemployeeservice.serviceImpl;

import com.example.fullstackemployeeservice.entity.Employee;
import com.example.fullstackemployeeservice.exception.ResourceAlreadyExistsException;
import com.example.fullstackemployeeservice.exception.ResourceInvalidException;
import com.example.fullstackemployeeservice.exception.ResourceNotFoundException;
import com.example.fullstackemployeeservice.fiegnClient.EmailFeignClient;
import com.example.fullstackemployeeservice.inDTO.EmailInDTO;
import com.example.fullstackemployeeservice.inDTO.EmployeeInDTO;
import com.example.fullstackemployeeservice.mapper.EmployeeMapper;
import com.example.fullstackemployeeservice.outDTO.EmailOutDTO;
import com.example.fullstackemployeeservice.outDTO.EmployeeOutDTO;
import com.example.fullstackemployeeservice.repository.EmployeeRepository;
import com.example.fullstackemployeeservice.service.EmployeeService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link EmployeeService} interface that provides business logic
 * for handling employee operations.
 * <p>
 * This service handles CRUD operations for employees, as well as specialized operations
 * such as bulk importing employees from CSV files.
 * </p>
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final EmailFeignClient emailFeignClient;
    @Autowired
    private Validator validator;

    /**
     * Constructs a new EmployeeServiceImpl with required dependencies.
     *
     * @param employeeRepository the repository for employee data access operations
     * @param employeeMapper     the mapper for converting between entities and DTOs
     */
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, EmailFeignClient emailFeignClient) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.emailFeignClient = emailFeignClient;
    }

    /**
     * Creates a new employee in the system.
     *
     * @param employeeInDTO the DTO containing employee information to be created
     * @return EmployeeOutDTO representing the newly created employee
     * @throws ResourceAlreadyExistsException if an employee with the same email already exists
     */
    @Override
    @Transactional
    public EmployeeOutDTO createEmployee(EmployeeInDTO employeeInDTO) {
        logger.info("Creating employee with email: {}", employeeInDTO.getEmail());

        if (employeeRepository.existsByEmail(employeeInDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Employee", "email", employeeInDTO.getEmail());
        }

        Employee employee = employeeMapper.toEntity(employeeInDTO);
        Employee savedEmployee = employeeRepository.save(employee);

        logger.info("Employee created successfully with ID: {}", savedEmployee.getId());
        return employeeMapper.toDto(savedEmployee);
    }

    /**
     * Retrieves all employees from the system.
     *
     * @return List of all employees as DTOs
     * @throws ResourceNotFoundException if no employees are found in the system
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeOutDTO> getAllEmployees() {
        logger.info("Fetching all employees");
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No Employees Found");
        }
        return employeeMapper.toDtoList(employees);
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param id the unique identifier of the employee
     * @return EmployeeOutDTO representing the found employee
     * @throws ResourceNotFoundException if no employee with the given ID exists
     */
    @Override
    @Transactional(readOnly = true)
    public EmployeeOutDTO getEmployeeById(Long id) {
        logger.info("Fetching employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        return employeeMapper.toDto(employee);
    }

    /**
     * Retrieves an employee by their email address.
     *
     * @param email the email address of the employee to find
     * @return EmployeeOutDTO representing the found employee
     * @throws ResourceNotFoundException if no employee with the given email exists
     */
    @Override
    @Transactional(readOnly = true)
    public EmployeeOutDTO getEmployeeByEmail(String email) {
        logger.info("Fetching employee with email: {}", email);
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "email", email));
        return employeeMapper.toDto(employee);
    }

    /**
     * Updates an existing employee identified by their ID.
     *
     * @param id            the unique identifier of the employee to update
     * @param employeeInDTO the DTO containing updated employee information
     * @return EmployeeOutDTO representing the updated employee
     * @throws ResourceNotFoundException      if no employee with the given ID exists
     * @throws ResourceAlreadyExistsException if the updated email is already in use by another employee
     */
    @Override
    @Transactional
    public EmployeeOutDTO updateEmployeeById(Long id, EmployeeInDTO employeeInDTO) {
        logger.info("Updating employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));

        if (!employee.getEmail().equals(employeeInDTO.getEmail()) &&
                employeeRepository.existsByEmail(employeeInDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Employee", "email", employeeInDTO.getEmail());
        }

        employeeMapper.updateEntityFromDto(employeeInDTO, employee);
        Employee updatedEmployee = employeeRepository.save(employee);

        logger.info("Employee updated successfully with ID: {}", updatedEmployee.getId());
        return employeeMapper.toDto(updatedEmployee);
    }

    /**
     * Updates an existing employee identified by their email address.
     *
     * @param email         the email address of the employee to update
     * @param employeeInDTO the DTO containing updated employee information
     * @return EmployeeOutDTO representing the updated employee
     * @throws ResourceNotFoundException      if no employee with the given email exists
     * @throws ResourceAlreadyExistsException if the updated email is already in use by another employee
     */
    @Override
    @Transactional
    public EmployeeOutDTO updateEmployeeByEmail(String email, EmployeeInDTO employeeInDTO) {
        logger.info("Updating employee with email: {}", email);

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "email", email));

        if (!employee.getEmail().equals(employeeInDTO.getEmail()) &&
                employeeRepository.existsByEmail(employeeInDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Employee", "email", employeeInDTO.getEmail());
        }

        employeeMapper.updateEntityFromDto(employeeInDTO, employee);
        Employee updatedEmployee = employeeRepository.save(employee);

        logger.info("Employee updated successfully with ID: {}", updatedEmployee.getId());
        return employeeMapper.toDto(updatedEmployee);
    }

    /**
     * Deletes an employee from the system by their ID.
     *
     * @param id the unique identifier of the employee to delete
     * @throws ResourceNotFoundException if no employee with the given ID exists
     */
    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        logger.info("Deleting employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));

        employeeRepository.delete(employee);
        logger.info("Employee deleted successfully with ID: {}", id);
    }

    /**
     * Bulk imports employees from a CSV file.
     * <p>
     * The CSV file must have headers and include at minimum the following columns:
     * email, firstName, lastName, and role. The phoneNumber column is optional.
     * </p>
     *
     * @param file the MultipartFile containing CSV data of employees to import
     * @return List of EmployeeOutDTO representing all successfully imported employees
     * @throws ResourceInvalidException       if the file is empty, contains validation errors,
     *                                        contains duplicate emails, or has processing errors
     * @throws ResourceAlreadyExistsException if any employee in the CSV has an email that already exists in the system
     */
    @Override
    @Transactional
    public List<EmployeeOutDTO> addEmployeesFromCsv(MultipartFile file) {
        logger.info("Processing CSV file for bulk employee upload");

        if (file.isEmpty()) {
            throw new ResourceInvalidException("Please upload a non-empty CSV file");
        }

        List<EmployeeInDTO> employeeDTOs = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            int lineNumber = 1;
            for (CSVRecord csvRecord : csvParser) {
                lineNumber++;
                try {
                    String email = csvRecord.get("email");
                    String firstName = csvRecord.get("firstName");
                    String lastName = csvRecord.get("lastName");
                    String phoneNumber = csvRecord.get("phoneNumber");
                    String role = csvRecord.get("role");
                    String department = csvRecord.get("department");
                    String salaryStr = csvRecord.get("salary");

                    if (email == null || email.isEmpty()) {
                        errorMessages.add("Line " + lineNumber + ": Email is required");
                        continue;
                    }
                    if (firstName == null || firstName.isEmpty()) {
                        errorMessages.add("Line " + lineNumber + ": First name is required");
                        continue;
                    }
                    if (lastName == null || lastName.isEmpty()) {
                        errorMessages.add("Line " + lineNumber + ": Last name is required");
                        continue;
                    }
                    if (role == null || role.isEmpty()) {
                        errorMessages.add("Line " + lineNumber + ": Role is required");
                        continue;
                    }
                    if (department == null || department.isEmpty()) {
                        errorMessages.add("Line " + lineNumber + ": Department is required");
                        continue;
                    }
                    if (salaryStr == null || salaryStr.isEmpty()) {
                        errorMessages.add("Line " + lineNumber + ": Salary is required");
                        continue;
                    }

                    Double salary;
                    try {
                        salary = Double.valueOf(salaryStr);
                    } catch (NumberFormatException e) {
                        errorMessages.add("Line " + lineNumber + ": Invalid salary value");
                        continue;
                    }

                    if (employeeRepository.existsByEmail(email)) {
                        errorMessages.add("Line " + lineNumber + ": Employee with email '" + email + "' already exists");
                        continue;
                    }

                    boolean duplicateInBatch = employeeDTOs.stream()
                            .anyMatch(dto -> dto.getEmail().equals(email));

                    if (duplicateInBatch) {
                        errorMessages.add("Line " + lineNumber + ": Duplicate email '" + email + "' found in CSV file");
                        continue;
                    }

                    EmployeeInDTO employeeDTO = new EmployeeInDTO();
                    employeeDTO.setEmail(email);
                    employeeDTO.setFirstName(firstName);
                    employeeDTO.setLastName(lastName);
                    employeeDTO.setPhoneNumber(phoneNumber);
                    employeeDTO.setRole(role);
                    employeeDTO.setDepartment(department);
                    employeeDTO.setSalary(salary);

                    Set<ConstraintViolation<EmployeeInDTO>> violations = validator.validate(employeeDTO);
                    if (!violations.isEmpty()) {
                        String validationErrors = violations.stream()
                                .map(v -> v.getPropertyPath() + " " + v.getMessage())
                                .collect(Collectors.joining(", "));
                        errorMessages.add("Line " + lineNumber + ": " + validationErrors);
                        continue;
                    }

                    employeeDTOs.add(employeeDTO);

                } catch (Exception e) {
                    errorMessages.add("Line " + lineNumber + ": Error processing record - " + e.getMessage());
                }
            }

            if (!errorMessages.isEmpty()) {
                throw new ResourceInvalidException("CSV file contains errors: " + String.join("; ", errorMessages));
            }

            List<Employee> employeesToSave = employeeDTOs.stream()
                    .map(employeeMapper::toEntity)
                    .toList();

            List<Employee> savedEmployees = employeeRepository.saveAll(employeesToSave);
            logger.info("Successfully saved {} employees from CSV file", savedEmployees.size());

            return employeeMapper.toDtoList(savedEmployees);

        } catch (ResourceInvalidException e) {
            logger.error("Validation error in CSV file: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error processing CSV file", e);
            throw new ResourceInvalidException("Error processing CSV file: " + e.getMessage());
        }
    }


    /**
     * Retrieves a list of employees belonging to the specified department.
     *
     * @param department the name of the department to filter employees by.
     * @return a list of {@link EmployeeOutDTO} objects representing the employees in the specified department.
     * @throws ResourceNotFoundException if no employees are found in the given department.
     */
    @Override
    public List<EmployeeOutDTO> getEmployeesByDepartment(String department) {
        logger.info("Fetching employees with department: {}", department);
        List<Employee> employees = employeeRepository.findByDepartment(department);
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found in department: " + department);
        }
        logger.info("Successfully Fetched employees with department: {}", department);
        return employeeMapper.toDtoList(employees);
    }

    /**
     * Retrieves a list of employees whose salaries fall within the specified range.
     *
     * @param minSalary the minimum salary (inclusive).
     * @param maxSalary the maximum salary (inclusive).
     * @return a list of {@link EmployeeOutDTO} objects representing the employees within the specified salary range.
     * @throws ResourceInvalidException  if the minimum salary is greater than the maximum salary.
     * @throws ResourceNotFoundException if no employees are found within the specified salary range.
     */
    public List<EmployeeOutDTO> getEmployeesBySalaryRange(Double minSalary, Double maxSalary) {
        logger.info("Fetching employees with salary range: {} - {}", minSalary, maxSalary);
        if (minSalary > maxSalary) {
            throw new ResourceInvalidException("Minimum salary cannot be greater than maximum salary");
        }

        List<Employee> employees = employeeRepository.findBySalaryBetween(minSalary, maxSalary);
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No employees found with salary between " + minSalary + " and " + maxSalary);
        }
        logger.info("Successfully Fetched employees with salary range: {} - {}", minSalary, maxSalary);
        return employeeMapper.toDtoList(employees);
    }

    @Override
    public EmailOutDTO emailViaFeignClient(EmailInDTO emailInDTO) {
        EmailOutDTO emailOutDTO = emailFeignClient.sendEmail(emailInDTO);
        return emailOutDTO;
    }
}