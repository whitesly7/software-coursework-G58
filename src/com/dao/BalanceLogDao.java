package com.dao;

import com.pojo.BalanceLog;

/**
 * Data Access Object specifically for managing {@link BalanceLog} instances.
 * This class extends {@link BaseDao} to provide custom functionality tailored
 * for {@link BalanceLog} entities, utilizing a JSON-based storage.
 */
public class BalanceLogDao<T>  extends BaseDao<BalanceLog>{
    /**
     * Constructs a new {@code BalanceLogDao} with a predefined JSON file name.
     * Initializes the data access object to use "BalanceLog.json" as its data source.
     */
    public BalanceLogDao() {
        super("BalanceLog.json");
    }
}
