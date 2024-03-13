package Utility;

import Data.DataProvider;

import java.io.BufferedReader;import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class Console {
    public static class InvalidScriptArgument extends Exception {}
    public HashMap<String, Command> commands;
    public ArrayList<String> commandsHistory;
    public ArrayList<String> lastScriptsNames;
    public HashMap<String, BufferedReader> lastScripts;
    public ArrayList<String> getCommandsHistory() {
        return commandsHistory;
    }
    public abstract void pushToHistory(Command command);
    public abstract void validateCommand(String command);
    public abstract void start();
    public abstract String readLine();
    public abstract String readLine(BufferedReader fileReader);
    public abstract void print(Object object);
    public abstract void println(Object object);
    public abstract void changeInputStream(BufferedReader reader);
    public abstract DataProvider getDataProvider();
}