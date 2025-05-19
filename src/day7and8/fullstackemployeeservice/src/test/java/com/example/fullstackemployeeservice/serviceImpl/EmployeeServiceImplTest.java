package com.example.fullstackemployeeservice.serviceImpl;

import com.example.fullstackemployeeservice.entity.Employee;
import com.example.fullstackemployeeservice.exception.ResourceAlreadyExistsException;
import com.example.fullstackemployeeservice.exception.ResourceInvalidException;
import com.example.fullstackemployeeservice.exception.ResourceNotFoundException;
import com.example.fullstackemployeeservice.inDTO.EmployeeInDTO;
import com.example.fullstackemployeeservice.mapper.EmployeeMapper;
import com.example.fullstackemployeeservice.outDTO.EmployeeOutDTO;
import com.example.fullstackemployeeservice.repository.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEmployee_Success() {
        EmployeeInDTO inDTO = new EmployeeInDTO("test@gmail.com", "John", "Doe", "1234567890", "EMPLOYEE");
        Employee employeeEntity = new Employee();
        employeeEntity.setEmail(inDTO.getEmail());
        Employee savedEntity = new Employee();
        savedEntity.setId(1L);
        savedEntity.setEmail(inDTO.getEmail());

        EmployeeOutDTO outDTO = new EmployeeOutDTO();
        outDTO.setEmail(inDTO.getEmail());

        when(employeeRepository.existsByEmail(inDTO.getEmail())).thenReturn(false);
        when(employeeMapper.toEntity(inDTO)).thenReturn(employeeEntity);
        when(employeeRepository.save(employeeEntity)).thenReturn(savedEntity);
        when(employeeMapper.toDto(savedEntity)).thenReturn(outDTO);

        EmployeeOutDTO result = employeeService.createEmployee(inDTO);

        assertNotNull(result);
        assertEquals(inDTO.getEmail(), result.getEmail());

        verify(employeeRepository).existsByEmail(inDTO.getEmail());
        verify(employeeMapper).toEntity(inDTO);
        verify(employeeRepository).save(employeeEntity);
        verify(employeeMapper).toDto(savedEntity);
    }

    @Test
    void createEmployee_ThrowsResourceAlreadyExistsException() {
        EmployeeInDTO inDTO = new EmployeeInDTO("exists@gmail.com", "John", "Doe", "1234567890", "EMPLOYEE");

        when(employeeRepository.existsByEmail(inDTO.getEmail())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> employeeService.createEmployee(inDTO));
        verify(employeeRepository).existsByEmail(inDTO.getEmail());
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void getEmployeeById_Success() {
        long id = 1L;
        Employee employee = new Employee();
        employee.setId(id);
        employee.setEmail("someone@gmail.com");
        EmployeeOutDTO dto = new EmployeeOutDTO();
        dto.setEmail(employee.getEmail());

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(employeeMapper.toDto(employee)).thenReturn(dto);

        EmployeeOutDTO result = employeeService.getEmployeeById(id);
        assertNotNull(result);
        assertEquals(employee.getEmail(), result.getEmail());

        verify(employeeRepository).findById(id);
        verify(employeeMapper).toDto(employee);
    }

    @Test
    void getEmployeeById_ThrowsResourceNotFoundException() {
        long id = 999L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(id));
        verify(employeeRepository).findById(id);
    }

    @Test
    void updateEmployeeById_Success() {
        long id = 1L;
        EmployeeInDTO updateDto = new EmployeeInDTO("updated@gmail.com", "John", "Doe", "1234567890", "EMPLOYEE");

        Employee existingEmployee = new Employee();
        existingEmployee.setId(id);
        existingEmployee.setEmail("old@gmail.com");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(id);
        updatedEmployee.setEmail(updateDto.getEmail());

        EmployeeOutDTO outDto = new EmployeeOutDTO();
        outDto.setEmail(updateDto.getEmail());

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.existsByEmail(updateDto.getEmail())).thenReturn(false);
        doAnswer(invocation -> {
            EmployeeInDTO dtoArg = invocation.getArgument(0);
            Employee empArg = invocation.getArgument(1);
            empArg.setEmail(dtoArg.getEmail());
            return null;
        }).when(employeeMapper).updateEntityFromDto(updateDto, existingEmployee);
        when(employeeRepository.save(existingEmployee)).thenReturn(updatedEmployee);
        when(employeeMapper.toDto(updatedEmployee)).thenReturn(outDto);

        EmployeeOutDTO result = employeeService.updateEmployeeById(id, updateDto);

        assertNotNull(result);
        assertEquals(updateDto.getEmail(), result.getEmail());

        verify(employeeRepository).findById(id);
        verify(employeeRepository).existsByEmail(updateDto.getEmail());
        verify(employeeMapper).updateEntityFromDto(updateDto, existingEmployee);
        verify(employeeRepository).save(existingEmployee);
        verify(employeeMapper).toDto(updatedEmployee);
    }

    @Test
    void updateEmployeeById_ThrowsResourceNotFoundException() {
        long id = 999L;
        EmployeeInDTO updateDto = new EmployeeInDTO("updated@gmail.com", "John", "Doe", "1234567890", "EMPLOYEE");
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployeeById(id, updateDto));
        verify(employeeRepository).findById(id);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void updateEmployeeById_ThrowsResourceAlreadyExistsException_WhenEmailExists() {
        long id = 1L;
        EmployeeInDTO updateDto = new EmployeeInDTO("taken@gmail.com", "John", "Doe", "1234567890", "EMPLOYEE");
        Employee existingEmployee = new Employee();
        existingEmployee.setId(id);
        existingEmployee.setEmail("old@gmail.com");

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.existsByEmail(updateDto.getEmail())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> employeeService.updateEmployeeById(id, updateDto));

        verify(employeeRepository).findById(id);
        verify(employeeRepository).existsByEmail(updateDto.getEmail());
    }

    @Test
    void deleteEmployee_Success() {
        long id = 1L;
        Employee employee = new Employee();
        employee.setId(id);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        assertDoesNotThrow(() -> employeeService.deleteEmployee(id));

        verify(employeeRepository).findById(id);
        verify(employeeRepository).delete(employee);
    }

    @Test
    void deleteEmployee_ThrowsResourceNotFoundException() {
        long id = 999L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployee(id));
        verify(employeeRepository).findById(id);
    }

    @Test
    void addEmployeesFromCsv_Success() throws Exception {
        String csvContent = "email,firstName,lastName,phoneNumber,role\n" +
                "a@gmail.com,John,Doe,1234567890,EMPLOYEE\n" +
                "b@gmail.com,Jane,Doe,0987654321,ADMIN\n";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "employees.csv",
                "text/csv",
                csvContent.getBytes(StandardCharsets.UTF_8)
        );

        EmployeeInDTO dto1 = new EmployeeInDTO("a@gmail.com", "John", "Doe", "1234567890", "EMPLOYEE");
        EmployeeInDTO dto2 = new EmployeeInDTO("b@gmail.com", "Jane", "Doe", "0987654321", "ADMIN");

        Employee emp1 = new Employee();
        emp1.setEmail(dto1.getEmail());
        Employee emp2 = new Employee();
        emp2.setEmail(dto2.getEmail());

        EmployeeOutDTO out1 = new EmployeeOutDTO();
        out1.setEmail(dto1.getEmail());
        EmployeeOutDTO out2 = new EmployeeOutDTO();
        out2.setEmail(dto2.getEmail());

        when(employeeRepository.existsByEmail("a@gmail.com")).thenReturn(false);
        when(employeeRepository.existsByEmail("b@gmail.com")).thenReturn(false);

        when(employeeMapper.toEntity(any(EmployeeInDTO.class))).thenAnswer(invocation -> {
            EmployeeInDTO dto = invocation.getArgument(0);
            Employee e = new Employee();
            e.setEmail(dto.getEmail());
            return e;
        });

        when(employeeRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        when(employeeMapper.toDtoList(anyList())).thenReturn(List.of(out1, out2));

        List<EmployeeOutDTO> results = employeeService.addEmployeesFromCsv(file);

        assertEquals(2, results.size());
        assertEquals("a@gmail.com", results.get(0).getEmail());
        assertEquals("b@gmail.com", results.get(1).getEmail());

        verify(employeeRepository, times(2)).existsByEmail(anyString());
        verify(employeeMapper, times(2)).toEntity(any(EmployeeInDTO.class));
        verify(employeeRepository).saveAll(anyList());
        verify(employeeMapper).toDtoList(anyList());
    }

    @Test
    void addEmployeesFromCsv_ThrowsResourceInvalidException_WhenFileEmpty() {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "empty.csv", "text/csv", new byte[0]);

        ResourceInvalidException ex = assertThrows(ResourceInvalidException.class,
                () -> employeeService.addEmployeesFromCsv(emptyFile));

        assertTrue(ex.getMessage().contains("non-empty CSV file"));
    }

    @Test
    void addEmployeesFromCsv_ThrowsResourceInvalidException_WhenInvalidCSV() {
        String csvContent = "email,firstName,lastName,phoneNumber,role\n" +
                ",John,Doe,1234567890,EMPLOYEE\n"; // missing email

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "employees.csv",
                "text/csv",
                csvContent.getBytes(StandardCharsets.UTF_8)
        );

        ResourceInvalidException ex = assertThrows(ResourceInvalidException.class,
                () -> employeeService.addEmployeesFromCsv(file));

        assertTrue(ex.getMessage().contains("Email is required"));
    }
}

