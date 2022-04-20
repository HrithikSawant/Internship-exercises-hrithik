package Practice.fileio;

import java.io.File;

public class RootLister {
    public static void main(String[] args) {
        File[] root = File.listRoots();
        for (File file : root) {
            System.out.println(file);
        }
    }
}
