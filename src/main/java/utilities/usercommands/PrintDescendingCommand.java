package utilities.usercommands;

import utilities.commandsutilities.CommandInfo;
import utilities.commandsutilities.CommandInvoker;
import utilities.iowork.Printable;
import utilities.sql.SQLCollection;
import utilities.upgradedcollections.UpgradedPriorityQueue;
import utilities.users.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrintDescendingCommand implements ClientServerCommand {
    private Printable printable;
    private UpgradedPriorityQueue<?> queue = SQLCollection.getCollection();

    public PrintDescendingCommand(Printable printable) throws SQLException {
        this.printable = printable;
    }

    @Override
    public String execute(User user) throws IOException {
        String output = "";
        List<?> queueInList = new ArrayList<Object>(queue);
        for(int i = queueInList.size()-1; i > -1; i--) {
            output += queueInList.get(i).toString();
        }
        return output;
    }

    @Override
    public String toString() {
        return "print_descending";
    }

    public static String name() {
        return "print_descending";
    }

    public static CommandInfo getInfo() {
        return new CommandInfo(0, 0, true, null);
    }

}
