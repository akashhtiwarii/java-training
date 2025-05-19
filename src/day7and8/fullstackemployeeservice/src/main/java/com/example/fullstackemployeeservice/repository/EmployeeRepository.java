package com.example.fullstackemployeeservice.repository;

import com.example.fullstackemployeeservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Employee entity that provides database operations.
 * <p>
 * This repository extends JpaRepository to inherit basic CRUD operations and
 * provides additional custom query methods for employee-specific operations.
 * </p>
 *
 * @author Your Name
 * @version 1.0
 * @since 2025-05-19
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Finds an employee by their unique email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the found employee or empty if no employee exists with the given email
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Checks if an employee with the specified email exists in the database.
     *
     * @param email the email address to check for existence
     * @return true if an employee with the given email exists, false otherwise
     */
    boolean existsByEmail(String email);
}