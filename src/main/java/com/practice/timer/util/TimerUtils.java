package com.practice.timer.util;

import com.practice.timer.info.TimerInfo;
import org.quartz.*;

import java.util.Date;

public class TimerUtils {

    private TimerUtils() {}

    public static JobDetail buildJobDetail(final Class jobClass, final TimerInfo info) {
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), info);

        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap)
                .build();
    }

    public static Trigger buildTrigger(final Class jobClass, final TimerInfo info) {
        SimpleScheduleBuilder schedule = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(info.getRepeatIntervalMs());

        if (info.isRunForever()) {
            schedule = schedule.repeatForever();
        } else {
            schedule = schedule.withRepeatCount(info.getTotalFireCount() - 1);
        }

        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(schedule)
                .startAt(new Date(System.currentTimeMillis() + info.getInitialOffsetMs()))
                .build();
    }
}
