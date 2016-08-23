/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.service.impl;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import com.alibaba.druid.sql.PagerUtils;
import com.github.davidmoten.rx.jdbc.Database;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ymatou.datamonitor.config.DbUtil;
import com.ymatou.datamonitor.model.DataSourceEnum;
import com.ymatou.datamonitor.model.DbEnum;
import com.ymatou.datamonitor.service.ExecLogService;
import com.ymatou.datamonitor.util.MapResultSet;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ymatou.datamonitor.dao.jpa.MonitorRepository;
import com.ymatou.datamonitor.dao.mapper.MonitorMapper;
import com.ymatou.datamonitor.model.RunStatusEnum;
import com.ymatou.datamonitor.model.StatusEnum;
import com.ymatou.datamonitor.model.pojo.Monitor;
import com.ymatou.datamonitor.model.vo.MonitorVo;
import com.ymatou.datamonitor.service.MonitorService;
import com.ymatou.datamonitor.service.SchedulerService;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ymatou.datamonitor.util.Constants.JOB_SPEC;

/**
 * 
 * @author qianmin 2016年8月18日 下午2:50:49
 *
 */
@Service
public class MonitorServiceImpl  extends BaseServiceImpl<Monitor> implements MonitorService{
    
    private static final Logger logger = LoggerFactory.getLogger(MonitorServiceImpl.class);
    
    private MonitorRepository monitorRepository;
    
    @Autowired
    private MonitorMapper monitorMapper;
    
    @Autowired
    private SchedulerService schedulerService;
    
    @Autowired
    private ExecLogService execLogService;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    public MonitorServiceImpl(MonitorRepository monitorRepository) {
        super(monitorRepository);
        this.monitorRepository = monitorRepository;
    }

    @Override
    @Transactional
    public void addMonitor(MonitorVo monitorVo) throws SchedulerException {
        //save monitor info 
        Monitor monitor = new Monitor();
        BeanUtils.copyProperties(monitorVo, monitor);
        monitor.setRunStatus(RunStatusEnum.RUNNING.name());
        save(monitor);
        
        //add quartz scheduler job
        schedulerService.addJob(ExecuteJob.class, getJobName(monitor), monitor.getCronExpression());
        
        //update nextFireTime
        monitor.setNextFireTime(schedulerService.getNextFireTime(JOB_SPEC + monitor.getId()));
        save(monitor);
        
        logger.info("Monitor added. {}", JSON.toJSONString(monitor));
    }

    @Override
    @Transactional
    public void modifyMonitor(MonitorVo monitorVo) throws SchedulerException {

        Monitor monitor = monitorRepository.findOne(monitorVo.getId());
        if(!monitorVo.getCronExpression().equals(monitor.getCronExpression())) {
            schedulerService.modifyScheduler(getJobName(monitorVo), monitorVo.getCronExpression());
            monitor.setCronExpression(monitorVo.getCronExpression());
            monitor.setNextFireTime(schedulerService.getNextFireTime(JOB_SPEC + monitor.getId()));
        }
        monitor.setName(monitorVo.getName());
        monitor.setDbSource(monitorVo.getDbSource());
        monitor.setSql(monitorVo.getSql());
        monitor.setEmails(monitorVo.getEmails());
        monitor.setPhones(monitorVo.getPhones());
        monitor.setEmailThreshold(monitorVo.getEmailThreshold());
        monitor.setPhoneThreshold(monitorVo.getPhoneThreshold());
        monitor.setNotifyTitle(monitorVo.getNotifyTitle());
        monitor.setRemark(monitorVo.getRemark());

        monitorRepository.save(monitor);
        
        logger.info("Monitor modified. {}", JSON.toJSONString(monitor));
    }

