package org.example.springinit.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 骑手注册入参DTO
 */
@Data
public class RiderRegisterDTO {
    /**
     * 骑手姓名（非空）
     */
    @NotBlank(message = "骑手姓名不能为空")
    private String username;

    /**
     * 密码（6-18位字母+数字）
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message = "密码需为6-18位字母+数字组合")
    private String password;

    /**
     * 手机号（11位）
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;

    /**
     * 身份证号（18位）
     */
    @NotBlank(message = "身份证号不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", message = "身份证号格式错误")
    private String idCard;

    /**
     * 配送区域（非空）
     */
    @NotBlank(message = "配送区域不能为空")
    private String deliveryArea;

    /**
     * 身份证正面照URL（可选）
     */
    private String idCardFront;

    /**
     * 身份证反面照URL（可选）
     */
    private String idCardBack;

    /**
     * 健康证URL（可选）
     */
    private String healthCertificate;
}