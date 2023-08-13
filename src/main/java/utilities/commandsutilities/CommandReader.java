package utilities.commandsutilities;

import client.Client;
import utilities.factories.ObjectsFactory;
import utilities.iowork.ConsolePrint;
import utilities.iowork.ConsoleScan;
import utilities.iowork.Printable;
import utilities.iowork.Scannable;
import utilities.organizations.Organization;
import utilities.upgradedcollections.UpgradedPriorityQueue;
import utilities.usercommands.ClientCommand;
import utilities.usercommands.ServerCommand;
import utilities.usercommands.UserCommand;
import utilities.users.User;

import java.io.Console;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class CommandReader {
    private Printable consolePrint = new ConsolePrint();
    private Scannable consoleScan = new ConsoleScan(consolePrint);

    private UpgradedPriorityQueue<Organization> organizationQueue;

    private Class<? extends UserCommand> getCommand(String commandName) {
        return CommandList.commandList.get(commandName);
    }

    private CommandInfo getCommandInfo(Class<? extends UserCommand> command) throws NoSuchMethodException, SecurityException, InvocationTargetException, IllegalAccessException {
        return (CommandInfo) command.getMethod("getInfo").invoke(command);
    }

    private List<String> getWordList(String line) {
        return Arrays.asList(line.split("[ \r]"));
    }

    private List<Object> askComplexArgs(CommandInfo commandInfo, Printable printable, Scannable scannable) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < commandInfo.getComplexArgs(); ++i){
            ObjectsFactory objectsFactory = (ObjectsFactory) commandInfo.getComplexConstructors().get(i).getConstructors()[0].newInstance(printable, scannable);
            list.add(objectsFactory.buildWithQuestions());
        }
        return list;
    }

    private List<String> scanSimpleArgs(CommandInfo commandInfo, List<String> words) {
        List<String> list = new ArrayList<>();
        int numOfSimpArgs = commandInfo.getSimpleArgs();
        if (words.size() < numOfSimpArgs) {
            System.out.println("You forgot to enter the argument");
        }
        try {
            for (int i = 0; i < numOfSimpArgs; ++i){
                list.add(words.get(i));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("You forgot to enter the argument");
        }

        return list;
    }

    private List<Object> scanComplexArgs(CommandInfo commandInfo, Printable printable, Scannable scannable) throws InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < commandInfo.getComplexArgs(); ++i){
            ObjectsFactory objectsFactory = (ObjectsFactory) commandInfo.getComplexConstructors().get(i).getConstructors()[0].newInstance(printable, scannable);
            list.add(objectsFactory.buildWithoutQuestions(Client.user));
        }
        return list;
    }

    private ArrayList<Object> getArgs(Class<? extends UserCommand> command, CommandInfo commandInfo, List<String> words) throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        List<String> simpleArgs = scanSimpleArgs(commandInfo, words);
        List<Object> complexArgs = askComplexArgs(commandInfo, consolePrint, consoleScan);
        ArrayList<Object> finalArgs = new ArrayList<>();

        String commandName = (String) command.getMethod("name").invoke(null);

        finalArgs.add(commandName);
        if (Client.user != null) finalArgs.add(Client.user);
        if (commandInfo.getIsPrintable()){
            finalArgs.add(consolePrint);
        }
        if (!simpleArgs.isEmpty()) finalArgs.addAll(simpleArgs);
        if (!complexArgs.isEmpty()) finalArgs.addAll(complexArgs);
        finalArgs.removeAll(Collections.singleton(null));
        return finalArgs;
    }

    private UserCommand createCommand(Class<? extends UserCommand> command, ArrayList<Object> finalArgs) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        java.lang.reflect.Constructor<?> constructor = command.getConstructors()[0];
        finalArgs.remove(0);
        if (!finalArgs.isEmpty()) if (finalArgs.get(0) instanceof User) finalArgs.remove(0);
        return (UserCommand) constructor.newInstance(finalArgs.toArray());
    }

    public UserCommand commandFromConsole() throws Exception {
       consolePrint.print("Enter command name: ");

        if (!consoleScan.hasNextLine()) System.exit(0);
        String input = consoleScan.scanLine();

        List<String> words = getWordList(input);
        Class<? extends UserCommand> command = getCommand(words.get(0));
        CommandInfo commandInfo = getCommandInfo(command);


        UserCommand userCommand = createCommand(command, getArgs(command, commandInfo, words));

        return userCommand;
    }

    public ArrayList<Object> commandToClient() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        consolePrint.print("Enter command name: ");

        if (!consoleScan.hasNextLine()) System.exit(0);
        String input = consoleScan.scanLine();

        List<String> words = getWordList(input);
        String commandName = words.get(0);

        Class<? extends UserCommand> command = getCommand(commandName);
        CommandInfo commandInfo = getCommandInfo(command);

        Class<?>[] interfaces = command.getInterfaces();
        boolean isClientCommand = false;
        boolean isServerCommand = false;

        for (int i = 0; i < interfaces.length; i++) {
            if (interfaces[i].equals(ClientCommand.class)) isClientCommand = true;
            if (interfaces[i].equals(ServerCommand.class)) isServerCommand = true;
        }

        if (isClientCommand) {
            CommandInvoker.invoke(createCommand(command, getArgs(command, commandInfo, words)), Client.user);
            return null;
        }

        if (isServerCommand) {
            consolePrint.println("Sorry, you cannot do this. The collection will be saved automatically");
            return null;
        }

        return getArgs(command, commandInfo, words);
    }

    public ArrayList<Object> authorizeClient() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchAlgorithmException {
        consolePrint.print("Do you want to register or login? ");
        if (!consoleScan.hasNextLine()) System.exit(0);
        String commandName = consoleScan.scanLine().trim().toLowerCase(Locale.ROOT);
        Class<? extends UserCommand> command = getCommand(commandName);
        CommandInfo commandInfo = getCommandInfo(command);

        consolePrint.print("Enter username: ");
        if (!consoleScan.hasNextLine()) System.exit(0);
        String username = consoleScan.scanLine();

        String password = getPasswordMasked();
        password = encryptPassword(password);

        List<String> words = new ArrayList<>();
        words.add(username);
        words.add(password);
        return getArgs(command, commandInfo, words);
    }

    private static String getPasswordMasked()
    {
        char[] passwd;
        while (true) {
            Console console = System.console();
            passwd = console.readPassword("Enter password:");
            if (passwd != null) {
                if (passwd.length > 0) {
                    return new String(passwd);
                } else {
                    System.out.println("Invalid input\n");
                }
            }
        }
    }

    private String encryptPassword(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] messageDigest = md.digest(input.getBytes());

        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);

        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

    public UserCommand commandFromServer(ArrayList<Object> input) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<? extends UserCommand> command = getCommand((String) input.get(0));
        UserCommand userCommand = createCommand(command, input);

        return userCommand;
    }

    public List<UserCommand> commandsFromFile(Scannable scannable) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        List<UserCommand> userCommands = new ArrayList<>();
        int lineNumber = 0;
        while (scannable.hasNextLine()){
            ++lineNumber;
            String line = scannable.scanLine();
            if (line == null)
                break;
            if (line.equals(""))
                continue;
            List<String> words = getWordList(line);

            Class<? extends UserCommand> command;

            try {
                command = getCommand(words.get(0));
            } catch (Exception e) {
                System.out.println();
                System.out.println("---------------Exception---------------");
                System.out.println(e.getMessage() + " on line " + lineNumber);
                System.out.println("---------------------------------------");
                System.out.println();
                break;
            }
            CommandInfo commandInfo = getCommandInfo(command);
            UserCommand userCommand = createCommand(command, getArgs(command, commandInfo, words));
            userCommands.add(userCommand);
        }
        return userCommands;
    }
}
