package utilities.usercommands;

import utilities.commandsutilities.CommandInfo;
import utilities.iowork.Printable;
import utilities.users.User;

import java.io.IOException;

public class ExitCommand implements ClientCommand {

    public ExitCommand() {

    }

    @Override
    public String execute(User user) {
        System.exit(0);
        return "The program should have been ended";
    }

    @Override
    public String toString() {
        return "exit";
    }

    public static String name() {
        return "exit";
    }

    public static CommandInfo getInfo(){
        return new CommandInfo(0,0,false, null);
    }

}
