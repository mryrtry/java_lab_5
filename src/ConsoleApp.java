import Data.DataProvider;
import Data.DataProviderImplementation;
import Utility.ConsoleImplementation;

import java.io.File;

public class ConsoleApp {
    static  {
        new ConsoleImplementation(System.in, System.out, new DataProviderImplementation(new File("/Projcets/Java Projects/Console Application Lab-5-2/src/Data/data.json"))).start();

    }
    public static void main(String[] args) {}
}