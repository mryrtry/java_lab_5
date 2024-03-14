import Data.DataProviderImplementation;
import Utility.ConsoleImplementation;
import java.io.File;

public class ConsoleApp {

    static {
        new ConsoleImplementation(System.in, System.out, new DataProviderImplementation(new File("save_file.json"))).start();
    }
    public static void main(String[] args) {}
}