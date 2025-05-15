package day1.task2;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Parses a CSV file containing employee data and filters employees
 * based on certain criteria (Department: Engineering, Salary > 70000).
 * Writes the filtered employees to a new CSV file using legacy IO API.
 */
public class LegacyCSVParser {

    private static final Logger logger = Logger.getLogger(LegacyCSVParser.class.getName());

    /**
     * Main method to process the input and output files.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        String inputFile = "src/day1/task2/employees.csv";
        String outputFile = "src/day1/task2/filtered_legacy.csv";

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))
        ) {
            String header = reader.readLine();
            writer.write(header);
            writer.newLine();

            EmployeeCsvMapper mapper = new EmployeeCsvMapper();

            List<Employee> filteredEmployees = reader.lines()
                    .map(mapper::deserialize)
                    .filter(emp -> emp.getDepartment().equals("Engineering") && emp.getSalary() > 70000)
                    .collect(Collectors.toList());

            for (Employee e : filteredEmployees) {
                writer.write(mapper.serialize(e));
                writer.newLine();
            }

            logger.info("Filtered employees written using legacy API.");
        } catch (IOException e) {
            logger.severe("Error occurred while processing the CSV files: " + e.getMessage());
        }
    }
}
