package utilities.usercommands;

import utilities.commandsutilities.CommandInfo;
import utilities.commandsutilities.CommandInvoker;
import utilities.commandsutilities.CommandReader;
import utilities.commandsutilities.FilesToExecute;
import utilities.exceptions.RecursiveException;
import utilities.iowork.FileScan;
import utilities.iowork.Scannable;
import utilities.organizations.Organization;
import utilities.sql.SQLCollection;
import utilities.upgradedcollections.UpgradedPriorityQueue;
import utilities.users.User;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class ExecuteScriptCommand implements ClientServerCommand {
    private String fileName;

    public ExecuteScriptCommand(String fileName) throws SQLException {
        File file = new File(fileName);
        this.fileName = file.getAbsolutePath();
        if (FilesToExecute.getInstance().containsFile(this.fileName)) throw new RecursiveException("Attention! The recursion was detected!");
        FilesToExecute.getInstance().addFile(this.fileName);
    }

    @Override
    public String execute(User user) throws IOException {
        try {
            Scannable fileScan = new FileScan(fileName);
            CommandReader commandReader = new CommandReader();
            commandReader.commandsFromFile(fileScan).forEach(userCommand -> CommandInvoker.invoke(userCommand, user));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            FilesToExecute.getInstance().remove(fileName);
        }

        return "Done";
    }

    @Override
    public String toString() {
        return "execute_script";
    }

    public static String name(){
        return "execute_script";
    }

    public static CommandInfo getInfo(){
        return new CommandInfo(1,0,false, null);
    }

}
