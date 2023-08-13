package utilities.usercommands;

import utilities.users.User;

import java.io.IOException;
import java.sql.SQLException;

public interface UserCommand {
    String execute(User user) throws IOException, SQLException;
}
