package org.example.springinit.VO;

import lombok.Data;

/**
 * 登录响应数据
 */
@Data
public class UserLoginVO {
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户名/昵称
     */
    private String username;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户角色：1-个人寄件人，2-商户
     */
    private Integer role;
    /**
     * JWT令牌（接口访问凭证）
     */
    private String token;
    /**
     * 令牌过期时间（单位：秒）
     */
    private Long expireTime;

}