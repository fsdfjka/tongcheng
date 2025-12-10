package org.example.springinit.pojo;

import lombok.Data;
import java.util.Date;

/**
 * 用户实体类（个人/商户）
 */
@Data
public class User {
    /**
     * 用户ID（自增）
     */
    private Long id;
    /**
     * 用户名（昵称）
     */
    private String username;
    /**
     * 加密后的密码
     */
    private String password;
    /**
     * 手机号（唯一）
     */
    private String phone;
    /**
     * 角色：1-个人寄件人，2-商户
     */
    private Integer role;
    /**
     * 常用地址（可选）
     */
    private String address;
    /**
     * 创建时间
     */
    private Date createTime;
    private Integer status;
}