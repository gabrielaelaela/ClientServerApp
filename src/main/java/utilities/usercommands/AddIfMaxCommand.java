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

public class AddIfMaxCommand implements ClientServerCommand {
    private Organization organization;
    /**
     * Collection
     */
    private Printable printable;

    public AddIfMaxCommand(Printable printable, Organization organization) {
        this.organization = organization;
        this.printable = printable;
    }

    /**
     * Execute command method
     * @throws IOException
     */
    @Override
    public String execute(User user) throws IOException, SQLException {
        List<Organization> organizationList = new ArrayList<>(SQLCollection.getCollection());
        return organization.getId() > organizationList.get(organizationList.size()-1).getId() ? add() : "The organization was not added";
    }

    private String add() throws SQLException {
        SQLCollection.addToCollection(organization);
        return "The organization was added";
    }

    @Override
    public String toString() {
        return "add_if_max";
    }

    public static String name() {
        return "add_if_max";
    }

    public static CommandInfo getInfo() {
        return new CommandInfo(0, 1, true, Arrays.asList(OrganizationFactory.class));
    }
}
