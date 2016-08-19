/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */
package com.ymatou.datamonitor.service;

import com.ymatou.datamonitor.model.pojo.ExecLog;
import com.ymatou.datamonitor.model.pojo.User;
import com.ymatou.datamonitor.model.vo.MonitorVo;
import org.quartz.Job;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.Map;


public interface ExecLogService extends BaseService<ExecLog>  {

    void saveLogAndDecideNotity(MonitorVo monitorVo, List<Map<String,Object>> result);

}
