/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.util.cron;

import org.springframework.scheduling.support.CronSequenceGenerator;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author luoshiqian 2016/8/24 17:58
 */
public class CronTrigger {
    private final CronSequenceGenerator sequenceGenerator;


    /**
     * Build a {@link CronTrigger} from the pattern provided in the default time zone.
     * @param cronExpression a space-separated list of time fields,
     * following cron expression conventions
     */
    public CronTrigger(String cronExpression) {
        this(cronExpression, TimeZone.getDefault());
    }

    /**
     * Build a {@link CronTrigger} from the pattern provided.
     * @param cronExpression a space-separated list of time fields,
     * following cron expression conventions
     * @param timeZone a time zone in which the trigger times will be generated
     */
    public CronTrigger(String cronExpression, TimeZone timeZone) {
        this.sequenceGenerator = new CronSequenceGenerator(cronExpression, timeZone);
    }


    public Date nextExecutionTime(SimpleTriggerContext triggerContext) {
        Date date = triggerContext.lastCompletionTime();
        if (date != null) {
            Date scheduled = triggerContext.lastScheduledExecutionTime();
            if (scheduled != null && date.before(scheduled)) {
                // Previous task apparently executed too early...
                // Let's simply use the last calculated execution time then,
                // in order to prevent accidental re-fires in the same second.
                date = scheduled;
            }
        }
        else {
            date = new Date();
        }
        return this.sequenceGenerator.next(date);
    }


    @Override
    public boolean equals(Object obj) {
        return (this == obj || (obj instanceof CronTrigger &&
                this.sequenceGenerator.equals(((CronTrigger) obj).sequenceGenerator)));
    }

    @Override
    public int hashCode() {
        return this.sequenceGenerator.hashCode();
    }

    @Override
    public String toString() {
        return sequenceGenerator.toString();
    }
}
