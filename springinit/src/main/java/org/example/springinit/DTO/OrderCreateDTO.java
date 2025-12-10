package org.example.springinit.DTO;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * 商户创建订单入参DTO
 */
@Data
public class OrderCreateDTO {
    /**
     * 寄件人ID（商户用户ID，从JWT令牌中解析，前端无需传递）
     */
    private Long senderId;

    /**
     * 寄件人姓名（商户名称，非空）
     */
    @NotBlank(message = "寄件人姓名不能为空")
    private String senderName;

    /**
     * 寄件人手机号（11位格式）
     */
    @NotBlank(message = "寄件人手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String senderPhone;

    /**
     * 寄件地址（商户地址，非空）
     */
    @NotBlank(message = "寄件地址不能为空")
    private String senderAddress;

    /**
     * 收件人姓名（非空）
     */
    @NotBlank(message = "收件人姓名不能为空")
    private String receiverName;

    /**
     * 收件人手机号（11位格式）
     */
    @NotBlank(message = "收件人手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String receiverPhone;

    /**
     * 收件地址（非空）
     */
    @NotBlank(message = "收件地址不能为空")
    private String receiverAddress;

    /**
     * 订单类型：1-文件速递，2-物品配送，3-生鲜外卖，4-代买（必填，范围1-4）
     */
    @NotNull(message = "订单类型不能为空")
    @Range(min = 1, max = 4, message = "订单类型只能是1-4")
    private Integer orderType;

    /**
     * 物品描述（可选）
     */
    private String goodsDesc;

    /**
     * 订单金额（必填，大于0）
     */
    @NotNull(message = "订单金额不能为空")
    @Range(min = 0, message = "订单金额必须大于0")
    private BigDecimal amount;
}