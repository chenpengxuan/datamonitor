/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymatou.datamonitor.dao.jpa.MonitorRepository;
import com.ymatou.datamonitor.dao.mapper.MonitorMapper;
import com.ymatou.datamonitor.model.pojo.Monitor;
import com.ymatou.datamonitor.model.vo.MonitorVo;
import com.ymatou.datamonitor.service.MonitorService;

/**
 * 
 * @author qianmin 2016年8月18日 下午2:50:49
 *
 */
@Service
public class MonitorServiceImpl  extends BaseServiceImpl<Monitor> implements MonitorService{
    
    @Autowired
    private MonitorRepository monitorRepository;
    
    @Autowired
    private MonitorMapper monitorMapper;
    
    public MonitorServiceImpl(MonitorRepository monitorRepository) {
        super(monitorRepository);
        this.monitorRepository = monitorRepository;
    }

    @Override
    public void addMonitor(MonitorVo monitorVo) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void modifyMonitor(MonitorVo monitorVo) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeMonitor(MonitorVo monitorVo) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pauseMonitor(MonitorVo monitorVo) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resumeMonitor(MonitorVo monitorVo) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void runNow(MonitorVo monitorVo) {
        // TODO Auto-generated method stub
        
    }

}
