package cn.wangqiqi.barkserver.runner;

import cn.hutool.core.collection.CollUtil;
import cn.wangqiqi.barkserver.config.schedule.CronTaskRegistrar;
import cn.wangqiqi.barkserver.config.schedule.SchedulingRunnable;
import cn.wangqiqi.barkserver.entity.ScheduleSetting;
import cn.wangqiqi.barkserver.enums.ScheduleSettingJobStatusEnum;
import cn.wangqiqi.barkserver.mapper.ScheduleSettingMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleJobRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobRunner.class);

    @Resource
    private CronTaskRegistrar cronTaskRegistrar;

    @Resource
    private ScheduleSettingMapper scheduleSettingMapper;

    @Override
    public void run(String... args) {
        // 初始加载数据库里状态为正常的定时任务
        List<ScheduleSetting> jobList = scheduleSettingMapper.selectList(
                Wrappers.<ScheduleSetting>lambdaQuery().eq(ScheduleSetting::getJobStatus, ScheduleSettingJobStatusEnum.ENABLE.getStatus()));

        if (CollUtil.isNotEmpty(jobList)) {
            logger.info("启用的动态定时任务配置共{}个", jobList.size());
            for (ScheduleSetting job : jobList) {
                logger.info("开始注册实例{}，方法名称{}，参数为{}，频率为{}", job.getBeanName(), job.getMethodName(), job.getMethodParams(), job.getCronExpression());
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
                cronTaskRegistrar.addCronTask(task, job.getCronExpression());
            }
            logger.info("动态定时任务已加载完毕");
        }
    }
}