package Commands;

import Utility.Command;
import Utility.CommandResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExecuteScript extends Command {

    public ExecuteScript() {
        super("execute_script", "считать и исполнить скрипт из указанного файла", "execute_script {file_name}");
    }

    public static class ScriptsRecursionException extends Exception {}

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws ArgumentFormatException, ScriptsRecursionException {
        if (arguments.size() != 1) throw new ArgumentFormatException(true, useExample);
        else {
            String fileName = arguments.get(0);
            if (console.lastScriptsNames.contains(fileName)) throw new ScriptsRecursionException();
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
                console.println("execute_script: Запущен скрипт " + fileName + ";");
                console.changeInputStream(bufferedReader);

                console.lastScripts.put(fileName, bufferedReader);
                console.lastScriptsNames.add(fileName);

            } catch (FileNotFoundException e) {
                console.println("execute_script: Файл '" + fileName + "' не найден.");
            }
        }
        return new CommandResponse();
    }
}
