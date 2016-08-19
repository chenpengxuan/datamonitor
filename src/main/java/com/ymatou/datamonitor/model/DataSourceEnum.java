/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.model;

/**
 * @author luoshiqian 2016/8/18 12:47
 */
public enum DataSourceEnum {

    ymtRelease(DbEnum.sqlserver)
    ;
    DbEnum dbEnum;

    DataSourceEnum(DbEnum dbEnum) {
        this.dbEnum = dbEnum;
    }

    public DbEnum getDbEnum() {
        return dbEnum;
    }

    public static DataSourceEnum findByDbEnum(DbEnum dbEnum){
        for (DataSourceEnum dataSourceEnum:DataSourceEnum.values()){
            if(dataSourceEnum.getDbEnum() == dbEnum){
                return dataSourceEnum;
            }
        }
        return null;
    }

}
