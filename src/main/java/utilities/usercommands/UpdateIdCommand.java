package utilities.usercommands;

import server.Server;
import server.ServerRunner;
import utilities.commandsutilities.CommandInfo;
import utilities.commandsutilities.CommandInvoker;
import utilities.factories.OrganizationFactory;
import utilities.organizations.Organization;
import utilities.sql.SQLCollection;
import utilities.upgradedcollections.UpgradedPriorityQueue;
import utilities.users.User;

import java.sql.SQLException;
import java.util.Arrays;

public class UpdateIdCommand implements ClientServerCommand {
    private Organization organization;
    private int id;

    public UpdateIdCommand(String id, Organization organization) {
        this.organization = organization;
        this.id = Integer.parseInt(id);
    }

    @Override
    public String execute(User user) throws SQLException {
        if (!user.getUsername().equals(SQLCollection.getUsername(id))) return "You can only edit your data";

        SQLCollection.changeObject(organization);
        return "Done";
    }

    @Override
    public String toString() {
        return "update_id";
    }

    public static String name() {
        return "update_id";
    }

    public static CommandInfo getInfo(){
        return new CommandInfo(1,1,false, Arrays.asList(OrganizationFactory.class));
    }
}
