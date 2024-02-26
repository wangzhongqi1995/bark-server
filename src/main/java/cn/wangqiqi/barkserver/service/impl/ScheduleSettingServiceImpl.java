package cn.wangqiqi.barkserver.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.wangqiqi.barkserver.config.schedule.CronTaskRegistrar;
import cn.wangqiqi.barkserver.config.schedule.SchedulingRunnable;
import cn.wangqiqi.barkserver.domain.core.PageQuery;
import cn.wangqiqi.barkserver.domain.core.TableDataInfo;
import cn.wangqiqi.barkserver.domain.dto.ScheduleSettingQueryParamDTO;
import cn.wangqiqi.barkserver.entity.BarkInfo;
import cn.wangqiqi.barkserver.entity.ScheduleSetting;
import cn.wangqiqi.barkserver.enums.ScheduleSettingJobStatusEnum;
import cn.wangqiqi.barkserver.mapper.ScheduleSettingMapper;
import cn.wangqiqi.barkserver.service.IScheduleSettingService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ScheduleSettingServiceImpl extends ServiceImpl<ScheduleSettingMapper, ScheduleSetting> implements IScheduleSettingService {

    @Resource
    private CronTaskRegistrar cronTaskRegistrar;

    @Transactional
    @Override
    public void createScheduleSetting(ScheduleSetting scheduleSetting) {
        scheduleSetting.setCreateTime(new Date());
        scheduleSetting.setUpdateTime(new Date());

        boolean flag = save(scheduleSetting);
        if (flag) {
            // 添加成功,并且状态是1，直接放入任务器
            if (ObjectUtil.equal(scheduleSetting.getJobStatus(), ScheduleSettingJobStatusEnum.ENABLE.getStatus())) {
                SchedulingRunnable task = new SchedulingRunnable(scheduleSetting.getBeanName(), scheduleSetting.getMethodName(), scheduleSetting.getMethodParams());
                cronTaskRegistrar.addCronTask(task, scheduleSetting.getCronExpression());
            }
        }
    }

    @Transactional
    @Override
    public void updateScheduleSetting(ScheduleSetting scheduleSetting) {
        scheduleSetting.setCreateTime(new Date());
        scheduleSetting.setUpdateTime(new Date());

        ScheduleSetting originScheduleSetting = getById(scheduleSetting.getJobId());

        boolean flag = updateById(scheduleSetting);
        if (flag) {
            // 修改成功,则先删除任务器中的任务,并重新添加
            SchedulingRunnable originTask = new SchedulingRunnable(originScheduleSetting.getBeanName(), originScheduleSetting.getMethodName(), originScheduleSetting.getMethodParams());
            cronTaskRegistrar.removeCronTask(originTask);
            if (ObjectUtil.equal(scheduleSetting.getJobStatus(), ScheduleSettingJobStatusEnum.ENABLE.getStatus())) {
                SchedulingRunnable task = new SchedulingRunnable(scheduleSetting.getBeanName(), scheduleSetting.getMethodName(), scheduleSetting.getMethodParams());
                cronTaskRegistrar.addCronTask(task, scheduleSetting.getCronExpression());
            }
        }
    }

    @Override
    public void delScheduleSetting(Integer jobId) {
        // 先查询要删除的任务信息
        ScheduleSetting existedSysJob = getById(jobId);
        // 删除
        boolean flag = removeById(jobId);
        if (flag) {
            // 删除成功时要清除定时任务器中的对应任务
            SchedulingRunnable task = new SchedulingRunnable(existedSysJob.getBeanName(), existedSysJob.getMethodName(), existedSysJob.getMethodParams());
            cronTaskRegistrar.removeCronTask(task);
        }
    }

    @Override
    @Transactional
    public void changeScheduleSettingJobStatus(Integer jobId, Integer jobStatus) {

        ScheduleSetting existedSysJob = getById(jobId);
        existedSysJob.setJobStatus(jobStatus);
        boolean flag = updateById(existedSysJob);

        if (flag) {

            // 如果状态是1则添加任务
            if (ObjectUtil.equal(existedSysJob.getJobStatus(), ScheduleSettingJobStatusEnum.ENABLE.getStatus())) {
                SchedulingRunnable task = new SchedulingRunnable(existedSysJob.getBeanName(), existedSysJob.getMethodName(), existedSysJob.getMethodParams());
                cronTaskRegistrar.addCronTask(task, existedSysJob.getCronExpression());
            } else {
                // 否则清除任务
                SchedulingRunnable task = new SchedulingRunnable(existedSysJob.getBeanName(), existedSysJob.getMethodName(), existedSysJob.getMethodParams());
                cronTaskRegistrar.removeCronTask(task);
            }
        }
    }

    @Override
    public TableDataInfo<ScheduleSetting> page(PageQuery pageQuery, ScheduleSettingQueryParamDTO scheduleSettingQueryParamDTO) {
        return TableDataInfo.build(page(pageQuery.build(), Wrappers.<ScheduleSetting>lambdaQuery()
                .like(StrUtil.isNotBlank(scheduleSettingQueryParamDTO.getBeanName()), ScheduleSetting::getBeanName, scheduleSettingQueryParamDTO.getBeanName())
        ));
    }
}
