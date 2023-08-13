package utilities.commandsutilities;

import utilities.usercommands.ClientCommand;
import utilities.usercommands.ClientServerCommand;
import utilities.usercommands.ServerCommand;
import utilities.usercommands.UserCommand;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CommandList {
    public static Map<String, Class<? extends UserCommand>> commandList = new HashMap<>();

    public static void fillList(String packageName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Reflections reflections = new Reflections(packageName);
        for (Class<? extends ClientCommand> userCommand: reflections.getSubTypesOf(ClientCommand.class)) {
            commandList.put((String) userCommand.getMethod("name").invoke(null), userCommand);
        }
        for (Class<? extends ServerCommand> userCommand: reflections.getSubTypesOf(ServerCommand.class)) {
            commandList.put((String) userCommand.getMethod("name").invoke(null), userCommand);
        }
        for (Class<? extends ClientServerCommand> userCommand: reflections.getSubTypesOf(ClientServerCommand.class)) {
            commandList.put((String) userCommand.getMethod("name").invoke(null), userCommand);
        }
    }
}
