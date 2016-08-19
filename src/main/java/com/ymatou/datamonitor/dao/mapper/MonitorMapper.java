/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ymatou.common.mybatis.annotation.MyBatisDao;
import com.ymatou.datamonitor.model.vo.MonitorVo;

/**
 * 
 * @author qianmin 2016年8月18日 下午2:54:29
 *
 */
@MyBatisDao
public interface MonitorMapper {
    
    Page<MonitorVo> findByMonitorVo(@Param("function") MonitorVo monitor, @Param("pageable") Pageable pageable);

}
