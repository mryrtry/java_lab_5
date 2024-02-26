package Commands;

import Utility.Command;
import Utility.CommandResponse;
import Utility.Console;

import java.util.List;

public class Update extends Command {
    public Update() {
        super("update", "обновить значение элемента коллекции, id которого равен заданному", "update {id}");
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws ArgumentFormatException, InvalidArgumentException, Console.InvalidScriptArgument {
        if (arguments.size() != 1) throw new ArgumentFormatException(false, useExample);
        else try {
            int inputId = Integer.parseInt(arguments.get(0));
            if (elementService.collectionContains(inputId)) {
                    elementService.updateByKey(console, inputId, fromFile);
            } else throw new InvalidArgumentException("Коллекция не содержит элемента с данным ключом.");
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("update: Ключ является целым положительным числом, повторите попытку ввода;");
        } catch (Insert.ValidationBreak e) {
            console.println("update: Выполнен выход из метода.");
        }
        return new CommandResponse();
    }
}
