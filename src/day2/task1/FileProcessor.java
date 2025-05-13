package day2.task1;

import java.io.*;

public class FileProcessor {
    public static void main(String[] args) {
        String filePath = "src/day2/data.txt";
        BufferedReader reader = null;
        try {
            File file = new File(filePath);
            reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                processLine(line);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file - " + e.getMessage());
        } catch (CustomException e) {
            System.err.println("Custom Exception " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                    System.out.println("Finally Block");
                }
            } catch (IOException e) {
                System.err.println("Error closing file reader - " + e.getMessage());
            }
        }
    }

    public static void processLine(String line) throws CustomException {
        if (!line.matches("^\\d+,\\w+$")) {
            throw new CustomException("Line format is invalid: " + line);
        }

        String[] parts = line.split(",");
        System.out.println("ID: " + parts[0] + ", Name: " + parts[1]);
    }
}
