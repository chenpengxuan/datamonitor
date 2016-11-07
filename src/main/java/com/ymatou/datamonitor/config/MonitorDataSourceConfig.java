/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.ymatou.datamonitor.model.DataSourceSettingEnum;


@Configuration
@EnableConfigurationProperties({ConnectionConfig.class})
public class MonitorDataSourceConfig {
    @Autowired
    private ConnectionConfig connectionConfig;

    @Bean(name = "monitorDataSourceList")
    public List<DataSource> monitorDataSourceList() {

        List<DataSource> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(connectionConfig.getDbSources())) {
            for (DbSource dbSource : connectionConfig.getDbSources()) {
                DruidDataSource dataSource = newDataSource(dbSource);
                DbUtil.addDataBase(dbSource.getDbName(), dataSource);
                list.add(dataSource);
            }
        }
        return list;
    }


    public DruidDataSource newDataSource(DbSource dbSource) {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDbType(dbSource.getDbType());
        dataSource.setUrl(dbSource.getUrl());
        dataSource.setUsername(dbSource.getUsername());
        dataSource.setPassword(dbSource.getPassword());
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
}
