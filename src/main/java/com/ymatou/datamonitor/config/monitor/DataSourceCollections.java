/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.config.monitor;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author luoshiqian 2016/8/18 12:55
 */
public class DataSourceCollections {

    public static Map<String, String> sqlDbMap = Maps.newHashMap();
    public static Map<String, String> mongoDbMap = Maps.newHashMap();
    public static Map<String, DruidDataSource> datasourceMap = Maps.newHashMap();
    public static Map<String, JdbcTemplate> jdbcTemplateMap = Maps.newHashMap();
    public static Map<String, SimpleMongoDbFactory> mongoDbFactoryMap = Maps.newHashMap();
    public static Map<String, MongoTemplate> mongoTemplateMap = Maps.newHashMap();

    public static void addDataBase(String dbName, String dbType, DruidDataSource dataSource) {
        sqlDbMap.put(dbName, dbType);
        datasourceMap.put(dbName, dataSource);
        jdbcTemplateMap.put(dbName, new JdbcTemplate(dataSource));
    }

    public static void addMongoDataBase(String dbName, SimpleMongoDbFactory mongoDbFactory){
        mongoDbMap.put(dbName,"MongoDB");
        mongoDbFactoryMap.put(dbName, mongoDbFactory);
        mongoTemplateMap.put(dbName, new MongoTemplate(mongoDbFactory));
   }

    public static DataSource getDs(String name) {
        return datasourceMap.get(name);
    }

    public static JdbcTemplate getJdbcTemplate(String name) {
        return jdbcTemplateMap.get(name);
    }

    public static MongoDbFactory getMongoDbFactory(String name) {
        return mongoDbFactoryMap.get(name);
    }

    public static MongoTemplate getMongoTemplate(String name) {
        return mongoTemplateMap.get(name);
    }

    public static Map<String, String> getDbMap(){
        HashMap<String, String> map = new HashMap<>();
        map.putAll(sqlDbMap);
        map.putAll(mongoDbMap);
        return map;
    }
}
