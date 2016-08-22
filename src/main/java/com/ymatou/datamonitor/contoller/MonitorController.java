/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.contoller;

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
        
        try {
            monitorService.addMonitor(monitorVo);
        } catch (SchedulerException e) {
            logger.warn("Add Monitor Failed.", e);
            WapperUtil.error("Add Monitor Failed.");
        }
        
        return WapperUtil.success();
    }
    
    @RequestMapping(path = "/modify", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object modifyMonitor(@RequestBody MonitorVo monitorVo){
        
        try {
            monitorService.modifyMonitor(monitorVo);
        } catch (SchedulerException e) {
            logger.warn("Modify Monitor Failed.", e);
            WapperUtil.error("Modify Monitor Failed.");
        }
        
        return WapperUtil.success();
    }
    
    @RequestMapping(path = "/remove", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object removeMonitor(@RequestBody MonitorVo monitorVo){
        
        try {
            monitorService.removeMonitor(monitorVo);
        } catch (SchedulerException e) {
            logger.warn("Remove Monitor Failed.", e);
            WapperUtil.error("Remove Monitor Failed.");
        }
        
        return WapperUtil.success();
    }
    
    @RequestMapping(path = "/pause", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object pauseMonitor(@RequestBody MonitorVo monitorVo){
        
        try {
            monitorService.pauseMonitor(monitorVo);
        } catch (SchedulerException e) {
            logger.warn("Pause Monitor Failed.", e);
            WapperUtil.error("Pause Monitor Failed.");
        }
        
        return WapperUtil.success();
    }
    
    @RequestMapping(path = "/resume", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object resumeMonitor(@RequestBody MonitorVo monitorVo){
        
        try {
            monitorService.resumeMonitor(monitorVo);
        } catch (SchedulerException e) {
            logger.warn("Resume Monitor Failed.", e);
            WapperUtil.error("Resume Monitor Failed.");
        }
        
        return WapperUtil.success();
    }
    
    @RequestMapping(path = "/runNow", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object runNow(@RequestBody MonitorVo monitorVo){
        
        monitorService.runNow(monitorVo);
        
        return WapperUtil.success();
    }
    
    @RequestMapping(path = "/list", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object list(MonitorVo monitorVo, Pageable pageable){

        monitorVo.setStatus(StatusEnum.ENABLE.name());
        Page<MonitorVo> monitorPage = monitorService.listMonitor(monitorVo,pageable);

        return WapperUtil.success(monitorPage);
    }
}
