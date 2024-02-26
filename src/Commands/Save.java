package Commands;

import Utility.Command;
import Utility.CommandResponse;
import java.util.List;

public class Save extends Command {
    public Save() {
        super("save", "сохранить коллекцию в файл", "save");
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws ArgumentFormatException {
        if (arguments.isEmpty()) {
            console.getDataProvider().saveCondition(elementService.getUnparsedInitTime(), elementService.getCollection(), console.getCommandsHistory());
        } else throw new ArgumentFormatException(false, useExample);
        return new CommandResponse();
    }
}
