/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymatou.datamonitor.config.BizConfig;
import com.ymatou.datamonitor.config.MailConfig;
import com.ymatou.datamonitor.service.IntegrationService;

import static com.ymatou.datamonitor.util.Constants.*;

import java.net.URLEncoder;

import com.ymatou.datamonitor.util.HttpClientUtil;
import com.ymatou.datamonitor.util.MailUtil;

/**
 * 
 * @author qianmin 2016年8月19日 下午3:27:22
 *
 */
@Service
public class IntegrationServiceImpl implements IntegrationService{

    protected static final Logger logger = LoggerFactory.getLogger(IntegrationServiceImpl.class);

    @Autowired
    private MailConfig mailConfig;
    
    @Autowired
    private BizConfig bizConfig;

    @Autowired
    private MailUtil mailUtil;
    
    @SuppressWarnings("deprecation")
    @Override
    public boolean sendMessage(String phone, String content) {
        int statusCode = -1;
        try {
            String url = String.format(bizConfig.getSmsUrl(), bizConfig.getDomain(), phone, URLEncoder.encode(content));
            statusCode = HttpClientUtil.sendPost(url);
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_NO_CONTENT) {
                statusCode = HttpClientUtil.sendPost(url);
            }
        } catch (Exception e) {
            logger.error(String.format("Send Short Message Error.Phone:%s, Content:%s", phone, content), e);
        }
        return statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_NO_CONTENT;
    }

   
    @Override
    public boolean sendHtmlEmail(String mailTo, String title, String content) {
        StringBuilder sb = new StringBuilder().append("send to: ").append(mailTo)
                    .append("; title: ").append(title);//.append("; content: ").append(content);
        logger.info(sb.toString());

        if (StringUtils.isBlank(mailTo) || StringUtils.isBlank(content) || StringUtils.isBlank(title)) {
            return false;
        }

        String mailTitle = title.substring(0, Math.min(ALARM_MAIL_TITLE_LENGTH, title.length()));
        String mailFrom = mailConfig.getFromEmail();
        String[] mailToList = mailTo.split(";");
        try {
            mailUtil.sendHtmlMail(mailFrom, mailToList, mailTitle, content);
        } catch (Exception e) {
            logger.error("When send alarm mail,we can't send it", e);
            return false;
        }

        return true;
    }
}
