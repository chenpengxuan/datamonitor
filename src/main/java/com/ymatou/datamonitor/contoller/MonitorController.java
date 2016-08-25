/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.contoller;

import com.ymatou.datamonitor.model.pojo.Monitor;
import com.ymatou.datamonitor.util.Converter;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ymatou.datamonitor.model.StatusEnum;
import com.ymatou.datamonitor.model.vo.MonitorVo;
import com.ymatou.datamonitor.service.MonitorService;
import com.ymatou.datamonitor.util.WapperUtil;

import java.text.ParseException;

/**
 * 
 * @author qianmin 2016年8月18日 下午2:41:41
 *
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {
    
    private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);
    
    @Autowired
    private MonitorService monitorService;
    
    @RequestMapping(path = "/add", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object addMonitor(@RequestBody MonitorVo monitorVo){
        if(!monitorVo.getSql().toLowerCase().startsWith("select")){
            return WapperUtil.error("SQL只允许SELECT!");
        }
        try{
            new CronExpression(monitorVo.getCronExpression());
        }catch (ParseException e){
            return WapperUtil.error("cron表达式格式不正确!");
        }

        try {
            monitorService.addMonitor(monitorVo);
        } catch (SchedulerException e) {
            logger.warn("Add Monitor Failed.", e);
            return WapperUtil.error("SchedulerException!");
        }
        
        return WapperUtil.success();
    }
    
    @RequestMapping(path = "/modify", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object modifyMonitor(@RequestBody MonitorVo monitorVo){
        if(!monitorVo.getSql().toLowerCase().startsWith("select")){
            return WapperUtil.error("SQL只允许SELECT!");
        }
        try{
            new CronExpression(monitorVo.getCronExpression());
        }catch (ParseException e){
            return WapperUtil.error("cron表达式格式不正确!");
        }

        try {
            monitorService.modifyMonitor(monitorVo);
        } catch (SchedulerException e) {
            logger.warn("Modify Monitor Failed.", e);
            WapperUtil.error("Modify Monitor Failed.");
        }
        
        return WapperUtil.success();
    }
    
    @RequestMapping(path = "/remove")
    public Object removeMonitor(MonitorVo monitorVo){
        
        try {
            monitorService.removeMonitor(monitorVo);
        } catch (SchedulerException e) {
            logger.warn("Remove Monitor Failed.", e);
            WapperUtil.error("Remove Monitor Failed.");
        }
        
        return WapperUtil.success();
    }
    
    @RequestMapping(path = "/pause")
    public Object pauseMonitor(MonitorVo monitorVo){
        
        try {
            monitorService.pauseMonitor(monitorVo);
        } catch (SchedulerException e) {
            logger.warn("Pause Monitor Failed.", e);
            WapperUtil.error("Pause Monitor Failed.");
        }
        
        return WapperUtil.success();
    }
    
    @RequestMapping(path = "/resume")
    public Object resumeMonitor(MonitorVo monitorVo){
        
        try {
            monitorService.resumeMonitor(monitorVo);
        } catch (SchedulerException e) {
            logger.warn("Resume Monitor Failed.", e);
            WapperUtil.error("Resume Monitor Failed.");
        }
        
        return WapperUtil.success();
    }
    
    @RequestMapping(path = "/runNow")
    public Object runNow(MonitorVo monitorVo){
        Monitor monitor = monitorService.findById(monitorVo.getId());

        monitorService.runNow(MonitorVo.from(monitor),Boolean.FALSE);
        
        return WapperUtil.success();
    }
    
    @RequestMapping(path = "/list")
    public Object list(MonitorVo monitorVo, Pageable pageable){

        monitorVo.setStatus(StatusEnum.ENABLE.name());
        Page<MonitorVo> monitorPage = monitorService.listMonitor(monitorVo,pageable);

        return WapperUtil.success(monitorPage);
    }
    
    @RequestMapping(path = "/get")
    public Object get(Long id){

        Monitor monitor = monitorService.findById(id);
        return WapperUtil.success(monitor);
    }

}
