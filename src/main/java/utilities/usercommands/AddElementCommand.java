package utilities.usercommands;

import utilities.commandsutilities.CommandInfo;
import utilities.commandsutilities.CommandInvoker;
import utilities.factories.OrganizationFactory;
import utilities.organizations.Organization;
import utilities.sql.SQLCollection;
import utilities.upgradedcollections.UpgradedPriorityQueue;
import utilities.users.User;

import java.sql.SQLException;
import java.util.Arrays;

public class AddElementCommand implements ClientServerCommand {
    private Organization organization;

    public AddElementCommand(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String execute(User user) throws SQLException {
        SQLCollection.addToCollection(organization);
        return "The organization was added";
    }

    @Override
    public String toString() {
        return "add";
    }

    public static String name() {
        return "add";
    }

    public static CommandInfo getInfo() throws Exception {
        return new CommandInfo(0,1,false, Arrays.asList(OrganizationFactory.class));
    }
}
