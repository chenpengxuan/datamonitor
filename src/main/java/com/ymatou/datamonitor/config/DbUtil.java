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
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author luoshiqian 2016/8/18 12:55
 */
public class DbUtil {

    public static Map<String, Database> databaseMap = Maps.newHashMap();
    public static Map<String, DataSource> datasourceMap = Maps.newHashMap();
    public static Map<String, JdbcTemplate> jdbcTemplateMap = Maps.newHashMap();

    public static void addDataBase(DataSourceEnum dataSourceEnum, DataSource dataSource) {
        Database database = Database.fromDataSource(dataSource);
        databaseMap.put(dataSourceEnum.name(), database);
        datasourceMap.put(dataSourceEnum.name(), dataSource);
        jdbcTemplateMap.put(dataSourceEnum.name(), new JdbcTemplate(dataSource));
    }

    public static Database getDb(String name) {
        return databaseMap.get(name);
    }
    public static DataSource getDs(String name) {
        return datasourceMap.get(name);
    }

    public static JdbcTemplate getJdbcTemplate(String name) {
        return jdbcTemplateMap.get(name);
    }
}
