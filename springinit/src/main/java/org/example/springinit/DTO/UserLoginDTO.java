package org.example.springinit.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 登录入参校验DTO
 */
@Data
public class UserLoginDTO {
    /**
     * 手机号（11位格式校验）
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;

    /**
     * 密码（6-18位字母+数字）
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message = "密码需为6-18位字母+数字组合")
    private String password;
}