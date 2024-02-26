package cn.wangqiqi.barkserver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ScheduleSettingJobStatusEnum {
    ENABLE(1),
    DISABLE(0),
    ;
    private Integer status;
}
