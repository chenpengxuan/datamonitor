/*
 *
 * (C) Copyright 2017 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.model.vo;

import java.util.List;

/**
 * @author luoshiqian 2017/3/9 11:50
 */
public class ConsumerGroupStatus {

    private String severity;
    private String tier;
    private String cluster;
    private String group;
    private String start;
    private boolean complete;
    private List<ConsumerTopicPartition> partitions;

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public List<ConsumerTopicPartition> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<ConsumerTopicPartition> partitions) {
        this.partitions = partitions;
    }
}
