/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.service.impl;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 
 * @author qianmin 2016年8月18日 下午4:12:12
 *
 */
@Component
public class ExecuteJob implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // TODO Auto-generated method stub
        context.getJobDetail().getKey().getName();
    }

}
