package FileAnalyser;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class WordLengthAnalyser extends TextAnalyser {
    private static Map<Integer, Integer> wordLengths = new HashMap<>();
    private int wordLengthSum;
    private int wordCount;

    public WordLengthAnalyser(File directory) {
        super(directory);
    }

    public void calculateStatistics() {
        setWordCount();
        setWordLengthSum();

        System.out.println("Average word length: " + getAverageWordLength());
        System.out.println("Dispersion: " + getDispersion());
        System.out.println("Standard deviation: " + getStandardDeviation());
    }

    @Override
    protected TextAnalyser createSubTask(File directory) {
        return new WordLengthAnalyser(directory);
    }

    @Override
    protected void analyseFile(File file) {
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
            wordLengths.computeIfAbsent(length, k -> 0);
            wordLengths.put(length, wordLengths.get(length) + 1);
        }
    }

    private void setWordCount() {
        int count = 0;
        for (int value : wordLengths.values()) {
            count += value;
        }
        this.wordCount = count;
    }

    private void setWordLengthSum() {
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : wordLengths.entrySet()) {
            sum += entry.getKey() * entry.getValue();
        }
        this.wordLengthSum = sum;
    }

    private double getAverageWordLength() {
        double average = (double) wordLengthSum / wordCount;
        return Math.round(average * Math.pow(10, 1)) / Math.pow(10, 1);
    }

    private double getDispersion() {
        double average = getAverageWordLength();
        double sumOfSquaredDeviations = 0;
        for (Map.Entry<Integer, Integer> entry : wordLengths.entrySet()) {
            sumOfSquaredDeviations += Math.pow(entry.getKey() - average, 2) * entry.getValue();
        }
        return Math.round(sumOfSquaredDeviations / wordCount * Math.pow(10, 1)) / Math.pow(10, 1);
    }

    private double getStandardDeviation() {
        double dispersion = getDispersion();
        return Math.round(Math.sqrt(dispersion) * Math.pow(10, 1)) / Math.pow(10, 1);
    }

}
