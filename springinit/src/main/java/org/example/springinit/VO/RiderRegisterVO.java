package org.example.springinit.VO;

import lombok.Data;

/**
 * 骑手注册响应VO
 */
@Data
public class RiderRegisterVO {
    /**
     * 骑手ID
     */
    private Long id;
    /**
     * 骑手姓名
     */
    private String username;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 配送区域
     */
    private String deliveryArea;
    /**
     * 账号状态（0-待审核）
     */
    private Integer status;

}