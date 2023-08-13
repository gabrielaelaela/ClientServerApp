package utilities.iowork;

public class ConsolePrint implements Printable {
    public ConsolePrint() {}

    @Override
    public void println(String s) {
        System.out.println(s);
    }

    @Override
    public void print(String s) {
        System.out.print(s);
    }
}
