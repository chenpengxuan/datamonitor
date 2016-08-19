/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.service;

/**
 * 
 * @author qianmin 2016年8月19日 下午3:26:47
 *
 */
public interface IntegrationService {

    /**
     * 发送短信
     * 
     * @param phone
     * @param content
     * @return
     */
    boolean sendMessage(String phone, String content);

    /**
     * 发送邮件
     * 
     * @param mailTo
     * @param title
     * @param content
     * @return
     */
    boolean sendHtmlEmail(String mailTo, String title, String content);
}
