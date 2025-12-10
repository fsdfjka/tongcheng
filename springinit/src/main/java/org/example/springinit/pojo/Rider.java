package org.example.springinit.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 骑手实体类
 */
@Data
public class Rider {
    /**
     * 骑手主键ID
     */
    private Long id;
    /**
     * 骑手姓名
     */
    private String username;
    /**
     * 加密密码
     */
    private String password;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 配送区域
     */
    private String deliveryArea;
    /**
     * 身份证正面照URL
     */
    private String idCardFront;
    /**
     * 身份证反面照URL
     */
    private String idCardBack;
    /**
     * 健康证URL
     */
    private String healthCertificate;
    /**
     * 账号状态：0-待审核，1-正常，2-禁用
     */
    private Integer status;
}