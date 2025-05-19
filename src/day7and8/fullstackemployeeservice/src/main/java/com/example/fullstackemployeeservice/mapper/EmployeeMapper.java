package com.example.fullstackemployeeservice.mapper;

import com.example.fullstackemployeeservice.entity.Employee;
import com.example.fullstackemployeeservice.inDTO.EmployeeInDTO;
import com.example.fullstackemployeeservice.outDTO.EmployeeOutDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class responsible for converting between Employee entities and DTOs.
 * <p>
 * It provides methods to:
 * <ul>
 *     <li>Convert input DTOs to entity objects</li>
 *     <li>Convert entity objects to output DTOs</li>
 *     <li>Update existing entities with new data from DTOs</li>
 *     <li>Convert lists of entities to lists of output DTOs</li>
 * </ul>
 */
@Component
public class EmployeeMapper {

    /**
     * Converts an {@link EmployeeInDTO} to an {@link Employee} entity.
     *
     * @param employeeInDTO the input DTO containing employee data
     * @return a new {@link Employee} entity with data from the DTO
     */
    public Employee toEntity(EmployeeInDTO employeeInDTO) {
        Employee employee = new Employee();
        employee.setEmail(employeeInDTO.getEmail());
        employee.setFirstName(employeeInDTO.getFirstName());
        employee.setLastName(employeeInDTO.getLastName());
        employee.setPhoneNumber(employeeInDTO.getPhoneNumber());
        employee.setRole(employeeInDTO.getRole());
        return employee;
    }

    /**
     * Converts an {@link Employee} entity to an {@link EmployeeOutDTO}.
     *
     * @param employee the employee entity to convert
     * @return a new {@link EmployeeOutDTO} with data from the entity
     */
    public EmployeeOutDTO toDto(Employee employee) {
        EmployeeOutDTO employeeOutDTO = new EmployeeOutDTO();
        employeeOutDTO.setEmail(employee.getEmail());
        employeeOutDTO.setFirstName(employee.getFirstName());
        employeeOutDTO.setLastName(employee.getLastName());
        employeeOutDTO.setPhoneNumber(employee.getPhoneNumber());
        employeeOutDTO.setRole(employee.getRole());
        return employeeOutDTO;
    }

    /**
     * Converts a list of {@link Employee} entities to a list of {@link EmployeeOutDTO}.
     *
     * @param employees the list of employee entities
     * @return a list of {@link EmployeeOutDTO} objects
     */
    public List<EmployeeOutDTO> toDtoList(List<Employee> employees) {
        return employees.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing {@link Employee} entity with values from an {@link EmployeeInDTO}.
     *
     * @param employeeInDTO the DTO containing updated employee data
     * @param employee      the employee entity to update
     */
    public void updateEntityFromDto(EmployeeInDTO employeeInDTO, Employee employee) {
        employee.setEmail(employeeInDTO.getEmail());
        employee.setFirstName(employeeInDTO.getFirstName());
        employee.setLastName(employeeInDTO.getLastName());
        employee.setPhoneNumber(employeeInDTO.getPhoneNumber());
        employee.setRole(employeeInDTO.getRole());
    }
}
