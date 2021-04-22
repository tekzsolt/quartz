package com.practice.timer.timeservice;

import com.practice.timer.info.TimerInfo;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

public class SimplerTriggerListener implements TriggerListener {

    private final SchedulerService schedulerService;

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        final String timerId = trigger.getKey().getName();

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        TimerInfo info = (TimerInfo) jobDataMap.get(timerId);

        if(!info.isRunForever()) {
            int remainingFireCount = info.getRemainingFireCount();
            if (remainingFireCount == 0) {
                return;
            }
            info.setRemainingFireCount(remainingFireCount - 1);
        }

        schedulerService.updateTimer(timerId, info);
    }

    public SimplerTriggerListener(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public String getName() {
        return SimplerTriggerListener.class.getSimpleName();
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {

    }
}
