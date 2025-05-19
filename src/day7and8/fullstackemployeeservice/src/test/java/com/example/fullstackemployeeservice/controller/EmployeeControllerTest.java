package com.example.fullstackemployeeservice.controller;

import com.example.fullstackemployeeservice.inDTO.EmployeeInDTO;
import com.example.fullstackemployeeservice.outDTO.EmployeeOutDTO;
import com.example.fullstackemployeeservice.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEmployee_ShouldReturnCreatedEmployee() {
        EmployeeInDTO inDTO = new EmployeeInDTO("test@gmail.com", "John", "Doe", "1234567890", "EMPLOYEE");
        EmployeeOutDTO outDTO = new EmployeeOutDTO();
        outDTO.setEmail(inDTO.getEmail());

        when(employeeService.createEmployee(inDTO)).thenReturn(outDTO);

        ResponseEntity<EmployeeOutDTO> response = employeeController.createEmployee(inDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(outDTO, response.getBody());

        verify(employeeService).createEmployee(inDTO);
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees() {
        EmployeeOutDTO dto1 = new EmployeeOutDTO();
        dto1.setEmail("a@gmail.com");
        EmployeeOutDTO dto2 = new EmployeeOutDTO();
        dto2.setEmail("b@gmail.com");

        List<EmployeeOutDTO> list = Arrays.asList(dto1, dto2);

        when(employeeService.getAllEmployees()).thenReturn(list);

        ResponseEntity<List<EmployeeOutDTO>> response = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());

        verify(employeeService).getAllEmployees();
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() {
        Long id = 1L;
        EmployeeOutDTO dto = new EmployeeOutDTO();
        dto.setEmail("test@gmail.com");

        when(employeeService.getEmployeeById(id)).thenReturn(dto);

        ResponseEntity<EmployeeOutDTO> response = employeeController.getEmployeeById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());

        verify(employeeService).getEmployeeById(id);
    }

    @Test
    void getEmployeeByEmail_ShouldReturnEmployee() {
        String email = "test@gmail.com";
        EmployeeOutDTO dto = new EmployeeOutDTO();
        dto.setEmail(email);

        when(employeeService.getEmployeeByEmail(email)).thenReturn(dto);

        ResponseEntity<EmployeeOutDTO> response = employeeController.getEmployeeByEmail(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());

        verify(employeeService).getEmployeeByEmail(email);
    }

    @Test
    void updateEmployeeById_ShouldReturnUpdatedEmployee() {
        Long id = 1L;
        EmployeeInDTO inDTO = new EmployeeInDTO("test@gmail.com", "John", "Doe", "1234567890", "EMPLOYEE");
        EmployeeOutDTO outDTO = new EmployeeOutDTO();
        outDTO.setEmail(inDTO.getEmail());

        when(employeeService.updateEmployeeById(id, inDTO)).thenReturn(outDTO);

        ResponseEntity<EmployeeOutDTO> response = employeeController.updateEmployeeById(id, inDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outDTO, response.getBody());

        verify(employeeService).updateEmployeeById(id, inDTO);
    }

    @Test
    void updateEmployeeByEmail_ShouldReturnUpdatedEmployee() {
        String email = "test@gmail.com";
        EmployeeInDTO inDTO = new EmployeeInDTO(email, "John", "Doe", "1234567890", "EMPLOYEE");
        EmployeeOutDTO outDTO = new EmployeeOutDTO();
        outDTO.setEmail(email);

        when(employeeService.updateEmployeeByEmail(email, inDTO)).thenReturn(outDTO);

        ResponseEntity<EmployeeOutDTO> response = employeeController.updateEmployeeByEmail(email, inDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outDTO, response.getBody());

        verify(employeeService).updateEmployeeByEmail(email, inDTO);
    }

    @Test
    void deleteEmployee_ShouldReturnNoContent() {
        Long id = 1L;

        doNothing().when(employeeService).deleteEmployee(id);

        ResponseEntity<Void> response = employeeController.deleteEmployee(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(employeeService).deleteEmployee(id);
    }

    @Test
    void uploadEmployeesCsv_ShouldReturnCreatedEmployees() throws Exception {
        String csvContent = "email,firstName,lastName,phoneNumber,role\n" +
                "a@gmail.com,John,Doe,1234567890,EMPLOYEE\n" +
                "b@gmail.com,Jane,Doe,0987654321,ADMIN\n";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "employees.csv",
                "text/csv",
                csvContent.getBytes(StandardCharsets.UTF_8)
        );

        EmployeeOutDTO dto1 = new EmployeeOutDTO();
        dto1.setEmail("a@gmail.com");
        EmployeeOutDTO dto2 = new EmployeeOutDTO();
        dto2.setEmail("b@gmail.com");

        List<EmployeeOutDTO> outList = Arrays.asList(dto1, dto2);

        when(employeeService.addEmployeesFromCsv(file)).thenReturn(outList);

        ResponseEntity<List<EmployeeOutDTO>> response = employeeController.uploadEmployeesCsv(file);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(outList, response.getBody());

        verify(employeeService).addEmployeesFromCsv(file);
    }
}

