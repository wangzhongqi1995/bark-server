package cn.wangqiqi.barkserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class BarkInfo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String url;
    private String remark;
}
