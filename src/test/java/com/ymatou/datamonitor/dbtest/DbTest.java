/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.dbtest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.druid.sql.PagerUtils;
import com.ymatou.datamonitor.config.DbUtil;
import com.ymatou.datamonitor.config.MonitorDataSourceConfig;
import com.ymatou.datamonitor.model.DataSourceEnum;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.davidmoten.rx.jdbc.Database;
import com.ymatou.datamonitor.model.DataSourceSettingEnum;
import com.ymatou.datamonitor.model.DbEnum;
import com.ymatou.datamonitor.util.MapResultSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

/**
 * @author luoshiqian 2016/8/17 17:25
 */
public class DbTest {


    @Test
    public void mysqlTest() {

        Database db = Database.from(
                "jdbc:mysql://localhost:3306/datamonitor?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useOldAliasMetadataBehavior=true",
                "root", "123456");
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
    public void pageTest(){

        String sqlSqlserver = PagerUtils.count("select * from ymt_orders order by daddtime desc",DbEnum.sqlserver.name());
        String sqlMysql = PagerUtils.count("select * from ymt_orders order by daddtime desc",DbEnum.mysql.name());

        System.out.println(sqlSqlserver);
        System.out.println(sqlMysql);


        String sqlSqlserver1 = PagerUtils.limit("select top 100 * from ymt_orders order by daddtime desc",DbEnum.sqlserver.name(),0,1000);
        String sqlMysql1 = PagerUtils.limit("select * from ymt_orders order by daddtime desc",DbEnum.mysql.name(),0,1000);

        System.out.println(sqlSqlserver1);
        System.out.println(sqlMysql1);


    }


    @Test
    public void testTimeout(){

        getDataSource();


//        Connection connection = DataSourceUtils.getConnection(dataSource);

//        try {
//            Statement s = connection.createStatement();
//            s.setQueryTimeout(1);
//            System.out.println(System.currentTimeMillis());
//            ResultSet resultSet = s.executeQuery("select top 100000 * from ymt_orders order by sphone");
//            System.out.println(System.currentTimeMillis());
////            while (resultSet.next()){
//////                System.out.println(resultSet.getObject(1));
////            }
////
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println(System.currentTimeMillis());
//        //执行sql
//        List<Map<String, Object>> result =  DbUtil.getDb("ymtRelease").select("select top 100000 * from ymt_orders order by sphone")
//                .get(new MapResultSet())
//                .toList().toBlocking().single();
//        System.out.println(System.currentTimeMillis());
//        System.out.println(result);

    }


    private DataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDbType("sqlserver");
        dataSource.setUrl("jdbc:jtds:sqlserver://172.16.110.153:1433/YmtRelease");
        dataSource.setUsername("YmtRelease");
        dataSource.setPassword("123456");
        dataSource.setTimeBetweenConnectErrorMillis(Integer.valueOf(DataSourceSettingEnum.maxWait.getValue()));
        dataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(DataSourceSettingEnum.maxWait.getValue()));
        dataSource.setValidationQuery(DataSourceSettingEnum.validationQuery.getValue());
        dataSource.setTestWhileIdle(Boolean.valueOf(DataSourceSettingEnum.testWhileIdle.getValue()));
        dataSource.setTestOnBorrow(Boolean.valueOf(DataSourceSettingEnum.testOnBorrow.getValue()));
        dataSource.setDefaultAutoCommit(false);
        dataSource.setQueryTimeout(Integer.valueOf(DataSourceSettingEnum.queryTimeout.getValue()));
        dataSource.setTransactionThresholdMillis(1000L);
        dataSource.setTransactionQueryTimeout(1);

        DbUtil.addDataBase(DataSourceEnum.ymtRelease.name(),dataSource);
        return dataSource;
    }
    @Test
    public void testTransactionTimeout()throws Exception{

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(getDataSource());
        transactionManager.setDefaultTimeout(1);

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.setTimeout(1);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(transactionManager.getDataSource());
        transactionTemplate.execute(status -> {

            System.out.println(System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Connection connection = transactionManager.getDataSource().getConnection();
                connection.createStatement().execute("select top 100000 * from ymt_orders order by sphone");
            } catch (SQLException e) {
                e.printStackTrace();
            }


//            jdbcTemplate.execute("\"select top 100000 * from ymt_orders order by sphone\"");
//            //执行sql
//            List<Map<String, Object>> result =  DbUtil.getDb("ymtRelease").select("select top 100000 * from ymt_orders order by sphone")
//                    .get(new MapResultSet())
//                    .toList().toBlocking().single();
//            System.out.println(System.currentTimeMillis());
//            System.out.println(result);
            return null;
        });


    }
}
