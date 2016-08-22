/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.service;

import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ymatou.datamonitor.model.pojo.Monitor;
import com.ymatou.datamonitor.model.vo.MonitorVo;

/**
 * 
 * @author qianmin 2016年8月18日 下午2:50:49
 *
 */
public interface MonitorService extends BaseService<Monitor> {

    /**
     * 添加任务
     * 
     * @param monitorVo
     * @throws SchedulerException
     */
    void addMonitor(MonitorVo monitorVo) throws SchedulerException;

    /**
     * 修改任务
     * 
     * @param monitorVo
     * @throws SchedulerException
     */
    void modifyMonitor(MonitorVo monitorVo) throws SchedulerException;

    /**
     * 删除任务
     * 
     * @param monitorVo
     * @throws SchedulerException
     */
    void removeMonitor(MonitorVo monitorVo) throws SchedulerException;

    /**
     * 暂停任务
     * 
     * @param monitorVo
     * @throws SchedulerException
     */
    void pauseMonitor(MonitorVo monitorVo) throws SchedulerException;

    /**
     * 重启任务
     * 
     * @param monitorVo
     * @throws SchedulerException
     */
    void resumeMonitor(MonitorVo monitorVo) throws SchedulerException;

    /**
     * 立即执行任务
     * 
     * @param monitorVo
     */
    void runNow(MonitorVo monitorVo);

    /**
     * 获取所有任务
     * 
     * @param monitorVo
     * @param pageable
     * @return
     */
    Page<MonitorVo> listMonitor(MonitorVo monitorVo, Pageable pageable);
}