    @Override
    @Transactional
    public void removeMonitor(MonitorVo monitorVo) throws SchedulerException {
        schedulerService.removeScheduler(getJobName(monitorVo));
        
        Monitor monitor = monitorRepository.findOne(monitorVo.getId());
        monitor.setNextFireTime(null);
        monitor.setStatus(StatusEnum.DISABLE.name());
        monitorRepository.save(monitor);
        
        logger.info("Monitor removed. {}", JSON.toJSONString(monitor));
    }

    @Override
    @Transactional
    public void pauseMonitor(MonitorVo monitorVo) throws SchedulerException {
        schedulerService.pauseScheduler(getJobName(monitorVo));
        
        Monitor monitor = monitorRepository.findOne(monitorVo.getId());
        monitor.setRunStatus(RunStatusEnum.STOPPED.name());
        monitor.setNextFireTime(null);
        monitorRepository.save(monitor);
        
        logger.info("Monitor paused. {}", JSON.toJSONString(monitor));
    }

    @Override
    @Transactional
    public void resumeMonitor(MonitorVo monitorVo) throws SchedulerException {
        schedulerService.resumeScheduler(getJobName(monitorVo));
        
        Monitor monitor = monitorRepository.findOne(monitorVo.getId());
        monitor.setRunStatus(RunStatusEnum.RUNNING.name());
        monitor.setNextFireTime(schedulerService.getNextFireTime(JOB_SPEC + monitor.getId()));
        monitorRepository.save(monitor);
        
        logger.info("Monitor resumed. {}", JSON.toJSONString(monitor));
    }

    @Override
    public void runNow(MonitorVo monitor,Boolean isSystemRun) {

        Database db = DbUtil.getDb(monitor.getDbSource());

        //处理sql 等
        DataSourceEnum dataSourceEnum = DataSourceEnum.valueOf(monitor.getDbSource());

        List<Map<String, Object>> result = Lists.newArrayList();
        String sql = "";
        try {
            if(dataSourceEnum.getDbEnum() != DbEnum.mongodb){
                sql= monitor.getSql();
                String countSql = PagerUtils.count(sql,dataSourceEnum.getDbEnum().name());
                Long count = db.select(countSql).getAs(Long.class).toBlocking().single();

                if(count > 1000){
                    sql = PagerUtils.limit(sql,dataSourceEnum.getDbEnum().name(),0,1000);
                }

                //执行sql
                result = db.select(sql)
                        .get(new MapResultSet())
                        .toList().toBlocking().single();
            }else {
                throw new RuntimeException("暂不支持mongodb");
            }
        } catch (Exception e) {
            //出现异常 比如查询超时等 日志需要记录下来
            result.add(ImmutableMap.of("sql",sql,"error",getPrintStackTraceMessage(e)));
            monitor.setQueryError(true);
        }

        final List<Map<String, Object>> resultFinal = result;
        transactionTemplate.execute(status -> {
            //处理返回值
            execLogService.saveLogAndDecideNotity(monitor, resultFinal);
            Monitor m = MonitorVo.to(monitor);
            if (isSystemRun) {
                try {
                    m.setNextFireTime(schedulerService.getNextFireTime(JOB_SPEC + monitor.getId()));
                } catch (SchedulerException e) {
                    logger.info("error setNextFireTime", e);
                }
            }
            save(m);
            return null;
        });

    }

    private String getPrintStackTraceMessage(Exception e) {
        ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
        e.printStackTrace(new java.io.PrintWriter(buf, true));
        String str = buf.toString();
        try {
            buf.close();
        } catch (IOException e1) {
            //ignore
        }
        return str;
    }
    @Override
    public Page<MonitorVo> listMonitor(MonitorVo monitorVo, Pageable pageable) {
        Page<MonitorVo> monitorVos = monitorMapper.findByMonitorVo(monitorVo, pageable);

        return monitorVos;
    }
    
    private String getJobName(MonitorVo monitorVo){
        return JOB_SPEC + monitorVo.getId();
    }
    
    private String getJobName(Monitor monitor){
        return JOB_SPEC + monitor.getId();
    }
}
