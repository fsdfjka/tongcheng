package org.example.springinit.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 注册入参校验DTO
 */
@Data
public class UserRegisterDTO {
    /**
     * 用户名（不能为空）
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码（6-18位，字母+数字）
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message = "密码需为6-18位字母+数字组合")
    private String password;

    /**
     * 手机号（11位手机号格式）
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;

    /**
     * 角色：1-个人，2-商户
     */
    @NotBlank(message = "角色不能为空")
    @Pattern(regexp = "^[12]$", message = "角色只能是1（个人）或2（商户）")
    private String role;

    /**
     * 常用地址（可选）
     */
    private String address;
}