/*
 *
 * (C) Copyright 2017 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.model.vo;

/**
 * @author luoshiqian 2017/3/9 11:47
 */
public class KafkaAlertEventVo {

    private String cluster;
    private String group;
    private String id;

    private ConsumerGroupStatus event;

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ConsumerGroupStatus getEvent() {
        return event;
    }

    public void setEvent(ConsumerGroupStatus event) {
        this.event = event;
    }
}
