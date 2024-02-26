package Commands;

import Utility.Command;
import Utility.CommandResponse;
import Utility.Console;
import Utility.Element;

import java.util.ArrayList;
import java.util.List;

public class RemoveGreater extends Command {
    public RemoveGreater() {
        super("remove_greater", "удалить из коллекции все элементы, превышающие заданный", "remove_greater {id}");
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws ArgumentFormatException, Console.InvalidScriptArgument {
        if (arguments.size() != 1) throw new ArgumentFormatException(true, useExample);
        try {
            int inputId = Integer.parseInt(arguments.get(0));
            if (elementService.collectionContains(inputId)) {
                Element element = elementService.getElementById(inputId);
                ArrayList<Integer> deletedElements = new ArrayList<>();
                for (Element currElement : elementService.getCollection()) {
                    if (element.compareTo(currElement) < 0) {
                        console.println("remove_greater: Удалён элемент: " + currElement);
                        deletedElements.add(currElement.getId());
                    }
                }
                if (deletedElements.isEmpty()) {
                    console.println("remove_greater: Нет элементов, больших чем данный;");
                } else for (int id: deletedElements) { elementService.removeByKey(id); }
            } else {
                console.println("remove_greater: Коллекция не содержит элемент с таким id;");
            }
        } catch (NumberFormatException e) {
            if (fromFile) throw new Console.InvalidScriptArgument();
            throw new ArgumentFormatException(true, useExample);
        }
        return new CommandResponse();
    }
}
