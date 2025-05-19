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
    void toEntity_ShouldMapAllFields() {
        EmployeeInDTO dto = new EmployeeInDTO();
        dto.setEmail("test@example.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPhoneNumber("1234567890");
        dto.setRole("Developer");

        Employee entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
        assertEquals(dto.getPhoneNumber(), entity.getPhoneNumber());
        assertEquals(dto.getRole(), entity.getRole());
    }

    @Test
    void toDto_ShouldMapAllFields() {
        Employee entity = new Employee();
        entity.setEmail("test@example.com");
        entity.setFirstName("Jane");
        entity.setLastName("Smith");
        entity.setPhoneNumber("0987654321");
        entity.setRole("Manager");

        EmployeeOutDTO dto = mapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getFirstName(), dto.getFirstName());
        assertEquals(entity.getLastName(), dto.getLastName());
        assertEquals(entity.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(entity.getRole(), dto.getRole());
    }

    @Test
    void toDtoList_ShouldMapListOfEntities() {
        Employee e1 = new Employee();
        e1.setEmail("e1@example.com");
        e1.setFirstName("E1First");
        e1.setLastName("E1Last");
        e1.setPhoneNumber("1111111111");
        e1.setRole("Role1");

        Employee e2 = new Employee();
        e2.setEmail("e2@example.com");
        e2.setFirstName("E2First");
        e2.setLastName("E2Last");
        e2.setPhoneNumber("2222222222");
        e2.setRole("Role2");

        List<Employee> employees = Arrays.asList(e1, e2);

        List<EmployeeOutDTO> dtos = mapper.toDtoList(employees);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());

        assertEquals(e1.getEmail(), dtos.get(0).getEmail());
        assertEquals(e2.getEmail(), dtos.get(1).getEmail());
    }

    @Test
    void updateEntityFromDto_ShouldUpdateAllFields() {
        EmployeeInDTO dto = new EmployeeInDTO();
        dto.setEmail("update@example.com");
        dto.setFirstName("UpdatedFirst");
        dto.setLastName("UpdatedLast");
        dto.setPhoneNumber("9999999999");
        dto.setRole("UpdatedRole");

        Employee entity = new Employee();
        entity.setEmail("old@example.com");
        entity.setFirstName("OldFirst");
        entity.setLastName("OldLast");
        entity.setPhoneNumber("0000000000");
        entity.setRole("OldRole");

        mapper.updateEntityFromDto(dto, entity);

        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
        assertEquals(dto.getPhoneNumber(), entity.getPhoneNumber());
        assertEquals(dto.getRole(), entity.getRole());
    }
}

