package utilities.commandsutilities;

import utilities.factories.ObjectsFactory;
import java.util.List;

public class CommandInfo {
    private final int simpleArgs;
    private final int complexArgs;
    private final boolean isPrintable;
    private final List<Class<? extends ObjectsFactory>> complexConstructors;

    public CommandInfo(int simpleArgs, int complexArgs, boolean isPrintable, List<Class<? extends ObjectsFactory>> complexConstructors) {
        this.simpleArgs = simpleArgs;
        this.complexArgs = complexArgs;
        this.isPrintable = isPrintable;
        this.complexConstructors = complexConstructors;
    }
    public int getSimpleArgs(){
        return simpleArgs;
    }
    public int getComplexArgs(){
        return complexArgs;
    }
    public boolean getIsPrintable(){
        return isPrintable;
    }
    public List<Class<? extends ObjectsFactory>> getComplexConstructors() {
        return complexConstructors;
    }
}
