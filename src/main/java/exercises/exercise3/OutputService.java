package exercises.exercise3;

import java.nio.file.Path;
import java.util.List;

public interface OutputService {
    void printResultToStdout(List<String> result); //{System.out.print(results)}

    void printResultToFile(List<String> results, Path filename);
}
