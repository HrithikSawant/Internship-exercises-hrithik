package exercises.exercise3;

import org.junit.Test;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GrepTest {

    Grep grep = new Grep();

    @Test
    public void searchInFile() {

        //noMatch
        List<String> expected_noMatch = new ArrayList<>(List.of());
        List<String> noMatch =
                grep.searchInFile("beautiful",
                        Path.of("src", "test", "java", "resources", "A", "lorem.txt"));
        assertThat(noMatch, is(expected_noMatch));

        //oneMatch
        List<String> expected_oneLineMatch = new ArrayList<>(
                List.of("During the first part of your life."));
        List<String> oneLineMatch =
                grep.searchInFile("part",
                        Path.of("src", "test", "java", "resources", "A", "lorem.txt"));
        assertThat(oneLineMatch, is(expected_oneLineMatch));

        //manyMatch
        List<String> expected_manyMatches =
                new ArrayList<>(List.of("During the first part of your life.",
                        "you only become aware of happiness.", "once you have lost it."));
        List<String> manyMatches =
                grep.searchInFile("you",
                        Path.of("src", "test", "java", "resources", "A", "lorem.txt"));
        assertThat(manyMatches, is(expected_manyMatches));

    }

    @Test
    public void searchInSubstring() throws FileNotFoundException {
        InputStream StdIn = System.in;

        System.setIn(new FileInputStream("src/test/java/resources/E/test.txt"));
        //given
        String search = "apple";
        List<String> expected =
                new ArrayList<>(List.of("applesauce.", "applejacks.", "applecarts.", "appledrain.",
                        "appleshare.", "appleworks.", "appletiser.", "applecross"));

        //when
        Scanner sc = new Scanner(System.in);
        List<String> userInputs = new ArrayList<>();
        while (sc.hasNext()) {
            userInputs.add(sc.nextLine());
        }
        List<String> result = grep.searchInSubstring(search, userInputs);

        //then
        assertThat(expected, is(result));

        System.setIn(StdIn);
    }

    @Test
    public void printResultToStdOut() {
        PrintStream StdOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        //given
        List<String> lines = grep.searchInFile("you",
                Path.of("src", "test", "java", "resources", "A", "lorem.txt"));

        //when
        grep.writeToStdOut(lines);

        //then
        assertThat("During the first part of your life." +
                "you only become aware of happiness." +
                "once you have lost it.", is(outputStream.toString().replaceAll("\r\n|\r|\n", "")));

        System.setOut(StdOut);
    }

    @Test
    public void recursiveSearch() {
        LinkedHashMap<String, List<String>> linkedHashMap = new LinkedHashMap<>();

        //noMatch
        LinkedHashMap<String, List<String>> noMatch = grep.recursiveSearch("Dog",
                Path.of("src", "test", "java", "resources"));
        assertThat(noMatch, is(linkedHashMap));

        //one Match
        linkedHashMap.put("src\\test\\java\\resources\\D\\C\\Test.txt",
                List.of("This is a test file",
                        "one can test file using testCases"));
        LinkedHashMap<String, List<String>> oneMatch = grep.recursiveSearch("test",
                Path.of("src", "test", "java", "resources", "D"));
        assertThat(oneMatch, is(linkedHashMap));

        //many Match
        linkedHashMap.put("src\\test\\java\\resources\\E\\Friends.txt",
                List.of("This is first testFile",
                        "This is second testFile"));
        LinkedHashMap<String, List<String>> manyMatches = grep.recursiveSearch("test",
                Path.of("src", "test", "java", "resources"));
        assertThat(manyMatches, is(linkedHashMap));
    }

    @Test
    public void ReadFileAndWrite() {

        //given
        List<String> lines =
                grep.readFromFile(Path.of("src", "test", "java", "resources", "A", "lorem.txt"));
        //when
        grep.writeToFile(lines, Path.of("src", "test", "java", "resources", "A", "output.txt"));
        List<String> actual =
                grep.readFromFile(Path.of("src", "test", "java", "resources", "A", "output.txt"));

        //then
        assertThat(actual, is(lines));
    }


    //    @Test
//    public void throwsForFileNotFound(){
//        //Non Existing File
//
//        assertThatThrownBy(() -> grep.searchInFile("you",
//                Path.of("src", "test", "java", "resources", "A", "no_such_file.txt")))
//                .isInstanceOf(IOException.class)
//                .hasMessageContaining("File Not Found");
//    }


}