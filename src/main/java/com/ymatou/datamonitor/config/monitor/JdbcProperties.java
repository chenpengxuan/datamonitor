/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.config.monitor;

/**
 * @author luoshiqian 2016/8/18 12:42
 */
public class JdbcProperties {

    private String dbName;
    private String dbType;
    private String url;
    private String username;
    private String password;

    public JdbcProperties() {
    }

    public JdbcProperties(String dbName, String dbType, String url, String username, String password) {
        this.dbName = dbName;
        this.dbType = dbType;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbType() {

        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
