package Commands;

import Utility.Command;
import Utility.CommandResponse;

import java.util.List;

public class RemoveKey extends Command {
    public RemoveKey() {
        super("remove_key", "удалить элемент из коллекции по его ключу", "remove_key {id}");
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws ArgumentFormatException, InvalidArgumentException {

        if (arguments.size() != 1) throw new ArgumentFormatException(true, useExample);
        else try {
            int inputId = Integer.parseInt(arguments.get(0));
            if (elementService.collectionContains(inputId)) {
                elementService.removeByKey(inputId);
            } else throw new InvalidArgumentException("Коллекция не содержит элемента с данным ключом.");
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("Ключ является целым положительным числом, повторите попытку ввода;");
        }
        return new CommandResponse();
    }
}
