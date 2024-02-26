package Commands;

import Utility.Command;
import Utility.CommandResponse;

import java.util.List;

public class Help extends Command {
    public Help() {
        super("help", "вывести справку по доступным командам", "help");
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws ArgumentFormatException {
        for (Command command: console.commands.values())
            console.println(command.getName() + ": " + command.getDescription() + "; Пример использования: '" + command.getUseExample() + "'");
        return new CommandResponse();
    }
}
