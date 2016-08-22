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

import com.alibaba.fastjson.JSON;
import com.ymatou.datamonitor.model.pojo.User;
import com.ymatou.datamonitor.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ymatou.datamonitor.dao.jpa.ExecLogRepository;
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
    private IntegrationService integrationService;

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

        //处理邮件 或短信
        if(null != monitor.getEmailThreshold() && resultCount > monitor.getEmailThreshold()){
            String html = generateHtml(monitor, result);
            integrationService.sendHtmlEmail(monitor.getEmails(), monitor.getNotifyTitle(), html);
        }

        if(null != monitor.getPhoneThreshold() && resultCount > monitor.getPhoneThreshold()){
            integrationService.sendMessage(monitor.getPhones(), 
                    String.format("Monitor[%s]Threshold[%s]CurrentCount[%s]", 
                            monitor.getName(), monitor.getPhoneThreshold(),resultCount));
        }
    }
    
    private String generateHtml(MonitorVo monitor, List<Map<String, Object>> result){
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html><head>")
          .append("<meta charset='utf-8'><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>")
          .append("</head><body><h3>")
          .append(String.format("Monitor[%s] Threshold[%s] CurrentCount[%s] ResultSet: ", 
                  monitor.getName(), monitor.getEmailThreshold(), result.size()))
          .append("</h3><table border='1'><tr>");
        for(Entry<String, Object> entry : result.get(0).entrySet()){
            sb.append("<td>").append(entry.getKey()).append("</td>");
        }
        sb.append("</tr>");
        
        for(int index = 1; index < result.get(0).size(); index++){
            sb.append("<tr>");
            for(Entry<String, Object> entry : result.get(index).entrySet()){
                if(entry.getValue() instanceof Date){
                    sb.append("<td>")
                      .append(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format((Date) entry.getValue()))
                      .append("</td>");
                }else{
                    sb.append("<td>").append(entry.getValue()).append("</td>");
                }
            }
            sb.append("</tr>");
        }
        sb.append("</table></body></html>");
        
        return sb.toString();
    }

    @Override
    public Page<ExecLogVo> listExecLogVo(ExecLogVo monitorVo, Pageable pageable) {
        return null;
    }
}
