/*
 *
 *  (C) Copyright 2017 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.model.vo;

/**
 * @author luoshiqian 2017/3/9 11:51
 */
public class ConsumerTopicPartition {
    private String topic;
    private int partition;
    private String status;
    private Partition start;
    private Partition end;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Partition getStart() {
        return start;
    }

    public void setStart(Partition start) {
        this.start = start;
    }

    public Partition getEnd() {
        if(end == null){
            return new Partition();
        }
        return end;
    }

    public void setEnd(Partition end) {
        this.end = end;
    }
}
