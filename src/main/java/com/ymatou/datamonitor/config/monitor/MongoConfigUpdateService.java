package com.ymatou.datamonitor.config.monitor;

import com.baidu.disconf.client.DisConf;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by qianmin on 2017/2/6.
 */
@DisconfUpdateService(confFileKeys = {"mongo.properties"})
@Component
@DependsOn("disconfMgrBean2")
public class MongoConfigUpdateService implements IDisconfUpdate {

    private static final Logger logger = LoggerFactory.getLogger(MongoConfigUpdateService.class);
    private static final String URL = "mongo.configs[&].dbUrl";
    private static final String NAME = "mongo.configs[&].dbName";

    @Override
    public void reload() throws Exception {
        File mongoFile = DisConf.getLocalConfig("mongo.properties");
        Properties props = new Properties();
        props.load(new FileInputStream(mongoFile));
        clearMap();

        int index = 0;
        boolean flag = false;
        do {
            String dbUrl = (String) props.get(URL.replace("&", String.valueOf(index)));
            String dbName = (String) props.get(NAME.replace("&", String.valueOf(index)));
            if (StringUtils.isNotBlank(dbUrl) && StringUtils.isNotBlank(dbName)) {
                SimpleMongoDbFactory mongoDbFactory = DbUtil.newMongoDbFactory(new MongoProperties(dbUrl, dbName));
                DataSourceCollections.addMongoDataBase(dbName, mongoDbFactory);
                flag = true;
                index++;
            }else {
                flag = false;
            }
        } while (flag);

        logger.info("mongo properties reload.");
    }

    public void clearMap() {
        DataSourceCollections.mongoDbFactoryMap.values().forEach(dbFactory -> {
                    try {
                        dbFactory.destroy();
                    } catch (Exception e) {
                        logger.error("close mongoDbFactory failed.", e);
                    }
                }
        );
        DataSourceCollections.mongoDbMap.clear();
        DataSourceCollections.mongoDbFactoryMap.clear();
        DataSourceCollections.mongoTemplateMap.clear();
    }
}
