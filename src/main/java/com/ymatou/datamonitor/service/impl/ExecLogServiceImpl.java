/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.ymatou.datamonitor.model.pojo.User;
import com.ymatou.datamonitor.util.CurrentUserUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ymatou.datamonitor.config.BizConfig;
import com.ymatou.datamonitor.config.MailConfig;
import com.ymatou.datamonitor.dao.jpa.ExecLogRepository;
import com.ymatou.datamonitor.dao.mapper.ExecLogMapper;
import com.ymatou.datamonitor.model.pojo.ExecLog;
import com.ymatou.datamonitor.model.vo.ExecLogVo;
import com.ymatou.datamonitor.model.vo.MonitorVo;
import com.ymatou.datamonitor.service.ExecLogService;
import com.ymatou.datamonitor.service.IntegrationService;

/**
 * @author luoshiqian 2016/8/18 18:20
 */
@Service
public class ExecLogServiceImpl extends BaseServiceImpl<ExecLog> implements ExecLogService {


    private ExecLogRepository repository;
    
    @Autowired
    private ExecLogMapper execLogMapper;
    
    @Autowired
    private IntegrationService integrationService;
    
    @Autowired
    private MailConfig mailConfig;
    
    @Autowired
    private BizConfig bizConfig;

    @Autowired
    public ExecLogServiceImpl(ExecLogRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public void saveLogAndDecideNotity(MonitorVo monitor, List<Map<String, Object>> result, Set<String> keys) {
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
                resultCount = Long.valueOf(first.get(key).toString());
            }
        }else {
            resultCount = Long.valueOf(result.size());
        }
        execLog.setResultCount(resultCount);
        repository.save(execLog);

        //处理邮件 或短信
        if((null != monitor.getEmailThreshold() && resultCount > monitor.getEmailThreshold() 
                && mailConfig.isEmailMonitorOn()) || monitor.isQueryError()){
            String html = generateHtml(monitor, resultCount, result, keys);
            integrationService.sendHtmlEmail(monitor.getEmails(), monitor.getNotifyTitle(), html);
        }

        if(null != monitor.getPhoneThreshold() && resultCount > monitor.getPhoneThreshold() 
                && bizConfig.isPhoneMonitorOn() || monitor.isQueryError()){
            integrationService.sendMessage(monitor.getPhones(), 
                    String.format("Env[%s] Monitor[%s] ExecTime[%s] Threshold[%s] CurrentCount[%s]", 
                            bizConfig.getEnv(),monitor.getName(), new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()),
                            monitor.getPhoneThreshold(),resultCount));
        }
    }
    
    private String generateHtml(MonitorVo monitor, long resultCount, List<Map<String, Object>> result, Set<String> keys){
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html><head>")
          .append("<meta charset='utf-8'><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>")
          .append("</head><body><h3>")
          .append(String.format("Env[%s] Monitor[%s] ExecTime[%s] Threshold[%s] CurrentCount[%s] ResultSet: ", 
                  bizConfig.getEnv(), monitor.getName(), new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()), 
                  monitor.getEmailThreshold(), resultCount))
          .append("</h3><table border='1'><tr>");
        if(CollectionUtils.isNotEmpty(keys)){
            for (String key : keys) {
                sb.append("<td>").append(key).append("</td>");
            }
        }else {
            for (Entry<String, Object> entry : result.get(0).entrySet()) {
                sb.append("<td>").append(StringUtils.isBlank(entry.getKey()) ? "count" : entry.getKey()).append("</td>");
            }
        }
        sb.append("</tr>");

        Set<String> realKeys = CollectionUtils.isNotEmpty(keys) ? keys : result.get(0).keySet();
        for(int index = 0; index < result.size(); index++){
            sb.append("<tr>");
            Map<String, Object> temp = result.get(index);
            for(String key : realKeys){
                if(temp.get(key) instanceof Date){
                    sb.append("<td>")
                            .append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format((Date) temp.get(key)))
                            .append("</td>");
                }else{
                    sb.append("<td>").append(temp.get(key)).append("</td>");
                }
            }
            sb.append("</tr>");
        }
        sb.append("</table><br/><br/><br/></body></html>");
        
        return sb.toString();
    }

    @Override
    public Page<ExecLogVo> listExecLogVo(ExecLogVo execLogVo, Pageable pageable) {
        
        Page<ExecLogVo> execLogs = execLogMapper.findByExecLogVo(execLogVo, pageable);
        
        return execLogs;
    }
}
