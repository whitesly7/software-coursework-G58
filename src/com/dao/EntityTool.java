package com.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for working with JSON data and entity objects.
 *
 * @param <T> The type of the entity.
 */
public class EntityTool<T> {
    private   String jsonData="";

    private Class<T> type;

    /**
     * Constructs an EntityTool with the specified JSON data and element type.
     *
     * @param jsonData    The JSON data file path.
     * @param elementType The class type of the elements.
     */
    public EntityTool(String jsonData,Class<T> elementType) {
        this.type = elementType;
        this.jsonData = jsonData;
    }

    /**
     * Saves a list of entities to JSON file.
     *
     * @param entityList The list of entities to be saved.
     * @throws IOException If an I/O error occurs while saving to JSON.
     */
    public  void saveToJson(List<T> entityList) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(jsonData), entityList);
    }

    /**
     * Loads a list of entities from JSON file.
     *
     * @return The list of loaded entities.
     * @throws IOException If an I/O error occurs while loading from JSON.
     */
    public <T> List<T> loadFromJson() throws IOException {
        File file = new File(jsonData);
        if (!file.exists()) {
            // 如果文件不存在，则创建一个空的文件
            file.createNewFile();
            return new ArrayList<>();
        }
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<HashMap> arrayList;
        try {
            arrayList= mapper.readValue(file, ArrayList.class);
        }catch (MismatchedInputException e){
            arrayList=new ArrayList<>();
        }


        List<T> list = new ArrayList<>();
        for (HashMap map : arrayList) {
            T element = (T) mapper.convertValue(map, type);
            list.add(element);
        }

        List<T> filteredList = list.stream()
                .filter(obj -> obj.getClass().getGenericSuperclass().equals(type.getGenericSuperclass()))
                .collect(Collectors.toList());
        return filteredList;
    }

    /**
     * Adds a new entity to the JSON file.
     *
     * @param entity The entity to be added.
     * @throws IOException If an I/O error occurs while adding the entity.
     */
    public  void addEntity(T entity) throws IOException {
        List<T> entityList = this.loadFromJson();
        entityList.add(entity);
        this.saveToJson(entityList);

    }

    /**
     * Removes an entity from the JSON file by index.
     *
     * @param index The index of the entity to be removed.
     * @throws IOException If an I/O error occurs while removing the entity.
     */
    public  void removeEntity(int index) throws IOException {
        List<T> entityList = this.loadFromJson();
        entityList.remove(index);
        this.saveToJson(entityList);
    }

    /**
     * Updates an entity in the JSON file by index.
     *
     * @param index  The index of the entity to be updated.
     * @param entity The new entity data.
     * @throws IOException If an I/O error occurs while updating the entity.
     */
    public  void updateEntity(int index, T entity) throws IOException {
        List<T> entityList = this.loadFromJson();
        entityList.set(index, entity);
        this.saveToJson(entityList);
    }

    /**
     * Retrieves an entity from the JSON file by index.
     *
     * @param index The index of the entity to be retrieved.
     * @return The retrieved entity.
     * @throws IOException If an I/O error occurs while retrieving the entity.
     */
    public  T getEntity(int index) throws IOException {
        List<T> entityList = this.loadFromJson();
        return entityList.get(index);
    }
}