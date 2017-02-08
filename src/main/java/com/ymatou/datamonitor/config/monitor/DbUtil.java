package com.ymatou.datamonitor.config.monitor;

import com.alibaba.druid.pool.DruidDataSource;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.ymatou.datamonitor.model.DataSourceSettingEnum;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * Created by qianmin on 2017/2/6.
 */
public class DbUtil {

    public static DruidDataSource newDataSource(JdbcProperties jdbcProperties) {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDbType(jdbcProperties.getDbType());
        dataSource.setUrl(jdbcProperties.getUrl());
        dataSource.setUsername(jdbcProperties.getUsername());
        dataSource.setPassword(jdbcProperties.getPassword());
        dataSource.setTimeBetweenConnectErrorMillis(Integer.valueOf(DataSourceSettingEnum.maxWait.getValue()));
        dataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(DataSourceSettingEnum.maxWait.getValue()));
        dataSource.setValidationQuery(DataSourceSettingEnum.validationQuery.getValue());
        dataSource.setTestWhileIdle(Boolean.valueOf(DataSourceSettingEnum.testWhileIdle.getValue()));
        dataSource.setTestOnBorrow(Boolean.valueOf(DataSourceSettingEnum.testOnBorrow.getValue()));
        dataSource.setDefaultAutoCommit(false);
        dataSource.setQueryTimeout(Integer.valueOf(DataSourceSettingEnum.queryTimeout.getValue()));
        dataSource.setTransactionQueryTimeout(Integer.valueOf(DataSourceSettingEnum.queryTimeout.getValue()));
        return dataSource;
    }

    public static SimpleMongoDbFactory newMongoDbFactory(MongoProperties mongoProperties){
        MongoClientURI mongoClientURI = new MongoClientURI(mongoProperties.getDbUrl());
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        SimpleMongoDbFactory mongoDbFactory =  new SimpleMongoDbFactory(mongoClient, mongoProperties.getDbName());
        return mongoDbFactory;
    }
}
