package Utility;

import Commands.*;
import Data.DataProvider;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ConsoleImplementation extends Console {
    private BufferedReader reader;
    private final BufferedWriter writer;
    private static final String prompt = "$ ";
    private final ElementService elementService;
    private static final int historySize = 7;
    private final BufferedReader standartReader;
    private final DataProvider dataProvider;

    public ConsoleImplementation(InputStream inputStream, OutputStream outputStream, DataProvider dataProvider) {

        // Настраиваем чтение
        this.reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream), StandardCharsets.UTF_8));
        this.standartReader = reader;

        // Настраиваем вывод
        this.writer = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(outputStream), StandardCharsets.UTF_8));

        // Получаем DataProvider и данные
        this.dataProvider = dataProvider;
        this.commandsHistory = dataProvider.loadHistory();

        // Инициализация менеджера коллекции
        this.elementService = new ElementsServiceHashMap(dataProvider);

        // Настраиваем доступные команды
        commands = new HashMap<>();
        List<Command> commandsList = Arrays.asList(new Help(), new Info(), new Show(), new Insert(), new Update(), new RemoveKey(), new Clear(), new Save(), new ExecuteScript(), new Exit(), new RemoveGreater(), new History(), new ReplaceIfLowe(), new CountLessThanCave(), new FilterStartsWithName(), new PrintFieldDescendingCave());
        commandsList.forEach(item -> { item.setConsole(this); item.setElementService(elementService); commands.put(item.getName(), item); });
    }

    @Override
    public void changeInputStream(BufferedReader newReader) {
        reader = newReader;
    }

    @Override
    public void print(Object object) {
        try {
            writer.write(object.toString());
            writer.flush();
        } catch (IOException e) {
            System.err.println("Output Stream Exception.\n");
            e.printStackTrace();
        }
    }

    @Override
    public void println(Object object) {
        try {
            writer.write(object.toString() + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Output Stream Exception.\n");
            e.printStackTrace();
        }
    }

    @Override
    public void pushToHistory(Command command) {
        if (commandsHistory.size() == historySize)
            commandsHistory.remove(0);
        commandsHistory.add(command.getName());
    }

    @Override
    public void validateCommand(String command) {
        String[] splitCommand = command.toLowerCase().split(" ");
        String instruction = splitCommand[0];
        ArrayList<String> arguments = new ArrayList<>(Arrays.asList(splitCommand));
        boolean readingFromFile = false;
        arguments.remove(0);

        if (instruction.isEmpty()) { println("app: Пустой ввод."); }
        else if (commands.containsKey(instruction)) {
            pushToHistory(commands.get(instruction));
            try {
                if (reader != standartReader)
                    readingFromFile = true;
                try{
                    commands.get(instruction).execute(arguments, readingFromFile);
                } catch (InvalidScriptArgument e) {
                    println("Аргумент был введён неверно. Переход к следующей известной команде.");
                    while(true) {
                        String newLine = readLine();
                        if (commands.containsKey(newLine.split(" ")[0])) {
                            validateCommand(newLine);
                            break;
                        }
                    }
                }
            } catch (Executable.ArgumentFormatException e) {
                println(instruction + ": " + e.exceptionMSG + " Пример использования команды: '" + e.useExample + "'");
            } catch (Executable.InvalidArgumentException e) {
                println(instruction + ": " + e.exceptionMSG);
            }
        } else println("app: Неизвестная команда: '" + instruction + "'\n" + "app: Введите \"help\" для вывода списка доступных команд.");

    }

    @Override
    public void start() {
        while (true) {
            try {
                if (standartReader == reader) print(prompt);
                String command = reader.readLine();
                validateCommand(command);
            } catch (IOException e) {
                System.err.println("Input Stream Exception.\n");
                e.printStackTrace();
            } catch (NullPointerException e) {
                println("Выполнение скрипта завершено.");
                reader = standartReader;
            }
        }
    }

    @Override
    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.err.println("Input Stream Exception.\n");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DataProvider getDataProvider() {
        return dataProvider;
    }
}