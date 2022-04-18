package exercises.exercise3;

import java.util.List;

public interface ReadService {
    List<String> searchInFile(String search, String filepath);
    List<String> searchInSubstring(String search,List<String> subStrings);
//    List<String> searchInFileAndOutputFile(String search, String InputPath,String outputPath);
}
