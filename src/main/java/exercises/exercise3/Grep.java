package exercises.exercise3;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Grep implements ReadService, OutputService {
    SearchOptions searchOptions = new SearchOptions();

    public Grep() {}

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: Grep pattern [searchString,filename...]");
            System.exit(1);
        }
        new Grep(args);
    }

    private Grep(String[] args) {
        switch (args.length) {
            case 1:
                Scanner sc = new Scanner(System.in);
                List<String> strings = new ArrayList<>();
                while (sc.hasNext()) {
                    strings.add(sc.nextLine());
                }
                sc.close();
                printResultToStdout(searchInSubstring(args[0], strings));
                break;
            case 2:
                File file = new File(String.valueOf(Paths.get(args[1])));
                if (file.isFile()) {
                    printResultToStdout(searchInFile(args[0], args[1]));
                } else { //recursive
                    recursiveSearch(args[0], args[1]);
                }
                break;
            default:  //args.length == 3
                switch (args[2]) {
                    case "-o" -> printResultToFile(searchInFile(args[0], args[1]), args[3]);
                    case "-i" -> {
                        searchOptions.setIgnoreCase(true);
                        printResultToFile(searchInFile(args[0], args[1]), args[3]);
                    }
                    default -> System.err.println("Illegal Command");
                }
                break;
        }
    }

    private List<String> process(String search, List<String> stringList,SearchOptions  options) {
        if(options.isIgnoreCase()){
            return stringList.stream()
                    .filter(s -> s.toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }else{
            return stringList.stream()
                    .filter(s -> s.contains(search))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<String> searchInFile(String search, String filepath) {
        try (Stream<String> file = Files.lines(Paths.get(filepath))) {
         return process(search,file.collect(Collectors.toList()),searchOptions);
        } catch (Exception e) {
            System.err.print(e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> searchInSubstring(String search, List<String> subStrings) {
        return process(search, subStrings,searchOptions);
    }

    public void recursiveSearch(String search, String filepath) {
        try (Stream<Path> paths = Files.walk(Paths.get(filepath))) {
            paths.filter(p -> p.toString().contains(search))
                            .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printResultToStdout(List<String> result) {
        result.forEach(System.out::println);
    }

    @Override
    public void printResultToFile(List<String> results, String filename) {
        try (OutputStream out = new FileOutputStream(filename);
             Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
                results.forEach(str -> {
                    try {
                        writer.write(str+"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            });
        } catch (IOException e) {
            System.err.print(e);
        }
    }



}
