package com.example.fullstackemployeeservice.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testNoArgsConstructor() {
        Employee employee = new Employee();
        assertNotNull(employee);
    }

    @Test
    void testAllArgsConstructor() {
        Employee employee = new Employee(1L, "john.doe@example.com", "John", "Doe",
                "1234567890", "Developer", "IT", 75000.0);

        assertEquals(1L, employee.getId());
        assertEquals("john.doe@example.com", employee.getEmail());
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("1234567890", employee.getPhoneNumber());
        assertEquals("Developer", employee.getRole());
        assertEquals("IT", employee.getDepartment());
        assertEquals(75000.0, employee.getSalary());
    }

    @Test
    void testSettersAndGetters() {
        Employee employee = new Employee();

        employee.setId(2L);
        employee.setEmail("jane.doe@example.com");
        employee.setFirstName("Jane");
        employee.setLastName("Doe");
        employee.setPhoneNumber("0987654321");
        employee.setRole("Manager");
        employee.setDepartment("HR");
        employee.setSalary(85000.0);

        assertEquals(2L, employee.getId());
        assertEquals("jane.doe@example.com", employee.getEmail());
        assertEquals("Jane", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("0987654321", employee.getPhoneNumber());
        assertEquals("Manager", employee.getRole());
        assertEquals("HR", employee.getDepartment());
        assertEquals(85000.0, employee.getSalary());
    }

    @Test
    void testEqualsAndHashCode() {
        Employee emp1 = new Employee(1L, "test@example.com", "Alice", "Smith", "1112223333", "Engineer", "R&D", 90000.0);
        Employee emp2 = new Employee(1L, "test@example.com", "Alice", "Smith", "1112223333", "Engineer", "R&D", 90000.0);
        Employee emp3 = new Employee(2L, "different@example.com", "Bob", "Jones", "2223334444", "Manager", "Sales", 95000.0);

        assertEquals(emp1, emp2);
        assertEquals(emp1.hashCode(), emp2.hashCode());

        assertNotEquals(emp1, emp3);
        assertNotEquals(emp1.hashCode(), emp3.hashCode());
    }

    @Test
    void testEqualsWithSameObject() {
        Employee emp = new Employee();
        assertEquals(emp, emp);
    }

    @Test
    void testEqualsWithNull() {
        Employee emp = new Employee();
        assertNotEquals(emp, null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        Employee emp = new Employee();
        String other = "NotAnEmployee";
        assertNotEquals(emp, other);
    }

    @Test
    void testToString() {
        Employee employee = new Employee(3L, "mark.taylor@example.com", "Mark", "Taylor",
                "5551234567", "Analyst", "Finance", 67000.0);

        String result = employee.toString();

        assertTrue(result.contains("id=3"));
        assertTrue(result.contains("email='mark.taylor@example.com'"));
        assertTrue(result.contains("firstName='Mark'"));
        assertTrue(result.contains("lastName='Taylor'"));
        assertTrue(result.contains("phoneNumber='5551234567'"));
        assertTrue(result.contains("role='Analyst'"));
        assertTrue(result.contains("department='Finance'"));
        assertTrue(result.contains("salary=67000.0"));
    }
}
