package FileAnalyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SequentialWordLengthAnalyser {
    private Map<Integer, Integer> wordLengths = new HashMap<>();
    private int wordLengthSum;
    private int wordCount;

    public SequentialWordLengthAnalyser(File directory) {
        analyseDirectory(directory);
    }

    public void calculateStatistics() {
        int count = 0;
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : wordLengths.entrySet()) {
            int length = entry.getKey();
            int frequency = entry.getValue();
            count += frequency;
            sum += length * frequency;
        }
        wordCount = count;
        wordLengthSum = sum;

        System.out.println("Average word length: " + getAverageWordLength());
        System.out.println("Dispersion: " + getDispersion());
        System.out.println("Standard deviation: " + getStandardDeviation());
    }

    private void analyseDirectory(File directory) {
        File[] files = directory.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                analyseDirectory(file);
            } else {
//                System.out.println("Analyzing file: " + file.getAbsolutePath());
                analyseFile(file);
            }
        }
    }

    private void analyseFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> words = getWords(line);
                processWordLength(words);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getWords(String line) {
        String[] words = line.split("\\s+");
        List<String> filteredWords = new ArrayList<>();
        for (String word : words) {
            if (word.matches("\\p{L}+")) {
                filteredWords.add(word.toLowerCase());
            }
        }
        return filteredWords;
    }

    private void processWordLength(List<String> words) {
        for (String word : words) {
            int length = word.length();
            wordLengths.merge(length, 1, Integer::sum);
        }
    }

    private double getAverageWordLength() {
        double average = (double) wordLengthSum / wordCount;
        return Math.round(average * 10.0) / 10.0;
    }

    private double getDispersion() {
        double average = getAverageWordLength();
        double sumOfSquaredDeviations = 0;
        for (Map.Entry<Integer, Integer> entry : wordLengths.entrySet()) {
            int length = entry.getKey();
            int frequency = entry.getValue();
            sumOfSquaredDeviations += Math.pow(length - average, 2) * frequency;
        }
        return Math.round(sumOfSquaredDeviations / wordCount * 10.0) / 10.0;
    }

    private double getStandardDeviation() {
        double dispersion = getDispersion();
        return Math.round(Math.sqrt(dispersion) * 10.0) / 10.0;
    }
}

