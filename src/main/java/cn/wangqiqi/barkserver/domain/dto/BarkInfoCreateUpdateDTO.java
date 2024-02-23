package cn.wangqiqi.barkserver.domain.dto;

import lombok.Data;

@Data
public class BarkInfoCreateUpdateDTO {
    private Integer id;
    private String url;
    private String remark;
}
