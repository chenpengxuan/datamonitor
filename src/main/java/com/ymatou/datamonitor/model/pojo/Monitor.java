/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author qianmin 2016年8月18日 下午2:26:25
 *
 */
@Entity
@Table(name = "monitor")
public class Monitor extends Audit{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @Column(name = "name")
    protected String name;
    
    @Column(name = "db_source")
    protected String dbSource;
    
    @Column(name = "`sql`")
    protected String sql;
    
    @Column(name = "cron_expression")
    protected String cronExpression;
    
    @Column(name = "script_type")
    protected String scriptType;
    
    @Column(name = "script_code")
    protected String scriptCode;
    
    @Column(name = "emails")
    protected String emails;
    
    @Column(name = "phones")
    protected String phones;
    
    @Column(name = "email_threshold")
    protected Long emailThreshold;
    
    @Column(name = "phone_threshold")
    protected Long phoneThreshold;
    
    @Column(name = "remark")
    protected String remark;
    
    @Column(name = "run_status")
    protected String runStatus;
    
    @Column(name = "notify_title")
    protected String notifyTitle;
    
    @Column(name = "last_fire_time")
    private Date lastFireTime;
    
    @Column(name = "next_fire_time")
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
}
