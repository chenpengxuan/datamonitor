/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.util;

import com.github.davidmoten.rx.jdbc.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author luoshiqian 2016/8/18 10:12
 */
public class MapResultSet implements ResultSetMapper<Map<String,Object>> {
    @Override
    public Map<String,Object> call(ResultSet rs) throws SQLException {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        ResultSetMetaData metaData = rs.getMetaData();

        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            map.put(columnName, rs.getObject(columnName));
        }

        return map;
    }
}
