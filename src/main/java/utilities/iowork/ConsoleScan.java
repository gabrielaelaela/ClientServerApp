package utilities.iowork;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleScan implements Scannable {
    private Scanner scanner;
    private Printable printable;

    public ConsoleScan(Printable printable) {
        this.scanner = new Scanner(System.in);
        this.printable = printable;
    }

    @Override
    public int scanInt() throws IOException {
        String input = scanLine();
        int inputInteger;
        try {
            inputInteger = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            printable.print("Wrong number format, please, try again: ");
            return scanInt();
        }
        return inputInteger;
    }

    @Override
    public String scanLine() {
        String input = scanner.nextLine().trim();
        return input;
    }

    @Override
    public float scanFloat() throws IOException {
        String input = scanLine();
        float inputFloat;
        try {
            inputFloat = Float.parseFloat(input);
        } catch (NumberFormatException e) {
            printable.print("Wrong number format, please, try again: ");
            return scanFloat();
        }
        return inputFloat;
    }

    @Override
    public double scanDouble() throws IOException {
        String input = scanLine();
        double inputDouble;
        try {
            inputDouble = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            printable.print("Wrong number format, please, try again: ");
            return scanDouble();
        }
        return inputDouble;
    }

    @Override
    public long scanLong() throws IOException {
        String input = scanLine();
        long inputLong;
        try {
            inputLong = Long.parseLong(input);
        } catch (NumberFormatException e) {
            printable.print("Wrong number format, please, try again: ");
            return scanLong();
        }
        return inputLong;
    }

    @Override
    public boolean hasNextLine() {
        return scanner.hasNext();
    }

}
