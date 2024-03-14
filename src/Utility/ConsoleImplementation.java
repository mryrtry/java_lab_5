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
    private String lastCommand;

    public ConsoleImplementation(InputStream inputStream, OutputStream outputStream, DataProvider dataProvider) {

        // Настраиваем чтение
        this.reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream), StandardCharsets.UTF_8));
        this.standartReader = reader;

        // Стек вызова скриптов
        this.lastScriptsNames = new Stack<>();
        this.lastScripts = new HashMap<>();

        // Настраиваем вывод
        this.writer = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(outputStream), StandardCharsets.UTF_8));

        // Получаем DataProvider и данные
        this.dataProvider = dataProvider;

        if (!dataProvider.isTempClear()) {
            println("Обнаружены не сохраненные изменения с последнего запуска. Восстановить? Yes / No");

            if (readLine().equalsIgnoreCase("Yes")) {

                dataProvider.saveTemp();
                println("Изменения сохранены.");

            } else println("Пропущено.");

        }

        dataProvider.clearTempFile();

        this.lastCommand = "info";

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
        ArrayList<String> arguments = new ArrayList<>(Arrays.asList(splitCommand));
        String instruction = arguments.get(0);
        arguments.remove(0);
        boolean readingFromFile = false;

        if (instruction.isEmpty()) { println("app: Пустой ввод."); }

        else if (commands.containsKey(instruction)) {
            pushToHistory(commands.get(instruction));

            try {
                commands.get(instruction).execute(arguments, readingFromFile);
            } catch (Executable.ArgumentFormatException e) {
                println(instruction + ": " + e.exceptionMSG + " Пример использования команды: '" + e.useExample + "'");
            } catch (Executable.InvalidArgumentException e) {
                println(instruction + ": " + e.exceptionMSG);
            } catch (InvalidScriptArgument e) {
                println("execute_script: Аргумент был введён неверно. Переход к следующей известной команде.");
                while(true) {
                    String newLine = readLine();
                    if (commands.containsKey(newLine.split(" ")[0])) {
                        validateCommand(newLine);
                        break;
                    }
                }
            } catch (ExecuteScript.ScriptsRecursionException e) {
                lastScripts.clear();
                lastScriptsNames.clear();
                println("execute_script: Обнаружена рекурсия. Стек вызова скриптов очищен, выполнение прервано.");
            }
        } else println("app: Неизвестная команда: '" + instruction + "'\n" + "app: Введите \"help\" для вывода списка доступных команд.");

    }

    @Override
    public void start() {

        while (true) {
            try {

                if (standartReader == reader) print(prompt);
                String command = readLine();

                validateCommand(command);

                if (!command.equals("save"))
                    dataProvider.temporarySaveCondition(elementService.getUnparsedInitTime(), elementService.getCollection(), commandsHistory);

            } catch (NullPointerException e) {

                if (!lastScripts.isEmpty()) {
                    lastScripts.remove(lastScriptsNames.lastElement());
                    lastScriptsNames.pop();
                }

                if (lastScripts.isEmpty()) this.reader = standartReader;
                else this.reader = lastScripts.get(lastScriptsNames.lastElement());

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
    public String readLine(BufferedReader fileReader) {
        try {
            return fileReader.readLine();
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