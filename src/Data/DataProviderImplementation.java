package Data;

import Model.Dragon;
import Utility.Element;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DataProviderImplementation implements DataProvider {

    public static class InvalidFileData extends Exception {}

    private File loadingFile;
    private final File temporaryFile;

    public DataProviderImplementation(File file) {
        this.loadingFile = file;
        this.temporaryFile = new File("temporary.json");
    }

    @Override
    public Collection<Element> loadElements() {
        Collection<Element> elements = new ArrayList<>();

        // Парсим файл
        JSONObject parsedFile;
        try {
            parsedFile = (JSONObject) new JSONParser().parse(new FileReader(loadingFile));
        } catch (IOException | ParseException e) {
            return elements;
        }

        // Достаем из jsonObject все элементы
        JSONArray elementsArr = (JSONArray) parsedFile.get("elements");
        if (elementsArr == null) return elements;
        for (Object element : elementsArr) {
            JSONObject jsonElement = (JSONObject) element;
            try {
                Dragon dragon = new Dragon().arcParseJson(jsonElement);
                elements.add(dragon);
            } catch (InvalidFileData ignored) {}
        }

        return elements;
    }

    @Override
    public ArrayList<String> loadHistory() {
        // Новый лист для истории команд
        ArrayList<String> history = new ArrayList<>();

        // Парсим файл
        JSONObject parsedFile;
        try {
            parsedFile = (JSONObject) new JSONParser().parse(new FileReader(loadingFile));
        } catch (IOException | ParseException e) {
            return history;
        }

        // Достаем из jsonObject историю команд
        assert parsedFile != null;
        JSONArray historyArr = (JSONArray) parsedFile.get("history");
        if (historyArr == null) return history;
        for (Object command : historyArr) {
            JSONObject jsonCommand = (JSONObject) command;
            String strCommand = (String) jsonCommand.get("commandName");
            if (strCommand != null) history.add(strCommand);
        }

        return history;
    }

    @Override
    public LocalDateTime loadCollectionInitTime() {

        // Парсим файл
        JSONObject parsedFile;
        try {
            parsedFile = (JSONObject) new JSONParser().parse(new FileReader(loadingFile));
        } catch (IOException | ParseException e) {
            return LocalDateTime.now();
        }

        if (parsedFile.get("initTime") == null) return LocalDateTime.now();
        try {
            return LocalDateTime.parse((String) parsedFile.get("initTime"));
        } catch (DateTimeParseException e) {
            System.err.println("Invalid File Data (Init. Time)");
            return LocalDateTime.now();
        }
    }

    private String saveCollection(LocalDateTime initTime, Collection<Element> collection) {
        StringBuilder jsonString = new StringBuilder("  \"initTime\": \"" + initTime + "\",\n  \"elements\": [\n");
        int counter = 1;
        for (Element element : collection) {
            if (counter++ != 1) jsonString.append(",\n");
            jsonString.append(element.parseToJson());
        }
        jsonString.append("\n  ]");
        return jsonString.toString();
    }

    private String saveHistory(ArrayList<String> history) {
        StringBuilder jsonString = new StringBuilder("  \"history\": [\n");
        int counter = 1;
        for (String command : history) {
            if (counter++ != 1) jsonString.append(",\n");
            jsonString.append("    {\n      \"commandName\": \"").append(command).append("\"\n    }");
        }
        jsonString.append("\n  ],\n");
        return jsonString.toString();
    }

    private void writeToFile(String string, File file) {
        try {
            BufferedWriter dataWriter = new BufferedWriter(new FileWriter(file));
            dataWriter.write(string);
            dataWriter.flush();
            dataWriter.close();
        } catch (IOException e) {
            System.err.println("IO Exception, can't write to file: " + loadingFile + ";");
        }
    }

    private void compileLoadingFile(String history, String initNElements, File file) {
        String textFile = "{\n";
        textFile += history;
        textFile += initNElements;
        textFile += "\n}";
        writeToFile(textFile, file);
    }

    public void saveCondition(LocalDateTime initTime, Collection<Element> elements, ArrayList<String> history) {
        String jsonInitElements = saveCollection(initTime, elements);
        String jsonHistory = saveHistory(history);
        compileLoadingFile(jsonHistory, jsonInitElements, loadingFile);
    }

    @Override
    public void temporarySaveCondition(LocalDateTime initTime, Collection<Element> elements, ArrayList<String> history) {
        String jsonInitElements = saveCollection(initTime, elements);
        String jsonHistory = saveHistory(history);
        compileLoadingFile(jsonHistory, jsonInitElements, temporaryFile);
    }

    @Override
    public void clearTempFile() {
        File file = loadingFile;
        loadingFile = temporaryFile;
        writeToFile("", temporaryFile);
        loadingFile = file;
    }

    @Override
    public boolean isTempClear() {
        return temporaryFile.length() == 0;
    }

    @Override
    public void saveTemp() {
        try {
            String string2;
            StringBuilder string = new StringBuilder();
            FileReader fileReader = new FileReader(temporaryFile);
            BufferedReader reader = new BufferedReader(fileReader);
            while(true) {
                string2 = reader.readLine();
                if (string2 == null || string2.startsWith("null")) break;
                else { string.append(string2); }
            }
            writeToFile(String.valueOf(string), loadingFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
