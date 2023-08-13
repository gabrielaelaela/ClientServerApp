package utilities.usercommands;

import server.Server;
import server.ServerRunner;
import utilities.commandsutilities.CommandInfo;
import utilities.commandsutilities.CommandInvoker;
import utilities.organizations.Organization;
import utilities.sql.SQLCollection;
import utilities.upgradedcollections.UpgradedPriorityQueue;
import utilities.users.User;

import java.sql.SQLException;

public class ClearCommand implements ClientServerCommand {

    public ClearCommand() throws SQLException {}

    @Override
    public String execute(User user) throws SQLException {
        SQLCollection.clearUsersCollection(user.getUsername());
        return "The collection was cleared";
    }

    @Override
    public String toString() {
        return "clear";
    }

    public static String name() {
        return "clear";
    }

    public static CommandInfo getInfo(){
        return new CommandInfo(0,0,false, null);
    }

}
