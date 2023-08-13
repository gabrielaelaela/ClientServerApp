package utilities.usercommands;

import utilities.commandsutilities.CommandInfo;
import utilities.commandsutilities.CommandInvoker;
import utilities.iowork.Printable;
import utilities.sql.SQLCollection;
import utilities.upgradedcollections.UpgradedPriorityQueue;
import utilities.users.User;

import java.io.IOException;
import java.sql.SQLException;

public class PrintAscendingCommand implements ClientServerCommand {
    private Printable printable;
    private UpgradedPriorityQueue<?> queue = SQLCollection.getCollection();

    public PrintAscendingCommand(Printable printable) throws SQLException {
        this.printable = printable;
    }

    @Override
    public String execute(User user) throws IOException {
        final String[] output = {""};
        queue.forEach(elem -> output[0] += elem.toString() + "\n");

        return output[0];
    }

    @Override
    public String toString() {
        return "print_ascending";
    }

    public static String name() {
        return "print_ascending";
    }

    public static CommandInfo getInfo() {
        return new CommandInfo(0, 0, true, null);
    }
}
