package utilities.iowork;

import utilities.exceptions.WrongInputException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class FileScan implements Scannable {
    private BufferedInputStream bufferedInputStream;
    private boolean eof = false;

    public FileScan(String fileName) throws IOException {
        bufferedInputStream = new BufferedInputStream(new FileInputStream(fileName));
        if (bufferedInputStream.read() == -1)
            eof = true;
        bufferedInputStream.close();
        bufferedInputStream = new BufferedInputStream(new FileInputStream(fileName));
    }

    @Override
    public int scanInt() throws IOException {
        if (hasNextLine()){
            StringBuilder stringBuilder = new StringBuilder();
            while (true){
                int next = bufferedInputStream.read();
                if (next == -1){
                    eof = true;
                    break;
                }
                char nextChar = (char) next;
                if (nextChar == ' ' || nextChar == '\n')
                    break;
                stringBuilder.append(nextChar);
            }
            return Integer.parseInt(stringBuilder.toString().replaceAll("\r",""));
        }
        throw new WrongInputException("Not a number");
    }

    @Override
    public String scanLine() throws IOException {
        if (hasNextLine()){
            StringBuilder stringBuilder = new StringBuilder();
            while (true){
                int next = bufferedInputStream.read();
                if (next == -1){
                    eof = true;
                    break;
                }
                char nextChar = (char) next;
                if (nextChar == '\n')
                    break;
                stringBuilder.append(nextChar);
            }
            return stringBuilder.toString().replaceAll("\r","");
        }
        return null;
    }

    @Override
    public float scanFloat() throws IOException {
        if (hasNextLine()){
            StringBuilder stringBuilder = new StringBuilder();
            while (true){
                int next = bufferedInputStream.read();
                if (next == -1){
                    eof = true;
                    break;
                }
                char nextChar = (char) next;
                if (nextChar == ' ' || nextChar == '\n')
                    break;
                stringBuilder.append(nextChar);
            }
            return Float.parseFloat(stringBuilder.toString().replaceAll("\r",""));
        }
        throw new WrongInputException("Not a number");
    }

    @Override
    public double scanDouble() throws IOException {
        if (hasNextLine()){
            StringBuilder stringBuilder = new StringBuilder();
            while (true){
                int next = bufferedInputStream.read();
                if (next == -1){
                    eof = true;
                    break;
                }
                char nextChar = (char) next;
                if (nextChar == ' ' || nextChar == '\n')
                    break;
                stringBuilder.append(nextChar);
            }
            return Double.parseDouble(stringBuilder.toString().replaceAll("\r",""));
        }
        throw new WrongInputException("Not a number");
    }

    @Override
    public long scanLong() throws IOException {
        if (hasNextLine()){
            StringBuilder stringBuilder = new StringBuilder();
            while (true){
                int next = bufferedInputStream.read();
                if (next == -1){
                    eof = true;
                    break;
                }
                char nextChar = (char) next;
                if (nextChar == ' ' || nextChar == '\n')
                    break;
                stringBuilder.append(nextChar);
            }
            return Long.parseLong(stringBuilder.toString().replaceAll("\r",""));
        }
        throw new WrongInputException("Not a number");
    }

    @Override
    public boolean hasNextLine() {
        return !eof;
    }
}
