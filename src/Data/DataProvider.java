package Data;

import Utility.Command;
import Utility.Element;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public interface DataProvider {
    Collection<Element> loadElements();
    ArrayList<String> loadHistory();
    LocalDateTime loadCollectionInitTime();
    void saveCondition(LocalDateTime initTime, Collection<Element> elements, ArrayList<String> history);

}
