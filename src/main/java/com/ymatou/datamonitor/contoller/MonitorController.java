/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.contoller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ymatou.datamonitor.model.vo.MonitorVo;

/**
 * 
 * @author qianmin 2016年8月18日 下午2:41:41
 *
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {
    
    @RequestMapping(path = "/add", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object addMonitor(@RequestBody MonitorVo monitorVo){
        
        return null;
    }
    
    @RequestMapping(path = "/modify", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object modifyMonitor(@RequestBody MonitorVo monitorVo){
        
        return null;
    }
    
    @RequestMapping(path = "/remove", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object removeMonitor(@RequestBody MonitorVo monitorVo){
        
        return null;
    }
    
    @RequestMapping(path = "/pause", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object pauseMonitor(@RequestBody MonitorVo monitorVo){
        
        return null;
    }
    
    @RequestMapping(path = "/resume", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object resumeMonitor(@RequestBody MonitorVo monitorVo){
        
        return null;
    }
    
    @RequestMapping(path = "/runNow", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object runNow(@RequestBody MonitorVo monitorVo){
        
        return null;
    }
}
