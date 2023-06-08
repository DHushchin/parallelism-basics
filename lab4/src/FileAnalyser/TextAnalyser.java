package FileAnalyser;

import java.io.File;
import java.util.*;
import java.util.concurrent.RecursiveAction;

public abstract class TextAnalyser extends RecursiveAction {
    protected final File directory;

    public TextAnalyser(File directory) {
        this.directory = directory;
    }

    @Override
    protected void compute() {
        List<TextAnalyser> subTasks = new ArrayList<>();

        File[] files = directory.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                TextAnalyser subTask = createSubTask(file);
                subTasks.add(subTask);
                subTask.fork();
            } else {
                System.out.println("Analyzing file: " + file.getAbsolutePath());
                analyseFile(file);
            }
        }

        for (TextAnalyser subTask : subTasks) {
            subTask.join();
        }
    }

    protected abstract TextAnalyser createSubTask(File directory);

    protected abstract void analyseFile(File file);
}

