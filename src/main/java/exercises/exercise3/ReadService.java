package exercises.exercise3;

import java.nio.file.Path;
import java.util.List;

public interface ReadService {
    List<String> searchInFile(String search, Path filepath);

    List<String> searchInSubstring(String search, List<String> subStrings);
}
