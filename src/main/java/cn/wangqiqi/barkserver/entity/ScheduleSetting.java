package cn.wangqiqi.barkserver.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
 
import java.util.Date;
 
@Data
@TableName("schedule_setting")
public class ScheduleSetting {
    /**
     * 任务ID
     */
    @TableId
    private Integer jobId;
    /**
     * bean名称
     */
    private String beanName;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 方法参数
     */
    private String methodParams;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 状态（1正常 0暂停）
     */
    private Integer jobStatus;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}