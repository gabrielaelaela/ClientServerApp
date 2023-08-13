package utilities.iowork;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FilePrint implements Printable {
    BufferedWriter bufferedWriter;

    public FilePrint(String fileName) throws IOException {
        bufferedWriter = new BufferedWriter(new FileWriter(fileName));
    }

    @Override
    public void println(String s) throws IOException {
        bufferedWriter.write(s + "\n");
        bufferedWriter.close();
    }

    @Override
    public void print(String s) throws IOException {
        bufferedWriter.write(s);
        bufferedWriter.close();
    }
}
