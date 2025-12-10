package org.example.springinit.pojo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体类
 */
@Data
public class Order {
    /**
     * 订单主键ID
     */
    private Long id;
    /**
     * 订单编号（唯一，格式：商户ID+时间戳）
     */
    private String orderNo;
    /**
     * 寄件人ID（商户用户ID，关联t_user.id）
     */
    private Long senderId;
    /**
     * 骑手ID（接单后赋值，关联t_rider.id）
     */
    private Long riderId;
    /**
     * 寄件人姓名（商户名称）
     */
    private String senderName;
    /**
     * 寄件人手机号
     */
    private String senderPhone;
    /**
     * 寄件地址（商户地址）
     */
    private String senderAddress;
    /**
     * 收件人姓名
     */
    private String receiverName;
    /**
     * 收件人手机号
     */
    private String receiverPhone;
    /**
     * 收件地址
     */
    private String receiverAddress;
    /**
     * 订单类型：1-文件速递，2-物品配送，3-生鲜外卖，4-代买
     */
    private Integer orderType;
    /**
     * 物品描述
     */
    private String goodsDesc;
    /**
     * 订单金额
     */
    private BigDecimal amount;
    /**
     * 订单状态：0-待接单，1-已接单，2-已取件，3-配送中，4-已签收，5-已取消
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 接单时间
     */
    private Date acceptTime;
    /**
     * 取件时间
     */
    private Date pickupTime;
    /**
     * 签收时间
     */
    private Date deliverTime;
}