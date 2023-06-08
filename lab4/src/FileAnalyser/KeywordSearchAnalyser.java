package FileAnalyser;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class KeywordSearchAnalyser extends TextAnalyser {
    private List<String> keywords;
    private static Set<String> documentsWithKeywords = new HashSet<>();

    public KeywordSearchAnalyser(File directory, List<String> keywords) {
        super(directory);
        this.keywords = new ArrayList<>(keywords);
        this.keywords.replaceAll(String::toLowerCase);
    }

    @Override
    protected TextAnalyser createSubTask(File directory) {
        return new KeywordSearchAnalyser(directory, keywords);
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
            processKeywords(setWords, file);
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

    private void processKeywords(Set<String> setWords, File file) {
        setWords.retainAll(keywords);
        if (!setWords.isEmpty()) {
            documentsWithKeywords.add(file.getPath().split("/")[file.getPath().split("/").length - 1]);
        }
    }

    public Set<String> getDocumentsWithKeywords() {
        return documentsWithKeywords;
    }
}
