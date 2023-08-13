package utilities.usercommands;

import server.Server;
import server.ServerRunner;
import utilities.commandsutilities.CommandInfo;
import utilities.serializers.Serializer;
import utilities.sql.SQLCollection;
import utilities.users.User;

import java.sql.SQLException;

public class LoginCommand implements ServerCommand {
    private String username;
    private String password;

    public LoginCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String execute(User user) throws SQLException {
        boolean result = SQLCollection.checkPassword(username, password);
        if (result) {
            user = new User(username, password);
        }
        return (result == true) ? "Login successfully completed\n" + Serializer.serialize(user) : "Wrong username or password";
    }

    @Override
    public String toString() {
        return "login";
    }

    public static String name() {
        return "login";
    }

    public static CommandInfo getInfo(){
        return new CommandInfo(2,0,false, null);
    }

}
