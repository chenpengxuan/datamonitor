package com.ymatou.datamonitor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.ServletContextListener;

/**
 * Created by qianmin on 2017/2/9.
 */
@Configuration
@DependsOn("disconfMgrBean2")
public class WebConfigInitializer{

    @Autowired
    private BizConfig bizConfig;

    @Bean
    public EmbeddedServletContainerFactory createEmbeddedServletContainerFactory() {
        TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();
        tomcatFactory.setPort(bizConfig.getServerPort());
        return tomcatFactory;
    }
}
