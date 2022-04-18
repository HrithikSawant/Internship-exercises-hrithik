package exercises.exercise3;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Grep implements ReadService, OutputService {

    @Override
    public List<String> searchInFile(String searchWord, Path path) {
        try (Stream<String> file = Files.lines(path)) {
            return search(searchWord, file.toList());//, searchOptions
        } catch (Exception e) {
            System.err.print(e);
            return Collections.emptyList();
        }
    }

    @Override
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
                            printResultToStdout(result);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        
    }

    @Override
    public void printResultToStdout(List<String> result) {
        result.forEach(System.out::println);
    }

    @Override
    public void printResultToFile(List<String> result, Path filename) {
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

    private List<String> search(String searchWord, List<String> listOfLines) { //, SearchOptions options
//        if(options.isIgnoreCase()){
//            return listOfLines.stream()
//                    .filter(s -> s.toLowerCase().contains(search.toLowerCase()))
//                    .collect(Collectors.toList());
//        }else{
        return listOfLines.stream()
                .filter(s -> s.contains(searchWord))
                .collect(Collectors.toList());
//        }
    }

    //    SearchOptions = new SearchOptions();

//    public Grep() {}

//    public void  recursiveSearch(String search, String filepath) {
//        try (Stream<Path> paths = Files.walk(Paths.get(filepath))) {
//            paths.filter(Files::isRegularFile)
//                    .map(p -> searchInFile(search,p.toString()))
//                    .filter(a -> !a.isEmpty())
//                    .forEach(System.out::println);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void main(String[] args) {
//        Grep g = new Grep();
//        g.recursiveSearch("the", Path.of("src/test/java/resources/"));
////        if (args.length < 1) {
////            System.err.println("Usage: Grep pattern [searchString,filename...]");
////            System.exit(1);
////        }
////        new Grep(args);
//    }

//    Grep(String[] args) {
//        switch (args.length) {
//            case 1:
//                Scanner sc = new Scanner(System.in);
//                List<String> strings = new ArrayList<>();
//                while (sc.hasNext()) {
//                    strings.add(sc.nextLine());
//                }
//                sc.close();
//                printResultToStdout(searchInSubstring(args[0], strings));
//                break;
//            case 2:
//                File file = new File(String.valueOf(Paths.get(args[1])));
//                if (file.isFile()) {
//                    printResultToStdout(searchInFile(args[0], args[1]));
//                } else { //recursive
//                    recursiveSearch(args[0], args[1]);
//                }
//                break;
//            default:  //args.length == 3
//                switch (args[2]) {
//                    case "-o" -> printResultToFile(searchInFile(args[0], args[1]), args[3]);
//                    case "-i" -> {
//                        searchOptions.setIgnoreCase(true);
//                        printResultToFile(searchInFile(args[0], args[1]), args[3]);
//                    }
//                    default -> System.err.println("Illegal Command");
//                }
//                break;
//        }
//    }


}
