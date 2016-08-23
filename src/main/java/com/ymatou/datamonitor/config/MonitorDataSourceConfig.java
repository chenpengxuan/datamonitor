/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.ymatou.datamonitor.model.DataSourceEnum;
import com.ymatou.datamonitor.model.DataSourceSettingEnum;
import com.ymatou.datamonitor.model.DbEnum;


@Configuration
@EnableConfigurationProperties({ConnectionConfig.class})
public class MonitorDataSourceConfig {
    @Autowired
    private ConnectionConfig connectionConfig;

    @Bean(name = "ymtReleaseDataSource")
    public DataSource ymtReleaseDataSource() {

        DruidDataSource dataSource = newDataSource(connectionConfig.getYmtRelease(), DataSourceEnum.ymtRelease.getDbEnum());

        DbUtil.addDataBase(DataSourceEnum.ymtRelease, dataSource);
        return dataSource;
    }



    public DruidDataSource newDataSource(DbSource dbSource, DbEnum dbEnum) {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDbType(dbEnum.name());
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
