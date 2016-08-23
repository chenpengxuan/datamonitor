/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.service.impl;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ymatou.datamonitor.model.vo.MonitorVo;
import com.ymatou.datamonitor.service.MonitorService;
import com.ymatou.datamonitor.util.Constants;
import com.ymatou.datamonitor.util.JpaEntityUtil;
import com.ymatou.datamonitor.util.SpringContextHolder;

/**
 * 
 * @author qianmin 2016年8月18日 下午4:12:12
 *
 */
public class ExecuteJob implements Job{

    public static final Logger logger = LoggerFactory.getLogger(ExecuteJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JpaEntityUtil jpaEntityUtil = SpringContextHolder.getBean(JpaEntityUtil.class);
        MonitorService monitorService = SpringContextHolder.getBean(MonitorService.class);
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
