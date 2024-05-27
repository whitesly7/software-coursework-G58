package com.dao;

import com.pojo.Child;
/**
 * Data Access Object (DAO) for accessing and manipulating Child entities.
 *
 * @param <T> The type of the Child entity or its subclass.
 */
public class ChildDao<T> extends BaseDao<Child>{
    /**
     * Constructs a ChildDao instance.
     */
    public ChildDao() {
        super("Child.json");
    }
}
