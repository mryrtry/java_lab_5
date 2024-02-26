package Commands;

import Model.Dragon;
import Model.DragonCave;
import Utility.Command;
import Utility.CommandResponse;
import Utility.Element;

import java.util.List;

public class CountLessThanCave extends Command {
    public CountLessThanCave() {
            super("count_less_than_cave", "вывести количество элементов, значение поля cave которых меньше заданного", "cont_less_than_cave {cave}");
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws InvalidArgumentException, ArgumentFormatException {
        if (arguments.size() == 2) {
            try {
                if (Long.parseLong(arguments.get(1)) < 0)
                    throw new InvalidArgumentException("Значение NUMBER OF TREASURES не может быть < 0");
                DragonCave dragonCave = new DragonCave(Long.parseLong(arguments.get(0)), Long.parseLong(arguments.get(1)));
                int counter = 0;
                for (Element element: elementService.getCollection()) {
                    if (element instanceof Dragon) {
                        if (((Dragon) element).getCave() == null) continue;
                        if (((Dragon) element).getCave().compareTo(dragonCave) > 0)
                            counter++;
                    }
                }
                console.println("count_less_than_cave: Всего " + counter + " пещер(ы), меньших чем данная.");
            } catch (NumberFormatException e) {
                throw new ArgumentFormatException(true, useExample);
            }
        }
        return new CommandResponse();
    }
}
