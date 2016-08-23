/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.service;

import com.ymatou.datamonitor.BaseTest;
import com.ymatou.datamonitor.model.pojo.ExecLog;
import com.ymatou.datamonitor.model.pojo.Monitor;
import com.ymatou.datamonitor.model.vo.MonitorVo;
import com.ymatou.datamonitor.util.Converter;
import com.ymatou.datamonitor.util.JpaEntityUtil;
import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Random;

/**
 * @author luoshiqian 2016/8/18 19:09
 */
public class MonitorServiceTest extends BaseTest{

    @Autowired
    MonitorService monitorService;
    @Autowired
    ExecLogService execLogService;
    @Autowired
    JpaEntityUtil jpaEntityUtil;

    @Test
    public void testRunNow(){

        Long id = 1L;
        Monitor monitor = monitorService.findById(id);
        MonitorVo monitorVo = Converter.convert(monitor,MonitorVo.class);

        jpaEntityUtil.supportJpaEntity(o -> {
            monitorService.runNow(monitorVo,Boolean.FALSE);
            return null;
        });


    }

    @Test
    public void test1RunNow(){

        Long id = 1L;
        Monitor monitor = monitorService.findById(id);

        ExecLog execLog  = new ExecLog();
        execLog.setResult("test");
        execLog.setResultCount(1L);
        execLog.setExecUser("");
        execLog.setExecTime(new Date());
        execLog.setMonitorId(1L);

        Monitor newM = Converter.convert(monitor,Monitor.class);
        newM.setName("tewtd"+ new Random().nextInt(10000000));
        newM.setId(null);

        jpaEntityUtil.supportJpaEntity(o -> {
//            execLogService.save(execLog);
            monitorService.save(newM);
            return null;
        });

    }

    @Test
    public void testCheckSchedule(){
        String test = "fda";
        try {
            CronScheduleBuilder.cronSchedule(test);
        } catch (Exception e) {
            e.printStackTrace();
        }


        String str = "*/5 * * * * ?";
        try {
            CronScheduleBuilder.cronSchedule(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
