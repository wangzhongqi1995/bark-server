package cn.wangqiqi.barkserver.domain.dto;

import lombok.Data;

@Data
public class LoginDTO {
    /**
     * 密码
     */
    private String password;
    /**
     * 用户名
     */
    private String username;
    /**
     * 验证码
     */
    private String code;
}
