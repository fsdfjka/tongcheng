package org.example.springinit.VO;

import lombok.Data;

/**
 * 平台管理员登录响应VO
 */
@Data
public class AdminLoginVO {
    /**
     * 管理员ID
     */
    private Long adminId;

    /**
     * 管理员姓名
     */
    private String username;

    /**
     * 登录令牌（JWT）
     */
    private String token;

    /**
     * 令牌有效期（秒，如7200=2小时）
     */
    private Long expireTime;

    /**
     * 角色标识（固定为3-管理员）
     */
    private Integer role;
}