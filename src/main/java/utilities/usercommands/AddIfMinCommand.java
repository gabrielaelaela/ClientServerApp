package utilities.usercommands;

import utilities.commandsutilities.CommandInfo;
import utilities.commandsutilities.CommandInvoker;
import utilities.factories.OrganizationFactory;
import utilities.iowork.Printable;
import utilities.organizations.Organization;
import utilities.sql.SQLCollection;
import utilities.upgradedcollections.UpgradedPriorityQueue;
import utilities.users.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddIfMinCommand implements ClientCommand {
    private Organization organization;
    private UpgradedPriorityQueue<Organization> organizationUpgradedPriorityQueue = SQLCollection.getCollection();
    private Printable printable;

    public AddIfMinCommand(Printable printable, Organization organization) throws SQLException {
        this.organization = organization;
        this.printable = printable;
    }

    @Override
    public String execute(User user ) throws IOException, SQLException {
        List<Organization> organizationList = new ArrayList<>(organizationUpgradedPriorityQueue);
        return organization.getId() < organizationUpgradedPriorityQueue.peek().getId() ? add() : "The organization was not added";
    }

    private String add() throws SQLException {
        SQLCollection.addToCollection(organization);
        return "The organization was added";
    }

    @Override
    public String toString() {
        return "add_if_min";
    }

    public static String name() {
        return "add_if_min";
    }

    public static CommandInfo getInfo() {
        return new CommandInfo(0, 1, true, Arrays.asList(OrganizationFactory.class));
    }
}
