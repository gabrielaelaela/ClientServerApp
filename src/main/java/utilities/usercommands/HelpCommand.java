package utilities.usercommands;

import utilities.commandsutilities.CommandInfo;
import utilities.iowork.Printable;
import utilities.users.User;

public class HelpCommand implements ClientServerCommand {
    private Printable printable;

    public HelpCommand(Printable printable) {
        this.printable = printable;
    }

    @Override
    public String execute(User user) {
        return ("help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add : добавить новый элемент в коллекцию\n" +
                "update_id id : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "add_if_max : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "add_if_min : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "history : вывести последние 10 команд (без их аргументов)\n" +
                "count_greater_than_annual_turnover annualTurnover : вывести количество элементов, значение поля annualTurnover которых больше заданного\n" +
                "print_ascending : вывести элементы коллекции в порядке возрастания\n" +
                "print_descending : вывести элементы коллекции в порядке убывания");
    }

    @Override
    public String toString() {
        return "help";
    }

    public static String name() {
        return "help";
    }

    public static CommandInfo getInfo(){
        return new CommandInfo(0,0,true, null);
    }

}
