/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import rx.functions.FuncN;

/**
 * @author luoshiqian 2016/8/18 18:42
 */
@Component
public class JpaEntityUtil {
    private static final Logger logger = LoggerFactory.getLogger(JpaEntityUtil.class);

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public Object supportJpaEntity(FuncN funcN,Object ...o) {
        openEntity();
        Object result = null;
        try{
            result = funcN.call(o);
        }catch(Exception e){
            logger.error("执行出错", e);
        }
        closeEntity();
        return result;
    }


    private void openEntity() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityManagerHolder emHolder = new EntityManagerHolder(em);
        
        if(!TransactionSynchronizationManager.hasResource(entityManagerFactory)){
            TransactionSynchronizationManager.bindResource(entityManagerFactory, emHolder);
        }
        logger.info("Open JPA EntityManager in OpenEntityManagerInViewInterceptor");
    }

    private void closeEntity() {
        if(TransactionSynchronizationManager.hasResource(entityManagerFactory)){
            EntityManagerHolder emHolder =
                    (EntityManagerHolder) TransactionSynchronizationManager.unbindResource(entityManagerFactory);
            logger.debug("Closing JPA EntityManager in OpenEntityManagerInViewInterceptor");
            EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager());
        }
    }

}
