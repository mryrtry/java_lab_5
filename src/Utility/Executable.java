package Utility;

import Commands.ExecuteScript;

import java.util.List;

public interface Executable {

    // Exception for invalid Argument Format
    class ArgumentFormatException extends Exception {
        public String useExample;
        public String exceptionMSG;
        public ArgumentFormatException(boolean hasArgs, String useExample) {
            if (hasArgs) this.exceptionMSG = "неверный формат ввода аргументов.";
            else this.exceptionMSG = "не принимает аргументы.";
            this.useExample = useExample;
        }
    }

    // Exception for invalid Argument Value
    class InvalidArgumentException extends Exception {
        public String exceptionMSG;
        public InvalidArgumentException(String exceptionMSG) {
            this.exceptionMSG = exceptionMSG;
        }
    }

    CommandResponse execute(List<String> arguments, boolean readingFromFile) throws ArgumentFormatException, InvalidArgumentException, Console.InvalidScriptArgument, ExecuteScript.ScriptsRecursionException;
}