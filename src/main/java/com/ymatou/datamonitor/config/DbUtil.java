/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.config;

import java.util.Map;

import javax.sql.DataSource;

import com.github.davidmoten.rx.jdbc.Database;
import com.google.common.collect.Maps;
import com.ymatou.datamonitor.model.DataSourceEnum;

/**
 * @author luoshiqian 2016/8/18 12:55
 */
public class DbUtil {

    public static Map<String, Database> databaseMap = Maps.newHashMap();

    public static void addDataBase(DataSourceEnum dataSourceEnum, DataSource dataSource) {
        Database database = Database.fromDataSource(dataSource);
        databaseMap.put(dataSourceEnum.name(), database);
    }

    public static Database getDb(String name) {
        return databaseMap.get(name);
    }
}
