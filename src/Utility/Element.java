package Utility;

import Commands.Insert;

public interface Element extends Comparable<Element> {
    int getId();
    String getName();
    Element validateElement(Console console, boolean fromFile) throws Insert.ValidationBreak, Console.InvalidScriptArgument;
    void updateElement(Console console, boolean fromFile) throws Insert.ValidationBreak, Console.InvalidScriptArgument;
    String parseToJson();
}
