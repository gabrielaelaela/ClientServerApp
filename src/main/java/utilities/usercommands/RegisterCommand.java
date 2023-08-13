package utilities.usercommands;

import utilities.commandsutilities.CommandInfo;
import utilities.sql.SQLCollection;
import utilities.users.User;

import java.sql.SQLException;

public class RegisterCommand implements ServerCommand {
    private String username;
    private String password;

    public RegisterCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String execute(User user) throws SQLException {
        if (SQLCollection.containsUser(username)) {
            return "The user with this username already exists";
        } else {
            SQLCollection.addUser(username, password);
        }
        LoginCommand loginCommand = new LoginCommand(username, password);
        return "Register successfully completed\n" + loginCommand.execute(user);
    }

    @Override
    public String toString() {
        return "register";
    }

    public static String name() {
        return "register";
    }

    public static CommandInfo getInfo(){
        return new CommandInfo(2,0,false, null);
    }

}
