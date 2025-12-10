package org.example.springinit.VO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户创建订单响应VO
 */
@Data
public class OrderCreateVO {
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单状态（0-待接单）
     */
    private Integer status;
    /**
     * 订单金额
     */
    private BigDecimal amount;
    /**
     * 创建时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String createTime;
}