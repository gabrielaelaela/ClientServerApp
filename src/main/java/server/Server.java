package server;

import utilities.commandsutilities.CommandList;
import utilities.iowork.ConsolePrint;
import utilities.iowork.Printable;
import utilities.iowork.SocketWriter;
import utilities.sql.SQLCollection;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static SocketWriter writer;
    private static Printable consolePrint;
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {
        setup();

        int port = 8000;
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ServerRunner serverRunner = new ServerRunner(clientSocket, (ConsolePrint) consolePrint);
            executorService.submit(serverRunner);
            //serverRunner.start();
        }

    }

    private static void setup() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, SQLException {
        consolePrint = new ConsolePrint();
        consolePrint.println("The server is setting up");

        SQLCollection.setup();

        CommandList.fillList("utilities.usercommands");
    }

}
