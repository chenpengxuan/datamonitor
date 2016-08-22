/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.model.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ymatou.datamonitor.model.pojo.Monitor;
import com.ymatou.datamonitor.util.Converter;

/**
 * 
 * @author qianmin 2016年8月18日 下午2:26:25
 *
 */
public class MonitorVo {

    private Long id;
    private String name;
    private String dbSource;
    private String sql;
    private String cronExpression;
    private String scriptType;
    private String scriptCode;
    private String emails;
    private String phones;
    private Long emailThreshold;
    private Long phoneThreshold;
    private String remark;
    private String runStatus;
    private String notifyTitle;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String createUser;
    private String updateUser;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastFireTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nextFireTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDbSource() {
        return dbSource;
    }

    public void setDbSource(String dbSource) {
        this.dbSource = dbSource;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    public String getScriptCode() {
        return scriptCode;
    }

    public void setScriptCode(String scriptCode) {
        this.scriptCode = scriptCode;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public Long getEmailThreshold() {
        return emailThreshold;
    }

    public void setEmailThreshold(Long emailThreshold) {
        this.emailThreshold = emailThreshold;
    }

    public Long getPhoneThreshold() {
        return phoneThreshold;
    }

    public void setPhoneThreshold(Long phoneThreshold) {
        this.phoneThreshold = phoneThreshold;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    public String getNotifyTitle() {
        return notifyTitle;
    }

    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastFireTime() {
        return lastFireTime;
    }

    public void setLastFireTime(Date lastFireTime) {
        this.lastFireTime = lastFireTime;
    }

    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public static MonitorVo from(Monitor monitor){
        return Converter.convert(monitor,MonitorVo.class);
    }
    public static Monitor to(MonitorVo MonitorVo){
        return Converter.convert(MonitorVo,Monitor.class);
    }
}
