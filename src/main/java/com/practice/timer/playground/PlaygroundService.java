package com.practice.timer.playground;

import com.practice.timer.info.TimerInfo;
import com.practice.timer.jobs.HelloWorldJob;
import com.practice.timer.timeservice.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaygroundService {

    private final SchedulerService scheduler;

    @Autowired
    public PlaygroundService(SchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    public void runHellWorldJob() {
        TimerInfo info = new TimerInfo();
        info.setTotalFireCount(5);
        info.setRemainingFireCount(info.getTotalFireCount());
        info.setRunForever(false);
        info.setRepeatIntervalMs(2000);
        info.setInitialOffsetMs(1000);
        info.setCallbackData("My callback data");

        scheduler.schedule(HelloWorldJob.class, info);
    }

    public List<TimerInfo> getAllRunningTimers() {
        return scheduler.getAllRunningTimers();
    }

    public TimerInfo getRunningTimer(String timerId) {
        return scheduler.getRunningTimer(timerId);
    }

    public Boolean deleteTimer(final String timerId) {
        return scheduler.deleteTimer(timerId);
    }
}
