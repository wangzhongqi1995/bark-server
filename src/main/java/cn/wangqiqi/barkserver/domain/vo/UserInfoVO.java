package cn.wangqiqi.barkserver.domain.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserInfoVO {
    /**
     * 角色
     */
    private List<String> roles;
    /**
     * 用户名
     */
    private String username;
}
