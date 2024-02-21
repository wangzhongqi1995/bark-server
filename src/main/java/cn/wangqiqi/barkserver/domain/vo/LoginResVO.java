package cn.wangqiqi.barkserver.domain.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResVO {
    private String token;
}
