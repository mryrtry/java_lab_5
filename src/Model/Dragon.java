package Model;

import Commands.Insert;
import Data.DataProviderImplementation;
import Utility.*;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Dragon implements Element {

    private static int idInc = 1;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
    private long age; //Значение поля должно быть больше 0
    private double weight; //Значение поля должно быть больше 0
    private Boolean speaking; //Поле может быть null
    private DragonType type; //Поле может быть null
    private DragonCave cave; //Поле может быть null

    @Override
    public int compareTo(Element o) {
        return this.name.compareTo(o.getName());
    }

    enum UpdatingFields {
        NAME, COORDINATES, AGE, WEIGHT, SPEAKING, TYPE, CAVE
    }

    public Dragon(String name, Coordinates coordinates, long age, double weight, Boolean speaking, DragonType type, DragonCave cave) {
        this.id = idInc++;
        this.creationDate = LocalDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        this.age = age;
        this.weight = weight;
        this.speaking = speaking;
        this.type = type;
        this.cave = cave;
    }

    private Dragon(int id, LocalDateTime creationDate, String name, Coordinates coordinates, long age, double weight, Boolean speaking, DragonType type, DragonCave cave) {
        this.id = id;
        if (idInc < this.id) idInc = this.id;
        idInc++;
        this.name = name;
        this.creationDate = creationDate;
        this.coordinates = coordinates;
        this.age = age;
        this.weight = weight;
        this.speaking = speaking;
        this.type = type;
        this.cave = cave;
    }

    public Dragon() {
    }

    @Override
    public String toString() {
        return "Name{" + this.name + "} Coordinates{" + this.coordinates + "} " + "Age{" + this.age + "} Weight{" + this.weight + "} " + "Speaking{" + this.speaking + "} " + "Type{" + this.type + "} Cave{" + this.cave + "} Creation Date{" + this.creationDate.format(formatter) + "}";
    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Element validateElement(Console console, boolean fromFile) throws Insert.ValidationBreak, Console.InvalidScriptArgument {

        String input;
        String inputName;
        Coordinates inputCoordinates;
        long inputAge;
        double inputWeight;
        Boolean inputSpeaking;
        DragonType inputType;
        DragonCave inputCave;

        // Валидация имени
        console.println("{Input: String} (exit: выход из конструктора класса) Введите имя дракона:");
        while (true) {
            input = console.readLine();

            // Проверяем выход из конструктора
            if (input.equals("exit")) throw new Insert.ValidationBreak();

            try {

                // Проверяем ввод "null"
                if (input.isEmpty())
                    throw new Executable.InvalidArgumentException("Dragon NAME: Имя не может быть пустым. (exit: выход из конструктора класса)");

                inputName = input.toUpperCase();
                console.println("NAME: Установлено значение: " + inputName + ";");
                break;

            } catch (Executable.InvalidArgumentException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println(e.exceptionMSG);
            }
        }

        // Валидация координат
        inputCoordinates = Coordinates.validate(console, inputName, fromFile);

        // Валидация возраста
        console.println("{Input: Long} (exit: выход из конструктора класса) Введите возраст " + inputName + ":");
        while (true) {
            input = console.readLine();

            // Проверяем выход из конструктора
            if (input.equals("exit")) throw new Insert.ValidationBreak();

            try {

                // Проверяем ввод "null"
                if (input.isEmpty())
                    throw new Executable.InvalidArgumentException("Dragon AGE: Возраст не может быть пустым. (exit: выход из конструктора класса)");

                // Проверяем возраст >= 0
                if (Long.parseLong(input) <= 0)
                    throw new Executable.InvalidArgumentException("Dragon AGE: Возраст не может быть 0 или отрицательным. (exit: выход из конструктора класса)");

                inputAge = Long.parseLong(input);
                console.println("AGE: Установлено значение: " + inputAge + ";");
                break;

            } catch (Executable.InvalidArgumentException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println(e.exceptionMSG);
            } catch (NumberFormatException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println("Dragon AGE: Возраст может быть только ЦЕЛЫМ ЧИСЛОМ. (exit: выход из конструктора класса)");
            }
        }

        // Валидация веса
        console.println("{Input: Double} (exit: выход из конструктора класса) Введите вес " + inputName + ":");
        while (true) {
            input = console.readLine();

            // Проверяем выход из конструктора
            if (input.equals("exit")) throw new Insert.ValidationBreak();

            try {

                // Проверяем ввод "null"
                if (input.isEmpty())
                    throw new Executable.InvalidArgumentException("Dragon WEIGHT: Вес не может быть пустым. (exit: выход из конструктора класса)");

                // Проверяем возраст >= 0
                if (Double.parseDouble(input) <= 0)
                    throw new Executable.InvalidArgumentException("Dragon WEIGHT: Вес не может быть 0 или отрицательным. (exit: выход из конструктора класса)");

                inputWeight = Double.parseDouble(input);
                console.println("WEIGHT: Установлено значение: " + inputWeight + ";");
                break;

            } catch (Executable.InvalidArgumentException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println(e.exceptionMSG);
            } catch (NumberFormatException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println("Dragon WEIGHT: Вес может быть только ЧИСЛОВЫМ. (exit: выход из конструктора класса)");
            }
        }

        // Валидация Speaking
        console.println("{Input: Integer} (exit: выход из конструктора класса, enter: установить null) Может ли " + inputName + " разговаривать?:");
        console.println("1: Да, может\n2: Нет, не может");
        while (true) {
            input = console.readLine();

            // Проверяем выход из конструктора
            if (input.equals("exit")) throw new Insert.ValidationBreak();

            if (input.isEmpty()) {
                inputSpeaking = null;
                console.println("SPEAKING: Установлено значение: " + null + ";");
                break;
            }

            try {
                if (input.equals("1")) {
                    inputSpeaking = true;
                    console.println("SPEAKING: Установлено значение: " + true + ";");
                    break;
                }

                if (input.equals("2")) {
                    inputSpeaking = false;
                    console.println("SPEAKING: Установлено значение: " + false + ";");
                    break;
                } else
                    throw new Executable.InvalidArgumentException("Dragon SPEAKING: На вход принимается только '1' или '2'. (exit: выход из конструктора класса, enter: установить null)");
            } catch (Executable.InvalidArgumentException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println(e.exceptionMSG);
            }

        }

        // Валидация Type
        DragonType[] dragonTypes = DragonType.values();
        int counter = 1;

        console.println("{Input: Integer} (exit: выход из конструктора класса, enter: установить null) Введите тип " + inputName + ":");
        for (DragonType dragonType : dragonTypes) console.println(counter++ + ": " + dragonType);
        while (true) {
            input = console.readLine();

            // Проверяем выход из конструктора
            if (input.equals("exit")) throw new Insert.ValidationBreak();

            if (input.isEmpty()) {
                inputType = null;
                console.println("TYPE: Установлено значение: " + null + ";");
                break;
            }

            try {
                if (Integer.parseInt(input) <= dragonTypes.length && Integer.parseInt(input) >= 1) {
                    inputType = dragonTypes[Integer.parseInt(input) - 1];
                    console.println("TYPE: Установлено значение: " + inputType + ";");
                    break;
                } else
                    throw new Executable.InvalidArgumentException("Dragon TYPE: На вход принимается только целое число от 1 до " + dragonTypes.length + " . (exit: выход из конструктора класса, enter: установить null)");

            } catch (NumberFormatException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println("Dragon TYPE: Вход может быть только ЧИСЛОВЫМ. (exit: выход из конструктора класса, enter: установить null)");
            } catch (Executable.InvalidArgumentException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println(e.exceptionMSG);
            }

        }

        // Валидация пещеры
        inputCave = DragonCave.validate(console, inputName, fromFile);

        return new Dragon(inputName, inputCoordinates, inputAge, inputWeight, inputSpeaking, inputType, inputCave);
    }

    @Override
    public void updateElement(Console console, boolean fromFile) throws Insert.ValidationBreak, Console.InvalidScriptArgument {
        String input;
        int inputClass;
        UpdatingFields[] updatingFields = UpdatingFields.values();
        int counter = 1;
        UpdatingFields currentField;
        console.println("{Input: Integer} (exit: выход из метода) Введите номер поля для изменения:");
        for (UpdatingFields updatingField : updatingFields) console.println(counter++ + ": " + updatingField);
        counter = 1;

        while (true) {
            input = console.readLine();

            if (input.equals("exit") || input.isEmpty()) throw new Insert.ValidationBreak();

            try {
                inputClass = Integer.parseInt(input);
                if (inputClass <= updatingFields.length && inputClass >= 1) {
                    currentField = updatingFields[inputClass - 1];
                    console.println("Dragon FIELD: Выбрано поле " + currentField);
                    break;
                } else
                    throw new Executable.InvalidArgumentException("Dragon FIELD: На вход принимается только целое число от 1 до " + updatingFields.length + " . (exit: выход из конструктора класса)");
            } catch (NumberFormatException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println("Dragon FIELD: Вход может быть только ЧИСЛОВЫМ. (exit: выход из метода)");
            } catch (Executable.InvalidArgumentException e) {
                if (fromFile) throw new Console.InvalidScriptArgument();
                console.println(e.exceptionMSG);
            }
        }

        switch (currentField) {

            case NAME -> {
                console.println("(exit: выход из метода) Введите новое значение поля NAME:");
                while (true) {
                    input = console.readLine();
                    if (input.equals("exit")) throw new Insert.ValidationBreak();

                    try {
                        if (input.isEmpty())
                            throw new Executable.InvalidArgumentException("NAME: Имя не может быть пустым. (exit: выход из метода)");

                        name = input.toUpperCase();
                        console.println("NAME: Значение обновлено. Новое значение: " + name + ";");
                        break;

                    } catch (Executable.InvalidArgumentException e) {
                        if (fromFile) throw new Console.InvalidScriptArgument();
                        console.println(e.exceptionMSG);
                    }
                }
            }

            case TYPE -> {
                DragonType[] dragonTypes = DragonType.values();
                console.println("{Input: Integer} (exit: выход из метода, enter: установить null) Введите новое значение поля TYPE:");
                for (DragonType dragonType : dragonTypes) console.println(counter++ + ": " + dragonType);
                while (true) {
                    input = console.readLine();

                    // Проверяем выход из метода
                    if (input.equals("exit")) throw new Insert.ValidationBreak();

                    // Проверяем установку null
                    if (input.isEmpty()) {
                        type = null;
                        console.println("TYPE: Значение обновлено. Новое значение: " + null + ";");
                        break;
                    }

                    try {
                        if (Integer.parseInt(input) <= dragonTypes.length && Integer.parseInt(input) >= 1) {
                            type = dragonTypes[Integer.parseInt(input) - 1];
                            console.println("TYPE: Значение обновлено. Новое значение: " + type + ";");
                            break;
                        } else
                            throw new Executable.InvalidArgumentException("Dragon TYPE: На вход принимается только целое число от 1 до " + dragonTypes.length + " . (exit: выход из метода, enter: установить null)");

                    } catch (NumberFormatException e) {
                        if (fromFile) throw new Console.InvalidScriptArgument();
                        console.println("Dragon TYPE: Вход может быть только ЧИСЛОВЫМ. (exit: выход из метода, enter: установить null)");
                    } catch (Executable.InvalidArgumentException e) {
                        if (fromFile) throw new Console.InvalidScriptArgument();
                        console.println(e.exceptionMSG);
                    }

                }
            }

            case CAVE -> {
                cave = new DragonCave(0,0).updateCave(console, fromFile);
            }

            case WEIGHT -> {
                console.println("{Input: Double} (exit: выход из метода) Введите новое значение поля WEIGHT:");
                while (true) {
                    input = console.readLine();

                    // Проверяем выход из конструктора
                    if (input.equals("exit")) throw new Insert.ValidationBreak();

                    try {

                        // Проверяем ввод "null"
                        if (input.isEmpty())
                            throw new Executable.InvalidArgumentException("Dragon WEIGHT: Вес не может быть пустым. (exit: выход из метода)");

                        // Проверяем возраст >= 0
                        if (Double.parseDouble(input) <= 0)
                            throw new Executable.InvalidArgumentException("Dragon WEIGHT: Вес не может быть 0 или отрицательным. (exit: выход из метода)");

                        weight = Double.parseDouble(input);
                        console.println("WEIGHT: Значение обновлено. Новое значение: " + weight + ";");
                        break;

                    } catch (Executable.InvalidArgumentException e) {
                        if (fromFile) throw new Console.InvalidScriptArgument();
                        console.println(e.exceptionMSG);
                    } catch (NumberFormatException e) {
                        if (fromFile) throw new Console.InvalidScriptArgument();
                        console.println("Dragon WEIGHT: Вес может быть только ЧИСЛОВЫМ. (exit: выход из метода)");
                    }
                }
            }
            case SPEAKING -> {
                console.println("{Input: Integer} (exit: выход из метода, enter: установить null) Введите новое значение поля SPEAKING:");
                console.println("1: Да, может\n2: Нет, не может");
                while (true) {
                    input = console.readLine();

                    // Проверяем выход из метода
                    if (input.equals("exit")) throw new Insert.ValidationBreak();

                    if (input.isEmpty()) {
                        speaking = null;
                        console.println("SPEAKING: Значение обновлено. Новое значение: " + null + ";");
                        break;
                    }

                    try {
                        if (input.equals("1")) {
                            speaking = true;
                            console.println("SPEAKING: Значение обновлено. Новое значение: " + true + ";");
                            break;
                        }

                        if (input.equals("2")) {
                            speaking = false;
                            console.println("SPEAKING: Значение обновлено. Новое значение: " + false + ";");
                            break;
                        } else
                            throw new Executable.InvalidArgumentException("Dragon SPEAKING: На вход принимается только '1' или '2'. (exit: выход из метода, enter: установить null)");
                    } catch (Executable.InvalidArgumentException e) {
                        if (fromFile) throw new Console.InvalidScriptArgument();
                        console.println(e.exceptionMSG);
                    }

                }
            }

            case COORDINATES -> coordinates = new Coordinates(0, 0).updateCoords(console, fromFile);

            case AGE -> {
                console.println("{Input: Long} (exit: выход из метода) Введите новое значение поля AGE:");
                while (true) {
                    input = console.readLine();

                    // Проверяем выход из конструктора
                    if (input.equals("exit")) throw new Insert.ValidationBreak();

                    try {

                        // Проверяем ввод "null"
                        if (input.isEmpty())
                            throw new Executable.InvalidArgumentException("Dragon AGE: Вес не может быть пустым. (exit: выход из метода)");

                        // Проверяем возраст >= 0
                        if (Double.parseDouble(input) <= 0)
                            throw new Executable.InvalidArgumentException("Dragon AGE: Вес не может быть 0 или отрицательным. (exit: выход из метода)");

                        age = Long.parseLong(input);
                        console.println("AGE: Значение обновлено. Новое значение: " + age + ";");
                        break;

                    } catch (Executable.InvalidArgumentException e) {
                        if (fromFile) throw new Console.InvalidScriptArgument();
                        console.println(e.exceptionMSG);
                    } catch (NumberFormatException e) {
                        if (fromFile) throw new Console.InvalidScriptArgument();
                        console.println("Dragon AGE: Вес может быть только ЧИСЛОВЫМ. (exit: выход из метода)");
                    }
                }
            }
        }

    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Dragon dragon = (Dragon) object;
        return id == dragon.id && age == dragon.age && Double.compare(weight, dragon.weight) == 0 && Objects.equals(name, dragon.name) && Objects.equals(coordinates, dragon.coordinates) && Objects.equals(creationDate, dragon.creationDate) && Objects.equals(speaking, dragon.speaking) && type == dragon.type && Objects.equals(cave, dragon.cave);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, age, weight, speaking, type, cave);
    }

    @Override
    public String parseToJson() {
        String jsonString = "    {\n";
        jsonString += "      \"id\": \"" +  this.id + "\"," + "\n";
        jsonString += "      \"name\": \"" + this.name + "\"," + "\n";
        jsonString += "      \"coordinates\": \"" + this.coordinates + "\"," + "\n";
        jsonString += "      \"creationDate\": \"" + this.creationDate + "\"," + "\n";
        jsonString += "      \"age\": \"" + this.age + "\"," + "\n";
        jsonString += "      \"weight\": \"" + this.weight + "\"," + "\n";
        jsonString += "      \"speaking\": \"" + this.speaking + "\"," + "\n";
        jsonString += "      \"type\": \"" + this.type + "\"," + "\n";
        jsonString += "      \"cave\": \"" + this.cave + "\"" + "\n";
        jsonString += "    }";
        return jsonString;
    }

    public Dragon arcParseJson(JSONObject jsonObject) throws DataProviderImplementation.InvalidFileData {

        try {
            int inputId = Integer.parseInt((String) jsonObject.get("id"));
            String inputName = (String) jsonObject.get("name");
            Coordinates inputCoordinates = (jsonObject.get("coordinates").equals("null")) ? null : Coordinates.arcParseString((String) jsonObject.get("coordinates"));
            LocalDateTime inputCreationDate = LocalDateTime.parse((String) jsonObject.get("creationDate"));
            long inputAge = Long.parseLong((String) jsonObject.get("age"));
            double inputWeight = Double.parseDouble((String) jsonObject.get("weight"));
            Boolean inputSpeaking = (jsonObject.get("speaking").equals("null")) ? null : Boolean.parseBoolean((String) jsonObject.get("speaking"));
            DragonType inputType = DragonType.parseTypeFromStr((String) jsonObject.get("type"));
            DragonCave inputCave = DragonCave.parseCaveFromStr((String) jsonObject.get("cave"));
            return new Dragon(inputId, inputCreationDate, inputName, inputCoordinates, inputAge, inputWeight, inputSpeaking, inputType, inputCave);
        } catch (NumberFormatException | NullPointerException e) {
            throw new DataProviderImplementation.InvalidFileData();
        }

    }

    public DragonCave getCave() {
        return cave;
    }
}
