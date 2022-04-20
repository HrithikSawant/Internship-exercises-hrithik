package Practice.fileio;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Paths {
    public static void main(String[] args) {
        File absolute = new File("src/test/java/resources/A/lorem.txt");
        File relative = new File("java/resources/A/lorem.txt");
        System.out.println("Absolute :");
        System.out.println("Name :" + absolute.getName());
        System.out.println("Path :" + absolute.getPath());
        System.out.println(absolute.getAbsolutePath());
        System.out.println(absolute.isAbsolute());
        System.out.println("Parent :" + absolute.getParent());
        System.out.println("Read :" + absolute.canRead());
        System.out.println("Write :" + absolute.canWrite());
        System.out.println("Execute :" + absolute.canExecute());
        System.out.println("Last Modified :" + new Date(absolute.lastModified()));


        try {
            System.out.println(absolute.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println();
        System.out.println("Relative :");
        System.out.println("Name :" + relative.getName());
        System.out.println("Path :" + relative.getPath());
        System.out.println("Parent :" + relative.getParent());
        System.out.println("Read :" + relative.canRead());
        System.out.println("Write :" + relative.canWrite());
        System.out.println("Execute :" + relative.canExecute());
        System.out.println("Rename");
//        absolute.renameTo(new File("lorem.txt"));

    }

}
