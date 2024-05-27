package com.dao;

import com.pojo.Parent;
/**
 * Data Access Object (DAO) for accessing Parent entities.
 *
 * @param <T> The type parameter for ParentDao.
 */
public class ParentDao<T> extends BaseDao<Parent>{
    /**
     * Constructs a ParentDao object.
     * Initializes the ParentDao with the filename for JSON storage.
     */
    public ParentDao() {
        super("Parent.json");
    }
}
