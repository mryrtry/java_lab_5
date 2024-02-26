package Utility;


abstract public class Command implements Executable {
    public String name;
    public String description;
    public String useExample;
    public Console console;
    public ElementService elementService;

    public Command(String name, String description, String useExample) {
        this.name = name;
        this.description = description;
        this.useExample = useExample;
    }

    public Command(String name, String description, Console console, ElementService elementService) {
        this.name = name;
        this.description = description;
        this.console = console;
        this.elementService = elementService;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getUseExample() {
        return useExample;
    }
    public void setConsole(Console console) {
        this.console = console;
    }
    public void setElementService(ElementService elementService) {
        this.elementService = elementService;
    }

}
