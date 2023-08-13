package utilities.usercommands;

import utilities.commandsutilities.CommandInfo;
import utilities.commandsutilities.CommandInvoker;
import utilities.iowork.Printable;
import utilities.sql.SQLCollection;
import utilities.upgradedcollections.UpgradedPriorityQueue;
import utilities.users.User;

import java.sql.SQLException;

public class InfoCommand implements ClientServerCommand {
    private UpgradedPriorityQueue<?> queue = SQLCollection.getCollection();
    private Printable printable;

    public InfoCommand(Printable printable) throws SQLException {
        this.printable = printable;
    }

    @Override
    public String execute(User user) throws SQLException {
        String output = "";
        output += "Type: Priority queue\n";
        output += "Number of the elements: " + SQLCollection.getNumberOfElements();
        return output;
    }

    @Override
    public String toString() {
        return "info";
    }

    public static String name() {
        return "info";
    }

    public static CommandInfo getInfo() {
        return new CommandInfo(0,0,true, null);
    }

}
