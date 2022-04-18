package exercises.exercise3;

import java.io.IOException;
import java.util.List;

public interface OutputService {
    void printResultToStdout(List<String> result) ;//{System.out.print(results)}
    void printResultToFile(List<String> results,String filename) throws IOException;// { FileWriter.write(results)}
//    streamResultsToSocket(results, socket) { SocketWriter.write(results)}
}
