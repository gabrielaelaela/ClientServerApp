package utilities.iowork;

import client.Client;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import server.Server;
import server.ServerRunner;
import utilities.users.User;
import utilities.commandsutilities.CommandReader;
import utilities.usercommands.UserCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SocketReader implements Scannable {
    private BufferedReader reader;
    private Printable printable;

    public SocketReader(Socket clientSocket, Printable printable) throws IOException {
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.printable = printable;
    }

    @Override
    public String scanLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public int scanInt() throws IOException {
        String input = scanLine();
        if (input.isEmpty()) return scanInt();
        int inputInteger;
        try {
            inputInteger = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            printable.print("Wrong number format.");
            return scanInt();
        }
        return inputInteger;
    }

    @Override
    public float scanFloat() throws IOException {
        String input = scanLine();
        float inputFloat;
        try {
            inputFloat = Float.parseFloat(input);
        } catch (NumberFormatException e) {
            printable.print("Wrong number format.");
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
            printable.print("Wrong number format.");
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
            printable.print("Wrong number format.");
            return scanLong();
        }
        return inputLong;
    }

    public void close() throws IOException {
        this.reader.close();
    }

    @Override
    public boolean hasNextLine() {
        return false;
    }

    public UserCommand readCommand(Socket clientSocket) throws Exception {
        Gson gson = new Gson();
        String commandName = reader.readLine();
        if (!(commandName.contains("login") || commandName.contains("register"))) {
            try{
                String input = reader.readLine();
                ServerRunner.usersMap.put(clientSocket, gson.fromJson(input, User.class));
            } catch (JsonSyntaxException e) {
                Server.writer.println("1");
                Server.writer.println("You should authorize first");
                throw new NullPointerException();
            }
        }

        String input = "";
        ArrayList<Object> arguments = new ArrayList<>();
        arguments.add(commandName);
        input = reader.readLine();
        while (!input.equals("End of output")) {
            Class classType = Class.forName(input);
            input = reader.readLine();
            arguments.add(gson.fromJson(input, classType));
            input = reader.readLine();
        }
        CommandReader commandReader = new CommandReader();
        UserCommand command = commandReader.commandFromServer(arguments);
        return command;
    }

    public User getUser() throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(scanLine(), User.class);
    }
}
