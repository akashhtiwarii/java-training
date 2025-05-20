package com.example.fullstackemployeeservice.serviceImpl;

import com.example.fullstackemployeeservice.entity.Employee;
import com.example.fullstackemployeeservice.exception.ResourceAlreadyExistsException;
import com.example.fullstackemployeeservice.exception.ResourceInvalidException;
import com.example.fullstackemployeeservice.exception.ResourceNotFoundException;
import com.example.fullstackemployeeservice.inDTO.EmployeeInDTO;
import com.example.fullstackemployeeservice.mapper.EmployeeMapper;
import com.example.fullstackemployeeservice.outDTO.EmployeeOutDTO;
import com.example.fullstackemployeeservice.repository.EmployeeRepository;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private Validator validator;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee testEmployee;
    private EmployeeInDTO testEmployeeInDTO;
    private EmployeeOutDTO testEmployeeOutDTO;
    private List<Employee> testEmployeeList;
    private List<EmployeeOutDTO> testEmployeeOutDTOList;

    @BeforeEach
    void setUp() {

        testEmployee = new Employee(
                1L,
                "test@gmail.com",
                "John",
                "Doe",
                "+1234567890",
                "EMPLOYEE",
                "IT",
                60000.0
        );

        testEmployeeInDTO = new EmployeeInDTO(
                "test@gmail.com",
                "John",
                "Doe",
                "+1234567890",
                "EMPLOYEE",
                "IT",
                60000.0
        );

        testEmployeeOutDTO = new EmployeeOutDTO(
                "test@gmail.com",
                "John",
                "Doe",
                "+1234567890",
                "EMPLOYEE",
                "IT",
                60000.0
        );

        testEmployeeList = new ArrayList<>();
        testEmployeeList.add(testEmployee);

        testEmployeeOutDTOList = new ArrayList<>();
        testEmployeeOutDTOList.add(testEmployeeOutDTO);
    }

    @Test
    @DisplayName("Should create employee successfully")
    void createEmployee_Success() {
        
        when(employeeRepository.existsByEmail(anyString())).thenReturn(false);
        when(employeeMapper.toEntity(any(EmployeeInDTO.class))).thenReturn(testEmployee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(testEmployee);
        when(employeeMapper.toDto(any(Employee.class))).thenReturn(testEmployeeOutDTO);

        
        EmployeeOutDTO result = employeeService.createEmployee(testEmployeeInDTO);

        
        assertNotNull(result);
        assertEquals(testEmployeeOutDTO.getEmail(), result.getEmail());
        assertEquals(testEmployeeOutDTO.getFirstName(), result.getFirstName());
        assertEquals(testEmployeeOutDTO.getLastName(), result.getLastName());

        
        verify(employeeRepository).existsByEmail(testEmployeeInDTO.getEmail());
        verify(employeeMapper).toEntity(testEmployeeInDTO);
        verify(employeeRepository).save(testEmployee);
        verify(employeeMapper).toDto(testEmployee);
    }

    @Test   
    @DisplayName("Should throw exception when creating employee with existing email")
    void createEmployee_ExistingEmail_ThrowsException() {
        
        when(employeeRepository.existsByEmail(anyString())).thenReturn(true);

        
        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> employeeService.createEmployee(testEmployeeInDTO)
        );

        assertTrue(exception.getMessage().contains("email"));
        assertTrue(exception.getMessage().contains(testEmployeeInDTO.getEmail()));

        
        verify(employeeRepository).existsByEmail(testEmployeeInDTO.getEmail());
        verifyNoMoreInteractions(employeeMapper, employeeRepository);
    }

    @Test
    @DisplayName("Should get all employees successfully")
    void getAllEmployees_Success() {
        
        when(employeeRepository.findAll()).thenReturn(testEmployeeList);
        when(employeeMapper.toDtoList(anyList())).thenReturn(testEmployeeOutDTOList);

        
        List<EmployeeOutDTO> result = employeeService.getAllEmployees();

        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testEmployeeOutDTO.getEmail(), result.get(0).getEmail());

        
        verify(employeeRepository).findAll();
        verify(employeeMapper).toDtoList(testEmployeeList);
    }

    @Test
    @DisplayName("Should throw exception when no employees found")
    void getAllEmployees_NoEmployees_ThrowsException() {
        
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.getAllEmployees()
        );

        assertEquals("No Employees Found", exception.getMessage());

        
        verify(employeeRepository).findAll();
        verifyNoInteractions(employeeMapper);
    }

    @Test
    @DisplayName("Should get employee by ID successfully")
    void getEmployeeById_Success() {
        
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(testEmployee));
        when(employeeMapper.toDto(any(Employee.class))).thenReturn(testEmployeeOutDTO);

        
        EmployeeOutDTO result = employeeService.getEmployeeById(1L);

        
        assertNotNull(result);
        assertEquals(testEmployeeOutDTO.getEmail(), result.getEmail());

        
        verify(employeeRepository).findById(1L);
        verify(employeeMapper).toDto(testEmployee);
    }

    @Test
    @DisplayName("Should throw exception when employee not found by ID")
    void getEmployeeById_NotFound_ThrowsException() {
        
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.getEmployeeById(1L)
        );

        assertTrue(exception.getMessage().contains("id"));

        
        verify(employeeRepository).findById(1L);
        verifyNoInteractions(employeeMapper);
    }

    @Test
    @DisplayName("Should get employee by email successfully")
    void getEmployeeByEmail_Success() {
        
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(testEmployee));
        when(employeeMapper.toDto(any(Employee.class))).thenReturn(testEmployeeOutDTO);

        
        EmployeeOutDTO result = employeeService.getEmployeeByEmail("test@gmail.com");

        
        assertNotNull(result);
        assertEquals(testEmployeeOutDTO.getEmail(), result.getEmail());

        
        verify(employeeRepository).findByEmail("test@gmail.com");
        verify(employeeMapper).toDto(testEmployee);
    }

    @Test
    @DisplayName("Should throw exception when employee not found by email")
    void getEmployeeByEmail_NotFound_ThrowsException() {
        
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.getEmployeeByEmail("test@gmail.com")
        );

        assertTrue(exception.getMessage().contains("email"));

        
        verify(employeeRepository).findByEmail("test@gmail.com");
        verifyNoInteractions(employeeMapper);
    }

    @Test
    @DisplayName("Should update employee by ID successfully")
    void updateEmployeeById_Success() {
        
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(testEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(testEmployee);
        when(employeeMapper.toDto(any(Employee.class))).thenReturn(testEmployeeOutDTO);
        doNothing().when(employeeMapper).updateEntityFromDto(any(EmployeeInDTO.class), any(Employee.class));

        
        EmployeeOutDTO result = employeeService.updateEmployeeById(1L, testEmployeeInDTO);

        
        assertNotNull(result);
        assertEquals(testEmployeeOutDTO.getEmail(), result.getEmail());

        
        verify(employeeRepository).findById(1L);
        verify(employeeMapper).updateEntityFromDto(testEmployeeInDTO, testEmployee);
        verify(employeeRepository).save(testEmployee);
        verify(employeeMapper).toDto(testEmployee);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent employee by ID")
    void updateEmployeeById_NotFound_ThrowsException() {
        
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.updateEmployeeById(1L, testEmployeeInDTO)
        );

        assertTrue(exception.getMessage().contains("id"));

        
        verify(employeeRepository).findById(1L);
        verifyNoMoreInteractions(employeeRepository, employeeMapper);
    }

    @Test
    @DisplayName("Should throw exception when updating employee with email that already exists")
    void updateEmployeeById_EmailExists_ThrowsException() {
        
        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        existingEmployee.setEmail("old@gmail.com");

        EmployeeInDTO updateDTO = new EmployeeInDTO();
        updateDTO.setEmail("new@gmail.com");

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.existsByEmail("new@gmail.com")).thenReturn(true);

        
        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> employeeService.updateEmployeeById(1L, updateDTO)
        );

        assertTrue(exception.getMessage().contains("email"));
        assertTrue(exception.getMessage().contains("new@gmail.com"));

        
        verify(employeeRepository).findById(1L);
        verify(employeeRepository).existsByEmail("new@gmail.com");
        verifyNoMoreInteractions(employeeRepository, employeeMapper);
    }

    @Test
    @DisplayName("Should update employee by email successfully")
    void updateEmployeeByEmail_Success() {
        
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(testEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(testEmployee);
        when(employeeMapper.toDto(any(Employee.class))).thenReturn(testEmployeeOutDTO);
        doNothing().when(employeeMapper).updateEntityFromDto(any(EmployeeInDTO.class), any(Employee.class));

        
        EmployeeOutDTO result = employeeService.updateEmployeeByEmail("test@gmail.com", testEmployeeInDTO);

        
        assertNotNull(result);
        assertEquals(testEmployeeOutDTO.getEmail(), result.getEmail());

        
        verify(employeeRepository).findByEmail("test@gmail.com");
        verify(employeeMapper).updateEntityFromDto(testEmployeeInDTO, testEmployee);
        verify(employeeRepository).save(testEmployee);
        verify(employeeMapper).toDto(testEmployee);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent employee by email")
    void updateEmployeeByEmail_NotFound_ThrowsException() {
        
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.updateEmployeeByEmail("test@gmail.com", testEmployeeInDTO)
        );

        assertTrue(exception.getMessage().contains("email"));

        
        verify(employeeRepository).findByEmail("test@gmail.com");
        verifyNoMoreInteractions(employeeRepository, employeeMapper);
    }

    @Test
    @DisplayName("Should throw exception when updating employee with email that already exists")
    void updateEmployeeByEmail_EmailExists_ThrowsException() {
        
        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        existingEmployee.setEmail("old@gmail.com");

        EmployeeInDTO updateDTO = new EmployeeInDTO();
        updateDTO.setEmail("new@gmail.com");

        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.existsByEmail("new@gmail.com")).thenReturn(true);

        
        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> employeeService.updateEmployeeByEmail("old@gmail.com", updateDTO)
        );

        assertTrue(exception.getMessage().contains("email"));
        assertTrue(exception.getMessage().contains("new@gmail.com"));

        
        verify(employeeRepository).findByEmail("old@gmail.com");
        verify(employeeRepository).existsByEmail("new@gmail.com");
        verifyNoMoreInteractions(employeeRepository, employeeMapper);
    }

    @Test
    @DisplayName("Should delete employee successfully")
    void deleteEmployee_Success() {
        
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(testEmployee));
        doNothing().when(employeeRepository).delete(any(Employee.class));

        
        employeeService.deleteEmployee(1L);

        
        verify(employeeRepository).findById(1L);
        verify(employeeRepository).delete(testEmployee);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent employee")
    void deleteEmployee_NotFound_ThrowsException() {
        
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.deleteEmployee(1L)
        );

        assertTrue(exception.getMessage().contains("id"));

        
        verify(employeeRepository).findById(1L);
        verify(employeeRepository, never()).delete(any(Employee.class));
    }

    @Test
    @DisplayName("Should get employees by department successfully")
    void getEmployeesByDepartment_Success() {
        
        when(employeeRepository.findByDepartment(anyString())).thenReturn(testEmployeeList);
        when(employeeMapper.toDtoList(anyList())).thenReturn(testEmployeeOutDTOList);

        
        List<EmployeeOutDTO> result = employeeService.getEmployeesByDepartment("IT");

        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testEmployeeOutDTO.getEmail(), result.get(0).getEmail());

        
        verify(employeeRepository).findByDepartment("IT");
        verify(employeeMapper).toDtoList(testEmployeeList);
    }

    @Test
    @DisplayName("Should throw exception when no employees found in department")
    void getEmployeesByDepartment_NotFound_ThrowsException() {
        
        when(employeeRepository.findByDepartment(anyString())).thenReturn(Collections.emptyList());

        
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.getEmployeesByDepartment("IT")
        );

        assertTrue(exception.getMessage().contains("department"));
        assertTrue(exception.getMessage().contains("IT"));

        
        verify(employeeRepository).findByDepartment("IT");
        verifyNoInteractions(employeeMapper);
    }

    @Test
    @DisplayName("Should get employees by salary range successfully")
    void getEmployeesBySalaryRange_Success() {
        
        when(employeeRepository.findBySalaryBetween(anyDouble(), anyDouble())).thenReturn(testEmployeeList);
        when(employeeMapper.toDtoList(anyList())).thenReturn(testEmployeeOutDTOList);

        
        List<EmployeeOutDTO> result = employeeService.getEmployeesBySalaryRange(50000.0, 70000.0);

        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testEmployeeOutDTO.getEmail(), result.get(0).getEmail());

        
        verify(employeeRepository).findBySalaryBetween(50000.0, 70000.0);
        verify(employeeMapper).toDtoList(testEmployeeList);
    }

    @Test
    @DisplayName("Should throw exception when min salary is greater than max salary")
    void getEmployeesBySalaryRange_InvalidRange_ThrowsException() {
        
        ResourceInvalidException exception = assertThrows(
                ResourceInvalidException.class,
                () -> employeeService.getEmployeesBySalaryRange(70000.0, 50000.0)
        );

        assertEquals("Minimum salary cannot be greater than maximum salary", exception.getMessage());

        
        verifyNoInteractions(employeeRepository, employeeMapper);
    }

    @Test
    @DisplayName("Should throw exception when no employees found in salary range")
    void getEmployeesBySalaryRange_NotFound_ThrowsException() {
        
        when(employeeRepository.findBySalaryBetween(anyDouble(), anyDouble())).thenReturn(Collections.emptyList());

        
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.getEmployeesBySalaryRange(50000.0, 70000.0)
        );

        assertTrue(exception.getMessage().contains("salary"));
        assertTrue(exception.getMessage().contains("50000.0"));
        assertTrue(exception.getMessage().contains("70000.0"));

        
        verify(employeeRepository).findBySalaryBetween(50000.0, 70000.0);
        verifyNoInteractions(employeeMapper);
    }

    @Test
    @DisplayName("Should throw exception when CSV has missing required fields")
    void addEmployeesFromCsv_MissingFields_ThrowsException() throws IOException {
        
        String csvContent = "email,firstName,lastName,phoneNumber,role,department,salary\n" +
                "test@gmail.com,,Doe,+1234567890,EMPLOYEE,IT,60000.0";

        MultipartFile file = new MockMultipartFile(
                "file",
                "employees.csv",
                "text/csv",
                csvContent.getBytes(StandardCharsets.UTF_8)
        );

        
        ResourceInvalidException exception = assertThrows(
                ResourceInvalidException.class,
                () -> employeeService.addEmployeesFromCsv(file)
        );

        assertTrue(exception.getMessage().contains("CSV file contains errors"));
        assertTrue(exception.getMessage().contains("First name is required"));
    }

    @Test
    @DisplayName("Should throw exception when CSV has invalid salary")
    void addEmployeesFromCsv_InvalidSalary_ThrowsException() throws IOException {
        
        String csvContent = "email,firstName,lastName,phoneNumber,role,department,salary\n" +
                "test@gmail.com,John,Doe,+1234567890,EMPLOYEE,IT,invalid";

        MultipartFile file = new MockMultipartFile(
                "file",
                "employees.csv",
                "text/csv",
                csvContent.getBytes(StandardCharsets.UTF_8)
        );

        
        ResourceInvalidException exception = assertThrows(
                ResourceInvalidException.class,
                () -> employeeService.addEmployeesFromCsv(file)
        );

        assertTrue(exception.getMessage().contains("CSV file contains errors"));
        assertTrue(exception.getMessage().contains("Invalid salary value"));
    }

}

