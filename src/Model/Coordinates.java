package Model;

import Commands.Insert;
import Utility.Console;

import java.util.ArrayList;

public class Coordinates {
    private final double x;
    private final double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return this.x + ", " + this.y;
    }

    public static Coordinates validate(Console console, String name, boolean fromFile) throws Insert.ValidationBreak, Console.InvalidScriptArgument {
        String input;
        double inputX;
        double inputY;

        // Валидация координат
        console.println("{Input: Double/Double} (exit: выход из конструктора класса, enter: установить null) Введите координаты " + name + ":");

        // Валидация координаты X
        console.println("Введите координату X:");
        while (true) {
            input = console.readLine();

            // Проверяем выход из конструктора
            if (input.equals("exit")) throw new Insert.ValidationBreak();

            try {

                // Проверяем ввод "null"
                if (input.isEmpty()) {
                    console.println("COORDINATES: Установлено значение: " + null + ";");
                    return null;
                }

                inputX = Double.parseDouble(input);
                break;

            } catch (NumberFormatException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println("Dragon COORDINATES(X): Координата может быть только ЧИСЛОВОЙ. (exit: выход из конструктора класса, enter: установить null)");
            }
        }

        // Валидация координаты Y
        console.println("Введите координату Y:");
        while (true) {
            input = console.readLine();

            // Проверяем выход из конструктора
            if (input.equals("exit")) throw new Insert.ValidationBreak();

            try {

                // Проверяем ввод "null"
                if (input.isEmpty()) {
                    console.println("COORDINATES: Установлено значение: " + null + ";");
                    return null;
                }

                inputY = Double.parseDouble(input);
                Coordinates coordinates = new Coordinates(inputX, inputY);
                console.println("COORDINATES: Установлено значение: " + coordinates + ";");
                return coordinates;

            } catch (NumberFormatException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println("COORDINATES(Y): Координата может быть только ЧИСЛОВОЙ. (exit: выход из конструктора класса, enter: установить null)");
            }
        }

    }

    public Coordinates updateCoords(Console console, boolean fromFile) throws Insert.ValidationBreak, Console.InvalidScriptArgument {
        String input;
        double inputX;
        double inputY;

        // Валидация координат
        console.println("{Input: Double/Double} (exit: выход из метода, enter: установить null) Введите новое значение поля COORDINATES:");

        // Валидация координаты X
        console.println("Введите координату X:");
        while (true) {
            input = console.readLine();

            // Проверяем выход из конструктора
            if (input.equals("exit")) throw new Insert.ValidationBreak();

            try {

                // Проверяем ввод "null"
                if (input.isEmpty()) {
                    console.println("COORDINATES: Значение обновлено, новое значение: " + null + ";");
                    return null;
                }

                inputX = Double.parseDouble(input);
                break;

            } catch (NumberFormatException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println("Dragon COORDINATES(X): Координата может быть только ЧИСЛОВОЙ. (exit: выход из метода, enter: установить null)");
            }
        }

        // Валидация координаты Y
        console.println("Введите координату Y:");
        while (true) {
            input = console.readLine();

            // Проверяем выход из конструктора
            if (input.equals("exit")) throw new Insert.ValidationBreak();

            try {

                // Проверяем ввод "null"
                if (input.isEmpty()) {
                    console.println("COORDINATES: Значение обновлено, новое значение: " + null + ";");
                    return null;
                }

                inputY = Double.parseDouble(input);
                Coordinates coordinates = new Coordinates(inputX, inputY);
                console.println("COORDINATES: Значение обновлено, новое значение: " + coordinates + ";");
                return coordinates;

            } catch (NumberFormatException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println("COORDINATES(Y): Координата может быть только ЧИСЛОВОЙ. (exit: выход из метода, enter: установить null)");
            }
        }
    }

    public static Coordinates arcParseString(String strCoordinates) {
        String[] coordinatesXY = strCoordinates.split(", ");
        return new Coordinates(Double.parseDouble(coordinatesXY[0]), Double.parseDouble(coordinatesXY[1]));
    }

}