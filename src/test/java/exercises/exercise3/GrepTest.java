package exercises.exercise3;

import org.junit.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GrepTest {

    Grep grep = new Grep();

    String filePath = "src/test/java/resources/";


    private List<String> getStrings() {
        String search = "you";
        return grep.searchInFile(search, Path.of(filePath + "A/lorem.txt"));
    }

    @Test
    public void searchInFile() {
        List<String> actual = getStrings();
        List<String> expected = new ArrayList<>(List.of("During the first part of your life, you only become aware of happiness once you have lost it."));
        assertThat(actual, is(expected));
    }

    @Test
    public void printResultToStdout() {
        List<String> actual = getStrings();
        grep.writeToStdOut(actual);
    }

    @Test
    public void printResultToFile() {
        List<String> actual = getStrings();
        grep.writeToFile(actual, Path.of(filePath + "B/output.txt"));
        List<String> expected = new ArrayList<>(List.of("During the first part of your life, you only become aware of happiness once you have lost it."));
        assertThat(actual, is(expected));
    }

    @Test
    public void searchInSubstring() {
        String search = "apple";
        List<String> expected = new ArrayList<>(Arrays.asList("applesauce.", "applejacks.", "applecarts.", "appledrain.", "appleshare.",
                "appleworks.", "appletiser.", "applecross"));
        List<String> result = grep.searchInSubstring(search, expected);
        grep.writeToStdOut(result);
    }

    @Test
    public void recursiveSearch() {
        String path = "src/test/java/resources/";
        grep.recursiveSearch("you", Path.of(path));
    }


}