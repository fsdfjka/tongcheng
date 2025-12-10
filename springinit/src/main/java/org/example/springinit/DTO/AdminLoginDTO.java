package org.example.springinit.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 平台管理员登录入参DTO
 */
@Data
public class AdminLoginDTO {
    /**
     * 管理员手机号/用户名（支持两种方式登录）
     */
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}