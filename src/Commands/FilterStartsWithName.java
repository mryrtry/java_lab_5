package Commands;

import Utility.Command;
import Utility.CommandResponse;
import Utility.Element;

import java.util.ArrayList;
import java.util.List;

public class FilterStartsWithName extends Command {
    public FilterStartsWithName() {
        super("filter_starts_with_name", "вывести элементы, значение поля name которых начинается с заданной подстроки", "filter_starts_with_name {name}");
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws ArgumentFormatException {
        if (arguments.size() != 1) throw new ArgumentFormatException(true, useExample);
        String subName = arguments.get(0);
        ArrayList<Element> goodElements = new ArrayList<>();
        String name;
        for (Element element: elementService.getCollection()) {
            name = element.getName();
            if (name.startsWith(subName.toUpperCase())) goodElements.add(element);
        }
        if (goodElements.isEmpty()) console.println("filter_starts_with_name: Совпадений не найдено.");
        else for (Element element: goodElements) {
            console.println(element);
        }
        return new CommandResponse();
    }
}
