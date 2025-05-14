package day1.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Parses a CSV file containing employee data and filters employees
 * based on certain criteria (Department: Engineering, Salary > 70000).
 * Writes the filtered employees to a new CSV file using NIO (New IO).
 */
public class NioCSVParser {

    private static final Logger logger = Logger.getLogger(NioCSVParser.class.getName());

    /**
     * Main method to process the input and output files.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Path inputPath = Paths.get("src/day1/task2/employees.csv");
        Path outputPath = Paths.get("src/day1/task2/filtered_nio.csv");

        try (Stream<String> lines = Files.lines(inputPath)) {
            EmployeeCsvMapper mapper = new EmployeeCsvMapper();

            List<String> filteredLines = lines.skip(1)
                    .map(mapper::deserialize)
                    .filter(emp -> emp.getDepartment().equals("Engineering") && emp.getSalary() > 70000)
                    .map(mapper::serialize)
                    .collect(Collectors.toList());

            filteredLines.add(0, "id,name,age,department,salary");
            Files.write(outputPath, filteredLines);

            logger.info("Filtered employees written using NIO.2.");
        } catch (IOException e) {
            logger.severe("Error occurred while processing the CSV files: " + e.getMessage());
        }
    }
}
