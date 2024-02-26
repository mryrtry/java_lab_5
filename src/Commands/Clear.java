package Commands;

import Utility.Command;
import Utility.CommandResponse;

import java.util.List;

public class Clear extends Command {
    public Clear() {
        super("clear", "очистить коллекцию", "clear");
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws ArgumentFormatException {
        if (arguments.isEmpty()) {
            if (elementService.getCollectionSize() == 0) console.println("clear: Коллекция пуста.");
            else {
                console.println("clear: Коллекция была очищена.");
                elementService.clearCollection();
            }
        }
        else throw new ArgumentFormatException(false, useExample);
        return new CommandResponse();
    }
}
