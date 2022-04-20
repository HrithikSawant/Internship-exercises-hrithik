package exercises.exercise3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grep {
    public LinkedHashMap<String, List<String>> recursiveSearch(String searchWord, Path path) {
        LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
        try (Stream<Path> paths = Files.walk(path)) {
            paths.filter(Files::isRegularFile)
                    .forEach(p -> {
                        List<String> result = searchInFile(searchWord, p);
                        if (!result.isEmpty()) {
                            map.put(p.toString(), result);
                        }
                    });
        } catch (IOException ignore) {
        }
        return map;
    }

    public List<String> searchInFile(String searchWord, Path filename) {
        return search(searchWord, readFromFile(filename));
    }

    public List<String> searchInSubstring(String searchWord, List<String> subStrings) {
        return search(searchWord, subStrings); // searchOptions
    }

    private List<String> search(String searchStr, List<String> lines) {
        return lines.stream()
                .filter(s -> s.contains(searchStr))
                .collect(Collectors.toList());
    }

    public List<String> readFromFile(Path path) {
        try (Stream<String> stream = Files.lines(path)) {
            return stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.print("File Not Found :" + e.getMessage());
        }
        return Collections.emptyList();
    }

    public void writeToStdOut(List<String> result) {
        result.forEach(System.out::println);
    }

    public void writeToFile(List<String> lines, Path path) {
        try {
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException error) {
            System.err.print("File Err" + error.getMessage());
        }
    }


}
