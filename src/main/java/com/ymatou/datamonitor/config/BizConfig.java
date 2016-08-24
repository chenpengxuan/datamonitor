/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="biz")
public class BizConfig {

    private String ldapUrl;
    
    private String smsUrl;
    
    private String domain;
    
    private String env;
    
    private boolean phoneMonitorOn;

    public String getLdapUrl() {
        return ldapUrl;
    }

    public void setLdapUrl(String ldapUrl) {
        this.ldapUrl = ldapUrl;
    }

    public String getSmsUrl() {
        return smsUrl;
    }

    public void setSmsUrl(String smsUrl) {
        this.smsUrl = smsUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public boolean isPhoneMonitorOn() {
        return phoneMonitorOn;
    }

    public void setPhoneMonitorOn(boolean phoneMonitorOn) {
        this.phoneMonitorOn = phoneMonitorOn;
    }
}
