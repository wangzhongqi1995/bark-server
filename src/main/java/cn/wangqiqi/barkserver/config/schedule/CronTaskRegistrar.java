package cn.wangqiqi.barkserver.config.schedule;

import cn.hutool.extra.spring.SpringUtil;
import cn.wangqiqi.barkserver.config.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CronTaskRegistrar implements DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(CronTaskRegistrar.class);

    private final Map<Runnable, ScheduledTask> scheduledTasks = new ConcurrentHashMap<>(16);

    @Autowired
    private TaskScheduler taskScheduler;

    public TaskScheduler getScheduler() {
        return this.taskScheduler;
    }

    public void addCronTask(Runnable task, String cronExpression) {
        try {
            logger.info("添加task：{},执行频率为{}", task.getClass().getName(), cronExpression);
            addCronTask(new CronTask(task, cronExpression));

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("添加任务异常");
        }
    }

    public void addCronTask(CronTask cronTask) {
        if (cronTask != null) {
            Runnable task = cronTask.getRunnable();
            if (this.scheduledTasks.containsKey(task)) {
                removeCronTask(task);
            }

            this.scheduledTasks.put(task, scheduleCronTask(cronTask));
        }
    }

    public void removeCronTask(Runnable task) {
        logger.info("移除task：{}", task.getClass().getName());
        ScheduledTask scheduledTask = this.scheduledTasks.remove(task);
        if (scheduledTask != null)
            scheduledTask.cancel();
    }

    public ScheduledTask scheduleCronTask(CronTask cronTask) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());

        return scheduledTask;
    }


    @Override
    public void destroy() {
        for (ScheduledTask task : this.scheduledTasks.values()) {
            task.cancel();
        }

        this.scheduledTasks.clear();
    }
}