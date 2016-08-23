/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.service.impl;

import com.ymatou.datamonitor.model.pojo.Monitor;
import com.ymatou.datamonitor.model.vo.MonitorVo;
import com.ymatou.datamonitor.service.MonitorService;
import com.ymatou.datamonitor.util.Constants;
import com.ymatou.datamonitor.util.JpaEntityUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author qianmin 2016年8月18日 下午4:12:12
 *
 */
@Component
public class ExecuteJob implements Job{

    public static final Logger logger = LoggerFactory.getLogger(ExecuteJob.class);

    @Autowired
    private JpaEntityUtil jpaEntityUtil;
    @Autowired
    private MonitorService monitorService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        String jobName = context.getJobDetail().getKey().getName();
        
        logger.info("begin execute job:" + jobName);

        Long id = Long.valueOf(jobName.replace(Constants.JOB_SPEC,""));
        MonitorVo monitorVo = MonitorVo.from(monitorService.findById(id));
        jpaEntityUtil.supportJpaEntity(o -> {
            monitorService.runNow(monitorVo,Boolean.TRUE);
            return null;
        });

        logger.info("end execute job:" + jobName);
    }

}
