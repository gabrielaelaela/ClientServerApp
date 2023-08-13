package client;

import utilities.commandsutilities.CommandList;
import utilities.commandsutilities.CommandReader;
import utilities.iowork.*;
import utilities.users.User;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Client {
    private static ConsolePrint consolePrint;
    private static Scannable consoleScan;
    private static SocketWriter writer;
    private static SocketReader reader;

    private static Socket clientSocket;

    private static CommandReader commandReader;
    public static User user;

    public static void main(String[] args) throws Exception {
        setup();
        run();
    }

    private static void run() throws Exception {
        consolePrint.println("The server is ready for work. Let's start");
        try {
            waitForAction(commandReader);
        } catch (IOException | InterruptedException e) {
            setup();
            run();
        }
    }

    private static boolean reconnect() throws InterruptedException {
        System.out.println("We lost connection to the server. We will try to fix it up. Wait a minute, please");
        boolean serverConnected = false;
        long end = System.currentTimeMillis() + 60000;
        while((System.currentTimeMillis() < end) && !serverConnected) {
            try {
                setClientSocket();
                serverConnected = true;
            } catch (IOException exception) {}
            Thread.sleep(100);
        }
        return serverConnected;
    }

    private static void setClientSocket() throws IOException {
        clientSocket = new Socket("localhost", 8000);
    }

    private static void setup() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException, InterruptedException, InstantiationException, NoSuchAlgorithmException {
        try {
            setClientSocket();
        } catch (IOException e) {
            if (!reconnect()) {
                consolePrint.println("Sorry, there is something wrong with the server. Please, try again later");
                close();
            }
        }

        CommandList.fillList("utilities.usercommands");

        consolePrint = new ConsolePrint();
        consoleScan = new ConsoleScan(consolePrint);
        reader = new SocketReader(clientSocket, consolePrint);
        writer = new SocketWriter(clientSocket);

        commandReader = new CommandReader();

        if (user == null) {
            authorize();
        }
    }

    private static void authorize() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchAlgorithmException {
        try {
            ArrayList<Object> userArgs = commandReader.authorizeClient();
            writer.writeCommand(userArgs);
        } catch (NullPointerException e) {
            consolePrint.println(e.getMessage());
            consolePrint.println("Something went wrong. Please, try again");
            authorize();
        }

        int n = reader.scanInt();
        String answer = reader.scanLine();
        if (!answer.contains("successfully")) {
            consolePrint.println(answer);
            consolePrint.println("Please, try again");
            authorize();
            return;
        }
        consolePrint.println(answer);
        n--;
        while (n > 1) {
            answer = reader.scanLine();
            consolePrint.println(answer);
            n--;
        }

        user = reader.getUser();
    }

    private static void waitForAction(CommandReader commandReader) throws Exception {
        while (clientSocket.isConnected()) {
            ArrayList<Object> commandArgs;
            try {
                commandArgs = commandReader.commandToClient();
                writer.writeCommand(commandArgs);
            } catch (NullPointerException e) {
                consolePrint.println("");
                waitForAction(commandReader);
            }

            int n = reader.scanInt();
            String output = "";
            for (int i = 0; i < n; i++) {
                output += reader.scanLine() + "\n";
            }
            consolePrint.println(output);
        }
    }

    private static void close() throws IOException {
        writer.close();
        reader.close();
        clientSocket.close();
        System.exit(0);
    }
}
