package FileAnalyser;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CommonWordsAnalyser extends TextAnalyser {
    private static Set<String> commonWords = new HashSet<>();

    public CommonWordsAnalyser(File directory) {
        super(directory);
    }

    @Override
    protected TextAnalyser createSubTask(File directory) {
        return new CommonWordsAnalyser(directory);
    }

    @Override
    protected void analyseFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            Set<String> setWords = new HashSet<>();
            while ((line = reader.readLine()) != null) {
                List<String> words = getWords(line);
                setWords.addAll(words);
            }
            processCommonWords(setWords);
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

    private void processCommonWords(Set<String> words) {
        words.removeIf(word -> !word.matches("\\p{L}+"));
        if (commonWords.isEmpty()) {
            commonWords.addAll(words);
        } else {
            commonWords.retainAll(words);
        }
    }

    public Set<String> getCommonWords() {
        return commonWords;
    }
}
