package Commands;

import Utility.*;
import com.sun.source.tree.BreakTree;

import java.util.List;

public class Insert extends Command {
    public Insert() {
        super("insert", "добавить новый элемент с заданным ключом", "insert");
    }

    public Insert(ElementService elementService, Console console) {
        this();
        this.elementService = elementService;
        this.console = console;
    }
    public static class ValidationBreak extends Exception {
    }

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws Console.InvalidScriptArgument {
        int inputClass;
        String input;
        ElementsServiceHashMap.CollectionElement[] availableClasses = elementService.getCollectionClasses();
        try {
            while (true) {

                try {
                    // Встречающее сообщение
                    console.println("(exit/enter: выход из конструктора класса) Выберите экземпляр какого класса должен быть создан:");
                    int counter = 1;
                    for (ElementsServiceHashMap.CollectionElement collectionElement : availableClasses)
                        console.println(counter++ + ": " + collectionElement);

                    input = console.readLine();

                    // Проверка выхода
                    if (input.isEmpty() || input.equals("exit")) throw new ValidationBreak();

                    inputClass = Integer.parseInt(input);
                    if (inputClass <= availableClasses.length && inputClass >= 1) {
                        console.println("insert: Создаём объект типа " + availableClasses[inputClass - 1] + "!");
                        Element element = elementService.validateCollectionElement(availableClasses[inputClass - 1], console, fromFile);
                        if (element == null) {
                            console.println("insert: Валидация класса не задана!");
                        } else {
                            console.println("insert: Создан объект типа " + element.getClass().getSimpleName() + ": " + element);
                            elementService.push(element);
                        }
                        break;
                    } else {
                        if (fromFile) throw new Console.InvalidScriptArgument();
                        throw new InvalidArgumentException("insert: На вход принимается целое число от 1 ДО " + availableClasses.length + ". (exit/enter: выход из конструктора класса);");
                    }

                } catch (NumberFormatException e) {
                    if (fromFile) throw new Console.InvalidScriptArgument();
                    console.println("insert: На вход принимается ЦЕЛОЕ число. (exit/enter: выход из конструктора класса);");
                } catch (InvalidArgumentException e) {
                    console.println(e.exceptionMSG);
                }

            }
        } catch (ValidationBreak e) {
            console.println("insert: Выполнен выход из конструктора.");
        }
        return new CommandResponse();
    }


    public Element privateExecute(List<String> arguments, boolean fromFile) throws Console.InvalidScriptArgument {
        int inputClass;
        String input;
        ElementsServiceHashMap.CollectionElement[] availableClasses = elementService.getCollectionClasses();
        try {
            while (true) {

                try {
                    // Встречающее сообщение
                    console.println("(exit/enter: выход из конструктора класса) Выберите экземпляр какого класса должен быть создан:");
                    int counter = 1;
                    for (ElementsServiceHashMap.CollectionElement collectionElement : availableClasses)
                        console.println(counter++ + ": " + collectionElement);

                    input = console.readLine();

                    // Проверка выхода
                    if (input.isEmpty() || input.equals("exit")) throw new ValidationBreak();

                    inputClass = Integer.parseInt(input);
                    if (inputClass <= availableClasses.length && inputClass >= 1) {
                        console.println("insert: Создаём объект типа " + availableClasses[inputClass - 1] + "!");
                        Element element = elementService.validateCollectionElement(availableClasses[inputClass - 1], console, fromFile);
                        if (element == null) {
                            console.println("insert: Валидация класса не задана!");
                        } else {
                            console.println("insert: Создан объект типа " + element.getClass().getSimpleName() + ": " + element);
                        }
                        return element;
                    } else {
                        if (fromFile) throw new Console.InvalidScriptArgument();
                        throw new InvalidArgumentException("insert: На вход принимается целое число от 1 ДО " + availableClasses.length + ". (exit/enter: выход из конструктора класса);");
                    }

                } catch (NumberFormatException e) {
                    if (fromFile) throw new Console.InvalidScriptArgument();
                    console.println("insert: На вход принимается ЦЕЛОЕ число. (exit/enter: выход из конструктора класса);");
                } catch (InvalidArgumentException e) {
                    console.println(e.exceptionMSG);
                }

            }
        } catch (ValidationBreak e) {
            console.println("insert: Выполнен выход из конструктора.");
        }
        return null;
    }
}
