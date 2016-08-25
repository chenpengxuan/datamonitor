/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.contoller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ymatou.datamonitor.util.cron.CronTrigger;
import com.ymatou.datamonitor.util.cron.SimpleTriggerContext;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("")
public class CronController {

    static SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/getCron")
    @ResponseBody
    public String getCron(HttpServletRequest request) {
        StringBuffer sbResult = new StringBuffer();
        sbResult.append("[");
        CronTrigger trigger = new CronTrigger(request.getParameter("CronExpression"));
        SimpleTriggerContext context = new SimpleTriggerContext();

        Date dt = trigger.nextExecutionTime(context);
        context.update(dt, dt, dt);
        sbResult.append("\"").append(formatDt(dt)).append("\"");
        for (int i = 1; i < 5; i++) {
            dt = trigger.nextExecutionTime(context);
            context.update(dt, dt, dt);
            sbResult.append(", \"").append(formatDt(dt)).append("\"");
        }
        sbResult.append("]");
        return sbResult.toString();
    }

    public String formatDt(Date dt) {
        try {
            return sdt.format(dt);
        } catch (Exception ex) {
            return "";
        }
    }


}
