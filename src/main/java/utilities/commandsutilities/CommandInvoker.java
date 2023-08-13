package utilities.commandsutilities;

import utilities.history.History;
import utilities.sql.SQLCollection;
import utilities.usercommands.UserCommand;
import utilities.users.User;

public class CommandInvoker {

    public static String invoke(UserCommand userCommand, User user) {
        try {
            if (!(user == null)) {
                if (SQLCollection.checkPassword(user.getUsername(), user.getPassword())) {
                    History.getInstance().addCommandToHistory(userCommand);
                    return userCommand.execute(user);
                } else return "Password or username is wrong";
            } else {
                History.getInstance().addCommandToHistory(userCommand);
                return userCommand.execute(user);
            }


        } catch (Exception e) {
            System.out.println("-----------!!!!!!!!!!!!!!!!!!!!!!!!-----------");
            if (e.getMessage() == null) System.out.println(e.getCause().getMessage());
            else System.out.println(e.getMessage());
            System.out.println("-----------!!!!!!!!!!!!!!!!!!!!!!!!-----------");
            System.out.println();
        }
        return null;
    }
}
