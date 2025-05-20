package day1.task2;

import java.util.logging.Logger;

/**
 * Provides methods to serialize and deserialize Employee objects to and from CSV format.
 */
public class EmployeeCsvMapper {

    private static final Logger logger = Logger.getLogger(EmployeeCsvMapper.class.getName());

    /**
     * Serializes an Employee object into a CSV string.
     *
     * @param employee the Employee object to serialize
     * @return a CSV-formatted string representing the Employee
     */
    public String serialize(Employee employee) {
        logger.info("Serializing employee: " + employee.getName());
        return employee.getId() + "," +
                employee.getName() + "," +
                employee.getAge() + "," +
                employee.getDepartment() + "," +
                employee.getSalary();
    }

    /**
     * Deserializes a CSV string into an Employee object.
     *
     * @param data the CSV-formatted string to deserialize
     * @return the corresponding Employee object
     * @throws IllegalArgumentException if the CSV string is not in the expected format
     */
    public Employee deserialize(String data) {
        logger.info("Deserializing data: " + data);
        String[] tokens = data.split(",");
        if (tokens.length != 5) {
            logger.warning("Invalid CSV format: " + data);
            throw new IllegalArgumentException("Invalid CSV format: " + data);
        }
        return new Employee(
                Integer.parseInt(tokens[0].trim()),
                tokens[1].trim(),
                Integer.parseInt(tokens[2].trim()),
                tokens[3].trim(),
                Double.parseDouble(tokens[4].trim())
        );
    }
}
