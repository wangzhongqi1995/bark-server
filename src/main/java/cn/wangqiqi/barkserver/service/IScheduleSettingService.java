package cn.wangqiqi.barkserver.service;

import cn.wangqiqi.barkserver.domain.core.PageQuery;
import cn.wangqiqi.barkserver.domain.core.TableDataInfo;
import cn.wangqiqi.barkserver.domain.dto.ScheduleSettingQueryParamDTO;
import cn.wangqiqi.barkserver.entity.ScheduleSetting;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IScheduleSettingService extends IService<ScheduleSetting> {
    void createScheduleSetting(ScheduleSetting scheduleSetting);

    void updateScheduleSetting(ScheduleSetting scheduleSetting);

    void delScheduleSetting(Integer jobId);

    void changeScheduleSettingJobStatus(Integer jobId, Integer jobStatus);

    TableDataInfo<ScheduleSetting> page(PageQuery pageQuery, ScheduleSettingQueryParamDTO scheduleSettingQueryParamDTO);
}
