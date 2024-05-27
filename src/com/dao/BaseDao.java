package com.dao;

import com.annotation.JsonId;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Generic data access object providing basic CRUD operations for entities.
 *
 * @param <T> Type of entity handled by this DAO.
 */
public class BaseDao<T>{
    private  EntityTool<T> entityTool;

    /**
     * Constructs a BaseDao with the specified file path.
     *
     * @param filePath The file path to load and save entities.
     */
    public BaseDao(String filePath) {
        Class<T> actualTypeArgument = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        entityTool=new EntityTool(filePath,actualTypeArgument);
    }

    /**
     * Retrieves an entity by its index in the data source.
     *
     * @param index The index of the entity to retrieve.
     * @return The entity at the specified index.
     * @throws IOException If an I/O error occurs.
     */
    public T getOneByIndex(int index) throws IOException {
        return entityTool.getEntity(index);
    }

    /**
     * Removes an entity by its identifier.
     *
     * @param id The identifier of the entity to remove.
     * @throws IOException If an I/O error occurs.
     */
    public void removeById(String id) throws IOException {
        List<Object> objects = entityTool.loadFromJson();
        for (int i = 0; i < objects.size(); i++) {
            Field[] objectFields = objects.get(i).getClass().getDeclaredFields();
            String  objid=null;
            for (Field field : objectFields) {
                // check
                Annotation annotation = field.getAnnotation(JsonId.class);
                if (annotation instanceof JsonId) {
                    // avilable
                    field.setAccessible(true);
                    try {
                        objid = (String) field.get(objects.get(i));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (id.equals(objid)) {
                entityTool.removeEntity(i);
            }
        }
    }

    /**
     * Updates an entity by its identifier.
     *
     * @param id  The identifier of the entity to update.
     * @param obj The updated entity.
     * @throws IOException If an I/O error occurs.
     */
    public void updateById(String id, T obj) throws IOException {
        List<T> objects = entityTool.loadFromJson();
        boolean found = false;
        for (int i = 0; i < objects.size(); i++) {
            Field[] objectFields = objects.get(i).getClass().getDeclaredFields();
            String objId = null;
            for (Field field : objectFields) {
                // 检查字段上是否有@JsonId注解
                Annotation annotation = field.getAnnotation(JsonId.class);
                if (annotation instanceof JsonId) {
                    // 设置字段可访问
                    field.setAccessible(true);
                    try {
                        objId = (String) field.get(objects.get(i));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            if (id.equals(objId)) {
                // 更新指定的对象
                objects.set(i, obj);
                found = true;
                break;
            }
        }
        if (found) {
            entityTool.saveToJson(objects);
        } else {
            // 未找到指定ID的对象,可以选择抛出异常或者添加新的对象
            throw new IllegalArgumentException("No object found with ID: " + id);
        }
    }

    /**
     * Saves an entity.
     *
     * @param obj The entity to save.
     * @return True if the entity is successfully saved, false otherwise.
     * @throws IOException If an I/O error occurs.
     */
    public Boolean save(T obj) throws IOException {
        List<T> dataList = entityTool.loadFromJson();
        if (dataList!=null&&dataList.size()!=0) {
        Class<?> clazz = dataList.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (T t : dataList) {
            for (Field field : fields) {
                Annotation annotation = field.getAnnotation(JsonId.class);
                if (annotation instanceof JsonId) {
                    // 设置字段可访问
                    field.setAccessible(true);
                    try {
                        String objid = (String) field.get(obj);
                        String tid=(String) field.get(t);
                        if (objid.equals(tid)) {
                            return false;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        }
        entityTool.addEntity(obj);
        return true;
    }

    /**
     * Retrieves all entities.
     *
     * @return A list containing all entities.
     * @throws IOException If an I/O error occurs.
     */
    public List<T> getAll() throws IOException {
        List<T> res = entityTool.loadFromJson();

        return  res;
    }

    /**
     * Retrieves one entity by a specified key-value pair.
     *
     * @param key   The key to search for.
     * @param value The value associated with the key.
     * @return The first entity matching the key-value pair, or null if not found.
     * @throws IOException            If an I/O error occurs.
     * @throws IllegalAccessException If access to the field is denied.
     */
    public T getOneByKey(String key,Object value) throws IOException, IllegalAccessException {
        List<T> listByKey = this.getListByKey(key, value);
        if (listByKey==null||listByKey.size()==0){
            return null;
        }else {
            return listByKey.get(0);
        }
    }
    /**
     * Retrieves entities based on multiple key-value pairs.
     *
     * @param map A map containing key-value pairs for filtering entities.
     * @return A list of entities matching all key-value pairs.
     * @throws IOException            If an I/O error occurs.
     * @throws IllegalAccessException If access to the field is denied.
     */
    public List<T> getListByKey(HashMap<String,Object> map) throws IOException, IllegalAccessException {
        if (map == null || map.size() == 0) {
            return this.getAll();
        } else if (map.size() == 1) {
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                return getListByKey(key, map.get(key));
            }
        } else {
            List<T> newList = new ArrayList<>();
            List<T> dataList = entityTool.loadFromJson();
            Class<?> clazz = dataList.get(0).getClass();
            Field[] fields = clazz.getDeclaredFields();
            Set<String> keySet = map.keySet();
            for (T obj : dataList) {
                int tagnum = 0;
                for (Field field : fields) {
                    for (String key : keySet) {
                        String fieldName = field.getName();
                        if (key.equals(fieldName)) {
                            Object fieldValue = field.get(obj);
                            if (map.get(key).equals(fieldValue)) {
                                tagnum++;
                            }
                        }
                    }
                }
                if (tagnum == map.size()) {
                    newList.add(obj);
                }
            }
            return newList;

        }
        return null;
    }

    /**
     * Retrieves entities by a specified key-value pair.
     *
     * @param key   The key to search for.
     * @param value The value associated with the key.
     * @return A list of entities matching the key-value pair.
     * @throws IOException            If an I/O error occurs.
     * @throws IllegalAccessException If access to the field is denied.
     */
    public List<T> getListByKey(String key,Object value) throws IOException, IllegalAccessException {

        List<T> newList=new ArrayList<>();
        List<T> dataList = entityTool.loadFromJson();
        if (value==null){
            return dataList;
        }
        if (dataList==null||dataList.size()==0){
            return newList;
        }
        Class<?> clazz = dataList.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (T obj : dataList) {
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (key.equals(fieldName)){
                    Object fieldValue = field.get(obj);
                    if (value.equals(fieldValue)) {
                        newList.add(obj);
                        break;
                    }
                }

            }
        }
        return newList;
    }
}
