/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ymatou.datamonitor.model.StatusEnum;
import com.ymatou.datamonitor.util.CurrentUserUtil;

/**
 *
 * @desc 审计类
 */
@MappedSuperclass
public class Audit {


    @Column(name = "create_time", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createTime;
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date updateTime;
    @Column(name = "create_user")
    protected String createUser;
    @Column(name = "update_user")
    protected String updateUser;

    @Column(name = "status")
    protected String status;

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

    @PreUpdate
    public void preUpdate() {
        setUpdateTime(new Date());
        User user = CurrentUserUtil.getCurrentUser();
        if (null != user) {
            setUpdateUser(user.getUsername());
        } else {
            setUpdateUser("system");
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @PrePersist
    public void PrePersist() {
        setCreateTime(new Date());
        setUpdateTime(new Date());
        if (StringUtils.isBlank(status)) {
            setStatus(StatusEnum.ENABLE.name());
        }
        User user = CurrentUserUtil.getCurrentUser();

        if (null != user) {
            setCreateUser(user.getUsername());
            setUpdateUser(user.getUsername());
        } else {
            setCreateUser("system");
            setUpdateUser("system");
        }
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
}
