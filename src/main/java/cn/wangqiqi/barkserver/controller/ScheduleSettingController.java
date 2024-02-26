package cn.wangqiqi.barkserver.controller;

import cn.wangqiqi.barkserver.domain.core.PageQuery;
import cn.wangqiqi.barkserver.domain.core.R;
import cn.wangqiqi.barkserver.domain.core.TableDataInfo;
import cn.wangqiqi.barkserver.domain.dto.ScheduleSettingQueryParamDTO;
import cn.wangqiqi.barkserver.entity.BarkInfo;
import cn.wangqiqi.barkserver.entity.ScheduleSetting;
import cn.wangqiqi.barkserver.service.IScheduleSettingService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduleSetting")
public class ScheduleSettingController {
    @Resource
    private IScheduleSettingService scheduleSettingService;

    /**
     * 添加定时任务
     * @param sysJob 定时任务信息
     */
    @PostMapping("add")
    public R<Void> add(@RequestBody ScheduleSetting sysJob) {
        scheduleSettingService.createScheduleSetting(sysJob);
        return R.ok();
    }

    /**
     * 修改定时任务
     * @param sysJob 定时任务信息
     */
    @PostMapping("update")
    public R<Void> update(@RequestBody ScheduleSetting sysJob) {
        scheduleSettingService.updateScheduleSetting(sysJob);
        return R.ok();
    }

    /**
     * 删除任务
     * @param jobId 任务id
     */
    @DeleteMapping("del/{jobId}")
    public R<Void> del(@PathVariable("jobId") Integer jobId) {
        scheduleSettingService.delScheduleSetting(jobId);

        return R.ok();
    }

    /**
     * 更新任务状态
     * @param jobId 任务id
     * @param jobStatus 任务状态
     */
    @GetMapping("changesStatus/{jobId}/{jobStatus}")
    public R<Void> changesStatus(@PathVariable("jobId") Integer jobId, @PathVariable("jobStatus") Integer jobStatus) {
        scheduleSettingService.changeScheduleSettingJobStatus(jobId, jobStatus);
        return R.ok();
    }

    /**
     * 分页查询
     * @param pageQuery 分页参数
     * @param scheduleSettingQueryParamDTO 查询参数
     */
    @GetMapping("page")
    public TableDataInfo<ScheduleSetting> page(PageQuery pageQuery, ScheduleSettingQueryParamDTO scheduleSettingQueryParamDTO) {
        return scheduleSettingService.page(pageQuery, scheduleSettingQueryParamDTO);
    }

}
