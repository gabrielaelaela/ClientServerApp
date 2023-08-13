package utilities.usercommands;

import utilities.commandsutilities.CommandInfo;
import utilities.history.History;
import utilities.iowork.Printable;
import utilities.users.User;

public class HistoryCommand implements ClientServerCommand {
    private Printable printable;

    public HistoryCommand(Printable printable) {
        this.printable = printable;
    }

    @Override
    public String execute(User user) {
        String output = "";
        int length = History.getInstance().getLength();
        for (int i = 0; i < length; i++) {
            output += (History.getInstance().get(i).toString()) + "\n";
        }
        if (output.equals("")) output = "History is empty";
        return output;
    }

    @Override
    public String toString() {
        return "history";
    }

    public static String name() {
        return "history";
    }

    public static CommandInfo getInfo(){
        return new CommandInfo(0,0,true, null);
    }

}
