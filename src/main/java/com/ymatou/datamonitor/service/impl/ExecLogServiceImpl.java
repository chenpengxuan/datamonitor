/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ymatou.datamonitor.model.pojo.User;
import com.ymatou.datamonitor.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymatou.datamonitor.dao.jpa.ExecLogRepository;
import com.ymatou.datamonitor.model.pojo.ExecLog;
import com.ymatou.datamonitor.model.vo.MonitorVo;
import com.ymatou.datamonitor.service.ExecLogService;

/**
 * @author luoshiqian 2016/8/18 18:20
 */
@Service
public class ExecLogServiceImpl extends BaseServiceImpl<ExecLog> implements ExecLogService {


    private ExecLogRepository repository;

    @Autowired
    public ExecLogServiceImpl(ExecLogRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public void saveLogAndDecideNotity(MonitorVo monitor, List<Map<String, Object>> result) {
        ExecLog execLog = new ExecLog();

        execLog.setExecTime(new Date());
        execLog.setMonitorId(monitor.getId());

        User user = CurrentUserUtil.getCurrentUser();
        if (null != user) {
            execLog.setExecUser(user.getUsername());
        } else {
            execLog.setExecUser("system");
        }
        execLog.setResult(JSON.toJSONString(result));

        Long resultCount = 0L;
        if(result.size() == 1 && result.get(0).size() == 1){ //返回值 一行 一列
            Map<String,Object> first = result.get(0);
            for (String key: first.keySet()){
                resultCount = Long.valueOf((String) first.get(key));
            }
        }else {
            resultCount = Long.valueOf(result.size());
        }
        execLog.setResultCount(resultCount);
        repository.save(execLog);

        // TODO 处理邮件 或短信
        if(resultCount > monitor.getEmailThreshold()){
            //TODO 处理邮件
        }

        if(resultCount > monitor.getPhoneThreshold()){
            //TODO 处理短信
        }

    }
}
