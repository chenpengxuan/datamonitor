/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.config.monitor;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableConfigurationProperties({JdbcConfig.class, MongoConfig.class})
public class MonitorDataSourceConfig {

    @Autowired
    private JdbcConfig jdbcConfig;

    @Autowired
    private MongoConfig mongoConfig;

    @Bean(name = "monitorDataSourceList")
    public List<DataSource> monitorDataSourceList() {

        List<DataSource> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jdbcConfig.getConfigs())) {
            for (JdbcProperties jdbcProperties : jdbcConfig.getConfigs()) {
                DruidDataSource dataSource = DbUtil.newDataSource(jdbcProperties);
                DataSourceCollections.addDataBase(jdbcProperties.getDbName(), jdbcProperties.getDbType(), dataSource);
                list.add(dataSource);
            }
        }
        return list;
    }

    @Bean(name = "monitorMongoDbFactoryList")
    public List<MongoDbFactory> monitorMongoDbFactoryList() {

        List<MongoDbFactory> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(mongoConfig.getConfigs())) {
            for (MongoProperties mongoProperties : mongoConfig.getConfigs()) {
                SimpleMongoDbFactory mongoDbFactory = DbUtil.newMongoDbFactory(mongoProperties);
                DataSourceCollections.addMongoDataBase(mongoProperties.getDbName(), mongoDbFactory);
                list.add(mongoDbFactory);
            }
        }
        return list;
    }
}
