package server;

import utilities.commandsutilities.CommandInvoker;
import utilities.iowork.ConsolePrint;
import utilities.iowork.SocketReader;
import utilities.iowork.SocketWriter;
import utilities.usercommands.UserCommand;
import utilities.users.User;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRunner extends Thread {

    private Socket clientSocket;

    private ConsolePrint consolePrint;
    private SocketReader reader;
    public SocketWriter writer;
    public static HashMap<Socket, User> usersMap;

    private ExecutorService readService = Executors.newFixedThreadPool(10);
    private ExecutorService respondService = Executors.newFixedThreadPool(10);
    private ExecutorService handleService = Executors.newCachedThreadPool();

    private RequestReader requestReader;
    private RequestHandler requestHandler;
    private RequestResponder requestResponder;

    public ServerRunner (Socket clientSocket, ConsolePrint consolePrint) {
        this.clientSocket = clientSocket;
        this.consolePrint = consolePrint;
        this.usersMap = new HashMap<>();
    }

    @Override
    public void run() {
        try {
            setClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setup();

        while (true) {
            try {
                completeRequests();
            } catch (NullPointerException e) {
                consolePrint.println("Connection " + clientSocket + " reset");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void setup() {
        requestReader = new RequestReader(reader, writer, clientSocket);
    }

    private void setClientSocket() throws IOException {
        consolePrint.println("Client " + clientSocket + " accepted");

        reader = new SocketReader(clientSocket, consolePrint);
        writer = new SocketWriter(clientSocket);
    }

    private void completeRequests() {
        while (true) {
            requestReader.run();
            //readService.submit(requestReader);
            UserCommand command = requestReader.getCommand();

            requestHandler = new RequestHandler(command, usersMap.get(clientSocket));
            //handleService.submit(requestHandler);
            requestHandler.run();
            String output = requestHandler.getOutput();
            String lines[] = output.split("\r\n|\r|\n");
            int numberOfLines = lines.length;

            requestResponder = new RequestResponder(writer, String.valueOf(numberOfLines), output);
            //respondService.submit(requestResponder);
            requestResponder.run();
        }
    }
}

class RequestReader implements Runnable {

    private UserCommand command = null;
    private SocketReader reader;
    private SocketWriter writer;
    private Socket clientSocket;

    public RequestReader(SocketReader reader, SocketWriter writer, Socket clientSocket) {
        this.reader = reader;
        this.writer = writer;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            command = reader.readCommand(clientSocket);
        } catch (SocketException e) {
            command = null;
        } catch (Exception e) {
            try {
                e.printStackTrace();
                System.out.println(e.getCause());
                writer.println("1");
                writer.println("Something went wrong");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public UserCommand getCommand() {
        return this.command;
    }
}

class RequestHandler implements Runnable {

    private String output;
    private User user;
    private UserCommand userCommand;

    public RequestHandler(UserCommand command, User user) {
        this.userCommand = command;
        this.user = user;
    }

    @Override
    public void run() {
        output = CommandInvoker.invoke(userCommand, user);
    }

    public String getOutput() {
        return this.output;
    }
}

class RequestResponder implements Runnable {

    private SocketWriter writer;
    private String numberOfLines;
    private String output;

    public RequestResponder(SocketWriter writer, String numberOfLines, String output) {
        this.writer = writer;
        this.numberOfLines = numberOfLines;
        this.output = output;
    }

    @Override
    public void run() {
        try {
            writer.println(numberOfLines);
            writer.println(output);
            output = "";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
