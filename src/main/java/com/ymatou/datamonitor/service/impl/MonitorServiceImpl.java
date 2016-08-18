/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.service.impl;

import javax.transaction.Transactional;

import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymatou.datamonitor.dao.jpa.MonitorRepository;
import com.ymatou.datamonitor.dao.mapper.MonitorMapper;
import com.ymatou.datamonitor.model.RunStatusEnum;
import com.ymatou.datamonitor.model.StatusEnum;
import com.ymatou.datamonitor.model.pojo.Monitor;
import com.ymatou.datamonitor.model.vo.MonitorVo;
import com.ymatou.datamonitor.service.MonitorService;
import com.ymatou.datamonitor.service.SchedulerService;
import static com.ymatou.datamonitor.util.Constants.JOB_SPEC;;

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
    
    @Autowired
    private SchedulerService schedulerService;
    
    public MonitorServiceImpl(MonitorRepository monitorRepository) {
        super(monitorRepository);
        this.monitorRepository = monitorRepository;
    }

    @Override
    @Transactional
    public void addMonitor(MonitorVo monitorVo) throws SchedulerException {
        Monitor monitor = new Monitor();
        BeanUtils.copyProperties(monitorVo, monitor);
        save(monitor);
        
        schedulerService.addJob(ExecuteJob.class, JOB_SPEC + monitor.getId(), monitor.getCronExpression());
    }

    @Override
    @Transactional
    public void modifyMonitor(MonitorVo monitorVo) throws SchedulerException {
        monitorRepository.updateCronExpressionById(monitorVo.getCronExpression(), monitorVo.getId());
        
        schedulerService.modifyScheduler(JOB_SPEC + monitorVo.getId(), monitorVo.getCronExpression());
    }

    @Override
    @Transactional
    public void removeMonitor(MonitorVo monitorVo) throws SchedulerException {
        Monitor monitor = monitorRepository.findOne(monitorVo.getId());
        monitor.setStatus(StatusEnum.DISABLE.name());
        monitorRepository.save(monitor);
        
        schedulerService.removeScheduler(JOB_SPEC + monitorVo.getId());
    }

    @Override
    @Transactional
    public void pauseMonitor(MonitorVo monitorVo) throws SchedulerException {
        monitorRepository.updateRunStatusById(RunStatusEnum.STOPPED.name(), monitorVo.getId());
        
        schedulerService.pauseScheduler(JOB_SPEC + monitorVo.getId());
    }

    @Override
    @Transactional
    public void resumeMonitor(MonitorVo monitorVo) throws SchedulerException {
        monitorRepository.updateRunStatusById(RunStatusEnum.RUNNING.name(), monitorVo.getId());
        
        schedulerService.removeScheduler(JOB_SPEC + monitorVo.getId());
    }

    @Override
    public void runNow(MonitorVo monitorVo) {
        // TODO Auto-generated method stub
        
    }

}
