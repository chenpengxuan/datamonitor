/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ymatou.common.mybatis.annotation.MyBatisDao;
import com.ymatou.datamonitor.model.vo.ExecLogVo;

/**
 * 
 * @author qianmin 2016年8月22日 下午3:48:12
 *
 */
@MyBatisDao
public interface ExecLogMapper {
    
    Page<ExecLogVo> findByExecLogVo(@Param("execLog") ExecLogVo ExecLog, @Param("pageable") Pageable pageable);
}
