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

    @Override
    public CommandResponse execute(List<String> arguments, boolean fromFile) throws ArgumentFormatException {
        if (arguments.size() != 1) throw new ArgumentFormatException(true, useExample);
        else {
            String fileName = arguments.get(0);
            File file = new File(fileName + ".txt");
            try {
                FileReader fileReader = new FileReader(file);
                console.println("execute_script: Запущен скрипт " + fileName + ";");
                console.changeInputStream(new BufferedReader(fileReader));
            } catch (FileNotFoundException e) {
                console.println("execute_script: Файл '" + fileName + "' не найден или не является файлом формата .txt;");
            }
        }
        return new CommandResponse();
    }
}
