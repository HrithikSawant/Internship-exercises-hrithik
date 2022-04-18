package exercises.exercise3;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GrepTest {

    Grep grep = new Grep();

    String search = "apple";
    String filePath = "src/main/java/fileio/";

    private List<String> getStrings() {
        SearchOptions s = new SearchOptions();
        return grep.searchInFile(search,filePath+"lorem.txt");
    }

    @Test
    public void searchInFile() {
        List<String> actual = getStrings();
        List<String> expected = new ArrayList<>(Arrays.asList("apple",
                "applecart", "applecarts", "applejackle", "applejacks",
                "apples", "applesauce", "applesauces", "gRapple", "pinEapples"));
        assertThat(actual, is(expected));
    }

    @Test
    public void printResultToStdout() {
        List<String> actual = getStrings();
        grep.printResultToStdout(actual);
    }

    @Test
    public void printResultToFile() {
        List<String> actual = getStrings();
        grep.printResultToFile(actual,filePath+"output.txt");
    }

    @Test
    public void searchInSubstring() {
        List<String> expected = new ArrayList<>(Arrays.asList("applesauce.", "applejacks.", "applecarts.", "appledrain.", "appleshare.",
                "appleworks.", "appletiser.", "applecross"));
        List<String> result = grep.searchInSubstring(search,expected);
        grep.printResultToStdout(result);
    }
}