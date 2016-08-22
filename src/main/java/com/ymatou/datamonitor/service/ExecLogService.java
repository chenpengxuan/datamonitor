/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */
package com.ymatou.datamonitor.service;

import com.ymatou.datamonitor.model.pojo.ExecLog;
import com.ymatou.datamonitor.model.vo.ExecLogVo;
import com.ymatou.datamonitor.model.vo.MonitorVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface ExecLogService extends BaseService<ExecLog>  {

    /**
     * 保存执行日志，异常通知
     * 
     * @param monitorVo
     * @param result
     */
    void saveLogAndDecideNotity(MonitorVo monitorVo, List<Map<String,Object>> result);
    
    /**
     * 获取执行日志
     * 
     * @param execLogVo
     * @param pageable
     * @return
     */
    Page<ExecLogVo> listExecLogVo(ExecLogVo execLogVo, Pageable pageable);
}
