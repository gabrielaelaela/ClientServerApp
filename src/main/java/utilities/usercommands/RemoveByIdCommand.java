package utilities.usercommands;

import server.Server;
import server.ServerRunner;
import utilities.commandsutilities.CommandInfo;
import utilities.commandsutilities.CommandInvoker;
import utilities.idobjects.IdObject;
import utilities.organizations.Organization;
import utilities.sql.SQLCollection;
import utilities.upgradedcollections.UpgradedPriorityQueue;
import utilities.users.User;

import java.io.IOException;
import java.sql.SQLException;

public class RemoveByIdCommand implements ClientServerCommand {
    private int id;

    public RemoveByIdCommand(String id) throws SQLException {
        this.id = Integer.parseInt(id);
    }

    @Override
    public String execute(User user) throws IOException, SQLException {
        if (!user.getUsername().equals(SQLCollection.getUsername(id))) return "You can only edit your data";
        SQLCollection.removeById(id);
        return "Done";
    }

    @Override
    public String toString() {
        return "remove_by_id";
    }

    public static String name() {
        return "remove_by_id";
    }

    public static CommandInfo getInfo() {
        return new CommandInfo(1,0,false, null);
    }

}
