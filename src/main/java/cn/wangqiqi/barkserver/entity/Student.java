package cn.wangqiqi.barkserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
//表名与实体类名称不一致需要手动指定表名
@TableName
public class Student implements Serializable {
    private Integer id;
    private String name;
    private Integer sex;
    private Integer age;
    private String grade;
}
