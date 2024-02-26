package Commands;

import Utility.Command;
import Utility.CommandResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class History extends Command {
    public History() {
        super("history", "вывести последние 7 команд (без их аргументов)", "history");
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws ArgumentFormatException {
        ArrayList<String> historyToStr = new ArrayList<>(console.getCommandsHistory());
        Collections.reverse(historyToStr);
        if (arguments.isEmpty()) console.println(historyToStr);
        else throw new ArgumentFormatException(false, useExample);
        return new CommandResponse();
    }
}
