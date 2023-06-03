package FileAnalyser;

import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        String root = System.getProperty("user.dir") + "/src/FileAnalyser";
        File directory = new File(root + "/Data/IT");

        // Task 1: word length analysis
        wordLengthAnalysis(pool, directory);

        // Task 2: common words search
        commonWordsSearch(pool, directory);

        // Task 3: keyword search
        keywordSearch(pool, directory);
    }

    public static void wordLengthAnalysis(ForkJoinPool pool, File directory) {
        long startTime = System.currentTimeMillis();
        WordLengthAnalyser wordLengthAnalyser = new WordLengthAnalyser(directory);
        pool.invoke(wordLengthAnalyser);
        long endTime = System.currentTimeMillis();

        System.out.println();
        System.out.println("Parallel time: " + (endTime - startTime) + " ms");
        wordLengthAnalyser.calculateStatistics();

        startTime = System.currentTimeMillis();
        SequentialWordLengthAnalyser sequentialWordLengthAnalyser = new SequentialWordLengthAnalyser(directory);
        endTime = System.currentTimeMillis();

        System.out.println();
        System.out.println("Sequential time: " + (endTime - startTime) + " ms");
        sequentialWordLengthAnalyser.calculateStatistics();
    }

    public static void commonWordsSearch(ForkJoinPool pool, File directory) {
        long startTime = System.currentTimeMillis();
        CommonWordsAnalyser commonWordsAnalyser = new CommonWordsAnalyser(directory);
        pool.invoke(commonWordsAnalyser);
        Set<String> commonWords = commonWordsAnalyser.getCommonWords();
        System.out.println();
        System.out.println("Common words: " + commonWords);
        long endTime = System.currentTimeMillis();
        System.out.println("Parallel time: " + (endTime - startTime) + " ms");
    }

    public static void keywordSearch(ForkJoinPool pool, File directory) {
        long startTime = System.currentTimeMillis();
//        List<String> keywords = Arrays.asList("software", "hardware", "programming", "language", "computer",
//                "algorithm", "data", "structure", "science", "technology", "internet", "network", "database");
        List<String> keywords = Arrays.asList("algol");
        KeywordSearchAnalyser keywordSearchAnalyser = new KeywordSearchAnalyser(directory, keywords);
        pool.invoke(keywordSearchAnalyser);
        Set<String> documentsWithKeywords = keywordSearchAnalyser.getDocumentsWithKeywords();
        System.out.println();
        System.out.println("Documents with keywords: " + documentsWithKeywords);
        long endTime = System.currentTimeMillis();
        System.out.println("Parallel time: " + (endTime - startTime) + " ms");
    }
}