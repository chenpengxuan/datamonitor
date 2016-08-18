/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ymatou.datamonitor.model.pojo.Monitor;

/**
 * 
 * @author qianmin 2016年8月18日 下午2:55:53
 *
 */
@Repository
public interface MonitorRepository extends JpaRepository<Monitor,Long>{

}
