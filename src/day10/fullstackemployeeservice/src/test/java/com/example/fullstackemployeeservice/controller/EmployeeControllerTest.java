package com.example.fullstackemployeeservice.controller;

import com.example.fullstackemployeeservice.inDTO.EmployeeInDTO;
import com.example.fullstackemployeeservice.outDTO.EmployeeOutDTO;
import com.example.fullstackemployeeservice.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployeeControllerTest {

    private EmployeeService employeeService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        employeeService = Mockito.mock(EmployeeService.class);
        EmployeeController employeeController = new EmployeeController(employeeService);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateEmployee() throws Exception {
        EmployeeInDTO inDTO = new EmployeeInDTO(
                "test@gmail.com",
                "John",
                "Doe",
                "+1234567890",
                "EMPLOYEE",
                "Engineering",
                50000.0
        );
        EmployeeOutDTO outDTO = new EmployeeOutDTO();
        when(employeeService.createEmployee(any(EmployeeInDTO.class))).thenReturn(outDTO);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(outDTO)));

        verify(employeeService, times(1)).createEmployee(any(EmployeeInDTO.class));
    }

    @Test
    void testGetAllEmployees() throws Exception {
        List<EmployeeOutDTO> employees = Arrays.asList(new EmployeeOutDTO(), new EmployeeOutDTO());
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeById() throws Exception {
        EmployeeOutDTO outDTO = new EmployeeOutDTO();
        when(employeeService.getEmployeeById(1L)).thenReturn(outDTO);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(outDTO)));

        verify(employeeService, times(1)).getEmployeeById(1L);
    }

    @Test
    void testGetEmployeeByEmail() throws Exception {
        EmployeeOutDTO outDTO = new EmployeeOutDTO();
        when(employeeService.getEmployeeByEmail("test@example.com")).thenReturn(outDTO);

        mockMvc.perform(get("/api/employees/email/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(outDTO)));

        verify(employeeService, times(1)).getEmployeeByEmail("test@example.com");
    }

    @Test
    void testUpdateEmployeeById() throws Exception {
        EmployeeInDTO inDTO = new EmployeeInDTO(
                "test@gmail.com",
                "John",
                "Doe",
                "+1234567890",
                "EMPLOYEE",
                "Engineering",
                50000.0
        );
        EmployeeOutDTO outDTO = new EmployeeOutDTO();
        when(employeeService.updateEmployeeById(eq(1L), any(EmployeeInDTO.class))).thenReturn(outDTO);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(outDTO)));

        verify(employeeService, times(1)).updateEmployeeById(eq(1L), any(EmployeeInDTO.class));
    }

    @Test
    void testUpdateEmployeeByEmail() throws Exception {
        EmployeeInDTO inDTO = new EmployeeInDTO(
                "test@gmail.com",
                "John",
                "Doe",
                "+1234567890",
                "EMPLOYEE",
                "Engineering",
                50000.0
        );
        EmployeeOutDTO outDTO = new EmployeeOutDTO();
        when(employeeService.updateEmployeeByEmail(eq("test@example.com"), any(EmployeeInDTO.class))).thenReturn(outDTO);

        mockMvc.perform(put("/api/employees/email/test@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(outDTO)));

        verify(employeeService, times(1)).updateEmployeeByEmail(eq("test@example.com"), any(EmployeeInDTO.class));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test
    void testGetEmployeesByDepartment() throws Exception {
        List<EmployeeOutDTO> employees = Arrays.asList(new EmployeeOutDTO());
        when(employeeService.getEmployeesByDepartment("IT")).thenReturn(employees);

        mockMvc.perform(get("/api/employees/department/IT"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));

        verify(employeeService, times(1)).getEmployeesByDepartment("IT");
    }

    @Test
    void testGetEmployeesBySalaryRange() throws Exception {
        List<EmployeeOutDTO> employees = Arrays.asList(new EmployeeOutDTO());
        when(employeeService.getEmployeesBySalaryRange(50000.0, 100000.0)).thenReturn(employees);

        mockMvc.perform(get("/api/employees/salary-range")
                        .param("minSalary", "50000")
                        .param("maxSalary", "100000"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));

        verify(employeeService, times(1)).getEmployeesBySalaryRange(50000.0, 100000.0);
    }

    @Test
    void testUploadEmployeesCsv() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "employees.csv",
                "text/csv", "id,name,email\n1,John Doe,john@example.com".getBytes());
        List<EmployeeOutDTO> employees = Arrays.asList(new EmployeeOutDTO());
        when(employeeService.addEmployeesFromCsv(any())).thenReturn(employees);

        mockMvc.perform(multipart("/api/employees/upload-csv").file(file))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));

        verify(employeeService, times(1)).addEmployeesFromCsv(any());
    }
}
