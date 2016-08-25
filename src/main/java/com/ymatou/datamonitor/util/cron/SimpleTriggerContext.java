/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.util.cron;

import java.util.Date;

/**
 * @author luoshiqian 2016/8/24 17:57
 */
public class SimpleTriggerContext {

    private volatile Date lastScheduledExecutionTime;

    private volatile Date lastActualExecutionTime;

    private volatile Date lastCompletionTime;


    /**
     * Update this holder's state with the latest time values.
     * @param lastScheduledExecutionTime last <i>scheduled</i> execution time
     * @param lastActualExecutionTime last <i>actual</i> execution time
     * @param lastCompletionTime last completion time
     */
    public void update(Date lastScheduledExecutionTime, Date lastActualExecutionTime, Date lastCompletionTime) {
        this.lastScheduledExecutionTime = lastScheduledExecutionTime;
        this.lastActualExecutionTime = lastActualExecutionTime;
        this.lastCompletionTime = lastCompletionTime;
    }


    public Date lastScheduledExecutionTime() {
        return this.lastScheduledExecutionTime;
    }

    public Date lastActualExecutionTime() {
        return this.lastActualExecutionTime;
    }

    public Date lastCompletionTime() {
        return this.lastCompletionTime;
    }
}
