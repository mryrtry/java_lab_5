package Commands;

import Utility.Command;
import Utility.CommandResponse;
import Utility.Element;

import java.util.Collection;
import java.util.List;

public class Show extends Command {
    public Show() {
        super("show", "вывести все элементы коллекции", "show");
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws ArgumentFormatException {
        if (arguments.isEmpty()) {
            Collection<Element> collection = elementService.getCollection();
            if (collection.isEmpty()) console.println("show: Коллекция пуста.");
            else for (Element element : collection) console.println(element.getId() + ": " + element);
        } else throw new ArgumentFormatException(false, "show");
        return new CommandResponse();
    }
}
