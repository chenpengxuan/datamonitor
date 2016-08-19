/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoshiqian 2016/8/9 13:01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@SpringApplicationConfiguration(classes = BaseTest.class)
//@IntegrationTest
//@WebAppConfiguration
@EnableAspectJAutoProxy
@EnableAutoConfiguration
        (exclude = {DataSourceAutoConfiguration.class
                , DataSourceTransactionManagerAutoConfiguration.class
                , JpaBaseConfiguration.class, HibernateJpaAutoConfiguration.class, PersistenceExceptionTranslationAutoConfiguration.class
        })
@ComponentScan(basePackages = "com.ymatou",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = RestController.class)
        })
public class BaseTest {
}
