/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ymatou.datamonitor.model.pojo.Monitor;

/**
 * 
 * @author qianmin 2016年8月18日 下午2:55:53
 *
 */
@Repository
public interface MonitorRepository extends JpaRepository<Monitor,Long>{

    @Modifying
    @Query("update Monitor m set m.status = ?1 where m.id = ?2")
    int updateStatusById(String status, Long id);
    
    @Modifying
    @Query("update Monitor m set m.runStatus = ?1 where m.id = ?2")
    int updateRunStatusById(String runStatus, Long id);
    
    @Modifying
    @Query("update Monitor m set m.cronExpression = ?1 where m.id = ?2")
    int updateCronExpressionById(String cronExpression, Long id);
}
