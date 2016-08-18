/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.dbtest;

import java.util.List;
import java.util.Map;

import com.alibaba.druid.sql.PagerUtils;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.davidmoten.rx.jdbc.Database;
import com.ymatou.datamonitor.model.DataSourceSettingEnum;
import com.ymatou.datamonitor.model.DbEnum;
import com.ymatou.datamonitor.util.MapResultSet;

/**
 * @author luoshiqian 2016/8/17 17:25
 */
public class DbTest {


    @Test
    public void mysqlTest() {

        Database db = Database.from(
                "jdbc:mysql://localhost:3306/datamonitor?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useOldAliasMetadataBehavior=true",
                "root", "");
        List<Map<String, Object>> objectList =
                db.select("select * from user")
                        .get(new MapResultSet()).toList().toBlocking().single();

        System.out.println(objectList);

        List<Map<String, Object>> objectList1 =
                db.select("select count(0) from user")
                        .get(new MapResultSet()).toList().toBlocking().single();

        System.out.println(objectList1);
        // rs.getMetaData().fields[0].name

        List<String> names = db
                .select("select name from db_source")
                .getAs(String.class)
                .toList().toBlocking().single();
        System.out.println(names);


    }

    @Test
    public void sqlServerTest() {


        DruidDataSource dataSource = new DruidDataSource();
        // dataSource.setDriverClassName(connectionConfig.getDriver());
        dataSource.setDbType(DbEnum.sqlserver.name());
        dataSource.setUrl("jdbc:jtds:sqlserver://172.16.188.81:1433/YmtAlpha");
        dataSource.setUsername("dev");
        dataSource.setPassword("123456");

        dataSource.setInitialSize(Integer.valueOf(DataSourceSettingEnum.initialSize.getValue()));
        dataSource.setMinIdle(Integer.valueOf(DataSourceSettingEnum.minIdle.getValue()));
        dataSource.setMaxActive(Integer.valueOf(DataSourceSettingEnum.maxActive.getValue()));
        dataSource.setMaxActive(Integer.valueOf(DataSourceSettingEnum.maxWait.getValue()));
        dataSource.setTimeBetweenConnectErrorMillis(Integer.valueOf(DataSourceSettingEnum.maxWait.getValue()));
        dataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(DataSourceSettingEnum.maxWait.getValue()));
        dataSource.setValidationQuery(DataSourceSettingEnum.validationQuery.getValue());
        dataSource.setTestWhileIdle(Boolean.valueOf(DataSourceSettingEnum.testWhileIdle.getValue()));
        dataSource.setTestOnBorrow(Boolean.valueOf(DataSourceSettingEnum.testOnBorrow.getValue()));

        dataSource.setDefaultAutoCommit(false);

        Database db = Database.fromDataSource(dataSource);

        List<Map<String, Object>> objectList =
                db.select("select top 10 * from ymt_orders order by daddtime desc")
                        .get(new MapResultSet()).toList().toBlocking().single();


        System.out.println(objectList);


        System.out.println(dataSource);
        List<Map<String, Object>> objectList1 =
                db.select("select top 10 * from ymt_orders order by daddtime desc")
                        .get(new MapResultSet()).toList().toBlocking().single();


        System.out.println(objectList1);
    }


    @Test
    public void PageTest(){

        String sqlSqlserver = PagerUtils.count("select * from ymt_orders order by daddtime desc",DbEnum.sqlserver.name());
        String sqlMysql = PagerUtils.count("select * from ymt_orders order by daddtime desc",DbEnum.mysql.name());

        System.out.println(sqlSqlserver);
        System.out.println(sqlMysql);


        String sqlSqlserver1 = PagerUtils.limit("select top 100 * from ymt_orders order by daddtime desc",DbEnum.sqlserver.name(),0,1000);
        String sqlMysql1 = PagerUtils.limit("select * from ymt_orders order by daddtime desc",DbEnum.mysql.name(),0,1000);

        System.out.println(sqlSqlserver1);
        System.out.println(sqlMysql1);


    }

}
