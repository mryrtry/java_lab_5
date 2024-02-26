package Commands;

import Model.Dragon;
import Model.DragonCave;
import Utility.Command;
import Utility.CommandResponse;
import Utility.Element;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PrintFieldDescendingCave extends Command {
    public PrintFieldDescendingCave() {
        super("print_field_descending_cave", "вывести значения поля cave всех элементов в порядке убывания", "print_field_descending_cave");
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws ArgumentFormatException {
        if (arguments.isEmpty()) {
            ArrayList<DragonCave> caves = new ArrayList<>();
            for (Element element: elementService.getCollection()) {
                if (element instanceof Dragon) {
                    caves.add(((Dragon) element).getCave());
                }
            }
            caves.sort(new Comparator<DragonCave>() {
                @Override
                public int compare(DragonCave o1, DragonCave o2) {
                    if (o1 == null) return -1;
                    if (o2 == null) return 1;
                    int result = 0;
                    if (o1.getNumberOfTreasures() < o2.getNumberOfTreasures())
                        result = -1;
                    if (o1.getNumberOfTreasures() > o2.getNumberOfTreasures())
                        result = 1;
                    return result;
                }
            });
            console.print("[");
            for (DragonCave dragonCave: caves) {
                console.print(dragonCave + "; ");
            }
            console.print("]");
            console.println("");
        } else throw new ArgumentFormatException(false, useExample);
        return new CommandResponse();
    }
}
