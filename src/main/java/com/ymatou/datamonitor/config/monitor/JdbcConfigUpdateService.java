package com.ymatou.datamonitor.config.monitor;

import com.alibaba.druid.pool.DruidDataSource;
import com.baidu.disconf.client.DisConf;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import com.baidu.disconf.client.utils.AppTagHelper;
import com.baidu.disconf.client.utils.PatternUtils;
import com.baidu.disconf.client.utils.TagPlaceholderHelper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by qianmin on 2017/2/6.
 */
@DisconfUpdateService(confFileKeys={"jdbc.properties"})
@Component
public class JdbcConfigUpdateService implements IDisconfUpdate{

    private static final Logger logger = LoggerFactory.getLogger(JdbcConfigUpdateService.class);
    private static final String NAME = "jdbc.configs[&].dbName";
    private static final String URL = "jdbc.configs[&].url";
    private static final String TYPE = "jdbc.configs[&].dbType";
    private static final String USER_NAME = "jdbc.configs[&].username";
    private static final String PASSWORD = "jdbc.configs[&].password";

    @Override
    public void reload() throws Exception {
        File mongoFile = DisConf.getLocalConfig("jdbc.properties");
        Properties props = new Properties();
        props.load(new FileInputStream(mongoFile));
        clearMap();

        int index = 0;
        boolean flag = false;
        do {
            String dbUrl = (String) props.get(URL.replace("&", String.valueOf(index)));
            String dbName = (String) props.get(NAME.replace("&", String.valueOf(index)));
            String dbType = (String) props.get(TYPE.replace("&", String.valueOf(index)));
            String userName = (String) props.get(USER_NAME.replace("&", String.valueOf(index)));
            String password = (String) props.get(PASSWORD.replace("&", String.valueOf(index)));

            if (StringUtils.isNotBlank(dbUrl) && StringUtils.isNotBlank(dbName) && StringUtils.isNotBlank(dbType)
                    && StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
                password = TagPlaceholderHelper.replaceTag(password);
                JdbcProperties jdbcProperties = new JdbcProperties(dbName,dbType, dbUrl, userName, password);
                DruidDataSource dataSource = DbUtil.newDataSource(jdbcProperties);
                DataSourceCollections.addDataBase(jdbcProperties.getDbName(), jdbcProperties.getDbType(), dataSource);
                flag = true;
                index++;
            }else {
                flag = false;
            }
        } while (flag);

        logger.info("jdbc properties reload.");
    }

    public void clearMap(){
        DataSourceCollections.datasourceMap.values().forEach(e ->
                e.close()
        );
        DataSourceCollections.sqlDbMap.clear();
        DataSourceCollections.datasourceMap.clear();
        DataSourceCollections.jdbcTemplateMap.clear();
    }
}
