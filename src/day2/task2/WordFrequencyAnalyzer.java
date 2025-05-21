package day2.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordFrequencyAnalyzer {
    public static void main(String[] args) {
        String filePath = "src/day2/task2/data.txt";
        int topN = 3;
        List<String> words = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            words = lines
                    .flatMap(line -> Arrays.stream(line.toLowerCase().split("\\W+")))
                    .toList();

            Set<String> stopwords = Set.of("the", "is", "in", "and", "are", "a", "an", "of");

            Predicate<String> isUsefulWord = word -> word != null && !word.isBlank() && !stopwords.contains(word);

            TextProcessor cleaner = word -> word.replaceAll("[^a-z]", "").trim();

            Map<String, Long> frequencyMap = words.stream()
                    .map(cleaner::process)
                    .filter(isUsefulWord)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            List<Map.Entry<String, Long>> topWords = frequencyMap.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(topN)
                    .toList();

            System.out.println("Top " + topN + " most frequent words:");
            topWords.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unknown Exception: " + e.getMessage());
        }
    }
}
