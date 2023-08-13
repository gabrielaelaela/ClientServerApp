package utilities.iowork;

import java.io.IOException;

public interface Scannable {
    int scanInt() throws IOException;
    String scanLine() throws IOException;
    float scanFloat() throws IOException;
    double scanDouble() throws IOException;
    long scanLong() throws IOException;
    boolean hasNextLine();
}
