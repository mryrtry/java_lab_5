package Utility;

import Commands.Insert;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ElementService {

    Class getCollectionType();
    String getInitTime();
    LocalDateTime getUnparsedInitTime();
    Collection<Element> getCollection();
    Integer getCollectionSize();
    ElementsServiceHashMap.CollectionElement[] getCollectionClasses();
    Element validateCollectionElement(ElementsServiceHashMap.CollectionElement collectionElement, Console console, boolean fromFile) throws Insert.ValidationBreak, Console.InvalidScriptArgument;
    Boolean collectionContains(int key);
    void clearCollection();
    void removeByKey(int id);
    void push(Element element);
    void updateByKey(Console console, int inputId, boolean fromFile) throws Insert.ValidationBreak, Console.InvalidScriptArgument;
    Element getElementById(int inputId);
    void swapElements(int inputId, Element element);
}
