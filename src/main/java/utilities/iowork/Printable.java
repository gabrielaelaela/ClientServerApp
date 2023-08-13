package utilities.iowork;

import java.io.IOException;

public interface Printable {
    void println(String s) throws IOException;
    void print(String s) throws IOException;
}
