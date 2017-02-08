package com.ymatou.datamonitor.config.monitor;

/**
 * Created by qianmin on 2017/1/23.
 */
public class MongoProperties {

    private String dbUrl;
    private String dbName;

    public MongoProperties() {
    }

    public MongoProperties(String dbUrl, String dbName) {
        this.dbUrl = dbUrl;
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }
}
