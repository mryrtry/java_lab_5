package Utility;

import Commands.Insert;
import Data.DataProvider;
import Data.DataProviderImplementation;
import Model.Dragon;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ElementsServiceHashMap implements ElementService {
    private final HashMap<Integer, Element> collection;
    private final LocalDateTime initDate;
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
    public enum CollectionElement {
        DRAGON
    }
    protected ElementsServiceHashMap(DataProvider dataProvider) {

        Collection<Element> currentCollection = dataProvider.loadElements();
        this.initDate = dataProvider.loadCollectionInitTime();

        // Складываем их в HashMap;
        this.collection = new HashMap<>();

        for (Element element: currentCollection)
            collection.put(element.getId(), element);

    }

    @Override
    public String getInitTime() {
        return initDate.format(formatter);
    }

    @Override
    public LocalDateTime getUnparsedInitTime() {
        return initDate;
    }

    @Override
    public Collection<Element> getCollection() {
        return collection.values();
    }

    @Override
    public Integer getCollectionSize() {
        return collection.size();
    }

    @Override
    public Class getCollectionType() {
        return collection.getClass();
    }

    @Override
    public Boolean collectionContains(int key) {
        return collection.containsKey(key);
    }

    @Override
    public CollectionElement[] getCollectionClasses() {
        return CollectionElement.values();
    }

    @Override
    public Element validateCollectionElement(CollectionElement collectionElement, Console console, boolean fromFile) throws Insert.ValidationBreak, Console.InvalidScriptArgument {
        switch (collectionElement) {
            case DRAGON -> {
                Dragon insertingElement = new Dragon();
                return insertingElement.validateElement(console, fromFile);
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public void push(Element element) {
        collection.put(element.getId(), element);
    }
    @Override
    public void removeByKey(int id) {
        collection.remove(id);
    }

    @Override
    public void updateByKey(Console console, int id, boolean fromFile) throws Insert.ValidationBreak, Console.InvalidScriptArgument {
        Element updatingElement = collection.get(id);
        console.println("update: Обновляется элемент класса " + updatingElement.getClass().getSimpleName() + ";");
        updatingElement.updateElement(console, fromFile);
    }

    @Override
    public void clearCollection() {
        collection.clear();
    }

    @Override
    public Element getElementById(int inputId) {
        return collection.get(inputId);
    }

    @Override
    public void swapElements(int inputId, Element element) {
        collection.remove(inputId);
        collection.put(inputId, element);
    }
}
