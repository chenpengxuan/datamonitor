package com.ymatou.datamonitor.config.monitor;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by qianmin on 2017/1/23.
 */
@Component
@ConfigurationProperties(locations = "mongo.properties", prefix="mongo")
@DisconfFile(fileName = "mongo.properties")
public class MongoConfig {

    private List<MongoProperties> configs; //属性名configs与mongo.properties的属性名要保持一致

    public List<MongoProperties> getConfigs() {
        return configs;
    }

    public void setConfigs(List<MongoProperties> configs) {
        this.configs = configs;
    }
}
