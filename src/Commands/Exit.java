package Commands;

import Utility.Command;
import Utility.CommandResponse;

import java.util.List;

public class Exit extends Command {
    public Exit() {
        super("exit", "завершить программу (без сохранения в файл)", "exit");
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws ArgumentFormatException {
        if (arguments.isEmpty()) System.exit(0);
        else throw  new ArgumentFormatException(false, useExample);
        return new CommandResponse();
    }
}
