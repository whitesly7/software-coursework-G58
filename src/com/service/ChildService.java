package com.service;

import com.dao.ChildDao;
import com.pojo.Child;
/**
 * Service layer for handling business operations on Child entities.
 * This class extends the {@link ChildDao} to leverage generic CRUD operations.
 */
public class ChildService extends ChildDao<Child>  {
}
