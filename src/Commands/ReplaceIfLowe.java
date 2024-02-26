package Commands;

import Utility.Command;
import Utility.CommandResponse;
import Utility.Console;
import Utility.Element;

import java.util.ArrayList;
import java.util.List;

public class ReplaceIfLowe extends Command {
    public ReplaceIfLowe() {
        super("replace_if_lowe", "заменить значение по ключу, если новое значение меньше старого", "replace_if_lowe {id}");
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws Console.InvalidScriptArgument, ArgumentFormatException {
        if (arguments.size() == 1) {
            try {
                int inputId = Integer.parseInt(arguments.get(0));
                if (elementService.collectionContains(inputId)) {
                    Element element1 = elementService.getElementById(inputId);
                    Element element2 = (Element) new Insert(elementService, console).privateExecute(new ArrayList<>(), fromFile);
                    if (element2 == null) {
                        console.println("replace_if_lowe: Объект не был создан.");
                        return new CommandResponse();
                    }
                    if  (element2.compareTo(element1) < 0) {
                        console.println("replace_if_lowe: Элемент был заменён.");
                        elementService.swapElements(element1.getId(), element2);
                    } else {
                        console.println("replace_if_lowe: Элемент не был заменён.");
                    }
                } else {
                    console.println("replace_if_lowe: Коллекция не содержит элемента с данным id;");
                }
            } catch (NumberFormatException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                throw new ArgumentFormatException(true, useExample);
            }
        } else throw new ArgumentFormatException(true, useExample);
        return new CommandResponse();
    }
}
