package utilities.commandsutilities;

import java.util.ArrayList;

public class FilesToExecute {
    private static FilesToExecute instance;
    private final ArrayList<String> listOfCommands = new ArrayList<>();

    private FilesToExecute() {}

    public static FilesToExecute getInstance() {
        if (instance == null)
            instance = new FilesToExecute();
        return instance;
    }

    public void addFile(String filename) {
        this.listOfCommands.add(filename);
    }

    public boolean containsFile(String filename) {
        return this.listOfCommands.contains(filename);
    }

    public void remove(String filename) {
        this.listOfCommands.remove(filename);
    }

    public void clear() {
        this.listOfCommands.clear();
    }
}
