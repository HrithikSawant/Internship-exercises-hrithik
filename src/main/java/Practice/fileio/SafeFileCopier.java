package Practice.fileio;

import java.io.*;

public class SafeFileCopier {
    public static void main(String[] args) {

    }

    public static void copy(File inFile, File outFile) throws IOException {
        if (inFile.getCanonicalPath().equals(outFile.getCanonicalPath())) {
            return; //file are same
        }
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new BufferedInputStream(new FileInputStream(inFile));
            out = new BufferedOutputStream(new FileOutputStream(outFile));
            for (int c = in.read(); c != -1; c = in.read()) {
                out.write(c);
            }
        } finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }
    }
}
