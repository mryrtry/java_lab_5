package Model;

import Commands.Insert;
import Utility.Console;
import Utility.Executable;

public class DragonCave implements Comparable<DragonCave> {
    private final long depth;
    private final long numberOfTreasures; //Значение поля должно быть больше 0

    public DragonCave(long depth, long numberOfTreasures) {
        this.depth = depth;
        this.numberOfTreasures = numberOfTreasures;
    }

    @Override
    public String toString() {
        return this.depth + ", " + this.numberOfTreasures;
    }

    public static DragonCave validate(Console console, String name, boolean fromFile) throws Insert.ValidationBreak, Console.InvalidScriptArgument {
        String input;
        long inputDepth;
        long inputNumberOfTreasures;

        // Валидация пещеры
        console.println("{Input: Long/Long} (exit: выход из конструктора класса, enter: установить null) Введите параметры пещеры " + name + ":");

        // Валидация глубины пещеры
        console.println("(exit: выход из конструктора класса, enter: установить null) Введите глубину пещеры:");
        while (true) {
            input = console.readLine();

            // Проверяем выход из конструктора
            if (input.equals("exit")) throw new Insert.ValidationBreak();

            try {

                // Проверяем ввод "null"
                if (input.isEmpty()) {
                    console.println("CAVE: Установлено значение: " + null + ";");
                    return null;
                }

                inputDepth = Long.parseLong(input);
                console.println("Dragon DEPTH: Установлено значение: " + inputDepth + ";");
                break;

            } catch (NumberFormatException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println("Dragon CAVE: Глубина может быть только ЧИСЛОВОЙ. (exit: выход из конструктора класса, enter: установить null)");
            }
        }

        // Валидация количества сокровищ
        console.println("(exit: выход из конструктора класса, enter: установить null) Введите количество сокровищ в пещере:");
        while (true) {
            input = console.readLine();

            // Проверяем выход из конструктора
            if (input.equals("exit")) throw new Insert.ValidationBreak();

            try {

                // Проверяем ввод "null"
                if (input.isEmpty()) {
                    console.println("CAVE: Установлено значение: " + null + ";");
                    return null;
                }

                if (Double.parseDouble(input) <= 0) throw new Executable.InvalidArgumentException("Dragon CAVE: Количество сокровищ не может быть 0 или отрицательным. (exit: выход из конструктора класса, enter: установить null)");
                else {
                    inputNumberOfTreasures = Long.parseLong(input);
                    DragonCave dragonCave = new DragonCave(inputDepth, inputNumberOfTreasures);
                    console.println("CAVE: Установлено значение: " + dragonCave + ";");
                    return dragonCave;
                }

            } catch (NumberFormatException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println("CAVE: Значение может быть только ЧИСЛОВЫМ. (exit: выход из конструктора класса, enter: установить null)");
            } catch (Executable.InvalidArgumentException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println(e.exceptionMSG);
            }
        }

    }

    public static DragonCave parseCaveFromStr(String strCave) {
        String[] caveArr = strCave.split(", ");
        try{
            return new DragonCave(Long.parseLong(caveArr[0]), Long.parseLong(caveArr[1]));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public DragonCave updateCave(Console console, boolean fromFile) throws Console.InvalidScriptArgument, Insert.ValidationBreak {
        String input;
        long inputDepth;
        long inputNumberOfTreasures;

        // Валидация пещеры
        console.println("{Input: Long/Long} (exit: выход из метода, enter: установить null) Введите новое значение поля Dragon CAVE:");

        // Валидация глубины пещеры
        console.println("(exit: выход из метода, enter: установить null) Введите глубину пещеры:");
        while (true) {
            input = console.readLine();

            // Проверяем выход из конструктора
            if (input.equals("exit")) throw new Insert.ValidationBreak();

            try {

                // Проверяем ввод "null"
                if (input.isEmpty()) {
                    console.println("CAVE: Значение обновлено. Новое значение: " + null + ";");
                    return null;
                }

                inputDepth = Long.parseLong(input);
                console.println("Dragon DEPTH: Установлено значение: " + inputDepth + ";");
                break;

            } catch (NumberFormatException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println("Dragon CAVE: Глубина может быть только ЧИСЛОВОЙ. (exit: выход из метода, enter: установить null))");
            }
        }

        // Валидация количества сокровищ
        console.println("(exit: выход из метода, enter: установить null) Введите количество сокровищ в пещере:");
        while (true) {
            input = console.readLine();

            // Проверяем выход из конструктора
            if (input.equals("exit")) throw new Insert.ValidationBreak();

            try {

                // Проверяем ввод "null"
                if (input.isEmpty()) {
                    console.println("CAVE: Значение обновлено. Новое значение: " + null + ";");
                    return null;
                }

                if (Double.parseDouble(input) <= 0) throw new Executable.InvalidArgumentException("Dragon CAVE: Количество сокровищ не может быть 0 или отрицательным. (exit: выход из метода, enter: установить null)");
                else {
                    inputNumberOfTreasures = Long.parseLong(input);
                    DragonCave dragonCave = new DragonCave(inputDepth, inputNumberOfTreasures);
                    console.println("CAVE: Значение обновлено. Новое значение: " + dragonCave + ";");
                    return dragonCave;
                }

            } catch (NumberFormatException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println("CAVE: Значение может быть только ЧИСЛОВЫМ. (exit: выход из метода, enter: установить null)");
            } catch (Executable.InvalidArgumentException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println(e.exceptionMSG);
            }
        }
    }

    @Override
    public int compareTo(DragonCave o) {
        if (o == null) return 1;
        int result = 0;
        if (this.numberOfTreasures > o.numberOfTreasures)
            result = -1;
        if (this.numberOfTreasures < o.numberOfTreasures)
            result = 1;
        return result;
    }

    public long getNumberOfTreasures() {
        return numberOfTreasures;
    }
}
