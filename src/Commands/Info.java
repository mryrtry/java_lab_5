package Commands;

import Utility.Command;
import Utility.CommandResponse;

import java.util.List;

public class Info extends Command {
    public Info() {
        super("info", "вывести информацию о коллекции", "info");
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws ArgumentFormatException {
        if (arguments.isEmpty()) console.println("info: Тип коллекции: " + elementService.getCollectionType().getSimpleName() + ", дата инициализации: " + elementService.getInitTime() + ", количество элементов: " + elementService.getCollectionSize() + ";");
        else throw new ArgumentFormatException(false, "info");
        return new CommandResponse();
    }
}
