package Practice.fileio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
//        //note /src will consider for user.dir
//        find10LongestWords("src/main/java/Practice.fileio/lorem.txt");
//        wordsEachLength("src/main/java/Practice.fileio/lorem.txt");
        fileList("src/main/");

    }

    //You want to process the contents of a text file using streams.
    //Finding the 10 longest words in the web2 dictionary
    public static void find10LongestWords(String filepath) {
        try (Stream<String> file = Files.lines(Paths.get(filepath))) {
            file.filter(s -> s.length() > 2)
                    .sorted(Comparator.comparing(String::length).reversed())
                    .limit(10)
                    .forEach(p -> System.out.printf("%s (%d) %n", p, p.length()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Number of words of each length, in descending order
    public static void wordsEachLength(String filepath) {
        try (Stream<String> file = Files.lines(Paths.get(filepath))) {
            Map<Integer, Long> map = file.filter(s -> s.length() > 2)
                    .collect(Collectors.groupingBy(String::length, Collectors.counting()));

            map.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                    .forEach(e -> System.out.printf("Length %d: %d words%n",
                            e.getKey(), e.getValue()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Example 7-6. Using Files.list(path)
    //this will list all the child dir or files of given path
    public static void fileList(String filepath) {
        try (Stream<Path> list = Files.list(Paths.get(filepath))){
            list.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
