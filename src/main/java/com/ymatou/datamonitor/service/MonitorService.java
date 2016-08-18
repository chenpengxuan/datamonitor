/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.service;

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
     * @param monitorVo
     */
    public void addMonitor(MonitorVo monitorVo);

    /**
     * 修改任务
     * @param monitorVo
     */
    public void modifyMonitor(MonitorVo monitorVo);
    
    /**
     * 删除任务
     * @param monitorVo
     */
    public void removeMonitor(MonitorVo monitorVo);

    /**
     * 暂停任务
     * @param monitorVo
     */
    public void pauseMonitor(MonitorVo monitorVo);

    /**
     * 重启任务
     * @param monitorVo
     */
    public void resumeMonitor(MonitorVo monitorVo);

    /**
     * 立即执行任务
     * @param monitorVo
     */
    public void runNow(MonitorVo monitorVo);
}
