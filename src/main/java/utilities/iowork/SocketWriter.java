package utilities.iowork;

import client.Client;
import utilities.users.User;
import utilities.serializers.Serializer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SocketWriter implements Printable {
    BufferedWriter writer;

    public SocketWriter(Socket clientSocket) throws IOException {
        writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    @Override
    public void println(String s) throws IOException {
        writer.write(s);
        writer.newLine();
        writer.flush();
    }

    @Override
    public void print(String s) throws IOException {
        writer.write(s);
        writer.flush();
    }

    public void close() throws IOException {
        this.writer.close();
    }

    public void writeCommand(ArrayList<Object> input) throws IOException {
        println(input.get(0).toString());
        input.remove(0);
        if (input.get(0) instanceof User) {
            println(Serializer.serialize(input.get(0)));
            input.remove(0);
        }

        for (Object object: input) {
            println(object.getClass().toString().replaceAll("class ", ""));
            println(Serializer.serialize(object));
        }
        println("End of output");
    }
}
