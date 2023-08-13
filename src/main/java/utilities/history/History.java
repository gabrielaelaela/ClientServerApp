package utilities.history;

import utilities.usercommands.UserCommand;

import java.util.ArrayList;

public class History {
    private static History instance;
    private ArrayList<UserCommand> listOfCommands = new ArrayList<>();

    private History() {}

    public static History getInstance() {
        if (instance == null)
            instance = new History();
        return instance;
    }

    public void addCommandToHistory(UserCommand command) {
        this.listOfCommands.add(command);
        if (listOfCommands.size() > 10)
            listOfCommands.remove(0);
    }

    public UserCommand get(int index){
        return listOfCommands.get(index);
    }

    public int getLength(){
        return listOfCommands.size();
    }

}
