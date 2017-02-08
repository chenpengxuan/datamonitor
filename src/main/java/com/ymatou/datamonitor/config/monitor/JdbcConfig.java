/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.config.monitor;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(locations = "classpath:jdbc.properties", prefix="jdbc")
@DisconfFile(fileName = "jdbc.properties")
public class JdbcConfig {

    private List<JdbcProperties> configs = new ArrayList<>();

    public List<JdbcProperties> getConfigs() {
        return configs;
    }

    public void setConfigs(List<JdbcProperties> configs) {
        this.configs = configs;
    }
}
