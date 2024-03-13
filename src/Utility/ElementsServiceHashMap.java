package Utility;

import Commands.Insert;
import Data.DataProvider;
import Model.Dragon;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Implementation of ElementService with tools for interaction with collection
 */
public class ElementsServiceHashMap implements ElementService {
    private final HashMap<Integer, Element> collection;
    private final LocalDateTime initDate;
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
    public enum CollectionElement {
        DRAGON
    }

    /**
     * @param dataProvider
     * Принимает dataProvider для синхронизации состояния коллекции
     */
    protected ElementsServiceHashMap(DataProvider dataProvider) {

        Collection<Element> currentCollection = dataProvider.loadElements();
        this.initDate = dataProvider.loadCollectionInitTime();

        // Складываем их в HashMap;
        this.collection = new HashMap<>();

        for (Element element: currentCollection)
            collection.put(element.getId(), element);

    }

    /**
     * @return collection initialization time as String
     */
    @Override
    public String getInitTime() {
        return initDate.format(formatter);
    }

    /**
     * @return collection initialization time as LocalDateTime
     */
    @Override
    public LocalDateTime getUnparsedInitTime() {
        return initDate;
    }

    /**
     * @return actual collection as Collection
     */
    @Override
    public Collection<Element> getCollection() {
        return collection.values();
    }

    /**
     * @return amount of elements in collection
     */
    @Override
    public Integer getCollectionSize() {
        return collection.size();
    }

    /**
     * @return type of collection
     */
    @Override
    public Class getCollectionType() {
        return collection.getClass();
    }

    /**
     * @param key checking key
     * @return collection has element with such id
     */
    @Override
    public Boolean collectionContains(int key) {
        return collection.containsKey(key);
    }

    /**
     * @return all classes as String that could be in collection
     */
    @Override
    public CollectionElement[] getCollectionClasses() {
        return CollectionElement.values();
    }

    /**
     * @param collectionElement class of validating element from "Collection Clasees"
     * @param console actual console
     * @param fromFile is executing from file
     * @return validated element as Element
     * @throws Insert.ValidationBreak if exit
     * @throws Console.InvalidScriptArgument if invalid command arguments in script
     */
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

    /**
     * @param element element to push into collection
     */
    @Override
    public void push(Element element) {
        collection.put(element.getId(), element);
    }

    /**
     * @param id key of removing element
     */
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

    /**
     * clears actual collection
     */
    @Override
    public void clearCollection() {
        collection.clear();
    }

    /**
     * @param inputId
     * @return element with such id from collection
     */
    @Override
    public Element getElementById(int inputId) {
        return collection.get(inputId);
    }

    /**
     * @param inputId id of element to change
     * @param element element to push
     */
    @Override
    public void swapElements(int inputId, Element element) {
        collection.remove(inputId);
        collection.put(inputId, element);
    }
}
