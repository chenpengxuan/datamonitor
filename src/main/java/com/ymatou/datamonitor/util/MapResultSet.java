/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.util;

import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.davidmoten.rx.jdbc.ResultSetMapper;
import com.mysql.jdbc.Blob;

/**
 * @author luoshiqian 2016/8/18 10:12
 */
public class MapResultSet implements ResultSetMapper<Map<String, Object>> {
    private static final Logger logger = LoggerFactory.getLogger(MapResultSet.class);

    @Override
    public Map<String, Object> call(ResultSet rs) throws SQLException {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        ResultSetMetaData metaData = rs.getMetaData();

        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);

            Object value = rs.getObject(columnName);

            if (value instanceof Clob) {
                Clob c = (Clob) value;
                String temp = "";

                if (null != c) {
                    temp = c.getSubString(1, (int) c.length());
                }

                map.put(columnName, temp);
            } else if (value instanceof Blob) {
                Blob b = (Blob) value;
                String temp = "";
                try {
                    if (null != b) {
                        temp = new String(b.getBytes(1, (int) b.length()), "utf-8");
                    }
                } catch (UnsupportedEncodingException e) {
                    logger.info("error encode", e);
                }
                map.put(columnName, temp);
            } else {
                map.put(columnName, value);
            }
        }

        return map;
    }


}
