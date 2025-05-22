package com.example.fullstackemployeeservice.mapper;

import com.example.fullstackemployeeservice.entity.Employee;
import com.example.fullstackemployeeservice.inDTO.EmployeeInDTO;
import com.example.fullstackemployeeservice.outDTO.EmployeeOutDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {

    private EmployeeMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new EmployeeMapper();
    }

    @Test
    void testToEntity() {
        EmployeeInDTO dto = new EmployeeInDTO(
                "test@example.com",
                "Alice",
                "Johnson",
                "1234567890",
                "EMPLOYEE",
                "Engineering",
                75000.0
        );

        Employee employee = mapper.toEntity(dto);

        assertEquals(dto.getEmail(), employee.getEmail());
        assertEquals(dto.getFirstName(), employee.getFirstName());
        assertEquals(dto.getLastName(), employee.getLastName());
        assertEquals(dto.getPhoneNumber(), employee.getPhoneNumber());
        assertEquals(dto.getRole(), employee.getRole());
        assertEquals(dto.getDepartment(), employee.getDepartment());
        assertEquals(dto.getSalary(), employee.getSalary());
    }

    @Test
    void testToDto() {
        Employee employee = new Employee(
                1L,
                "bob@example.com",
                "Bob",
                "Smith",
                "5551234567",
                "ADMIN",
                "HR",
                90000.0
        );

        EmployeeOutDTO dto = mapper.toDto(employee);

        assertEquals(employee.getEmail(), dto.getEmail());
        assertEquals(employee.getFirstName(), dto.getFirstName());
        assertEquals(employee.getLastName(), dto.getLastName());
        assertEquals(employee.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(employee.getRole(), dto.getRole());
        assertEquals(employee.getDepartment(), dto.getDepartment());
        assertEquals(employee.getSalary(), dto.getSalary());
    }

    @Test
    void testToDtoList() {
        Employee employee1 = new Employee(
                1L, "a@example.com", "A", "One", "111", "EMPLOYEE", "IT", 50000.0);
        Employee employee2 = new Employee(
                2L, "b@example.com", "B", "Two", "222", "HR", "Finance", 60000.0);

        List<Employee> employees = Arrays.asList(employee1, employee2);
        List<EmployeeOutDTO> dtoList = mapper.toDtoList(employees);

        assertEquals(2, dtoList.size());
        assertEquals("a@example.com", dtoList.get(0).getEmail());
        assertEquals("b@example.com", dtoList.get(1).getEmail());
    }

    @Test
    void testUpdateEntityFromDto() {
        Employee employee = new Employee();
        employee.setEmail("old@example.com");
        employee.setFirstName("Old");
        employee.setLastName("Data");
        employee.setPhoneNumber("0000000000");
        employee.setRole("OLD_ROLE");
        employee.setDepartment("OLD_DEPT");
        employee.setSalary(1000.0);

        EmployeeInDTO updateDto = new EmployeeInDTO(
                "new@example.com",
                "NewFirst",
                "NewLast",
                "9999999999",
                "NEW_ROLE",
                "NEW_DEPT",
                120000.0
        );

        mapper.updateEntityFromDto(updateDto, employee);

        assertEquals("new@example.com", employee.getEmail());
        assertEquals("NewFirst", employee.getFirstName());
        assertEquals("NewLast", employee.getLastName());
        assertEquals("9999999999", employee.getPhoneNumber());
        assertEquals("NEW_ROLE", employee.getRole());
        assertEquals("NEW_DEPT", employee.getDepartment());
        assertEquals(120000.0, employee.getSalary());
    }
}
