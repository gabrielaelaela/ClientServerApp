package utilities.usercommands;

import utilities.commandsutilities.CommandInfo;
import utilities.commandsutilities.CommandInvoker;
import utilities.iowork.Printable;
import utilities.organizations.Organization;
import utilities.sql.SQLCollection;
import utilities.upgradedcollections.UpgradedPriorityQueue;
import utilities.users.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CountGreaterThanAnnualTurnoverCommand implements ClientServerCommand {
    private long annualTurnover;
    private ArrayList<Long> anuualTurnoversList = SQLCollection.getAnnualTurnovers();
    private Printable printable;

    public CountGreaterThanAnnualTurnoverCommand(Printable printable, String annualTurnover) throws SQLException {
        this.annualTurnover = Long.parseLong(annualTurnover);
        this.printable = printable;
    }

    @Override
    public String execute(User user) throws IOException {
        int number = (int) anuualTurnoversList.stream().filter(elem -> elem > this.annualTurnover).count();
        return String.valueOf(number);
    }

    @Override
    public String toString() {return "count_greater_than_annual_turnover";}

    public static String name() {return "count_greater_than_annual_turnover";}

    public static CommandInfo getInfo() {
        return new CommandInfo(1, 0, true, null);
    }

}
