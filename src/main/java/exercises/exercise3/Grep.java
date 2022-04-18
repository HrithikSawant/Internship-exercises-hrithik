package exercises.exercise3;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grep {

    public List<String> searchInFile(String searchWord, Path path) {
        try (Stream<String> file = Files.lines(path)) {
            return search(searchWord, file.toList());//, searchOptions
        } catch (Exception e) {
            System.err.print(e);
        }
        return Collections.emptyList();
    }

    public List<String> searchInSubstring(String searchWord, List<String> subStrings) {
        return search(searchWord, subStrings); //, searchOptions
    }

    public void recursiveSearch(String searchWord, Path path) {
        try (Stream<Path> paths = Files.walk(path)) {
            paths.filter(Files::isRegularFile)
                    .forEach(w -> {
                        List<String> result = searchInFile(searchWord, w);
                        if (!result.isEmpty()) {
                            System.out.print(w + " : ");
                            writeToStdOut(result);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToStdOut(List<String> result) {
        result.forEach(System.out::println);
    }

    public void writeToFile(List<String> result, Path filename) {
        try (OutputStream out = new FileOutputStream(filename.toFile());
             Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            result.forEach(str -> {
                try {
                    writer.write(str + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.err.print(e);
        }
    }

    private List<String> search(String searchWord, List<String> listOfLines) {
        return listOfLines.stream()
                .filter(s -> s.contains(searchWord))
                .collect(Collectors.toList());
    }
}
