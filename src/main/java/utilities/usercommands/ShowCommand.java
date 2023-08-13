package utilities.usercommands;

import utilities.commandsutilities.CommandInfo;
import utilities.commandsutilities.CommandInvoker;
import utilities.iowork.Printable;
import utilities.sql.SQLCollection;
import utilities.upgradedcollections.UpgradedPriorityQueue;
import utilities.users.User;

import java.sql.SQLException;

public class ShowCommand implements ClientServerCommand {
    private UpgradedPriorityQueue<?> queue = SQLCollection.getCollection();
    private Printable printable;

    public ShowCommand(Printable printable) throws SQLException {
        this.printable = printable;
    }

    /**
     * Execute command method
     */
    @Override
    public String execute(User user) {
        final String[] output = {""};
        queue.forEach(elem -> output[0] += elem.toString() + "\n");
        if (queue.isEmpty()) output[0] = "The collection is empty";
        return output[0];
    }

    @Override
    public String toString() {
        return "show";
    }

    public static String name() {
        return "show";
    }

    public static CommandInfo getInfo(){
        return new CommandInfo(0,0,true, null);
    }

}
