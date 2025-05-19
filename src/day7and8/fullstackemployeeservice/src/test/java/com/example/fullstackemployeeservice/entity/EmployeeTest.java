package com.example.fullstackemployeeservice.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testGettersAndSetters() {
        Employee employee = new Employee();

        employee.setId(1L);
        employee.setEmail("test@example.com");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPhoneNumber("1234567890");
        employee.setRole("Developer");

        assertEquals(1L, employee.getId());
        assertEquals("test@example.com", employee.getEmail());
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("1234567890", employee.getPhoneNumber());
        assertEquals("Developer", employee.getRole());
    }

    @Test
    void testParameterizedConstructor() {
        Employee employee = new Employee(2L, "jane@example.com", "Jane", "Smith", "0987654321", "Manager");

        assertEquals(2L, employee.getId());
        assertEquals("jane@example.com", employee.getEmail());
        assertEquals("Jane", employee.getFirstName());
        assertEquals("Smith", employee.getLastName());
        assertEquals("0987654321", employee.getPhoneNumber());
        assertEquals("Manager", employee.getRole());
    }

    @Test
    void testEqualsAndHashCode() {
        Employee emp1 = new Employee(1L, "test@example.com", "John", "Doe", null, "Developer");
        Employee emp2 = new Employee(1L, "test@example.com", "John", "Doe", "12345", "Developer");
        Employee emp3 = new Employee(2L, "other@example.com", "Jane", "Smith", null, "Manager");

        assertEquals(emp1, emp2);
        assertEquals(emp1.hashCode(), emp2.hashCode());

        assertNotEquals(emp1, emp3);
        assertNotEquals(emp1.hashCode(), emp3.hashCode());
    }

    @Test
    void testToString() {
        Employee employee = new Employee(3L, "someone@example.com", "Sam", "Wilson", "5555555555", "Tester");
        String expected = "Employee{id=3, email='someone@example.com', firstName='Sam', lastName='Wilson', phoneNumber='5555555555', role='Tester'}";
        assertEquals(expected, employee.toString());
    }

    @Test
    void testEqualsWithDifferentObjects() {
        Employee employee = new Employee(1L, "email@example.com", "First", "Last", null, "Role");
        assertNotEquals(employee, null);
        assertNotEquals(employee, "some string");
    }
}

