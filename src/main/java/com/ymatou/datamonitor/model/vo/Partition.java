/*
 *
 * (C) Copyright 2017 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.model.vo;

/**
 * @author luoshiqian 2017/3/9 11:52
 */
public class Partition {

    private long offset;
    private long timestamp;
    private long lag;
    private long max_offset;

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getLag() {
        return lag;
    }

    public void setLag(long lag) {
        this.lag = lag;
    }

    public long getMax_offset() {
        return max_offset;
    }

    public void setMax_offset(long max_offset) {
        this.max_offset = max_offset;
    }
}
